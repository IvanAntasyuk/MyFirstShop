package ru.geekbrains.antasyuk.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.antasyuk.models.Brand;
import ru.geekbrains.antasyuk.models.Picture;

public interface PictureRepasitory extends JpaRepository<Picture,Long> {
}
