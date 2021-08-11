package ru.geekbrains.antasyuk.models;

import lombok.Data;

@Data
public class UserParams {

    private String usernameFilter;
    private Integer minAge;
    private Integer maxAge;
    private Integer page;
    private Integer size;
    private String sortField;
    private Boolean sortIsDown;

}
