package ru.geekbrains.antasyuk.interfaces;



import ru.geekbrains.antasyuk.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryInteraface {

    List<CategoryDto> findAll(Integer page, Integer size, String sortField);

    Optional<CategoryDto> findById(Long id);
}
