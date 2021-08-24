package ru.geekbrains.antasyuk.controllers;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.antasyuk.dto.BrandDto;
import ru.geekbrains.antasyuk.services.BrandService;


@Controller
@RequestMapping("/brand")
public class BrandController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public String listPage(@RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sortField") Optional<String> sortField, Model model){
        model.addAttribute("brands", brandService.findAll(
                page.orElse(1) - 1,
                size.orElse(5),
                sortField.filter(fld -> !fld.isBlank()).orElse("id")));
        return "brand";
    }

    @GetMapping("/new")
    public String newBrandForm(Model model){
        model.addAttribute("brand",new BrandDto());
        return "brand_form";
    }

    @PostMapping("/add")
    public String update(BrandDto brandDto) {
        if(brandDto.getId()==null){
            logger.info("Add product"+brandDto);
            brandService.save(brandDto);
        } else {
            logger.info("Update product"+brandDto);
            brandService.save(brandDto);
        }
        return "redirect:/brand";
    }

    @GetMapping("/{id}")
    public String editCategory(@PathVariable("id") Long id, Model model){
        model.addAttribute("brand",brandService.findById(id));
        return "brand_form";
    }

    @PostMapping("/del/{id}")
    public String deleteCategory(@PathVariable("id")Long id){
        brandService.deleteById(id);
        return "redirect:/brand";
    }
}