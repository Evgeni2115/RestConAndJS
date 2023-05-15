package ru.kata.spring.boot_security.demo.init;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Init {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    public Init(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void create() {
        Role userRole = new Role();
        userRole.setId(2L);
        userRole.setName("ROLE_USER");

        Role adminRole = new Role();
        adminRole.setId(1L);
        adminRole.setName("ROLE_ADMIN");

        roleRepository.saveAll(List.of(adminRole, userRole));

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);

        User user = new User("admin", "admin", 21, "admin@mail.ru", passwordEncoder.encode("123"), adminRoles);
        User admin = new User("user", "user",21, "user@mail.ru", passwordEncoder.encode("123"), userRoles);


        userRepository.save(admin);
        userRepository.save(user);
    }
}

