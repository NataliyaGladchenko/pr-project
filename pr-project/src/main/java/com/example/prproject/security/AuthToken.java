package com.example.prproject.security;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter
@Accessors(chain = true)
public class AuthToken {

    private String username;
    private String token;
    private Long assignmentId;
}
