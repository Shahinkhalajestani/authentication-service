package com.shahintraining.authenticationservice.contorller;

import com.shahintraining.authenticationservice.domain.AppUser;
import com.shahintraining.authenticationservice.domain.Role;
import com.shahintraining.authenticationservice.domain.RoleToUserDto;
import com.shahintraining.authenticationservice.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author sh.khalajestanii
 * @project authentication-service
 * @since 10/19/2021
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final AppUserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<AppUser>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping(value = "/user/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
      return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping(value = "/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping(value = "/role/addRoleToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserDto roleToUserDto){
        userService.addRoleToUser(roleToUserDto.getUsername(),roleToUserDto.getRoleName());
        return ResponseEntity.ok().build();
    }

}
