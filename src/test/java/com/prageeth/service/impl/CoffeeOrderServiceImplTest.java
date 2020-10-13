package com.prageeth.service.impl;

import com.prageeth.dto.CustomerOrderDTO;
import com.prageeth.dto.OrderDTO;
import com.prageeth.entity.CoffeeOrder;
import com.prageeth.entity.CustomerQueueDetail;
import com.prageeth.entity.MasterQueueData;
import com.prageeth.exception.ResourceNotFoundException;
import com.prageeth.repository.CoffeeOrderRepository;
import com.prageeth.repository.CustomerQueueDetailRepository;
import com.prageeth.repository.MasterQueueDataRepository;
import com.prageeth.service.CoffeeOrderService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class CoffeeOrderServiceImplTest {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @MockBean
    private CustomerQueueDetailRepository customerQueueDetailRepository;

    @MockBean
    private CoffeeOrderRepository coffeeOrderRepository;

    @MockBean
    private MasterQueueDataRepository masterQueueDataRepository;

    @TestConfiguration
    static class CoffeeOrderServiceImplTestContextConfiguration {

        @Bean
        public CoffeeOrderService coffeeOrderService() {
            return new CoffeeOrderServiceImpl();
        }
    }


    @BeforeEach
    void setUp() {
        CustomerQueueDetail customerQueueDetail = new CustomerQueueDetail();
        customerQueueDetail.setQueueId(1);
        customerQueueDetail.setShopId(10);
        customerQueueDetail.setPosition(1);
        List<CustomerQueueDetail> list = new ArrayList<>();
        list.add(customerQueueDetail);

        CoffeeOrder coffeeOrder;
        coffeeOrder = new CoffeeOrder();
        coffeeOrder.setOrderId(100);
        coffeeOrder.setShopId(10);
        coffeeOrder.setCustomerQueue(customerQueueDetail);

        MasterQueueData masterQueueData = new MasterQueueData();
        masterQueueData.setNumOfQueues(1);
        masterQueueData.setShopId(10);

        Mockito.when(coffeeOrderRepository.save(coffeeOrder)).thenReturn(coffeeOrder);
        Mockito.when(masterQueueDataRepository.findByShopId(10)).thenReturn(null);
        Mockito.when(customerQueueDetailRepository.findByShopId(10)).thenReturn(list);
        Mockito.when(coffeeOrderRepository.findById(100)).thenReturn(java.util.Optional.ofNullable(coffeeOrder));

    }

    @Test
    void getOrderByIdPassTest() {
        CustomerOrderDTO found = null;
        try {
            found = coffeeOrderService.getOrderById(100);
        } catch (ResourceNotFoundException exception) {
            exception.printStackTrace();
        }
        assertThat(found.getOrderId()).isEqualTo(100);
    }

    @Test
    void getOrderByIdFailTest() {
        Mockito.when(coffeeOrderRepository.findById(10)).thenReturn(java.util.Optional.ofNullable(null));
        CustomerOrderDTO found = null;
        boolean status =false;
        try {
            found = coffeeOrderService.getOrderById(10);
        } catch (ResourceNotFoundException exception) {
            status = true;
        }
        Assert.assertTrue(status);
    }

    @Test
    void addNewOrderFailTest() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setShopId(10);
        CustomerOrderDTO found = null;
        boolean status =false;
        try {
            coffeeOrderService.addNewOrder(orderDTO);
        } catch (ResourceNotFoundException exception) {
            status = true;
        }
        Assert.assertTrue(status);
    }

    @Test
    void changeOrderFailTest() {
        Mockito.when(coffeeOrderRepository.findById(10)).thenReturn(java.util.Optional.ofNullable(null));
        CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO();
        customerOrderDTO.setOrderId(10);
        customerOrderDTO.setShopId(10);
        CustomerOrderDTO found = null;
        boolean status =false;
        try {
            coffeeOrderService.changeOrder(10,customerOrderDTO);
        } catch (ResourceNotFoundException exception) {
            status = true;
        }
        Assert.assertTrue(status);
    }

    @Test
    void cancelOrderFailTest() {
        Mockito.when(coffeeOrderRepository.findById(10)).thenReturn(java.util.Optional.ofNullable(null));
        boolean status =false;
        try {
            coffeeOrderService.cancelOrder(10);
        } catch (ResourceNotFoundException exception) {
            status = true;
        }
        Assert.assertTrue(status);
    }
}