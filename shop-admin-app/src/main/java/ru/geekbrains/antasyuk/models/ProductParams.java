package ru.geekbrains.antasyuk.models;

import lombok.Data;

@Data
public class ProductParams {

    private String productNameFilter;
    private Integer minCost;
    private Integer maxCost;
    private Integer page;
    private Integer size;
    private String sortField;
    private Boolean sortIsDown;

}
