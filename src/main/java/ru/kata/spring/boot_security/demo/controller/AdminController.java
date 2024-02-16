package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.Collection;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController( UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAll(Model model) {
        Collection<User> userList = userService.getAll();
        model.addAttribute("userlist", userList);
        return "admin/userlist";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") long id, Model model) {
        User user = userService.get(id);
        model.addAttribute("user", user);
        return "/admin/show";
    }

    @GetMapping("/new")
    public String createForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.getAll());
        return "admin/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "admin/new";
        user.setPassword(user.getPassword());
        userService.create(user);
        return "redirect:";
    }

    @PostMapping("/{id}/del")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editForm(Model model,
                           @PathVariable("id") long id) {
        model.addAttribute("user", userService.get(id));
        model.addAttribute("roles", roleService.getAll());
        return "admin/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@ModelAttribute("user") @Valid User user,@PathVariable("id") long id,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "admin/edit";
        userService.update(user,id);
        return "redirect:/admin";

    }

}
