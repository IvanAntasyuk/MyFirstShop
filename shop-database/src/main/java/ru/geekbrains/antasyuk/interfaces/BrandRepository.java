package ru.geekbrains.antasyuk.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.antasyuk.models.Brand;

public interface BrandRepository extends JpaRepository<Brand,Long> {
}