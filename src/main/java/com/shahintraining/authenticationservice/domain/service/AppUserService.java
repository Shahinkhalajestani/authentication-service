package com.shahintraining.authenticationservice.domain.service;

import com.shahintraining.authenticationservice.domain.AppUser;
import com.shahintraining.authenticationservice.domain.Role;

import java.util.List;

/**
 * @author sh.khalajestanii on 10/19/2021
 * @project authentication-service
 */
public interface AppUserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
}
