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
        value = "security.enabled",
        havingValue = "true",
        matchIfMissing = true
)
@SuppressWarnings("unused")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";

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
                .antMatchers(HttpMethod.POST, "/login").permitAll()     //Allow login
                .antMatchers(HttpMethod.POST, "/register").permitAll()  //Allow registration
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()     //Allow CORS to everyone
                .antMatchers(HttpMethod.GET, "/generic-product/**").permitAll()
                .antMatchers(HttpMethod.POST, "/generic-product/**").hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.PUT, "/generic-product/**").hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.DELETE, "/generic-product/**").hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.GET, "/users").hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.GET, "/users/me").authenticated()
                .antMatchers("/user-pictures/**").authenticated()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.PUT, "/users").permitAll()
                .antMatchers(HttpMethod.DELETE, "/users").hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.GET, "/frames").permitAll()
                .antMatchers(HttpMethod.POST, "/frames").hasRole(ADMIN_ROLE)
                .antMatchers(HttpMethod.DELETE, "/frames").hasRole(ADMIN_ROLE)
                .anyRequest().authenticated()
                .and()
                // We filter the api/login requests
                .addFilterBefore(
                        new JWTLoginFilter(
                                "/login",
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
        auth.authenticationProvider(authProvider);
    }

}
