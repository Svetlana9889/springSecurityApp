package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public void create(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User get(long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    @Transactional(readOnly = true)
    public Collection<User> getAll() {
        return (Collection<User>) userRepository.findAll();
    }

    public void update(User user, Long id) {
        User existingUser = get(id);
        if (existingUser != null) {
            if (user.getPassword() == null) {
                user.setPassword(existingUser.getPassword());
            }
            user.setId(id);
        }
        userRepository.save(user);
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("No Username");
        }
        return user;
    }
}
