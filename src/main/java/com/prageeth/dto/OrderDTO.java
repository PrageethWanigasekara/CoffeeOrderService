package com.prageeth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@ApiModel
public class OrderDTO {

    private Integer orderId;

    @NotNull(message = "shopId can not be null")
    @ApiModelProperty(notes = "Please provide shopId")
    private Integer shopId;

    @NotEmpty(message = "shopName can not be null or empty")
    private String shopName;

    @NotEmpty(message = "menuItems can not be null or empty")
    private List<MenuItemDTO> orderedMenuItem;

    @NotNull(message = "userId can not be null")
    private Integer userId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<MenuItemDTO> getOrderedMenuItem() {
        return orderedMenuItem;
    }

    public void setOrderedMenuItem(List<MenuItemDTO> orderedMenuItem) {
        this.orderedMenuItem = orderedMenuItem;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}