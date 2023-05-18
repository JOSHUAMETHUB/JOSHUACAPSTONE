package com.menin.matchservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menin.matchservice.exception.ServiceNotAvailableException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;


@Configuration
public class ApplicationConfig {


    private final RestTemplate restTemplate;


    @Autowired
    public ApplicationConfig(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }



    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {


            // retrieve user details from authentication service
            Map<String, Object> userMap =  Collections.emptyMap();

            try {
               userMap = restTemplate.getForObject("http://AUTHENTICATION-SERVICE/api/v1/auth/user/email/" + username, Map.class);
            }catch (HttpClientErrorException e){
                throw new ServiceNotAvailableException("Either no user is found for that token or Authentication service is not available");
            }


            if(userMap.isEmpty())
                throw new ServiceNotAvailableException("Either no user is found for that token or Authentication service is not available");


            Map<String, Object> resultMap =  userMap;

            // create UserDetails object from retrieved user details
            return new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return Collections.singleton(new SimpleGrantedAuthority((String) resultMap.get("role")));
                }

                @Override
                public String getPassword() {
                    return (String) resultMap.get("password");
                }

                @Override
                public String getUsername() {
                    return (String) resultMap.get("email");
                }

                @Override
                public boolean isAccountNonExpired() {
                    return true;
                }

                @Override
                public boolean isAccountNonLocked() {
                    return true;
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return true;
                }

                @Override
                public boolean isEnabled() {
                    return true;
                }
            };


        };
    }
}
