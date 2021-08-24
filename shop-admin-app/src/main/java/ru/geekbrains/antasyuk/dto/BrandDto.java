package ru.geekbrains.antasyuk.dto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class BrandDto {

    private Long id;

    @NotBlank
    private String brandName;

    public BrandDto(Long id, String brandName) {
        this.id = id;
        this.brandName = brandName;
    }

    public BrandDto() {
    }
}