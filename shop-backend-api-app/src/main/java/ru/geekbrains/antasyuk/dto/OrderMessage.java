package ru.geekbrains.antasyuk.dto;

import lombok.Data;

@Data
public class OrderMessage {

    private Long id;

    private String status;

    public OrderMessage(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public OrderMessage() {
    }
}
