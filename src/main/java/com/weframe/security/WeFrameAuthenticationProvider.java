package com.weframe.security;

import com.weframe.controllers.EmptyResultException;
import com.weframe.user.model.User;
import com.weframe.user.service.persistence.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class WeFrameAuthenticationProvider implements AuthenticationProvider {

    private final static Logger logger = Logger.getLogger(WeFrameAuthenticationProvider.class);

    private final UserService userService;

    public WeFrameAuthenticationProvider(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        try {
            User user = userService.getByLogin(email, password);
            List<GrantedAuthority> grantedAuthentications = new ArrayList<>();
            grantedAuthentications.add(
                    new SimpleGrantedAuthority(
                            user.getRole().getName()
                    )
            );
            return new UsernamePasswordAuthenticationToken(
                    email,
                    password,
                    grantedAuthentications
            );
        } catch (EmptyResultException e) {
            return null;
        } catch (Exception e) {
            logger.error(
                    "There was an unexpected error while trying " +
                            "to fetch the user by login",
                    e
            );
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean support = (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
        return support;
    }

}
