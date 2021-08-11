package ru.geekbrains.antasyuk.controllers;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

//Data transfer object

@Data
public class UserDto {

    private Long id;

    @Min(value=18)
    private Integer age;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String repeatPassword;

    private Set<RoleDto> roles;

    public UserDto(Long id, String username, Integer age, Set<RoleDto> roles) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.roles = roles;
    }

    public UserDto() {

    }
}
