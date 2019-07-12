package me.chen.eztech.bootstrap;

import me.chen.eztech.model.Role;
import me.chen.eztech.model.User;
import me.chen.eztech.service.RoleService;
import me.chen.eztech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        // Create role admin
        Role role = roleService.create("ROLE_ADMIN", "System Administrator");
        User user = new User();
        user.setUsername("lastcow");
        user.setPassword(passwordEncoder.encode("paradise"));
        user.setEmail("ebiz@chen.me");
        user.setActive(true);
        user.setRole(role);
        userService.create(user);

        // Create role instructor
        role = roleService.create("ROLE_PROFESSOR", "Instructor");
        user = new User();
        user.setUsername("instructor 1");
        user.setFirstName("Instructor");
        user.setLastName("1");
        user.setPassword(passwordEncoder.encode("paradise"));
        user.setEmail("instructor1@chen.me");
        user.setActive(true);
        user.setRole(role);
        userService.create(user);

        // Create role student
        role = roleService.create("ROLE_STUDENT", "Student");
        user = new User();
        user.setUsername("student 1");
        user.setFirstName("Student");
        user.setLastName("1");
        user.setPassword(passwordEncoder.encode("paradise"));
        user.setEmail("student1@chen.me");
        user.setActive(true);
        user.setRole(role);
        userService.create(user);
    }
}
