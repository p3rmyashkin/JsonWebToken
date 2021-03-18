package com.example.securingweb.controllers;


import com.example.securingweb.entites.User;
import com.example.securingweb.security.cookie.CookieService;
import com.example.securingweb.security.jwt.SecureJsonWebTokenService;
import com.example.securingweb.security.userdetails.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class TestSecurityController {

    @Autowired
    private SecureJsonWebTokenService secureJsonWebTokenService;

    @Autowired
    private CookieService cookieService;

    @GetMapping(value = "/hi")
    private String response(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();
        response.addCookie(cookieService.createOncePerRequestCookie(secureJsonWebTokenService.generateOncePerRequestToken()));
        return "Hi, " + user;
    }

}
