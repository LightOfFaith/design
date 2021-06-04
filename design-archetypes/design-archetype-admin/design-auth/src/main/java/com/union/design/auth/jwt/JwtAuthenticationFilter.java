package com.union.design.auth.jwt;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final RequestMatcher requireAuthenticationRequestMatcher;
    private final JwtTokenManager tokenManager;
    private AuthenticationManager authenticationManager;
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;


    public JwtAuthenticationFilter(JwtTokenManager tokenManager) {
        this.tokenManager = tokenManager;
        requireAuthenticationRequestMatcher = new RequestHeaderRequestMatcher("Authorization");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!requireAuthenticationRequestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = null;
        AuthenticationException exception = null;
        String authorization = StringUtils.removeStart("Bearer ", request.getHeader("Authorization"));
        if (StringUtils.isNotBlank(authorization)) {
            authentication = authenticationManager.authenticate(tokenManager.getAuthentication(authorization));
        } else {
            exception = new InsufficientAuthenticationException("token is empty");
        }
        if (Objects.nonNull(authentication)) {
            authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        } else {
            authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
        }
    }

    public JwtAuthenticationFilter setAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        return this;
    }

    public JwtAuthenticationFilter setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        return this;
    }
}