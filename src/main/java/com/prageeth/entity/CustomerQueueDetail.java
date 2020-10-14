package com.prageeth.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Entity
@Where(clause = "is_deleted = 0")
@SQLDelete(sql = "UPDATE customer_queue_detail SET is_deleted = 1,last_modified_date =now()::timestamp WHERE id = ? AND is_deleted = 0")
public class CustomerQueueDetail extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer queueId;
    private Integer shopId;
    private Integer position;
    private Double waitingMinutes;
    private int isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQueueId() {
        return queueId;
    }

    public void setQueueId(Integer queueId) {
        this.queueId = queueId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Double getWaitingMinutes() {
        return waitingMinutes;
    }

    public void setWaitingMinutes(Double waitingMinutes) {
        this.waitingMinutes = waitingMinutes;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}
