package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.repositories.RoleDao;

public class RoleService {
    private final RoleDao roleDao;
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
}
