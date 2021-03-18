package com.example.securingweb.config;

import com.example.securingweb.security.cookie.CookieService;
import com.example.securingweb.security.cookie.encryption.AESCookieEncryptionService;
import com.example.securingweb.security.cookie.encryption.CookieEncryptionService;
import com.example.securingweb.security.filters.JsonWebTokenFilter;
import com.example.securingweb.security.jwt.JsonWebTokenService;
import com.example.securingweb.security.jwt.SecureJsonWebTokenService;
import com.example.securingweb.security.userdetails.CustomUserDetailService;
import com.example.securingweb.services.MetaTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Config {

    @Bean
    public SecureJsonWebTokenService secureJsonWebTokenService(MetaTokenService service) {
        return new SecureJsonWebTokenService(service);
    }

    @Bean
    public JsonWebTokenService jsonWebTokenService() {
        return new JsonWebTokenService();
    }

    @Bean
    public CookieEncryptionService cookieEncryptionService() {
        return new AESCookieEncryptionService();
    }

    @Bean
    public CookieService cookieService(CookieEncryptionService encryptionService) {
        return new CookieService(encryptionService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JsonWebTokenFilter jsonWebTokenFilter(JsonWebTokenService jsonWebTokenService,
                                                 CustomUserDetailService customUserDetailService,
                                                 CookieService cookieService,
                                                 SecureJsonWebTokenService secureJsonWebTokenService) {
        return new JsonWebTokenFilter(jsonWebTokenService, customUserDetailService, cookieService, secureJsonWebTokenService);
    }
}
