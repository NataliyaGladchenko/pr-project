package com.example.prproject.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@JsonIgnoreProperties(value = {"password"})
public class PlatformUser extends User {

    private Integer userId;

    public PlatformUser(String username, String password, Integer userId,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    @Override
    public void eraseCredentials() {

    }
}
