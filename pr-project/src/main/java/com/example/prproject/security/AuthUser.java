package com.example.prproject.security;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter
@Accessors(chain = true)
public class AuthUser {

    private String username;
    private String password;

}
