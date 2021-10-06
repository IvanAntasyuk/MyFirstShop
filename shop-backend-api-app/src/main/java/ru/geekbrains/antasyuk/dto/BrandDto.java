package ru.geekbrains.antasyuk.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class BrandDto {
    private Long id;

    private String brandName;

    public BrandDto(Long id, String brandName) {
        this.id = id;
        this.brandName = brandName;
    }

    public BrandDto() {
    }
}