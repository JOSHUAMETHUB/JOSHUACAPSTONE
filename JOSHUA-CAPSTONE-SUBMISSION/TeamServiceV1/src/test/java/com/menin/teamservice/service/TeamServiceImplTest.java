package com.menin.teamservice.service;

import com.menin.teamservice.constant.ResponseMessage;
import com.menin.teamservice.dao.TeamRepository;
import com.menin.teamservice.dto.PlayerDto;
import com.menin.teamservice.dto.TeamDto;
import com.menin.teamservice.entity.Team;
import com.menin.teamservice.exception.EmptyFieldException;
import com.menin.teamservice.exception.TeamAlreadyExistException;
import com.menin.teamservice.exception.TeamNotFoundException;
import com.menin.teamservice.feign.PlayerClient;
import com.menin.teamservice.util.EntityAndDtoConverter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = TeamServiceImpl.class)
public class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private EntityAndDtoConverter entityAndDtoConverter;

    @Mock
    private PlayerClient playerClient;

    @InjectMocks
    private TeamServiceImpl teamService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getTeamsNoFilters() {
        // Prepare mock data
        List<Team> teams = new ArrayList<>();
        teams.add(new Team(1, "Team 1", null));
        teams.add(new Team(2, "Team 2", null));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));


        Page<Team> teamPage = new PageImpl<>(teams);

        // Configure mock behavior
        when(teamRepository.findAll(any(Pageable.class))).thenReturn(teamPage);
        when(playerClient.getPlayers(any(), any(), any(), any(), any())).thenReturn(Collections.emptyList());
        when(entityAndDtoConverter.convertToTeamDto(any(Team.class))).thenReturn(new TeamDto());

        // Create the instance of TeamServiceImpl and inject the mocks
        TeamServiceImpl teamService = new TeamServiceImpl(playerClient, teamRepository, entityAndDtoConverter);

        // Call the method to test
        List<TeamDto> result = teamService.getTeams(null, null, pageable);

        // Assert the result
        assertEquals(2, result.size());
        verify(teamRepository, times(1)).findAll(any(Pageable.class));
        verify(playerClient, times(2)).getPlayers(any(), any(), any(), any(), any());
        verify(entityAndDtoConverter, times(2)).convertToTeamDto(any(Team.class));
    }

    @Test
    public void getTeamsHasId() {
        // Arrange
        Integer id = 1;
        String name = null;
        Pageable pageable = mock(Pageable.class);

        Team team = new Team();
        team.setId(1);

        when(teamRepository.findById(id)).thenReturn(Optional.of(team));
        when(entityAndDtoConverter.convertToTeamDto(team)).thenReturn(new TeamDto());

        // Act
        List<TeamDto> result = teamService.getTeams(id, name, pageable);

        // Assert
        assertEquals(1, result.size());
        verify(teamRepository, times(1)).findById(id);
        verify(entityAndDtoConverter, times(1)).convertToTeamDto(team);
    }

    @Test
    public void getTeamsHasName() {
        // Arrange
        Integer id = null;
        String name = "Team A";
        Pageable pageable = mock(Pageable.class);

        Team team = new Team();
        team.setId(1);

        when(teamRepository.findByName(name)).thenReturn(Optional.of(team));

        List<PlayerDto> playerDtos = new ArrayList<>();
        when(playerClient.getPlayers(null, null, null, team.getId(), PageRequest.of(0, 20))).thenReturn(playerDtos);

        when(entityAndDtoConverter.convertToTeamDto(team)).thenReturn(new TeamDto());

        // Act
        List<TeamDto> result = teamService.getTeams(id, name, pageable);

        // Assert
        assertEquals(1, result.size());
        verify(teamRepository, times(1)).findByName(name);
        verify(playerClient, times(1)).getPlayers(null, null, null, team.getId(), PageRequest.of(0, 20));
        verify(entityAndDtoConverter, times(1)).convertToTeamDto(team);
    }


    @Test
    public void addTeam() {

        // Arrange
        TeamDto teamDto = new TeamDto();
        teamDto.setName("Team A");

        Team team = new Team();
        team.setName("Team A");

        when(teamRepository.findByName(teamDto.getName())).thenReturn(Optional.empty());
        when(entityAndDtoConverter.convertToTeamEntity(teamDto)).thenReturn(team);
        when(teamRepository.save(team)).thenReturn(team);

        // Act
        String result = teamService.addTeam(teamDto);

        // Assert
        assertEquals(ResponseMessage.ADDED + team.getId(), result);
        verify(teamRepository, times(1)).findByName(teamDto.getName());
        verify(entityAndDtoConverter, times(1)).convertToTeamEntity(teamDto);
        verify(teamRepository, times(1)).save(team);
    }

    @Test(expected = EmptyFieldException.class)
    public void addTeamInvalidField() {
        // Arrange
        TeamDto teamDto = new TeamDto();
        teamDto.setName("");

        // Act
        teamService.addTeam(teamDto);
    }

    @Test(expected = TeamAlreadyExistException.class)
    public void addExistingTeam() {
        // Arrange
        TeamDto teamDto = new TeamDto();
        teamDto.setName("Team A");

        Team team = new Team();
        team.setName("Team A");

        when(teamRepository.findByName(teamDto.getName())).thenReturn(Optional.of(team));

        // Act
        teamService.addTeam(teamDto);
    }

    @Test
    public void testEditTeam() {
        int teamId = 1;

        TeamDto teamDto = new TeamDto();
        teamDto.setName("Updated Team Name");

        Team team = new Team();
        team.setId(teamId);
        team.setName("Original Team Name");

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        String expectedResponse = ResponseMessage.UPDATED;

        String actualResponse = teamService.editTeam(teamId, teamDto);

        assertEquals(expectedResponse, actualResponse);
        assertEquals("Updated Team Name", team.getName());

        verify(teamRepository, times(1)).findById(teamId);
        verify(teamRepository, times(1)).save(team);
    }



    @Test
    public void deleteTeamById() {
        int teamId = 1;

        Team team = new Team();
        team.setId(teamId);

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        String expectedResponse = ResponseMessage.DELETED;

        String actualResponse = teamService.deleteTeamById(teamId);

        assertEquals(expectedResponse, actualResponse);

        verify(teamRepository, times(1)).findById(teamId);
        verify(teamRepository, times(1)).delete(team);
    }

    @Test
    public void deleteTeamByName() {
        String teamName = "Team 1";

        Team team = new Team();
        team.setName(teamName);

        when(teamRepository.findByName(teamName)).thenReturn(Optional.of(team));

        String expectedResponse = ResponseMessage.DELETED;

        String actualResponse = teamService.deleteTeamByName(teamName);

        assertEquals(expectedResponse, actualResponse);

        verify(teamRepository, times(1)).findByName(teamName);
        verify(teamRepository, times(1)).delete(team);
    }

    @Test
    public void getId() {

        String teamName = "Team 1";
        int teamId = 1;

        Team team = new Team();
        team.setId(teamId);

        when(teamRepository.findByName(teamName)).thenReturn(Optional.of(team));

        Integer actualId = teamService.getId(teamName);

        assertEquals(teamId, actualId.intValue());

        verify(teamRepository, times(1)).findByName(teamName);
    }

    @Test
    public void getTeamById() {
        int teamId = 1;

        Team team = new Team();
        team.setId(teamId);

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(entityAndDtoConverter.convertToTeamDto(team)).thenReturn(new TeamDto());

        List<PlayerDto> playerDtos = Collections.singletonList(new PlayerDto());
        when(playerClient.getPlayers(null, null, null, teamId, PageRequest.of(0, 20))).thenReturn(playerDtos);

        TeamDto actualTeamDto = teamService.getTeamById(teamId);

        assertNotNull(actualTeamDto);
        assertEquals(playerDtos, actualTeamDto.getPlayers());

        verify(teamRepository, times(1)).findById(teamId);
        verify(entityAndDtoConverter, times(1)).convertToTeamDto(team);
        verify(playerClient, times(1)).getPlayers(null, null, null, teamId, PageRequest.of(0, 20));


    }



    // not found

    @Test(expected = TeamNotFoundException.class)
    public void testDeleteTeamByIdWhenTeamNotFound() {
        int id = 1;

        // Mock the behavior of the repository to return an empty Optional
        when(teamRepository.findById(id)).thenReturn(Optional.empty());

        teamService.deleteTeamById(id);
    }

    @Test(expected = TeamNotFoundException.class)
    public void testGetTeamByIdWhenTeamNotFound() {
        int id = 1;

        // Mock the behavior of the repository to return an empty Optional
        when(teamRepository.findById(id)).thenReturn(Optional.empty());

        teamService.getTeamById(id);
    }

    @Test(expected = TeamNotFoundException.class)
    public void testEditTeamWhenTeamNotFound() {
        int id = 1;
        TeamDto teamDto = new TeamDto();

        // Mock the behavior of the repository to return an empty Optional
        when(teamRepository.findById(id)).thenReturn(Optional.empty());

        teamService.editTeam(id, teamDto);
    }

    @Test
    public void testEditTeamConditionalStatements() {
        // Create a sample player and playerDto

        List<PlayerDto> players = new ArrayList<>();

        players.add(new PlayerDto("Joshua", "Salimbao", "PH",1));
        players.add(new PlayerDto("Jane", "Salimbao", "PH",1));
        Team team = new Team();
        team.setName("TeamA");
        team.setPlayers(players);



        TeamDto teamDto = new TeamDto();
        teamDto.setName(null);
        teamDto.setPlayers(null);

        // Mock the playerRepository.findById() method to return the sample player
        when(teamRepository.findById(anyInt())).thenReturn(Optional.of(team));

        // Call the updatePlayer() method
        String result = teamService.editTeam(1, teamDto);

        String expected = ResponseMessage.UPDATED;

        // Verify the updated player values
        assertEquals(expected , result);
        assertEquals("TeamA", team.getName()); // The value should remain unchanged
        assertEquals(players, team.getPlayers()); // The value should be updated

    }

    @Test(expected = TeamNotFoundException.class)
    public void testGetTeamByNameWhenTeamNotFound() {


        String name = "K";

        // Mock the behavior of the repository to return an empty Optional
        when(teamRepository.findByName(name)).thenReturn(Optional.empty());

        teamService.getId(name);
    }




// Similarly, add tests for other methods like addTeam, viewTeam, etc.



}