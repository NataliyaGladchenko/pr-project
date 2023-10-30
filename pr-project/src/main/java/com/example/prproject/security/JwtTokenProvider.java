package com.example.prproject.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;


import java.util.List;

public interface JwtTokenProvider {

    String createToken(String username, List<String> roles);

    Authentication getAuthentication(String token);

    String getUsername(String token);

    String getAuthorities(String token);

    String resolveToken(HttpServletRequest request);

    boolean validateToken(String token);

}
