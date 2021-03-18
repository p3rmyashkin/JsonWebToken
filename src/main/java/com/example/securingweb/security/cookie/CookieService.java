package com.example.securingweb.security.cookie;

import com.example.securingweb.security.cookie.encryption.CookieEncryptionService;
import com.example.securingweb.security.jwt.JsonWebToken;
import lombok.AllArgsConstructor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@AllArgsConstructor
public class CookieService {

    private final String LONG_TERM_COOKIE = "long_term_cookie";
    private final String ONCE_PER_REQUEST_COOKIE = "once_per_request";

    private final CookieEncryptionService cookieEncryptionService;

    public Cookie createLongTermCookie(String token, int duration) {
        return createCookie(LONG_TERM_COOKIE, token, duration);
    }

    public Cookie createOncePerRequestCookie(String token, int duration) {
        return createCookie(ONCE_PER_REQUEST_COOKIE, token, duration);
    }

    private Cookie createCookie(String type, String token, int duration) {
        Cookie cookie = new Cookie(type, cookieEncryptionService.encrypt(token));
        cookie.setMaxAge(duration);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }


    public Cookie createOncePerRequestCookie(JsonWebToken jsonWebToken) {
        return createOncePerRequestCookie(jsonWebToken.getValue(), jsonWebToken.getDuration());
    }

    public Cookie createLongTermCookie(JsonWebToken jsonWebToken) {
        return createLongTermCookie(jsonWebToken.getValue(), jsonWebToken.getDuration());
    }

    public Cookie deleteCookie(String type, String token) {
        return createCookie(type, token, 0);
    }

    public Optional<String> getCookie(HttpServletRequest request, String type) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (type.equals(cookie.getName())) {
                    String token = cookie.getValue();
                    if (token != null) return Optional.of(cookieEncryptionService.decrypt(token));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<String> getLongTermCookie(HttpServletRequest request) {
        return getCookie(request, LONG_TERM_COOKIE);
    }

    public Optional<String> getOncePerRequestCookie(HttpServletRequest request) {
        return getCookie(request, ONCE_PER_REQUEST_COOKIE);
    }
}
