package ru.geekbrains.antasyuk.interfaces;

import ru.geekbrains.antasyuk.models.Category;


import java.util.List;
import java.util.Optional;

public interface CategoryInterface {

    List<Category> findAll();

    void save(Category category);

    void deleteById(Long id);

    Optional<Category> findById(Long id);

}
