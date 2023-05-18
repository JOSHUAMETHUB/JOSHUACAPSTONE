package com.menin.tournamentservice.controller;


import com.menin.tournamentservice.dto.TournamentDto;
import com.menin.tournamentservice.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TournamentDto> addTournament(@RequestBody TournamentDto tournamentDto) {
        return ResponseEntity.ok(tournamentService.addTournament(tournamentDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDto> getTournamentById(@PathVariable int id) {

        return ResponseEntity.ok(tournamentService.getTournamentById(id));
    }

    @GetMapping
    public List<TournamentDto> getTournaments(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String style,
            Pageable pageable) {
        return tournamentService.getTournaments(name, category, style, pageable);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateTournament(@PathVariable Integer id, @RequestBody TournamentDto tournamentDto) {
        return ResponseEntity.ok(tournamentService.updateTournament(id, tournamentDto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteTournament(@PathVariable Integer id) {
        return ResponseEntity.ok(tournamentService.deleteTournament(id));
    }
}
