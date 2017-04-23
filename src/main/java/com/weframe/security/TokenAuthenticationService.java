package com.weframe.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;

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

    void addAuthentication(HttpServletResponse res, String email) {
        String JWT = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        res.addHeader(headerName, tokenPrefix + " " + JWT);
    }

    Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(headerName);
        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(tokenPrefix, ""))
                    .getBody()
                    .getSubject();

            return user != null ?
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            emptyList()
                    ) : null;
        }
        return null;
    }

}

