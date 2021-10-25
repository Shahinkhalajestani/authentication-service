package com.shahintraining.authenticationservice;

import com.shahintraining.authenticationservice.domain.AppUser;
import com.shahintraining.authenticationservice.domain.Role;
import com.shahintraining.authenticationservice.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class AuthenticationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(AppUserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));

            userService.saveUser(new AppUser(null, "shahin", "shahin@123", "1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "abbas", "abbas@123", "1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "abdul", "abdul@123", "1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "mirsad", "mirsad@123", "1234", new ArrayList<>()));

            userService.addRoleToUser("shahin@123", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("shahin@123", "ROLE_ADMIN");
            userService.addRoleToUser("shahin@123", "ROLE_USER");
            userService.addRoleToUser("abbas@123", "ROLE_MANAGER");
            userService.addRoleToUser("abdul@123", "ROLE_USER");
            userService.addRoleToUser("mirsad@123", "ROLE_ADMIN");
        };
    }

}
