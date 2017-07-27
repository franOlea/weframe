package com.weframe.configuration.security;

import com.weframe.security.JWTAuthenticationFilter;
import com.weframe.security.JWTLoginFilter;
import com.weframe.security.TokenAuthenticationService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(
        value = "security.enabled",
        havingValue = "false"
)
@SuppressWarnings("unused")
public class NoSecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final TokenAuthenticationService tokenAuthenticationService;
    private final AuthenticationProvider authenticationProvider;

    public NoSecurityConfiguration(final AuthenticationProvider authProvider,
                                   final TokenAuthenticationService tokenAuthenticationService) {
        this.authenticationProvider = authProvider;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                // We filter the api/login requests
                .addFilterBefore(
                        new JWTLoginFilter(
                                "/authentication/login",
                                authenticationManager(),
                                tokenAuthenticationService
                        ),
                        UsernamePasswordAuthenticationFilter.class
                )
                // And filter other requests to check the presence
                // of JWT in header
                .addFilterBefore(
                        new JWTAuthenticationFilter(
                                tokenAuthenticationService
                        ),
                        UsernamePasswordAuthenticationFilter.class
                );
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

}
