package com.example.securingweb.controllers;


import com.example.securingweb.entites.User;
import com.example.securingweb.security.userdetails.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestSecurityController {

    private String response() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();
        return "Hi, " + user;
    }

    @GetMapping(value = "/admin/hi")
    public String sayHiAdministrator() {
        return response();
    }

    @GetMapping(value = "/user/hi")
    public String sayHiUser() {
        return response();
    }

}
