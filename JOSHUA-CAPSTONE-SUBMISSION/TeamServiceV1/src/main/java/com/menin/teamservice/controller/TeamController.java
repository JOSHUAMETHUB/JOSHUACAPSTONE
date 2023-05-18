package com.menin.teamservice.controller;


import com.menin.teamservice.dto.TeamDto;
import com.menin.teamservice.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
@AllArgsConstructor
public class TeamController {

    private final TeamService teamService;


    @GetMapping("/{name}")
    public ResponseEntity<Integer> getId(@PathVariable String name) {
        return ResponseEntity.ok(teamService.getId(name));
    }



    @GetMapping
    public ResponseEntity<List<TeamDto>> getTeams(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name, Pageable pageable) {
        return ResponseEntity.ok(teamService.getTeams(id, name, pageable));
    }

    @GetMapping("/team-id/{id}")
    public TeamDto getTeamById( @PathVariable Integer id){
        return teamService.getTeamById(id);
    }


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addTeam(@RequestBody TeamDto teamDto) {
        return ResponseEntity.ok(teamService.addTeam(teamDto));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> editTeam(@PathVariable int id, @RequestBody TeamDto teamDto) {
        return ResponseEntity.ok(teamService.editTeam(id, teamDto));
    }

    @DeleteMapping("/delete/id/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteTeamById(@PathVariable int id) {
        return ResponseEntity.ok(teamService.deleteTeamById(id));
    }

    @DeleteMapping("/delete/name/{name}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteTeamByName(@PathVariable String name) {
        return ResponseEntity.ok(teamService.deleteTeamByName(name));
    }
}
