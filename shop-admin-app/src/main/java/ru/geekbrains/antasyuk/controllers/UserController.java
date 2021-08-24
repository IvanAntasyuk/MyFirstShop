package ru.geekbrains.antasyuk.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.antasyuk.dto.RoleDto;
import ru.geekbrains.antasyuk.dto.UserDto;
import ru.geekbrains.antasyuk.exceptions.NotFoundException;
import ru.geekbrains.antasyuk.interfaces.RoleRepository;
import ru.geekbrains.antasyuk.models.UserParams;
import ru.geekbrains.antasyuk.services.UserService;


import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    private final RoleRepository roleRepository;

    private final UserService userService;

    @Autowired
    public UserController(UserService userService,
                          RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository=roleRepository;
    }


    @GetMapping()
    public String listPage(Model model,
                           UserParams userParams) {
        logger.info("User list page requested");

        model.addAttribute("users", userService.findWithFilter(userParams));
        return "users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        logger.info("New user page requested");

        model.addAttribute("user", new UserDto());
        model.addAttribute("roles", roleRepository.findAll().stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toList()));
        return "user_form";
    }

    @GetMapping("/{id}")
    //  @Secured("ROLE_SUPER_ADMIN")
    public String editUser(@PathVariable("id") Long id, Model model) {
        logger.info("Edit user page requested");

        model.addAttribute("user", userService.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found")));
        model.addAttribute("roles", roleRepository.findAll().stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toList()));
        return "user_form";
    }

    @PostMapping("/add")
//    @Secured("ROLE_SUPER_ADMIN")
    public String update(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {

        if (!userDto.getPassword().equals(userDto.getRepeatPassword())) {
            result.rejectValue("password", "", "Repeated password is not correct");
            return "user_form";
        }

        if(userDto.getId()==null){
            logger.info("Add user "+userDto);
            userService.save(userDto);
        } else {
            logger.info("Update user "+userDto);
            userService.save(userDto);
        }
        return "redirect:/user";
    }

    @GetMapping("/del/{id}")
//    @Secured("ROLE_SUPER_ADMIN")
    public String delete(@PathVariable("id") Long id) {
        logger.info("Delete user id "+id);
        userService.deleteById(id);
        return "redirect:/user";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}