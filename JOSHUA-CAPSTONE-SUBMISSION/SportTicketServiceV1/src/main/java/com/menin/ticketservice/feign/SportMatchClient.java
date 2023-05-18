package com.menin.ticketservice.feign;

import com.menin.ticketservice.dto.SportMatchDto;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sportmatch-service", configuration = FeignAutoConfiguration.class)
public interface SportMatchClient {
    @GetMapping("/api/v1/sport-match/{id}")
    public SportMatchDto getSportMatchById(@PathVariable Integer id);
}
