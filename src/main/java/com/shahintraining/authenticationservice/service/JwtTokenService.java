package com.shahintraining.authenticationservice.service;

/**
 * @author sh.khalajestanii
 * @project authentication-service
 * @since 10/30/2021
 */
public interface JwtTokenService {
    String getAccessTokenFromRefreshToken(String token,String issuer);
}
