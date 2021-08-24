package ru.geekbrains.antasyuk.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.antasyuk.dto.ProductDto;
import ru.geekbrains.antasyuk.interfaces.BrandRepository;
import ru.geekbrains.antasyuk.interfaces.CategoryRepository;
import ru.geekbrains.antasyuk.models.Product;
import ru.geekbrains.antasyuk.models.ProductParams;
import ru.geekbrains.antasyuk.services.ProductService;

import java.util.Optional;


@Controller
@RequestMapping("/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Autowired
    public ProductController(ProductService productService,
                             CategoryRepository categoryRepository,
                             BrandRepository brandRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    @GetMapping
    public String listPage(@RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sortField") Optional<String> sortField, Model model) {
        logger.info("Product list page requested");

        model.addAttribute("products", productService.findAll(
                page.orElse(1) - 1,
                size.orElse(5),
                sortField.filter(fld -> !fld.isBlank()).orElse("id")));

        return "product";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        logger.info("New product page requested");

        model.addAttribute("product", new ProductDto());
        model.addAttribute("categorys", categoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        return "product_form";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categorys", categoryRepository.findAll());
        return "product_form";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("activePage", "Product");
    }

    @PostMapping("/add")
    public String update(ProductDto product) {
        if (product.getId() == null) {
            logger.info("Add product" + product);
            productService.save(product);
        } else {
            logger.info("Update product" + product);
            productService.save(product);
        }
        return "redirect:/product";
    }


    @GetMapping("/del/{id}")
    public String delete(@PathVariable("id") Long id) {
        logger.info("Delete product id " + id);
        productService.deleteById(id);
        return "redirect:/product";
    }

}