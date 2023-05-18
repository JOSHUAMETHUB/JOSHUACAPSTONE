package com.menin.teamservice.feign;

import com.menin.teamservice.config.FeignClientConfiguration;
import com.menin.teamservice.dto.PlayerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name= "player-service", configuration = FeignClientConfiguration.class)
public interface PlayerClient {

    @GetMapping("/api/v1/players")
    public List<PlayerDto> getPlayers(@RequestParam(required = false) String firstname,
                                      @RequestParam(required = false) String lastname,
                                      @RequestParam(required = false) String country,
                                      @RequestParam(required = false) Integer teamId,
                                      Pageable pageable);


    @GetMapping("/api/v1/players/say/hello")
    public String hello();
}
