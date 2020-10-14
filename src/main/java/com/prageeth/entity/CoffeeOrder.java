package com.prageeth.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Where(clause = "is_inactive = 0")
@SQLDelete(sql = "UPDATE coffee_order SET is_inactive = 1,last_modified_date =now()::timestamp WHERE order_id = ? AND is_inactive = 0")
public class CoffeeOrder extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private Integer shopId;

    private String shopName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderedMenuItem> orderedMenuItem;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "queue_detail_id")
    private CustomerQueueDetail customerQueue;

    private int isInactive;

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

    public List<OrderedMenuItem> getOrderedMenuItem() {
        return orderedMenuItem;
    }

    public void setOrderedMenuItem(List<OrderedMenuItem> orderedMenuItem) {
        this.orderedMenuItem = orderedMenuItem;
    }

    public CustomerQueueDetail getCustomerQueue() {
        return customerQueue;
    }

    public void setCustomerQueue(CustomerQueueDetail customerQueue) {
        this.customerQueue = customerQueue;
    }

    public int getIsInactive() {
        return isInactive;
    }

    public void setIsInactive(int isInactive) {
        this.isInactive = isInactive;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
