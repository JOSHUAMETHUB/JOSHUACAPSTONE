package com.menin.teamservice.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
@Configuration
public class FeignClientConfiguration {

    @Autowired
    private JwtService jwtService;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // Retrieve the JWT token from the current request header
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // Pass the access token to the Feign client
            template.header("Authorization", "Bearer " + token);
        };
    }



}

