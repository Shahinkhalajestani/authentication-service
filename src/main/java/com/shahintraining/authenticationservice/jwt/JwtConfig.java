package com.shahintraining.authenticationservice.jwt;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;

/**
 * @author sh.khalajestanii
 * @project authentication-service
 * @since 10/26/2021
 */
@Configuration
@PropertySource(value = "classpath:security.properties",ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "application.jwt")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtConfig {
    private  String tokenPrefix;
    private  String secureKey;
    private  Integer accessTokenExpirationAfterDays;
    private  Integer accessTokenExpirationAfterMinutes;
    private  Integer refreshTokenExpirationAfterDays;

    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
