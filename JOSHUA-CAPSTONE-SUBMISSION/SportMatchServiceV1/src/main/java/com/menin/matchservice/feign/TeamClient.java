package com.menin.matchservice.feign;


import com.menin.matchservice.config.FeignClientConfiguration;
import com.menin.matchservice.dto.TeamDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "team-service",configuration = FeignClientConfiguration.class)
public interface TeamClient {

    @GetMapping("/api/v1/teams/team-id/{id}")
    public TeamDto getTeamById(@PathVariable Integer id);


    @GetMapping("/api/v1/teams")
    public ResponseEntity<List<TeamDto>> getTeams(@RequestParam(required = false) Integer id, @RequestParam(required = false) String name, Pageable pageable);

}
