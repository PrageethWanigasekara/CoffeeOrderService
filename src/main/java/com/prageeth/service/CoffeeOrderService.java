package com.prageeth.service;

import com.prageeth.dto.CustomerOrderDTO;
import com.prageeth.dto.OrderDTO;
import com.prageeth.exception.ResourceNotFoundException;

/**
 * Coffee Order Service Interface
 */
public interface CoffeeOrderService {

    CustomerOrderDTO getOrderById(int orderId) throws ResourceNotFoundException;

    CustomerOrderDTO addNewOrder(OrderDTO orderDTO) throws ResourceNotFoundException;

    CustomerOrderDTO changeOrder(int orderId, CustomerOrderDTO customerOrderDTO) throws ResourceNotFoundException;

    void cancelOrder(int orderId) throws ResourceNotFoundException;

}
