package com.menin.teamservice.service;

import com.menin.teamservice.constant.ResponseMessage;
import com.menin.teamservice.constant.TeamExceptionMessage;
import com.menin.teamservice.dao.TeamRepository;
import com.menin.teamservice.dto.PlayerDto;
import com.menin.teamservice.dto.TeamDto;
import com.menin.teamservice.entity.Team;
import com.menin.teamservice.exception.EmptyFieldException;
import com.menin.teamservice.exception.PlayerServiceException;
import com.menin.teamservice.exception.TeamAlreadyExistException;
import com.menin.teamservice.exception.TeamNotFoundException;
import com.menin.teamservice.feign.PlayerClient;
import com.menin.teamservice.util.EntityAndDtoConverter;
import feign.FeignException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TeamServiceImpl implements TeamService {

    UriComponentsBuilder uriComponentsBuilder;


    private final PlayerClient playerClient;


    private final TeamRepository teamRepository;
    private final EntityAndDtoConverter entityAndDtoConverter;


    @Autowired
    public TeamServiceImpl(PlayerClient playerClient, TeamRepository teamRepository, EntityAndDtoConverter entityAndDtoConverter) {
        this.playerClient = playerClient;
        this.teamRepository = teamRepository;
        this.entityAndDtoConverter = entityAndDtoConverter;


    }



    @Override
    public List<TeamDto> getTeams(Integer id, String name, Pageable pageable) {
        List<Team> teams;

        if (id != null) {
            teams = Collections.singletonList(teamRepository.findById(id)
                    .orElseThrow(() -> new TeamNotFoundException(TeamExceptionMessage.TEAM_NOT_FOUND)));
        } else if (!StringUtils.isEmpty(name) || name != null) {
            teams = Collections.singletonList(teamRepository.findByName(name)
                    .orElseThrow(() -> new TeamNotFoundException(TeamExceptionMessage.TEAM_NOT_FOUND)));
        } else {
            int pageSize = pageable.getPageSize();
            int pageNum = pageable.getPageNumber();
            Sort pageSort = pageable.getSort();
            PageRequest pageRequest = PageRequest.of(pageNum, pageSize, pageSort);
            teams = teamRepository.findAll(pageRequest).getContent();
        }

        return teams.stream()
                .map(this::convertTeamWithPlayers)
                .collect(Collectors.toList());
    }

    private TeamDto convertTeamWithPlayers(Team team) {
        try {
            List<PlayerDto> playerDtos = playerClient.getPlayers(null, null, null, team.getId(), PageRequest.of(0, 20));
            if (playerDtos != null) {
                team.setPlayers(playerDtos);
            }
        } catch (FeignException e) {
            if (e.status() == 500) {
                String errorMessage = e.contentUTF8();
                if (errorMessage.contains("PlayerNotFoundException")) {
                    team.setPlayers(Collections.emptyList());
                } else {
                    throw new PlayerServiceException("There is an error in PlayerService Side!");
                }
            }
        }
        return entityAndDtoConverter.convertToTeamDto(team);
    }



    @Override
    public String addTeam(TeamDto teamDto) {

        if (StringUtils.isBlank(teamDto.getName())) {
            throw new EmptyFieldException("Team name cannot be blank or contain only whitespaces.");
        }

        Team checkExist = teamRepository.findByName(teamDto.getName()).orElse(null);

        if (checkExist != null)
            throw new TeamAlreadyExistException(TeamExceptionMessage.TEAM_EXIST);


        return ResponseMessage.ADDED + teamRepository.save(entityAndDtoConverter.convertToTeamEntity(teamDto)).getId();
    }


    @Override
    public String editTeam(int id, TeamDto teamDto) {

        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(TeamExceptionMessage.TEAM_NOT_FOUND));

        if (teamDto.getName() != null)
            team.setName(teamDto.getName());
        if (teamDto.getPlayers() != null)
            team.setPlayers(teamDto.getPlayers());

        teamRepository.save(team);

        return ResponseMessage.UPDATED;
    }

    @Override
    public String deleteTeamById(int id) {

        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(TeamExceptionMessage.TEAM_NOT_FOUND));

        teamRepository.delete(team);

        return ResponseMessage.DELETED;
    }

    @Override
    public String deleteTeamByName(String name) {
        Team team = teamRepository.findByName(name).orElseThrow(() -> new TeamNotFoundException(TeamExceptionMessage.TEAM_NOT_FOUND));

        teamRepository.delete(team);
        return ResponseMessage.DELETED;
    }

    @Override
    public Integer getId(String name) {
        return teamRepository.findByName(name).orElseThrow(() -> new TeamNotFoundException(TeamExceptionMessage.TEAM_NOT_FOUND)).getId();
    }

    @Override
    public TeamDto getTeamById(Integer id){

        Team team = teamRepository.findById(id).orElseThrow(()-> new TeamNotFoundException(TeamExceptionMessage.TEAM_NOT_FOUND));

        TeamDto teamDto = entityAndDtoConverter.convertToTeamDto(team);


        List<PlayerDto> playerDtos = playerClient.getPlayers(null, null, null, id, PageRequest.of(0, 20));
        if (playerDtos != null) {
            teamDto.setPlayers(playerDtos);
        }
        return teamDto;
    }


}
