package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleDao;

import java.util.Collection;

@Service
public class RoleService {
    private final RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    @Transactional(readOnly = true)
    public Collection<Role> getAll() {
        return (Collection<Role>) roleDao.findAll();
    }


}