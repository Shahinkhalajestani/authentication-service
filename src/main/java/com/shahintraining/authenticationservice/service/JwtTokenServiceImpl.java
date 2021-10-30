package com.shahintraining.authenticationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shahintraining.authenticationservice.jwt.JwtUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && jwtUtility.checkAuthorizationHeader(authorizationHeader)) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Map<String, String> tokens = getTokens(authorizationHeader, request.getRequestURI());
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        }else {
            throw new RuntimeException("refresh Token Is missing");
        }
    }

    private Map<String,String> getTokens(String token, String issuer) {
        try {
            String username = jwtUtility.getUsernameFromToken(token);
            UserDetails userDetails = userService.loadUserByUsername(username);
            String access_token = jwtUtility.generateAccessToken(userDetails, issuer);
//            String refresh_token= jwtUtility.generateRefreshToken(userDetails,issuer); in case we want to renew refresh token also!
            String refresh_token = token;
            Map<String,String> tokens= new HashMap<>();
            tokens.put("access_token",access_token);
            tokens.put("refresh_token",refresh_token);
            return tokens;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
