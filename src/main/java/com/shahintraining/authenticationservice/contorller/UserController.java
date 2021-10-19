package com.shahintraining.authenticationservice.contorller;

import com.shahintraining.authenticationservice.domain.AppUser;
import com.shahintraining.authenticationservice.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
