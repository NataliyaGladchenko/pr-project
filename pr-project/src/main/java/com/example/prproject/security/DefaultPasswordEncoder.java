package com.example.prproject.security;

import com.example.prproject.utils.BCryptUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DefaultPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return BCryptUtil.getPasswordHash(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return BCryptUtil.isVerified(rawPassword.toString(), encodedPassword);
    }
}
