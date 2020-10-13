package com.prageeth.dto;

import io.swagger.annotations.ApiModel;

@ApiModel
public class QueueDetailDTO {

    private int position;
    private int queueId;
    private int waitingMinutes;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public int getWaitingMinutes() {
        return waitingMinutes;
    }

    public void setWaitingMinutes(int waitingMinutes) {
        this.waitingMinutes = waitingMinutes;
    }
}
