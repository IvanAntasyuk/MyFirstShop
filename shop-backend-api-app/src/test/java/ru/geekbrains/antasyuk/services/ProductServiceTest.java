package ru.geekbrains.antasyuk.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.antasyuk.dto.ProductDto;
import ru.geekbrains.antasyuk.interfaces.ProductRepository;
import ru.geekbrains.antasyuk.models.Category;
import ru.geekbrains.antasyuk.models.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    private ProductService productService;

    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    public void testFindById() {
        Category expectedCategory = new Category();
        expectedCategory.setId(1L);
        expectedCategory.setCategoryName("Category name");

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setTitle("Product name");
        expectedProduct.setCategory(expectedCategory);
        expectedProduct.setPictures(new ArrayList<>());
        expectedProduct.setCost(new BigDecimal(12345));

        when(productRepository.findById(eq(expectedProduct.getId())))
                .thenReturn(Optional.of(expectedProduct));

        Optional<ProductDto> opt = productService.findById(expectedProduct.getId());

        assertTrue(opt.isPresent());
        assertEquals(expectedProduct.getId(), opt.get().getId());
        assertEquals(expectedProduct.getTitle(), opt.get().getTitle());
    }

    @Test
    public void testFindAll() {
        Category expectedCategory = new Category();
        expectedCategory.setId(1L);
        expectedCategory.setCategoryName("Category name");

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setTitle("Product name");
        expectedProduct.setCategory(expectedCategory);
        expectedProduct.setPictures(new ArrayList<>());
        expectedProduct.setCost(new BigDecimal(12345));

        when(productRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl(List.of(expectedProduct)));

        Page<ProductDto> products = productService.findAll(Optional.of(expectedCategory.getId()) ,
                Optional.of( expectedProduct.getTitle()), 1,3,"id");

        ProductDto expected = products.getContent().get(0);
        assertEquals(expected.getId(), expectedProduct.getId());
        assertEquals(expected.getCategory().getCategoryName(), expectedProduct.getCategory().getCategoryName());
        assertEquals(expected.getTitle(), expectedProduct.getTitle());
        assertEquals(expected.getDescription(), expectedProduct.getDescription());
        assertEquals(expected.getPrice(), expectedProduct.getCost());
    }
}