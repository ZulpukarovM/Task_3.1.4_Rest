package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final UserValidator userValidator;

    public AdminController(UserRepository userRepository, RoleRepository roleRepository, UserService userService, UserValidator userValidator) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "admin/users";
    }


    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {

        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "admin/new";
    }

    @PostMapping(path = "/new")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/new";
        }
        userService.add(user);
        return "redirect:/admin";
    }


    @GetMapping("/delete")
    public String usersId() {
        return "admin/delete";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }


    @GetMapping("/{id}/update")
    public String page(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getUser(id));
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);

        return "admin/update";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/update";
        }
        userService.update(id, user);

        return "redirect:/admin";

    }


}
