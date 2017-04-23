package com.weframe.configuration.security;

import com.weframe.security.JWTAuthenticationFilter;
import com.weframe.security.JWTLoginFilter;
import com.weframe.security.TokenAuthenticationService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(
        value = "security.enabled=true",
        matchIfMissing = true
)
@SuppressWarnings("unused")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenAuthenticationService tokenAuthenticationService;
    private final AuthenticationProvider authProvider;

    public WebSecurityConfiguration(final AuthenticationProvider authProvider,
                                    final TokenAuthenticationService tokenAuthenticationService) {
        this.authProvider = authProvider;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                // We filter the api/login requests
                .addFilterBefore(
                        new JWTLoginFilter(
                                "/login",
                                authenticationManager(),
                                tokenAuthenticationService
                        ),
                        UsernamePasswordAuthenticationFilter.class)
                // And filter other requests to check the presence
                // of JWT in header
                .addFilterBefore(
                        new JWTAuthenticationFilter(
                                tokenAuthenticationService
                        ),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        // Create a default account
//        auth.inMemoryAuthentication()
//                .withUser("john.doe@email.com")
//                .password("password")
//                .roles("ADMIN");
        auth.authenticationProvider(authProvider);
    }

}
