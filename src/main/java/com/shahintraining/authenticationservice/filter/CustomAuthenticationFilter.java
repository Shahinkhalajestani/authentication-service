package com.shahintraining.authenticationservice.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shahintraining.authenticationservice.domain.AppUser;
import com.shahintraining.authenticationservice.domain.UsernamePasswordRequest;
import com.shahintraining.authenticationservice.jwt.JwtUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author sh.khalajestanii
 * @project authentication-service
 * @since 10/25/2021
 */
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtility;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtility jwtUtility) {
        this.authenticationManager = authenticationManager;
        this.jwtUtility = jwtUtility;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            UsernamePasswordRequest usernamePasswordRequest =
                    new ObjectMapper().readValue(request.getInputStream(), UsernamePasswordRequest.class);
            log.info("Username : {}", usernamePasswordRequest.getUsername());
            log.info("Password : {}", usernamePasswordRequest.getPassword());
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(usernamePasswordRequest.getUsername(), usernamePasswordRequest.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
//        Algorithm algorithm = Algorithm.HMAC256("long ass secret key".getBytes());
//        String access_token = JWT.create()
//                .withSubject(user.getUsername())
//                .withIssuedAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
//                .withIssuer(request.getRequestURI())
//                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
//                        .collect(Collectors.toList()))
//                .sign(algorithm);
//        String refresh_token = JWT.create()
//                .withSubject(user.getUsername())
//                .withIssuedAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
//                .withIssuer(request.getRequestURI())
//                .sign(algorithm);
//        response.setHeader("access_token", access_token);
//        response.setHeader("refresh_token", refresh_token);
        String access_token = jwtUtility.generateAccessToken(user, request.getRequestURI());
        String refresh_token = jwtUtility.generateRefreshToken(user,request.getRequestURI());
        Map<String, String> tokens = new LinkedHashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

}
