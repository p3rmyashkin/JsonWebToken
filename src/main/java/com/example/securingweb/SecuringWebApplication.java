package com.example.securingweb;

import com.example.securingweb.security.cookie.encryption.AESCookieEncryptionService;
import com.example.securingweb.services.RoleService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaRepositories
public class SecuringWebApplication {

    @Autowired
    private RoleService roleService;

    public static final String USER_ROLE_NAME = "USER";
    public static final String ADMIN_ROLE_NAME = "ADMIN";


    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(SecuringWebApplication.class, args);
    }


    @PostConstruct
    public void init() {
        roleService.createRole(USER_ROLE_NAME);
        roleService.createRole(ADMIN_ROLE_NAME);
    }

}
