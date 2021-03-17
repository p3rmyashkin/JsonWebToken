package com.example.securingweb.security.cookie;

import com.example.securingweb.security.cookie.encryption.CookieEncryptionService;
import com.example.securingweb.security.jwt.JsonWebToken;
import lombok.AllArgsConstructor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@AllArgsConstructor
public class CookieService {

    private final String cookieName = "token";

    private final CookieEncryptionService cookieEncryptionService;

    public Cookie createTokenCookie(String token, int duration) {
        Cookie cookie = new Cookie(cookieName, cookieEncryptionService.encrypt(token));
        cookie.setMaxAge(duration);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    public Cookie createTokenCookie(JsonWebToken jsonWebToken) {
        return createTokenCookie(jsonWebToken.getValue(), jsonWebToken.getDuration());
    }

    public Cookie deleteCookie(String token) {
        return createTokenCookie(token, 0);
    }

    public Optional<String> getCookieFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    String token = cookie.getValue();
                    if (token != null) return Optional.of(cookieEncryptionService.decrypt(token));
                }
            }
        }
        return Optional.empty();
    }
}
