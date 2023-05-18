package com.menin.teamservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menin.teamservice.constant.ResponseMessage;
import com.menin.teamservice.dao.TeamRepository;
import com.menin.teamservice.dto.PlayerDto;
import com.menin.teamservice.dto.TeamDto;
import com.menin.teamservice.entity.Team;
import com.menin.teamservice.feign.PlayerClient;
import com.menin.teamservice.service.TeamService;
import com.menin.teamservice.util.EntityAndDtoConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamControllerTest {

    private static final String API_VERSION = "/api/v1";

    private static final String GET_ID_BY_NAME_URL = API_VERSION + "/teams/{name}";
    private static final String ADD_TEAM_URL = API_VERSION + "/teams/add";
    private static final String GET_TEAMS_URL = API_VERSION + "/teams";
    private static final String GET_TEAM_BY_ID_URL = API_VERSION + "/teams/team-id/{id}";
    private static final String UPDATE_TEAM_URL = API_VERSION + "/teams/update/{id}";
    private static final String DELETE_TEAM_URL = API_VERSION + "/teams/delete/id/{id}";

    private static final String DELETE_TEAM_URL_BY_NAME = API_VERSION + "/teams/delete/name/{name}";

    @InjectMocks
    private TeamController teamController;

    private MockMvc mockMvc;


    @Autowired
    PlayerClient playerClient;

    @Autowired
    TeamRepository teamRepository;

    @Mock
    private EntityAndDtoConverter entityAndDtoConverter;


    @Mock
    private TeamService teamService;

    private List<TeamDto> teams;
    private TeamDto team1;
    private TeamDto team2;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(teamController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

        team1 = new TeamDto("Team 1", Arrays.asList(new PlayerDto("John", "Doe", "USA", 3)));
        team2 = new TeamDto("Team 2", Arrays.asList(new PlayerDto("Jane", "Doe", "USA", 3)));
        teams = Arrays.asList(team1, team2);
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void deleteTeamByName_returnsOk() throws Exception {
        // Arrange
        String teamName = "Team 1";
        when(teamService.deleteTeamByName(teamName)).thenReturn(ResponseMessage.DELETED);

        // Act and Assert
        mockMvc.perform(delete(DELETE_TEAM_URL_BY_NAME, teamName))
                .andExpect(status().isOk())
                .andExpect(content().string(ResponseMessage.DELETED));
    }


    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void addTeam_returnsOk() throws Exception {
        when(teamService.addTeam(any(TeamDto.class))).thenReturn(ResponseMessage.ADDED + "1");

        mockMvc.perform(post(ADD_TEAM_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(team1)))
                .andExpect(status().isOk())
                .andExpect(content().string(ResponseMessage.ADDED + "1"))
                .andReturn();
    }

    @Test
    public void getId_returnsOk() throws Exception {
        // Arrange
        String teamName = "Team 1";
        int expectedId = 1;
        when(teamService.getId(teamName)).thenReturn(expectedId);

        // Act and Assert
        mockMvc.perform(get(GET_ID_BY_NAME_URL, teamName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedId)).andDo(print());
    }

    @Test
    public void getTeams_returnsOk() throws Exception {
        // Arrange
        int teamId = 1;
        String teamName = "Team 1";
        TeamDto teamDto = new TeamDto();
        teamDto.setName(teamName);
        List<TeamDto> teams = Collections.singletonList(teamDto);
        Pageable pageable = PageRequest.of(0, 10);

        when(teamService.getTeams(null, null, pageable)).thenReturn(teams);

        // Act and Assert
        mockMvc.perform(get(GET_TEAMS_URL)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(teamName)).andDo(print());
    }



    @Test
    public void getTeamById_returnsOk() throws Exception {
        // Arrange
        int teamId = 1;
        TeamDto teamTest = new TeamDto();
        teamTest.setName("Team 1");
        when(teamService.getTeamById(teamId)).thenReturn(teamTest);

        // Act and Assert
        mockMvc.perform(get(GET_TEAM_BY_ID_URL, teamId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(teamTest.getName())).andDo(print());
    }





    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void updateTeam_returnsOk() throws Exception {
        int teamId = 1;
        TeamDto updatedTeam = new TeamDto("Updated Team", Arrays.asList(new PlayerDto("John", "Doe", "USA", 3)));

        when(teamService.editTeam(eq(teamId), any(TeamDto.class))).thenReturn(ResponseMessage.UPDATED);

        mockMvc.perform(put(UPDATE_TEAM_URL, teamId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedTeam)))
                .andExpect(status().isOk())
                .andExpect(content().string(ResponseMessage.UPDATED))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void deleteTeam_returnsOk() throws Exception {
        int teamId = 1;

        when(teamService.deleteTeamById(teamId)).thenReturn(ResponseMessage.DELETED);

        mockMvc.perform(delete(DELETE_TEAM_URL, teamId))
                .andExpect(status().isOk())
                .andExpect(content().string(ResponseMessage.DELETED))
                .andReturn();
    }

    // Utility method to convert object to JSON string
    private String asJsonString(Object object) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
