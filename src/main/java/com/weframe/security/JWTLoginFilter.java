package com.weframe.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
    private static final String ORIGIN = "Origin";
    private final TokenAuthenticationService tokenAuthenticationService;

    public JWTLoginFilter(final String url,
                          final AuthenticationManager authManager,
                          final TokenAuthenticationService tokenAuthenticationService) {
        super(new AntPathRequestMatcher(url));
        this.tokenAuthenticationService = tokenAuthenticationService;
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest req,
                                                final HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
        if (req.getHeader(ORIGIN) != null) {
            String origin = req.getHeader(ORIGIN);
            res.addHeader("Access-Control-Allow-Origin", origin);
            res.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            res.addHeader("Access-Control-Allow-Credentials", "true");
            res.addHeader("Access-Control-Allow-Headers",
            req.getHeader("Access-Control-Request-Headers"));
            res.addHeader("Access-Control-Expose-Headers", "Authorization");
        }
        if (req.getMethod().equals("OPTIONS")) {
            res.getWriter().print("OK");
            res.getWriter().flush();
            return null;
        }
        AccountCredentials credentials = new ObjectMapper()
                .readValue(req.getInputStream(), AccountCredentials.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest req,
            final HttpServletResponse res, FilterChain chain,
            final Authentication auth) throws IOException, ServletException {
        tokenAuthenticationService
                .addAuthentication(res, auth);
    }

}
