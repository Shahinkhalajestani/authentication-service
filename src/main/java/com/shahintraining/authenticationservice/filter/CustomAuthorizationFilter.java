package com.shahintraining.authenticationservice.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shahintraining.authenticationservice.jwt.JwtConfig;
import com.shahintraining.authenticationservice.jwt.JwtUtility;
import lombok.extern.slf4j.Slf4j;
import netscape.security.Privilege;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sh.khalajestanii
 * @project authentication-service
 * @since 10/25/2021
 */
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtility jwtUtility;

    public CustomAuthorizationFilter(JwtUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/refresh-token")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null && jwtUtility.checkAuthorizationHeader(authorizationHeader)) {
                try {

//                    String token = authorizationHeader.substring("Bearer ".length());
//                    JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("long ass secret key".getBytes())).build();
//                    DecodedJWT decodedJWT = jwtVerifier.verify(token);
//                    String username = decodedJWT.getSubject();
//                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
//                    List<SimpleGrantedAuthority> authorities =
//                            Arrays.stream(roles).map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

                    String username = jwtUtility.getUsernameFromToken(authorizationHeader);
                    List<SimpleGrantedAuthority> authorities = Arrays.stream(jwtUtility.getRolesFromToken(authorizationHeader))
                            .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception ex) {
                    log.error("Error Loging In : {}",ex.getMessage());
                    response.setHeader("error",ex.getMessage());
                    response.setStatus(HttpStatus.FORBIDDEN.value());
//                    response.sendError(HttpStatus.FORBIDDEN.value()); it sends the respond
                    Map<String,String> errors = new HashMap<>();
                    errors.put("error_message",ex.getMessage());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(),errors);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
