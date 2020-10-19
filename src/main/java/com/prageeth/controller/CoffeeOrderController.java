package com.prageeth.controller;

import com.prageeth.dto.CustomerOrderDTO;
import com.prageeth.dto.OrderDTO;
import com.prageeth.exception.AuthException;
import com.prageeth.exception.JwtTokenException;
import com.prageeth.exception.BadRequestDataException;
import com.prageeth.service.CoffeeOrderService;
import com.prageeth.utility.EndPointNamingUtil;
import com.prageeth.utility.UrlNamingUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping(UrlNamingUtil.COFFEE_ORDER_URL)
public class CoffeeOrderController extends BaseController {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @GetMapping(value = EndPointNamingUtil.ORDER_ID)
    @ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer jwt_token")
    public ResponseEntity<CustomerOrderDTO> getOrderById(@PathVariable Integer orderId, HttpServletRequest httpServletRequest) throws BadRequestDataException, AuthException, JwtTokenException {
        int userId = checkAuthentication(httpServletRequest);
        return new ResponseEntity(coffeeOrderService.getOrderById(orderId,userId), HttpStatus.CREATED);
    }

    @PostMapping
    @ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer jwt_token")
    public ResponseEntity<CustomerOrderDTO> addNewOrder(@Valid @RequestBody OrderDTO request, HttpServletRequest httpServletRequest) throws BadRequestDataException, AuthException, JwtTokenException {
        int userId = checkAuthentication(httpServletRequest);
        return new ResponseEntity(coffeeOrderService.addNewOrder(request,userId), HttpStatus.OK);
    }

    @PutMapping(value = EndPointNamingUtil.ORDER_ID)
    @ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer jwt_token")
    public ResponseEntity<CustomerOrderDTO> changeOrder(@PathVariable Integer orderId, @Valid @RequestBody CustomerOrderDTO request, HttpServletRequest httpServletRequest) throws BadRequestDataException, AuthException, JwtTokenException {
        int userId = checkAuthentication(httpServletRequest);
        return new ResponseEntity(coffeeOrderService.changeOrder(orderId, request,userId), HttpStatus.OK);
    }

    @DeleteMapping(value = EndPointNamingUtil.ORDER_ID)
    @ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer jwt_token")
    public ResponseEntity<String> cancelOrder(@PathVariable Integer orderId, HttpServletRequest httpServletRequest) throws BadRequestDataException, AuthException, JwtTokenException {
        int userId = checkAuthentication(httpServletRequest);
        coffeeOrderService.cancelOrder(orderId,userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
