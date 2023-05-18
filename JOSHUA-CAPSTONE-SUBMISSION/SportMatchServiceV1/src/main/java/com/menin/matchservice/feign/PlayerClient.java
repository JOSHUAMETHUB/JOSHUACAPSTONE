package com.menin.matchservice.feign;


import com.menin.matchservice.config.FeignClientConfiguration;
import com.menin.matchservice.dto.PlayerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "player-service", configuration = FeignClientConfiguration.class)
public interface PlayerClient {
    @GetMapping("/api/v1//players/{id}")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable int id);
}