package ru.geekbrains.antasyuk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import ru.geekbrains.antasyuk.dto.BrandDto;
import ru.geekbrains.antasyuk.dto.CategoryDto;
import ru.geekbrains.antasyuk.dto.ProductDto;
import ru.geekbrains.antasyuk.exceptions.NotFoundException;
import ru.geekbrains.antasyuk.interfaces.BrandRepository;
import ru.geekbrains.antasyuk.interfaces.CategoryRepository;
import ru.geekbrains.antasyuk.interfaces.ProductInterface;
import ru.geekbrains.antasyuk.interfaces.ProductRepository;
import ru.geekbrains.antasyuk.models.Brand;
import ru.geekbrains.antasyuk.models.Category;
import ru.geekbrains.antasyuk.models.Product;



import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductInterface {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final BrandRepository brandRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository  = brandRepository;
    }

    @Override
    public Page<ProductDto> findAll(Integer page, Integer size, String sortField) {
        return productRepository.findAll(PageRequest.of(page, size, Sort.by(sortField)))
                .map(product -> new ProductDto(product.getId(),
                        product.getTitle(),
                        product.getDescription(),
                        product.getCost(),
                        new CategoryDto(product.getCategory().getId(), product.getCategory().getCategoryName()),
                        new BrandDto(product.getBrand().getId(),product.getBrand().getBrandName()))
                );
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(product -> new ProductDto(product.getId(),
                        product.getTitle(),
                        product.getDescription(),
                        product.getCost(),
                        new CategoryDto(product.getCategory().getId(), product.getCategory().getCategoryName()),
                        new BrandDto(product.getBrand().getId(),product.getBrand().getBrandName())));
    }

    @Override
    @Transactional
    public void save(ProductDto productDto) {
        Product product = (productDto.getId() != null) ? productRepository.findById(productDto.getId())
                .orElseThrow(() -> new NotFoundException("")) : new Product();
        Category category = categoryRepository.findById(productDto.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Brand brand = brandRepository.findById(productDto.getBrand().getId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        product.setTitle(productDto.getTitle());
        product.setCategory(category);
        product.setCost(productDto.getCost());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);
        product.setBrand(brand);

        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}