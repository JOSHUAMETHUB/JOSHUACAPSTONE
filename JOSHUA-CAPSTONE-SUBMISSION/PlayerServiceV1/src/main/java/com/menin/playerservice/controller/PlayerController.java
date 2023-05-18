package com.menin.playerservice.controller;

import com.menin.playerservice.dto.PlayerDto;
import com.menin.playerservice.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PlayerController {

    @Autowired
    private Environment environment;

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/players/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addPlayer(@RequestBody PlayerDto playerDto) {


        return ResponseEntity.ok(playerService.addPlayer(playerDto));
    }

    @GetMapping("/players")
    public List<PlayerDto> getPlayers(@RequestParam(required = false) String firstname,
                                      @RequestParam(required = false) String lastname,
                                      @RequestParam(required = false) String country,
                                      @RequestParam(required = false) Integer teamId,
                                      Pageable pageable) {
        
        return playerService.getPlayers(firstname, lastname, country, teamId, pageable);
    }


    @GetMapping("/players/{id}")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable int id) {
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

    @PutMapping("/players/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updatePlayer(@PathVariable int id, @RequestBody PlayerDto playerDto) {
        return ResponseEntity.ok(playerService.updatePlayer(id, playerDto));
    }

    @DeleteMapping("/players/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deletePlayer(@PathVariable int id) {
        return ResponseEntity.ok(playerService.deletePlayer(id));
    }
}
