package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleDao;
import ru.kata.spring.boot_security.demo.repositories.UserDao;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/admin")
public class AdminController {
 private final UserDao userDao;
 private final RoleDao roleDao;
 private final PasswordEncoder passwordEncoder;


    public AdminController(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping
    public String getAll(Model model) {
        Collection<User> userList = userDao.findAll();
        model.addAttribute("userlist", userList);
        return "admin/userlist";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") long id, Model model){
        User user = userDao.findById(id).get();
        System.out.println(user.getName());
        model.addAttribute("user",user);
        return "admin/show";
    }

    @GetMapping("/new")
    public String createForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleDao.findAll());
        return "admin/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "admin/new";
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
        return "redirect:";
    }

    @PostMapping("/{id}/del")
    public String delete(@PathVariable("id") long id) {
        userDao.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editForm(Model model,
                           @PathVariable("id") long id) {
        model.addAttribute("user", userDao.findById(id).get());
        model.addAttribute("roles", roleDao.findAll());
        return "admin/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@ModelAttribute("user") @Valid User user, Model model,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "admin/edit";
        userDao.save(user);
        return "redirect:/admin";

    }
}
