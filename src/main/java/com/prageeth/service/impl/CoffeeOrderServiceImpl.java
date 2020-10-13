package com.prageeth.service.impl;

import com.prageeth.dto.CustomerOrderDTO;
import com.prageeth.dto.OrderDTO;
import com.prageeth.dto.QueueDetailDTO;
import com.prageeth.entity.CoffeeOrder;
import com.prageeth.entity.CustomerQueueDetail;
import com.prageeth.entity.MasterQueueData;
import com.prageeth.exception.ResourceNotFoundException;
import com.prageeth.repository.CoffeeOrderRepository;
import com.prageeth.repository.CustomerQueueDetailRepository;
import com.prageeth.repository.MasterQueueDataRepository;
import com.prageeth.service.CoffeeOrderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CoffeeOrderServiceImpl implements CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    @Autowired
    private CustomerQueueDetailRepository customerQueueDetailRepository;

    @Autowired
    private MasterQueueDataRepository masterQueueDataRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public CustomerOrderDTO getOrderById(int orderId) throws ResourceNotFoundException {
        Optional<CoffeeOrder> result = Optional.ofNullable(coffeeOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("orderId : " + orderId)));
        CustomerOrderDTO response = modelMapper.map(result.get(), CustomerOrderDTO.class);
        response.setQueueDetailDTO(modelMapper.map(result.get().getCustomerQueue(), QueueDetailDTO.class));
        logger.info("Order found - orderId: {}", orderId);
        return response;
    }

    @Override
    public CustomerOrderDTO addNewOrder(OrderDTO orderDTO) throws ResourceNotFoundException {
        CoffeeOrder result = save(orderDTO, null);
        CustomerOrderDTO response = mapResponse(result);
        logger.info("Order successfully created - orderId: {}", result.getOrderId());
        return response;
    }

    @Override
    public CustomerOrderDTO changeOrder(int orderId, CustomerOrderDTO orderDTO) throws ResourceNotFoundException {
        Optional<CoffeeOrder> coffeeOrder = Optional.ofNullable(coffeeOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("orderId : " + orderId)));
        CoffeeOrder result = save(orderDTO, coffeeOrder.get().getCustomerQueue());
        CustomerOrderDTO response = mapResponse(result);
        logger.info("Order successfully updated - orderId: {}", result.getOrderId());
        return response;
    }

    @Override
    public void cancelOrder(int orderId) throws ResourceNotFoundException {
        Optional<CoffeeOrder> response = Optional.ofNullable(coffeeOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("orderId : " + orderId)));
        coffeeOrderRepository.deleteById(orderId);
        logger.info("Order successfully canceled - orderId: {}", orderId);
        updateQueuePosition(response.get());
        logger.info("Queues successfully updated");
    }

    @Transactional
    private CoffeeOrder save(OrderDTO orderDTO, CustomerQueueDetail customerQueueDetail) throws ResourceNotFoundException {
        CoffeeOrder order = modelMapper.map(orderDTO, CoffeeOrder.class);
        if (customerQueueDetail != null) {
            order.setCustomerQueue(customerQueueDetail);
        } else {
            order.setCustomerQueue(calculateQueuePosition(order.getShopId()));
        }
        return coffeeOrderRepository.save(order);
    }

    private CustomerQueueDetail calculateQueuePosition(int shopId) throws ResourceNotFoundException {
        logger.info("Starting calculating position");
        MasterQueueData masterQueue = findMasterQueueData(shopId);
        List<CustomerQueueDetail> queueList = customerQueueDetailRepository.findByShopId(shopId);

        HashMap<Integer, Integer> positionMap = new HashMap<>();
        CustomerQueueDetail customerQueueDetail = new CustomerQueueDetail();

        addToQueuePositionMap(queueList, positionMap);
        findQueue(customerQueueDetail, positionMap, masterQueue);
        customerQueueDetail.setShopId(shopId);

        logger.debug("Start saving: {}", customerQueueDetail);
        return customerQueueDetail;
    }

    private MasterQueueData findMasterQueueData(int shopId) throws ResourceNotFoundException {
        MasterQueueData masterQueue = masterQueueDataRepository.findByShopId(shopId);
        if (masterQueue == null) {
            logger.error("No master queue data exist");
            throw new ResourceNotFoundException("shopId : " + shopId);
        }
        return masterQueue;
    }

    private void addToQueuePositionMap(List<CustomerQueueDetail> queueList, HashMap<Integer, Integer> positionMap) {
        queueList.forEach(queue -> {
            if (!positionMap.containsKey(queue.getQueueId()) || (positionMap.containsKey(queue.getQueueId()) && queue.getPosition() > positionMap.get(queue.getQueueId()))) {
                positionMap.put(queue.getQueueId(), queue.getPosition());
            }
        });
        logger.info("Position map: {}", positionMap);
    }

    private void findQueue(CustomerQueueDetail customerQueueDetail, HashMap<Integer, Integer> positionMap, MasterQueueData masterQueue) {
        if (masterQueue.getNumOfQueues() == positionMap.keySet().size()) {
            findOptimumQueue(customerQueueDetail, positionMap);
        } else {
            findEmptyQueue(customerQueueDetail, positionMap, masterQueue);
        }
    }


    private void findOptimumQueue(CustomerQueueDetail customerQueueDetail, HashMap<Integer, Integer> positionMap) {
        logger.info("No empty queue found, Finding queue");
        int minValue = 0;
        int key = 0;

        for (Map.Entry<Integer, Integer> item : positionMap.entrySet()) {

            if (minValue == 0) {
                minValue = item.getValue();
                key = item.getKey();
            }
            if (minValue > item.getValue()) {
                minValue = item.getValue();
                key = item.getKey();
            }
        }
        customerQueueDetail.setQueueId(key);
        customerQueueDetail.setPosition(minValue + 1);
        customerQueueDetail.setWaitingMinutes((minValue + 1)*0.5);
    }

    private void findEmptyQueue(CustomerQueueDetail customerQueueDetail, HashMap<Integer, Integer> positionMap, MasterQueueData masterQueue) {
        logger.info("Finding empty queue");
        int queue = 0;
        for (int i = 1; i <= masterQueue.getNumOfQueues(); i++) {
            if (!positionMap.containsKey(i)) {
                queue = i;
                break;
            }
        }
        customerQueueDetail.setQueueId(queue);
        customerQueueDetail.setPosition(1);
        customerQueueDetail.setWaitingMinutes(1*0.5);
    }

    private void updateQueuePosition(CoffeeOrder coffeeOrder) {
        CustomerQueueDetail customerQueue = coffeeOrder.getCustomerQueue();
        List<CustomerQueueDetail> queueList = customerQueueDetailRepository.findByShopIdAndQueueId(customerQueue.getShopId(), customerQueue.getQueueId());
        queueList.forEach(queue -> {
            int position = queue.getPosition();
            if (position > customerQueue.getPosition()) {
                queue.setPosition(position - 1);
                queue.setWaitingMinutes((position - 1)*0.5);
                customerQueueDetailRepository.save(queue);
            }
        });
    }

    private CustomerOrderDTO mapResponse(CoffeeOrder result) {
        CustomerOrderDTO response = modelMapper.map(result, CustomerOrderDTO.class);
        response.setQueueDetailDTO(modelMapper.map(result.getCustomerQueue(), QueueDetailDTO.class));
        return response;
    }
}
