package com.shahintraining.authenticationservice.domain;

import lombok.*;

/**
 * @author sh.khalajestanii
 * @project authentication-service
 * @since 10/20/2021
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class RoleToUserDto {
    private String username;
    private String roleName;
}
