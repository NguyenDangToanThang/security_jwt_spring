package com.socialnetwork.socialbackend.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CustomSuccessHandle implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var user = SecurityContextHolder.getContext().getAuthentication();
        String role = user.getAuthorities().iterator().next().getAuthority();
        if(role.equals("ROLE_ADMIN")) {
            response.sendRedirect("/home-admin");
        } else if(role.equals("ROLE_USER")){
            response.sendRedirect("/home-user");
        } else {
            response.sendRedirect("/error-page");
        }
    }
}
