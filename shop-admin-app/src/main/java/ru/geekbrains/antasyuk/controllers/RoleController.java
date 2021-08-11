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
import ru.geekbrains.antasyuk.models.Role;
import ru.geekbrains.antasyuk.services.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String roleList (Model model){
        model.addAttribute("roles",roleService.findAll());
        return "role";
    }

    @GetMapping("/new")
    public String newCategoryForm(Model model){
        model.addAttribute("role",new Role());
        return "role_form";
    }

    @PostMapping("/add")
    public String update(Role role) {
        if(role.getId()==null){
            logger.info("Add role"+role);
            roleService.save(role);
        } else {
            logger.info("Update role"+role);
            roleService.save(role);
        }
        return "redirect:/role";
    }

    @GetMapping("/{id}")
    public String editCategory(@PathVariable("id") Long id, Model model){
        model.addAttribute("role",roleService.findById(id));
        return "role_form";
    }

    @PostMapping("/del/{id}")
    public String deleteCategory(@PathVariable("id")Long id){
        roleService.deleteById(id);
        return "redirect:/role";
    }

}
