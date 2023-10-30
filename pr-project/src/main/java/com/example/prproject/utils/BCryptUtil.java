package com.example.prproject.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class BCryptUtil {

    public static String getPasswordHash(String password) {
        if (password == null) {
            return null;
        }
        return BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(10, password.toCharArray());
    }

    public static Boolean isVerified(String password, String passwordHash) {
        if (password == null || passwordHash == null) {
            return false;
        }
        return BCrypt.verifyer().verify(password.toCharArray(), passwordHash).verified;
    }
}