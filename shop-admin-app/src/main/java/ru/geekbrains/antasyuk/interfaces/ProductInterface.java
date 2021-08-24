package ru.geekbrains.antasyuk.interfaces;

import org.springframework.data.domain.Page;
import ru.geekbrains.antasyuk.dto.ProductDto;
import ru.geekbrains.antasyuk.models.Product;
import ru.geekbrains.antasyuk.models.ProductParams;


import java.util.List;
import java.util.Optional;

public interface ProductInterface {

    Page<ProductDto> findAll(Integer page, Integer size, String sortField);

    Optional<ProductDto> findById(Long id);

    void save(ProductDto product);

    void deleteById(Long id);
}
