package ru.geekbrains.antasyuk.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class OrderDto {

    private Long id;

    private BigDecimal price;

    private String status;

    private String date;

    private String username;

    public OrderDto() {
    }

    public OrderDto(Long id, BigDecimal price, String status, String date, String username) {
        this.id = id;
        this.price = price;
        this.status = status;
        this.date = date;
        this.username = username;
    }
}