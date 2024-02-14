package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleDao;

public class RoleService {
    private final RoleDao roleDao;
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
//    public Role createRole(String role){
//        role = "ROLE_" + role;
//        Role r = roleDao.findRoleByAuthority(role);
//        if(r == null){
//            return roleDao.save(new Role(role));
//        }
//        return r;
//    }

}
