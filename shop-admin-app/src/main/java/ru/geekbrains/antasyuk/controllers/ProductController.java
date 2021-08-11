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
import ru.geekbrains.antasyuk.interfaces.CategoryRepository;
import ru.geekbrains.antasyuk.models.Product;
import ru.geekbrains.antasyuk.models.ProductParams;
import ru.geekbrains.antasyuk.services.ProductService;



@Controller
@RequestMapping("/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductController(ProductService productService,
                             CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository=categoryRepository;
    }

    @GetMapping
    public String listPage(Model model,
                           ProductParams productParams) {
        logger.info("Product list page requested");

        model.addAttribute("products", productService.findWithFilter(productParams));

        return "product";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        logger.info("New product page requested");

        model.addAttribute("product", new Product());
        model.addAttribute("categorys", categoryRepository.findAll());
        return "product_form";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product",productService.findById(id));
        model.addAttribute("categorys", categoryRepository.findAll());
        return "product_form";
    }

    @PostMapping("/add")
    public String update(Product product) {
        if(product.getId()==null){
            logger.info("Add product"+product);
            productService.save(product);
        } else {
            logger.info("Update product"+product);
            productService.save(product);
        }
        return "redirect:/product";
    }


    @GetMapping("/del/{id}")
    public String delete(@PathVariable("id") Long id) {
        logger.info("Delete product id "+id);
        productService.deleteById(id);
        return "redirect:/product";
    }


}
