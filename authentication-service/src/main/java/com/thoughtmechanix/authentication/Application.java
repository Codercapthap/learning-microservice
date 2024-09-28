package com.thoughtmechanix.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class Application {
    @RequestMapping(value = {"/user"}, produces = "application/json")
    public Map<String, Object> user(JwtAuthenticationToken jwtAuthentication) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user", jwtAuthentication.getPrincipal());
        userInfo.put("authorities", jwtAuthentication.getAuthorities());
        return userInfo;
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
