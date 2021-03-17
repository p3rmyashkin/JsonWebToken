package com.example.securingweb.controllers;

import com.example.securingweb.entites.Role;
import com.example.securingweb.entites.User;
import com.example.securingweb.security.cookie.CookieService;
import com.example.securingweb.security.jwt.JsonWebTokenService;
import com.example.securingweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RoleController {

    @Autowired
    private UserService userService;

    @Autowired
    private JsonWebTokenService jsonWebTokenService;

    @Autowired
    private CookieService cookieService;

    @GetMapping(value = "/user/role")
    public Optional<Role> getUserRole(@AuthenticationPrincipal User user) {
        return Optional.of(user.getRole());
    }
}
