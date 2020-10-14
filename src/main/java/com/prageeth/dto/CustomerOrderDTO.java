package com.prageeth.dto;

public class CustomerOrderDTO extends OrderDTO {

    private QueueDetailDTO queueDetailDTO;

    public QueueDetailDTO getQueueDetailDTO() {
        return queueDetailDTO;
    }

    public void setQueueDetailDTO(QueueDetailDTO queueDetailDTO) {
        this.queueDetailDTO = queueDetailDTO;
    }
}
