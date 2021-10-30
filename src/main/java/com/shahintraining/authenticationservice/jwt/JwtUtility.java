package com.shahintraining.authenticationservice.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.SQLData;
import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author sh.khalajestanii
 * @project authentication-service
 * @since 10/26/2021
 */
@RequiredArgsConstructor
@Component
public class JwtUtility {
    private final JwtConfig jwtConfig;

    public String generateAccessToken(UserDetails userDetails, String issuer) {

        return jwtConfig.getTokenPrefix() + JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()
                        + jwtConfig.getAccessTokenExpirationAfterMinutes() * 60 * 1000))
                .withIssuer(issuer)
                .withClaim("roles",
                        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .sign(getAlgorithm());
    }

    public Boolean checkAuthorizationHeader(String authorizationHeader) {
        return authorizationHeader.startsWith(jwtConfig.getTokenPrefix());
    }

    public String generateRefreshToken(UserDetails userDetails, String issuer) {
        return jwtConfig.getTokenPrefix() + JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                .withIssuer(issuer)
                .sign(getAlgorithm());
    }

    private DecodedJWT validateToken(String token) {
        String tokenWithoutPrefix = token.substring(jwtConfig.getTokenPrefix().length());
        JWTVerifier jwtVerifier = JWT.require(getAlgorithm()).build();
        return jwtVerifier.verify(tokenWithoutPrefix);
    }


    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = validateToken(token);
        return decodedJWT.getSubject();
    }

    public String[] getRolesFromToken(String token) {
        DecodedJWT decodedJWT = validateToken(token);
        return decodedJWT.getClaim("roles").asArray(String.class);
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtConfig.getSecureKey().getBytes());
    }

}
