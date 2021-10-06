package ru.geekbrains.antasyuk.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;


@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
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