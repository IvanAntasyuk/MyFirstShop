package ru.geekbrains.antasyuk.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class OrderDto {

    private Long id;

    private BigDecimal price;

    private  String status;

    private LocalDateTime date;

    private String username;

    public OrderDto() {
    }

    public OrderDto(Long id, BigDecimal price, String status, LocalDateTime date,String username) {
        this.id = id;
        this.price = price;
        this.status = status;
        this.date = date;
        this.username = username;
    }
}