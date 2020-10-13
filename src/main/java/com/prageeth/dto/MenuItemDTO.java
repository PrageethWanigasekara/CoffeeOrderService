package com.prageeth.dto;

import io.swagger.annotations.ApiModel;


@ApiModel
public class MenuItemDTO {

    private String itemName;

    private Integer numOfItems;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(Integer numOfItems) {
        this.numOfItems = numOfItems;
    }
}
