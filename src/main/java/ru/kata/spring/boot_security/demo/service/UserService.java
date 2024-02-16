package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleDao;
import ru.kata.spring.boot_security.demo.repositories.UserDao;


import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.Collection;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;

    }

    public void create(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    public User get(long id) {
        return userDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    @Transactional(readOnly = true)
    public Collection<User> getAll() {
        return (Collection<User>) userDao.findAll();
    }

    public void update(User user, Long id) {
        User existingUser = get(id);
        if (existingUser != null) {
            if (user.getPassword() == null) {
                user.setPassword(existingUser.getPassword());
            }
            user.setId(id);
        }
        userDao.save(user);
    }


    public void delete(long id) {
        userDao.deleteById(id);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    @Transactional(readOnly = true)
    public User find(User user) {
        return get(user.getId());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("No Username");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getRoles());
    }


    public User showUser(String username) {
        return userDao.findUserByUsername(username);

    }

}
