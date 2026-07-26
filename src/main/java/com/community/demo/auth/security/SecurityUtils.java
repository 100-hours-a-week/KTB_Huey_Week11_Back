package com.community.demo.auth.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import java.security.Principal;

public class SecurityUtils {
    public static Long resolveAuthentication(Object authentication) {
        if (authentication == null) {
           return 0L;
        } else if (authentication instanceof Authentication) {
            CustomUserDetails user = (CustomUserDetails) ((Authentication) authentication).getPrincipal();
            return user.getUserId();
        } else {
            return 0L;
        }
    }
}
