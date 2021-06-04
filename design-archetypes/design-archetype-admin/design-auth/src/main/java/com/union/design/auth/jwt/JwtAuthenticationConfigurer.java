package com.union.design.auth.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * @date 2021/5/18
 * @see CsrfConfigurer
 */
public class JwtAuthenticationConfigurer<H extends HttpSecurityBuilder<H>>
        extends AbstractHttpConfigurer<CsrfConfigurer<H>, H> {

    private JwtAuthenticationFilter authenticationFilter;

    @Override
    public void configure(H builder) throws Exception {
        // 获取共享对象
        authenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        JwtAuthenticationFilter filter = postProcess(authenticationFilter);
        // 在LogoutFilter之前添加此filter
        builder.addFilterBefore(filter, LogoutFilter.class);
    }
}
