package com.menin.matchservice.feign;


import com.menin.matchservice.config.FeignClientConfiguration;
import com.menin.matchservice.dto.TournamentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tournament-service",configuration = FeignClientConfiguration.class)
public interface TournamentClient {

    @GetMapping("/api/v1/tournaments/{id}")
    public ResponseEntity<TournamentDto> getTournamentById(@PathVariable int id);
}
