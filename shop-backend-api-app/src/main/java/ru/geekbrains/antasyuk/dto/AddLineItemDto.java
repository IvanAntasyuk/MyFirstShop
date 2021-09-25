package ru.geekbrains.antasyuk.dto;

import lombok.Data;

@Data
public class AddLineItemDto {

    private Long productId;

    private Integer qty;

    private String color;

    private String material;

    public AddLineItemDto() {
    }

}