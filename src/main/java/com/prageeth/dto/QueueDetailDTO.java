package com.prageeth.dto;

public class QueueDetailDTO {

    private Integer position;
    private Integer queueId;
    private Double waitingMinutes;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getQueueId() {
        return queueId;
    }

    public void setQueueId(Integer queueId) {
        this.queueId = queueId;
    }

    public Double getWaitingMinutes() {
        return waitingMinutes;
    }

    public void setWaitingMinutes(Double waitingMinutes) {
        this.waitingMinutes = waitingMinutes;
    }
}
