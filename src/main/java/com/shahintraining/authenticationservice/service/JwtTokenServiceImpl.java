package com.shahintraining.authenticationservice.service;

import com.shahintraining.authenticationservice.jwt.JwtUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author sh.khalajestanii
 * @project authentication-service
 * @since 10/30/2021
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService{

    private final AppUserService userService;
    private final JwtUtility jwtUtility;

    @Override
    public String getAccessTokenFromRefreshToken(String token,String issuer) {
        try {
            String username = jwtUtility.getUsernameFromToken(token);
            UserDetails userDetails = userService.loadUserByUsername(username);
           return jwtUtility.generateAccessToken(userDetails,issuer);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
