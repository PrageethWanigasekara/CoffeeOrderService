package com.prageeth.entity;

import javax.persistence.*;

@Entity
public class MasterQueueData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer shopId;
    private Integer numOfQueues;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getNumOfQueues() {
        return numOfQueues;
    }

    public void setNumOfQueues(Integer numOfQueues) {
        this.numOfQueues = numOfQueues;
    }
}
