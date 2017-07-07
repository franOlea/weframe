package com.weframe.user.service.security;

import org.springframework.security.core.Authentication;

@FunctionalInterface
public interface UserIdentityResolver {
    String resolve(final Authentication authentication);
}
