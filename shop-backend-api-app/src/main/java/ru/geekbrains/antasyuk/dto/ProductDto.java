package ru.geekbrains.antasyuk.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class ProductDto {
    private Long id;

    private String title;

    private String description;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
    @JsonSubTypes({ @JsonSubTypes.Type(name = "BIG_DECIMAL", value = BigDecimal.class) })
    private BigDecimal price;

    @JsonSerialize
    private CategoryDto category;

    @JsonSerialize
    private BrandDto brand;

    private List<Long> pictures;

    private Long mainPicture;


    public ProductDto(Long id, String title, String description, BigDecimal price, CategoryDto category, BrandDto brand, List<Long> pictures, Long mainPicture) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.brand=brand;
        this.pictures=pictures;
        this.mainPicture=mainPicture;
    }

    public ProductDto() {

    }
}