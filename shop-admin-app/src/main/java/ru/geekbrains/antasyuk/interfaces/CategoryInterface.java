package ru.geekbrains.antasyuk.interfaces;

import ru.geekbrains.antasyuk.dto.CategoryDto;
import ru.geekbrains.antasyuk.models.Category;


import java.util.List;
import java.util.Optional;

public interface CategoryInterface {

    List<CategoryDto> findAll(Integer page, Integer size, String sortField);

    void save(CategoryDto category);

    void deleteById(Long id);

    Optional<CategoryDto> findById(Long id);

}
