package ru.geekbrains.antasyuk.interfaces;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.geekbrains.antasyuk.models.Product;


public interface ProductRepository extends JpaRepository<Product,Long> , JpaSpecificationExecutor<Product> {

}
