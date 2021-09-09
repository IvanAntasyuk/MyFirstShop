package ru.geekbrains.antasyuk.interfaces;

import org.springframework.data.domain.Page;
import ru.geekbrains.antasyuk.dto.ProductDto;

import java.util.Optional;

public interface ProductInterface {
    Page<ProductDto> findAll(Integer page, Integer size, String sortField);

    Optional<ProductDto> findById(Long id);
}
