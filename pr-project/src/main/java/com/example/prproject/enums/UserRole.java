package com.example.prproject.enums;

import lombok.Getter;
import lombok.experimental.Accessors;

import static com.example.prproject.services.UserService.ROLE_PREFIX;

@Getter
@Accessors(fluent = true)
public enum UserRole {

    ADMIN,
    USER;

    @Override
    public String toString() {
        return ROLE_PREFIX + this.name();
    }
}
