package ru.geekbrains.antasyuk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.antasyuk.interfaces.ProductInterface;
import ru.geekbrains.antasyuk.interfaces.ProductRepository;
import ru.geekbrains.antasyuk.models.Product;
import ru.geekbrains.antasyuk.models.ProductParams;
import ru.geekbrains.antasyuk.models.ProductSpecifications;


import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductInterface {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findWithFilter(ProductParams productParams) {

        Specification<Product> spec = Specification.where(null);

        if (productParams.getProductNameFilter() != null && !productParams.getProductNameFilter().isBlank()) {
            spec = spec.and(ProductSpecifications.productPrefix(productParams.getProductNameFilter()));
        }
        if (productParams.getMinCost() != null) {
            spec = spec.and(ProductSpecifications.minCost(productParams.getMinCost()));
        }
        if (productParams.getMaxCost() != null ) {
            spec = spec.and(ProductSpecifications.maxCost(productParams.getMaxCost()));
        }

        if(productParams.getSortIsDown()==null || productParams.getSortIsDown()){
            return productRepository.findAll(spec,
                    PageRequest.of(
                            Optional.ofNullable(productParams.getPage()).orElse(1) - 1,
                            Optional.ofNullable(productParams.getSize()).orElse(3),
                            Sort.by(Sort.Direction.ASC,Optional.ofNullable(productParams.getSortField())
                                    .filter(c -> !c.isBlank())
                                    .orElse("id"))));
        }
        else{
            return productRepository.findAll(spec,
                    PageRequest.of(
                            Optional.ofNullable(productParams.getPage()).orElse(1) - 1,
                            Optional.ofNullable(productParams.getSize()).orElse(3),
                            Sort.by(Sort.Direction.DESC,Optional.ofNullable(productParams.getSortField())
                                    .filter(c -> !c.isBlank())
                                    .orElse("id"))));
        }


    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
