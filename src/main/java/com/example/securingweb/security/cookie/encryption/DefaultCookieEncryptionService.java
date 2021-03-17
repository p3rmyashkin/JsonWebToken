package com.example.securingweb.security.cookie.encryption;

public class DefaultCookieEncryptionService implements CookieEncryptionService {
    @Override
    public String encrypt(String input) {
        return input;
    }

    @Override
    public String decrypt(String input) {
        return input;
    }
}
