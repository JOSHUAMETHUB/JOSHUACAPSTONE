package com.menin.playerservice.feign;

import com.menin.playerservice.config.FeignClientConfiguration;
import com.menin.playerservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="authentication-service", configuration = FeignClientConfiguration.class)
public interface UserClient {

    @GetMapping("/api/v1/auth/user/email/{email}")
    public UserDto findByEmail(@PathVariable String email);



}
