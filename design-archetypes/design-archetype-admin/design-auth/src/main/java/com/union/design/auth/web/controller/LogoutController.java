package com.union.design.auth.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class LogoutController {

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            log.debug("Invalidating session: " + session.getId());
            session.invalidate();
        }
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication((Authentication) null);
        SecurityContextHolder.clearContext();
        return "";
    }

}