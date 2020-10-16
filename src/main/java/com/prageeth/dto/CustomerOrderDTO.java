package com.prageeth.dto;

public class CustomerOrderDTO extends OrderDTO {

    private QueueDetailDTO queueDetails;

    public QueueDetailDTO getQueueDetails() {
        return queueDetails;
    }

    public void setQueueDetails(QueueDetailDTO queueDetails) {
        this.queueDetails = queueDetails;
    }
}
