package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public AdminController(RoleRepository roleRepository, UserService userService, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String getAllUsers(Model model, Principal principal) {
        List<User> allUsers = userService.getAllUsers();
        if (userRepository.findByFirstName(principal.getName()).isPresent()) {
            model.addAttribute("user", userRepository.findByFirstName(principal.getName()).get());
        }
        model.addAttribute("allUsers", allUsers);
        return "admin/users";
    }


    @PostMapping(path = "/create")
    public String create(@ModelAttribute("user") User user, Model model) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        userService.add(user);
        return "redirect:/admin";
    }


    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }


    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("user") User user, Model model) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        userService.update(id, user);
        return "redirect:/admin";

    }


}
