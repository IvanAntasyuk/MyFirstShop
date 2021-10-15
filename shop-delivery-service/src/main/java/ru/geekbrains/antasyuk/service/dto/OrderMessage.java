package ru.geekbrains.antasyuk.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderMessage {
    private Long id;

    private String state;

    public OrderMessage(Long id, String state) {
        this.id = id;
        this.state = state;
    }
}