package com.menin.matchservice.controller;

import com.menin.matchservice.dto.SportMatchRequest;
import com.menin.matchservice.dto.SportMatchResponse;
import com.menin.matchservice.service.SportMatchService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sport-match")
@AllArgsConstructor
public class SportMatchController {

    @Autowired
    private final SportMatchService sportMatchService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SportMatchResponse> addSportMatch(@RequestBody SportMatchRequest sportMatchRequest) {
        return ResponseEntity.ok(sportMatchService.addSportMatch(sportMatchRequest));
    }


    @GetMapping("/{id}")
    public SportMatchResponse getSportMatchById(@PathVariable Integer id) {
        return sportMatchService.getSportMatchById(id);
    }

    @GetMapping
    public Page<SportMatchResponse> getSportMatches(

            @RequestParam(required = false) Integer field,
            @RequestParam(required = false) Integer tournament,
            @RequestParam(required = false) List<String> players,
            @RequestParam(required = false) List<String> teams,
            @RequestParam(required = false) Date dateTime,
            Pageable pageable) {
        return sportMatchService.getSportMatches(field, tournament, players, teams, dateTime, pageable);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SportMatchResponse updateSportMatch(@PathVariable int id, @RequestBody SportMatchRequest sportMatchRequest) {
        return sportMatchService.updateSportMatch(id, sportMatchRequest);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteSportMatch(@PathVariable int id) {
        return sportMatchService.deleteSportMatch(id);
    }


}
