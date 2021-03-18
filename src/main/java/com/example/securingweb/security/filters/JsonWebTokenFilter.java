package com.example.securingweb.security.filters;

import com.example.securingweb.security.cookie.CookieService;
import com.example.securingweb.security.jwt.JsonWebTokenService;
import com.example.securingweb.security.jwt.SecureJsonWebTokenService;
import com.example.securingweb.security.userdetails.CustomUserDetailService;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class JsonWebTokenFilter extends OncePerRequestFilter {

    private final JsonWebTokenService jsonWebTokenService;

    private final CustomUserDetailService customUserDetailService;

    private final CookieService cookieService;

    private final SecureJsonWebTokenService secureJsonWebTokenService;

    public JsonWebTokenFilter(JsonWebTokenService jsonWebTokenService, CustomUserDetailService customUserDetailService, CookieService cookieService, SecureJsonWebTokenService secureJsonWebTokenService) {
        this.jsonWebTokenService = jsonWebTokenService;
        this.customUserDetailService = customUserDetailService;
        this.cookieService = cookieService;
        this.secureJsonWebTokenService = secureJsonWebTokenService;
    }


    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        Optional<String> optionalToken = cookieService.getOncePerRequestCookie(request);

        optionalToken.ifPresent(oncePerRequestToken -> {
            if (secureJsonWebTokenService.validateToken(oncePerRequestToken)) {
                Optional<String> token = cookieService.getLongTermCookie(request);
                token.ifPresent(javaWebToken -> {
                    try {
                        String username = jsonWebTokenService.validateTokenAndGetUsername(javaWebToken);
                        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken userAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken);
                    } catch (Exception ignored) {
                    }
                });
            }
        });


        filterChain.doFilter(request, response);
    }
}
