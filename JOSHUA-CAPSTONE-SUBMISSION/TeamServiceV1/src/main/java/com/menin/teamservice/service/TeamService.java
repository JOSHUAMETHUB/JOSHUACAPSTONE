package com.menin.teamservice.service;

import com.menin.teamservice.dto.TeamDto;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeamService {
    public List<TeamDto> getTeams(Integer id, String name, Pageable pageable);
    public String addTeam(TeamDto teamDto);

    public TeamDto getTeamById(Integer id);
    public String editTeam(int id, TeamDto teamDto);
    public String deleteTeamById(int id);
    public String deleteTeamByName(String name);

    public Integer getId(String name);


}
