package com.menin.teamservice.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.menin.teamservice.dto.TeamDto;
import com.menin.teamservice.entity.Team;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class EntityAndDtoConverter {
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    @Autowired
    public EntityAndDtoConverter(ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }


    public String toJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.info("toJson Process failed : "+ e);
        }
        return null;
    }

    //IF ERROR SEE THIS

    public TeamDto convertToTeamDto(Team team) {
        TeamDto teamDto = modelMapper.map(team, TeamDto.class);
        if (team.getPlayers() == null) {
            teamDto.setPlayers(Collections.emptyList());
        }
        return teamDto;
    }
    public Team convertToTeamEntity(TeamDto teamDto){
        return modelMapper.map(teamDto, Team.class);
    }

    public Page<TeamDto> convertToTeamDtoPage(Page<Team> teams){
        TypeToken<Page<TeamDto>> typeToken = new TypeToken<Page<TeamDto>>(){};
        return modelMapper.map(teams, typeToken.getType());
    }

    public List<TeamDto> convertTeamDtoList(List<Team> teams) {
        TypeToken<List<TeamDto>> typeToken = new TypeToken<List<TeamDto>>() {
        };
        return modelMapper.map(teams, typeToken.getType());
    }

}
