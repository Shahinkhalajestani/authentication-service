package com.shahintraining.authenticationservice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sh.khalajestanii
 * @project authentication-service
 * @since 10/25/2021
 */
@Data
public class UsernamePasswordRequest {
    private String username;
    private String password;
}
