package ru.geekbrains.antasyuk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.antasyuk.dto.CategoryDto;
import ru.geekbrains.antasyuk.services.CategoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public List<CategoryDto> listPage(@RequestParam("page") Optional<Integer> page,
                                      @RequestParam("size") Optional<Integer> size,
                                      @RequestParam("sortField") Optional<String> sortField, Model model){
        return categoryService.findAll(
                page.orElse(1) - 1,
                size.orElse(5),
                sortField.filter(fld -> !fld.isBlank()).orElse("id"));

    }

    @GetMapping("/{id}")
    public String editCategory(@PathVariable("id") Long id, Model model){
        model.addAttribute("category",categoryService.findById(id));
        return "category_form";
    }


}