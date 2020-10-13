package com.prageeth.controller;

import com.prageeth.dto.CustomerOrderDTO;
import com.prageeth.dto.OrderDTO;
import com.prageeth.exception.ResourceNotFoundException;
import com.prageeth.service.CoffeeOrderService;
import com.prageeth.utility.EndPointNamingUtil;
import com.prageeth.utility.UrlNamingUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping(UrlNamingUtil.COFFEE_ORDER_URL)
public class CoffeeOrderController extends BaseController{

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @GetMapping(value = EndPointNamingUtil.ORDER_ID)
    public ResponseEntity<CustomerOrderDTO> getOrderById(@PathVariable Integer orderId, HttpServletRequest httpServletRequest) throws ResourceNotFoundException {
        checkAuthentication(httpServletRequest);
        return new ResponseEntity(coffeeOrderService.getOrderById(orderId),HttpStatus.OK);
    }
    @PostMapping(value = EndPointNamingUtil.ORDER)
    public ResponseEntity<CustomerOrderDTO> addNewOrder(@Valid @RequestBody OrderDTO request,HttpServletRequest httpServletRequest) throws ResourceNotFoundException {
        checkAuthentication(httpServletRequest);
        return new ResponseEntity(coffeeOrderService.addNewOrder(request),HttpStatus.OK);
    }

    @PutMapping(value = EndPointNamingUtil.ORDER_ID)
    public ResponseEntity<CustomerOrderDTO> changeOrder(@PathVariable Integer orderId,  @RequestBody CustomerOrderDTO request,HttpServletRequest httpServletRequest) throws ResourceNotFoundException {
        checkAuthentication(httpServletRequest);
        return new ResponseEntity(coffeeOrderService.changeOrder(orderId,request),HttpStatus.OK);
    }

    @DeleteMapping(value = EndPointNamingUtil.ORDER_ID)
    public ResponseEntity<String> cancelOrder(@PathVariable Integer orderId,HttpServletRequest httpServletRequest) throws ResourceNotFoundException {
        checkAuthentication(httpServletRequest);
        coffeeOrderService.cancelOrder(orderId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
