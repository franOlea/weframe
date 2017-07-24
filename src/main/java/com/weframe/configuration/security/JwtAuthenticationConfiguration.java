package com.weframe.configuration.security;

import com.weframe.security.TokenAuthenticationService;
import com.weframe.security.WeFrameAuthenticationProvider;
import com.weframe.user.service.persistence.UserService;
import com.weframe.user.service.security.JwtUserIdentityResolver;
import com.weframe.user.service.security.UserIdentityResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@Profile("jwt")
@SuppressWarnings("unused")
public class JwtAuthenticationConfiguration {

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

    @Bean
    public UserIdentityResolver getJwtUserIdentityResolver() {
        return new JwtUserIdentityResolver();
    }

}
