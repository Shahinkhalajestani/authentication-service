package com.shahintraining.authenticationservice.domain.service;

import com.shahintraining.authenticationservice.domain.AppUser;
import com.shahintraining.authenticationservice.domain.Role;
import com.shahintraining.authenticationservice.repo.AppUserRepo;
import com.shahintraining.authenticationservice.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author sh.khalajestanii on 10/19/2021
 * @project authentication-service
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService {


    private final AppUserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("saving new user to database");
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role to database");
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("adding a role to a user");
        Optional<AppUser> appUserByUsername = userRepo.findAppUserByUsername(username);
        if (appUserByUsername.isPresent()) {
            AppUser user = appUserByUsername.get();
            Optional<Role> roleOptional = roleRepo.findByName(roleName);
            if (roleOptional.isPresent()) {
                user.getRoles().add(roleOptional.get());
                userRepo.save(user);
            } else {
                throw new RuntimeException("the role is not present");
            }
        } else {
            throw new RuntimeException("user is not present");
        }
    }

    @Override
    public AppUser getUser(String username) {
        log.info("fetching a user from database");
        Optional<AppUser> userOptional = userRepo.findAppUserByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("user is not present");
        }
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("fetching all user form database");
        return userRepo.findAll();
    }
}
