package com.example.securingweb.security.filters;

import com.example.securingweb.security.cookie.CookieService;
import com.example.securingweb.security.jwt.JsonWebTokenService;
import com.example.securingweb.security.userdetails.CustomUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
public class JavaWebTokenFilter extends OncePerRequestFilter {

    private final JsonWebTokenService jsonWebTokenService;

    private final CustomUserDetailService customUserDetailService;

    private final CookieService cookieService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Optional<String> token = cookieService.getCookieFromRequest(request);

        token.ifPresent(javaWebToken -> {
            try {
                String username = jsonWebTokenService.validateTokenAndGetUsername(javaWebToken);
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken userAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken);
            } catch (Exception ignored) {
            }

        });

        filterChain.doFilter(request, response);
    }
}
