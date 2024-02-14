package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserDao;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UsersController {
    private final UserDao userDao;

    public UsersController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/user")
    public String showUser(Principal principal, Model model){

        User user = userDao.findUserByUsername(principal.getName());
        model.addAttribute("user",user);
        return "/user/user";
    }
}

