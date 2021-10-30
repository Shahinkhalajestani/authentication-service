package com.shahintraining.authenticationservice.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author sh.khalajestanii
 * @project authentication-service
 * @since 10/30/2021
 */
public interface JwtTokenService {
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
