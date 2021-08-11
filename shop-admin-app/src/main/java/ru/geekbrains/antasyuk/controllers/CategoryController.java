package ru.geekbrains.antasyuk.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.antasyuk.models.Category;
import ru.geekbrains.antasyuk.models.Product;
import ru.geekbrains.antasyuk.services.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listPage(Model model){
        model.addAttribute("categorys",categoryService.findAll());
        return "category";
    }

    @GetMapping("/new")
    public String newCategoryForm(Model model){
        model.addAttribute("category",new Category());
        return "category_form";
    }

    @PostMapping("/add")
    public String update(Category category) {
        if(category.getId()==null){
            logger.info("Add product"+category);
            categoryService.save(category);
        } else {
            logger.info("Update product"+category);
            categoryService.save(category);
        }
        return "redirect:/category";
    }

    @GetMapping("/{id}")
    public String editCategory(@PathVariable("id") Long id,Model model){
        model.addAttribute("category",categoryService.findById(id));
        return "category_form";
    }

    @PostMapping("/del/{id}")
    public String deleteCategory(@PathVariable("id")Long id){
        categoryService.deleteById(id);
        return "redirect:/category";
    }


}
