package ru.geekbrains.antasyuk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.geekbrains.antasyuk.dto.CategoryDto;
import ru.geekbrains.antasyuk.interfaces.CategoryInterface;
import ru.geekbrains.antasyuk.interfaces.CategoryRepository;
import ru.geekbrains.antasyuk.models.Category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService implements CategoryInterface {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    @Override
    public List<CategoryDto> findAll(Integer page, Integer size, String sortField) {
        return categoryRepository.findAll(PageRequest.of(page,size, Sort.by(sortField))).stream()
                .map(category -> new CategoryDto(category.getId(), category.getCategoryName()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getId(),categoryDto.getCategoryName());
        categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<CategoryDto> findById(Long id) {
        return categoryRepository.findById(id).
                map(category -> new CategoryDto(category.getId(),category.getCategoryName()));
    }
}