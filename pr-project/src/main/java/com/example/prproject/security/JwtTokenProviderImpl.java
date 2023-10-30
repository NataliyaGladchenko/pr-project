package com.example.prproject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.time.Clock;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class JwtTokenProviderImpl implements JwtTokenProvider {

    private final static Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    private static final String ROLES_ATTRIBUTE = "roles";
    private static final String BEARER_TYPE = "Bearer";

    private long tokenValidityInSeconds;

    private String secret;

    private DefaultUserDetailsManager defaultUserDetailsManager;

    private Clock clock;

    public JwtTokenProviderImpl(@Value("${security.jwt.token.expire-time}") long tokenValidityInSeconds,
                                @Value("${security.jwt.token.secret}") String secret,
                                DefaultUserDetailsManager defaultUserDetailsManager,
                                Clock clock) {
        this.tokenValidityInSeconds = tokenValidityInSeconds;
        this.secret = secret;
        this.defaultUserDetailsManager = defaultUserDetailsManager;
        this.clock = clock;
    }

    @Override
    public String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(ROLES_ATTRIBUTE, roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(clock.instant()))
                .setExpiration(Date.from(clock.instant().plusSeconds(tokenValidityInSeconds)))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = defaultUserDetailsManager.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, StringUtils.EMPTY, userDetails.getAuthorities());
    }

    @Override
    public String getUsername(String token) {
        return parseToken(token).getBody().getSubject();
    }

    @Override
    public String getAuthorities(String token) {
        return parseToken(token).getBody().get(ROLES_ATTRIBUTE).toString();
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(BEARER_TYPE.length()).trim();
        }
        return null;
    }

    @Override
    public boolean validateToken(String token) {
        try {
            return !parseToken(token).getBody().getExpiration().before(new Date(clock.millis()));
        } catch (JwtException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token);
    }
}
