package com.example.securingweb.security.cookie.encryption;

public interface CookieEncryptionService {

    String encrypt(String input);

    String decrypt(String input);

}
