package com.weframe.configuration.security;

import com.weframe.security.TokenAuthenticationService;
import com.weframe.security.WeFrameAuthenticationProvider;
import com.weframe.user.service.persistence.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(
        value = "security.enabled=true",
        matchIfMissing = true
)
@SuppressWarnings("unused")
public class AuthenticationConfiguration {

    @Value("${security.jwt.expiration.time}")
    private long expirationTime;
    @Value("${security.jwt.secret}")
    private String secret;
    @Value("${security.jwt.token.prefix}")
    private String tokenPrefix;
    @Value("${security.jwt.header.name}")
    private String headerName;

    @Bean
    public TokenAuthenticationService getTokenAuthenticationService() {
        return new TokenAuthenticationService(
                expirationTime,
                secret,
                tokenPrefix,
                headerName
        );
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider(
            final UserService userService) {
        return new WeFrameAuthenticationProvider(userService);
    }

}
