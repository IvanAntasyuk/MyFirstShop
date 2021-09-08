package ru.geekbrains.antasyuk.dto;

import lombok.Data;


@Data
public class CategoryDto {
    private Long id;

    private String categoryName;

    public CategoryDto(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public CategoryDto() {
    }
}