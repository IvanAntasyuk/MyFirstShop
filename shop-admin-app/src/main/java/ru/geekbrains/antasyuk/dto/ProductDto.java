package ru.geekbrains.antasyuk.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.antasyuk.models.Picture;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {

    private Long id;

    private String title;

    private String description;

    private BigDecimal cost;

    private CategoryDto category;

    private BrandDto brand;

    private List<Long> pictures;

    private MultipartFile[] newPictures;


    public ProductDto(Long id, String title, String description, BigDecimal cost,
                      CategoryDto category, BrandDto brand, List<Long> pictures) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.category = category;
        this.brand = brand;
        this.pictures = pictures;
    }

    public ProductDto() {

    }
}