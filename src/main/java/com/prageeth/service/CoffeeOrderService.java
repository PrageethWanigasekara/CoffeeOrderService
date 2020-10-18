package com.prageeth.service;

import com.prageeth.dto.CustomerOrderDTO;
import com.prageeth.dto.OrderDTO;
import com.prageeth.exception.AuthException;
import com.prageeth.exception.ResourceNotFoundException;

/**
 * Coffee Order Service Interface
 */
public interface CoffeeOrderService {

    CustomerOrderDTO getOrderById(Integer orderId, int userId) throws ResourceNotFoundException, AuthException;

    CustomerOrderDTO addNewOrder(OrderDTO orderDTO, int userId) throws ResourceNotFoundException;

    CustomerOrderDTO changeOrder(int orderId, CustomerOrderDTO customerOrderDTO, int userId) throws ResourceNotFoundException, AuthException;

    void cancelOrder(int orderId, int userId) throws ResourceNotFoundException, AuthException;

}
