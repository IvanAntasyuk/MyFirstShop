package ru.geekbrains.antasyuk.interfaces;

import ru.geekbrains.antasyuk.dto.BrandDto;

import java.util.List;
import java.util.Optional;

public interface BrandInterface {
    List<BrandDto> findAll(Integer page, Integer size, String sortField);

    void save(BrandDto brandDto);

    void deleteById(Long id);

    Optional<BrandDto> findById(Long id);

}