package com.weframe.security;

import com.weframe.user.service.persistence.RoleRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class TokenAuthenticationService {

    private final long expirationTime;
    private final String secret;
    private final String tokenPrefix;
    private final String headerName;

    public TokenAuthenticationService(final long expirationTime,
                                      final String secret,
                                      final String tokenPrefix,
                                      final String headerName) {
        this.expirationTime = expirationTime;
        this.secret = secret;
        this.tokenPrefix = tokenPrefix;
        this.headerName = headerName;
    }

    void addAuthentication(HttpServletResponse res, Authentication authentication) {
        String email = authentication.getName();
        Optional<? extends GrantedAuthority> roleAuthority = authentication.getAuthorities().stream().findAny();
        String roleName = (roleAuthority.isPresent() ? roleAuthority.get().getAuthority() : RoleRepository.DEFAULT_ROLE_NAME);

        String JWT = Jwts.builder()
                .setSubject(authentication.getName() + "\\\\" + roleName)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        res.addHeader(headerName, tokenPrefix + " " + JWT);
    }

    Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(headerName);
        if (token != null) {
            // parse the token.
            String[] userAndRole = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(tokenPrefix, ""))
                    .getBody()
                    .getSubject()
                    .split("\\\\");

            return new UsernamePasswordAuthenticationToken(
                    userAndRole[0],
                    null,
                    Collections.singleton(
                            new SimpleGrantedAuthority(userAndRole[2])
                    )
            );
        }
        return null;
    }

}

