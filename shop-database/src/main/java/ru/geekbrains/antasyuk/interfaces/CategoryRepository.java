package ru.geekbrains.antasyuk.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.antasyuk.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
