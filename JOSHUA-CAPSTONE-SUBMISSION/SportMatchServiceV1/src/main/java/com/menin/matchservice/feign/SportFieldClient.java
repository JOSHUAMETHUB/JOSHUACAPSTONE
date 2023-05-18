package com.menin.matchservice.feign;


import com.menin.matchservice.config.FeignClientConfiguration;
import com.menin.matchservice.dto.SportFieldDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sportfield-service",configuration = FeignClientConfiguration.class)
public interface SportFieldClient {

    @GetMapping("/api/v1/sport-fields")
    public ResponseEntity<Page<SportFieldDto>> getSportFields(@RequestParam(required = false) String name, Pageable pageable);


    @GetMapping("/api/v1/sport-fields/{id}")
    public ResponseEntity<SportFieldDto> getSportFieldById(@PathVariable int id);

}
