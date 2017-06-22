package com.weframe.configuration;

import com.auth0.spring.security.api.Auth0SecurityConfig;
import com.weframe.security.Auth0Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration extends Auth0SecurityConfig {

//    @Value(value = "${auth0.apiAudience}")
//    private String apiAudience;
//    @Value(value = "${auth0.issuer}")
//    private String issuer;

    @Bean
    public Auth0Client getAuth0Client() {
        return new Auth0Client(clientId, domain);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().permitAll()
                .antMatchers("/frames").authenticated();
//        JwtWebSecurityConfigurer
//                .forRS256(apiAudience, issuer)
//                .configure(http)
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS, "/frames").permitAll()
//                .antMatchers(HttpMethod.GET, "/frames").permitAll()
//                .antMatchers(HttpMethod.GET, "/photos/**").hasAuthority("read:photos")
//                .antMatchers(HttpMethod.POST, "/photos/**").hasAuthority("create:photos")
//                .antMatchers(HttpMethod.PUT, "/photos/**").hasAuthority("update:photos")
//                .antMatchers(HttpMethod.DELETE, "/photos/**").hasAuthority("delete:photos")
//                .anyRequest().authenticated();
    }

}













