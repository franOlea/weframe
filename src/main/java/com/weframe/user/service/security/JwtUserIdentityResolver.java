package com.weframe.user.service.security;

import org.springframework.security.core.Authentication;

public class JwtUserIdentityResolver implements UserIdentityResolver {
    @Override
    public String resolve(final Authentication authentication) {
        return authentication.getName();
    }
}
