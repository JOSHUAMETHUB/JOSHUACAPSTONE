package com.menin.matchservice.service;

import com.menin.matchservice.constant.SportMatchMsg;
import com.menin.matchservice.dto.*;
import com.menin.matchservice.entity.SportMatch;
import com.menin.matchservice.exception.*;
import com.menin.matchservice.feign.PlayerClient;
import com.menin.matchservice.feign.SportFieldClient;
import com.menin.matchservice.feign.TeamClient;
import com.menin.matchservice.feign.TournamentClient;
import com.menin.matchservice.repository.SportMatchRepository;
import feign.FeignException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = SportMatchServiceImpl.class)
public class SportMatchServiceImplTest {

    @InjectMocks
    private SportMatchServiceImpl sportMatchService;


    @Mock
    private SportMatchRepository sportMatchRepository;

    @Mock
    private PlayerClient playerClient;

    @Mock
    private SportFieldClient sportFieldClient;

    @Mock
    private TournamentClient tournamentClient;

    @Mock
    private TeamClient teamClient;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        sportMatchService = new SportMatchServiceImpl(teamClient, playerClient, sportFieldClient, tournamentClient, sportMatchRepository);
    }

    // ...

    @Test
    public void testAddSportMatch() {
        // Mocked DTOs
        SportFieldDto sportFieldDto = new SportFieldDto();
        sportFieldDto.setName("Field 1");

        TournamentDto tournamentDto = new TournamentDto();
        tournamentDto.setName("Tournament 2");

        TeamDto team1Dto = new TeamDto();
        team1Dto.setName("Team 3");

        TeamDto team2Dto = new TeamDto();
        team2Dto.setName("Team 4");

        PlayerDto player1Dto = new PlayerDto();
        player1Dto.setFirstName("Player 5");

        PlayerDto player2Dto = new PlayerDto();
        player2Dto.setFirstName("Player 6");

        // Mocked entities
        SportMatchRequest sportMatchRequest = SportMatchRequest.builder()
                .dateTime(new Date())
                .sportField(1)
                .tournament(2)
                .teams(Arrays.asList(3, 4))
                .participants(Arrays.asList(5, 6))
                .build();

        SportMatch sportMatch = SportMatch.builder()
                .dateTime(new Date())
                .sportField(1)
                .tournament(2)
                .teamsId("3, 4")
                .participantsId("5, 6")
                .build();

        // Mocked responses
        when(sportFieldClient.getSportFieldById(1)).thenReturn(ResponseEntity.ok(sportFieldDto));
        when(tournamentClient.getTournamentById(2)).thenReturn(ResponseEntity.ok(tournamentDto));
        when(teamClient.getTeamById(3)).thenReturn(team1Dto);
        when(teamClient.getTeamById(4)).thenReturn(team2Dto);
        when(playerClient.getPlayerById(5)).thenReturn(ResponseEntity.ok(player1Dto));
        when(playerClient.getPlayerById(6)).thenReturn(ResponseEntity.ok(player2Dto));

        // Use ArgumentCaptor to capture the argument passed to save()
        // Use ArgumentCaptor to capture the argument passed to save()
        ArgumentCaptor<SportMatch> sportMatchCaptor = ArgumentCaptor.forClass(SportMatch.class);
        doAnswer(invocation -> {
            SportMatch capturedSportMatch = sportMatchCaptor.getValue();
            capturedSportMatch.setId(0); // Set the ID to 0 manually
            return null; // Return null for a void method
        }).when(sportMatchRepository).save(sportMatchCaptor.capture());


        // Perform the test
        SportMatchResponse sportMatchResponse = sportMatchService.addSportMatch(sportMatchRequest);

        // Assert the captured argument
        SportMatch capturedSportMatch = sportMatchCaptor.getValue();
        assertEquals(1, capturedSportMatch.getSportField());
        assertEquals(2, capturedSportMatch.getTournament());
        assertEquals("3, 4", capturedSportMatch.getTeamsId());
        assertEquals("5, 6", capturedSportMatch.getParticipantsId());
        assertNotNull(capturedSportMatch.getDateTime());

        // Assert the response
        assertNotNull(sportMatchResponse);
        assertEquals(sportFieldDto.getName(), sportMatchResponse.getSportField().getName());
        assertEquals(tournamentDto.getName(), sportMatchResponse.getTournament().getName());
        assertEquals(team1Dto.getName(), sportMatchResponse.getTeams().get(0).getName());
        assertEquals(team2Dto.getName(), sportMatchResponse.getTeams().get(1).getName());
        assertEquals(player1Dto.getFirstName(), sportMatchResponse.getParticipants().get(0).getFirstName());
        assertEquals(player2Dto.getFirstName(), sportMatchResponse.getParticipants().get(1).getFirstName());

        // Verify method invocations
        verify(sportFieldClient).getSportFieldById(1);
        verify(tournamentClient).getTournamentById(2);
        verify(teamClient).getTeamById(3);
        verify(teamClient).getTeamById(4);
        verify(playerClient).getPlayerById(5);
        verify(playerClient).getPlayerById(6);
        verify(sportMatchRepository).save(any(SportMatch.class));
    }

    @Test
    public void testGetSportMatches() {
        // Mocked entities
        SportMatch sportMatch1 = SportMatch.builder()
                .id(1)
                .dateTime(new Date())
                .sportField(1)
                .tournament(2)
                .teamsId("3, 4")
                .participantsId("5, 6")
                .build();

        SportMatch sportMatch2 = SportMatch.builder()
                .id(2)
                .dateTime(new Date())
                .sportField(2)
                .tournament(2)
                .teamsId("7, 8")
                .participantsId("9, 10")
                .build();

        List<SportMatch> sportMatches = Arrays.asList(sportMatch1, sportMatch2);

        // Mock the repository
        when(sportMatchRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(sportMatches));

        TournamentDto tournamentDto = TournamentDto
                .builder()
                .name("Tournament 2")
                .style("Doggy")
                .category("Jabulan")
                .build();


        SportFieldDto sportFieldDto1 = SportFieldDto
                .builder()
                .name("Field 1")
                .address("Dyan lang")
                .capacity(500)
                .build();

        SportFieldDto sportFieldDto2 = SportFieldDto
                .builder()
                .name("Field 2")
                .address("Dyan lang")
                .capacity(500)
                .build();

        // Mock the necessary services
        when(sportFieldClient.getSportFieldById(1)).thenReturn(ResponseEntity.ok(sportFieldDto1));
        when(sportFieldClient.getSportFieldById(2)).thenReturn(ResponseEntity.ok(sportFieldDto2));
        when(tournamentClient.getTournamentById(2)).thenReturn(ResponseEntity.ok(tournamentDto));
        when(teamClient.getTeamById(3)).thenReturn(new TeamDto());
        when(teamClient.getTeamById(4)).thenReturn(new TeamDto());
        when(teamClient.getTeamById(7)).thenReturn(new TeamDto());
        when(teamClient.getTeamById(8)).thenReturn(new TeamDto());
        when(playerClient.getPlayerById(5)).thenReturn(ResponseEntity.ok(new PlayerDto()));
        when(playerClient.getPlayerById(6)).thenReturn(ResponseEntity.ok(new PlayerDto()));
        when(playerClient.getPlayerById(9)).thenReturn(ResponseEntity.ok(new PlayerDto()));
        when(playerClient.getPlayerById(10)).thenReturn(ResponseEntity.ok(new PlayerDto()));


        // Mock the necessary services


        // Perform the test
        Pageable pageable = mock(Pageable.class);
        Page<SportMatchResponse> sportMatchResponses = sportMatchService.getSportMatches(1, 2, Arrays.asList("5", "6"), Arrays.asList("3", "4"), new Date(), pageable);

        // Assert the response
        assertNotNull(sportMatchResponses);
        assertEquals(2, sportMatchResponses.getContent().size());
        assertEquals("Field 1", sportMatchResponses.getContent().get(0).getSportField().getName());
        assertEquals("Tournament 2", sportMatchResponses.getContent().get(1).getTournament().getName());

        // Verify method invocations
        verify(sportMatchRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(sportFieldClient).getSportFieldById(1);
        verify(sportFieldClient).getSportFieldById(2);
        verify(tournamentClient, times(2)).getTournamentById(2); // Verify the number of invocations
        verify(teamClient).getTeamById(3);
        verify(teamClient).getTeamById(4);
        verify(teamClient).getTeamById(7);
        verify(teamClient).getTeamById(8);
        verify(playerClient).getPlayerById(5);
        verify(playerClient).getPlayerById(6);
        verify(playerClient).getPlayerById(9);
        verify(playerClient).getPlayerById(10);
    }

    @Test
    public void testUpdateSportMatch() {
        int sportMatchId = 1;
        SportMatchRequest sportMatchRequest = new SportMatchRequest();
        sportMatchRequest.setSportField(1);
        sportMatchRequest.setTournament(2);
        sportMatchRequest.setDateTime(new Date());
        sportMatchRequest.setTeams(Arrays.asList(3, 4));
        sportMatchRequest.setParticipants(Arrays.asList(5, 6));

        SportMatch sportMatch = SportMatch.builder()
                .id(sportMatchId)
                .dateTime(new Date())
                .sportField(1)
                .tournament(2)
                .teamsId("3, 4")
                .participantsId("5, 6")
                .build();

        // Mock the repository
        when(sportMatchRepository.findById(sportMatchId)).thenReturn(Optional.of(sportMatch));
        when(sportMatchRepository.save(any(SportMatch.class))).thenReturn(sportMatch);

        TournamentDto tournamentDto = TournamentDto.builder()
                .name("Tournament 2")
                .style("Doggy")
                .category("Jabulan")
                .build();

        SportFieldDto sportFieldDto = SportFieldDto.builder()
                .name("Field 1")
                .address("Dyan lang")
                .capacity(500)
                .build();

        // Mock the necessary services
        when(sportFieldClient.getSportFieldById(1)).thenReturn(ResponseEntity.ok(sportFieldDto));
        when(tournamentClient.getTournamentById(2)).thenReturn(ResponseEntity.ok(tournamentDto));
        when(teamClient.getTeamById(3)).thenReturn(new TeamDto());
        when(teamClient.getTeamById(4)).thenReturn(new TeamDto());
        when(playerClient.getPlayerById(5)).thenReturn(ResponseEntity.ok(new PlayerDto()));
        when(playerClient.getPlayerById(6)).thenReturn(ResponseEntity.ok(new PlayerDto()));

        // Perform the test
        SportMatchResponse sportMatchResponse = sportMatchService.updateSportMatch(sportMatchId, sportMatchRequest);

        // Assert the response
        assertNotNull(sportMatchResponse);
        assertEquals("Field 1", sportMatchResponse.getSportField().getName());
        assertEquals("Tournament 2", sportMatchResponse.getTournament().getName());

        // Verify method invocations
        verify(sportMatchRepository).findById(sportMatchId);
        verify(sportMatchRepository).save(any(SportMatch.class));
        verify(sportFieldClient).getSportFieldById(1);
        verify(tournamentClient).getTournamentById(2);
        verify(teamClient).getTeamById(3);
        verify(teamClient).getTeamById(4);
        verify(playerClient).getPlayerById(5);
        verify(playerClient).getPlayerById(6);
    }


    @Test
    public void testDeleteSportMatch() {
        // Mocked entity
        SportMatch sportMatch = new SportMatch();
        sportMatch.setId(1);

        // Mock the repository
        when(sportMatchRepository.findById(1)).thenReturn(java.util.Optional.of(sportMatch));

        // Perform the test
        String result = sportMatchService.deleteSportMatch(1);

        // Verify the repository method invocations
        verify(sportMatchRepository, times(1)).findById(1);
        verify(sportMatchRepository, times(1)).delete(sportMatch);

        // Verify the returned result
        assertEquals(SportMatchMsg.SPORTMATCH_DELETED, result);
    }

    @Test(expected = SportMatchNotFoundException.class)
    public void testDeleteSportMatchNotFound() {
        // Mock the repository to return an empty optional
        when(sportMatchRepository.findById(1)).thenReturn(java.util.Optional.empty());

        // Perform the test
        sportMatchService.deleteSportMatch(1);
    }

    @Test
    public void testGetSportMatchById() {
        // Mocked entities
        SportMatch sportMatch = new SportMatch();
        sportMatch.setId(1);
        sportMatch.setSportField(1);
        sportMatch.setTournament(2);
        sportMatch.setTeamsId("3, 4");
        sportMatch.setParticipantsId("5, 6");

        // Mock the repository
        when(sportMatchRepository.findById(1)).thenReturn(Optional.of(sportMatch));

        // Mock the necessary services
        SportFieldDto sportFieldDto = new SportFieldDto();
        sportFieldDto.setName("Field 1");
        when(sportFieldClient.getSportFieldById(1)).thenReturn(ResponseEntity.ok(sportFieldDto));

        TournamentDto tournamentDto = new TournamentDto();
        tournamentDto.setName("Tournament 2");
        when(tournamentClient.getTournamentById(2)).thenReturn(ResponseEntity.ok(tournamentDto));

        TeamDto teamDto1 = new TeamDto();
        teamDto1.setName("Team 3");
        when(teamClient.getTeamById(3)).thenReturn(teamDto1);

        TeamDto teamDto2 = new TeamDto();
        teamDto2.setName("Team 4");
        when(teamClient.getTeamById(4)).thenReturn(teamDto2);

        PlayerDto playerDto1 = new PlayerDto();
        playerDto1.setFirstName("Player 5");
        when(playerClient.getPlayerById(5)).thenReturn(ResponseEntity.ok(playerDto1));

        PlayerDto playerDto2 = new PlayerDto();
        playerDto2.setFirstName("Player 6");
        when(playerClient.getPlayerById(6)).thenReturn(ResponseEntity.ok(playerDto2));

        // Perform the test
        SportMatchResponse result = sportMatchService.getSportMatchById(1);

        // Assert the response
        assertNotNull(result);
        assertEquals(sportFieldDto.getName(), result.getSportField().getName());
        assertEquals(tournamentDto.getName(), result.getTournament().getName());
        assertEquals(2, result.getTeams().size());
        assertEquals(2, result.getParticipants().size());

        // Verify method invocations
        verify(sportMatchRepository).findById(1);
        verify(sportFieldClient).getSportFieldById(1);
        verify(tournamentClient).getTournamentById(2);
        verify(teamClient).getTeamById(3);
        verify(teamClient).getTeamById(4);
        verify(playerClient).getPlayerById(5);
        verify(playerClient).getPlayerById(6);
    }


    @Test(expected = EmptyFieldException.class)
    public void testAddSportMatch_WithMissingSportField() {
        SportMatchRequest sportMatchRequest = new SportMatchRequest();
        sportMatchRequest.setSportField(1);

        when(sportFieldClient.getSportFieldById(1)).thenReturn(ResponseEntity.notFound().build());

        sportMatchService.addSportMatch(sportMatchRequest);

        verify(sportMatchRepository, never()).save(any(SportMatch.class));
    }

    @Test(expected = MatchTournamentdNotFoundException.class)
    public void testAddSportMatch_WithMissingTournament() {
        SportMatchRequest sportMatchRequest = new SportMatchRequest();
        sportMatchRequest.setSportField(1);
        sportMatchRequest.setTournament(1);
        sportMatchRequest.setDateTime(new Date());
        sportMatchRequest.setTeams(Arrays.asList(1));
        sportMatchRequest.setParticipants(Arrays.asList(1));

        when(sportFieldClient.getSportFieldById(1)).thenReturn(ResponseEntity.ok(new SportFieldDto()));
        when(tournamentClient.getTournamentById(1)).thenReturn(ResponseEntity.notFound().build());
        when(teamClient.getTeamById(1)).thenReturn(new TeamDto("Jabar", Arrays.asList(new PlayerDto("Joshua", "Salimbao", "ph"))));
        when(playerClient.getPlayerById(1)).thenReturn(ResponseEntity.ok(new PlayerDto("Joshua", "Salimbao", "ph")));

        sportMatchService.addSportMatch(sportMatchRequest);

        verify(sportMatchRepository, never()).save(any(SportMatch.class));
    }


    @Test(expected = MatchTeamNotFoundException.class)
    public void testAddSportMatch_WithMissingTeam() {
        SportMatchRequest sportMatchRequest = new SportMatchRequest();
        sportMatchRequest.setSportField(1);
        sportMatchRequest.setTournament(1);
        sportMatchRequest.setDateTime(new Date());
        sportMatchRequest.setTeams(Arrays.asList(1));
        sportMatchRequest.setParticipants(Arrays.asList(1));

        when(sportFieldClient.getSportFieldById(1)).thenReturn(ResponseEntity.ok(new SportFieldDto()));
        when(tournamentClient.getTournamentById(1)).thenReturn(ResponseEntity.ok(new TournamentDto()));
        when(teamClient.getTeamById(1)).thenReturn(null);
        when(playerClient.getPlayerById(1)).thenReturn(ResponseEntity.ok(new PlayerDto("Joshua", "Salimbao", "ph")));

        sportMatchService.addSportMatch(sportMatchRequest);

        verify(sportMatchRepository, never()).save(any(SportMatch.class));
    }

    @Test(expected = MatchPlayerNotFoundException.class)
    public void testAddSportMatch_WithMissingPlayer() {
        SportMatchRequest sportMatchRequest = new SportMatchRequest();
        sportMatchRequest.setSportField(1);
        sportMatchRequest.setTournament(1);
        sportMatchRequest.setDateTime(new Date());
        sportMatchRequest.setTeams(Arrays.asList(1));
        sportMatchRequest.setParticipants(Arrays.asList(1));

        when(sportFieldClient.getSportFieldById(1)).thenReturn(ResponseEntity.ok(new SportFieldDto()));
        when(tournamentClient.getTournamentById(1)).thenReturn(ResponseEntity.ok(new TournamentDto()));
        when(teamClient.getTeamById(1)).thenReturn(new TeamDto("Jabar", Arrays.asList(new PlayerDto("Joshua", "Salimbao", "ph"))));
        when(playerClient.getPlayerById(1)).thenReturn(ResponseEntity.notFound().build());

        sportMatchService.addSportMatch(sportMatchRequest);

        verify(sportMatchRepository, never()).save(any(SportMatch.class));
    }


    @Test(expected = MatchSportFieldNotFoundException.class)
    public void testAddSportMatch_WithMissingField() {
        SportMatchRequest sportMatchRequest = new SportMatchRequest();
        sportMatchRequest.setSportField(1);
        sportMatchRequest.setTournament(1);
        sportMatchRequest.setDateTime(new Date());
        sportMatchRequest.setTeams(Arrays.asList(1));
        sportMatchRequest.setParticipants(Arrays.asList(1));

        when(sportFieldClient.getSportFieldById(1)).thenReturn(ResponseEntity.notFound().build());
        when(tournamentClient.getTournamentById(1)).thenReturn(ResponseEntity.ok(new TournamentDto()));
        when(teamClient.getTeamById(1)).thenReturn(new TeamDto("Jabar", Arrays.asList(new PlayerDto("Joshua", "Salimbao", "ph"))));
        when(playerClient.getPlayerById(1)).thenReturn(ResponseEntity.ok(new PlayerDto("Joshua", "Salimbao", "ph")));

        sportMatchService.addSportMatch(sportMatchRequest);

        verify(sportMatchRepository, never()).save(any(SportMatch.class));
    }




    @Test(expected = EmptyFieldException.class)
    public void testAddSportMatch_withEmptySportMatchRequest_shouldThrowException() {
        // Arrange
        SportMatchRequest sportMatchRequest = null;

        // Act
        sportMatchService.addSportMatch(sportMatchRequest);

        // Assert
        // Exception is expected to be thrown
    }

    @Test(expected = EmptyFieldException.class)
    public void testAddSportMatch_withMissingFields_shouldThrowException() {
        // Arrange
        SportMatchRequest sportMatchRequest = new SportMatchRequest();
        sportMatchRequest.setSportField(123);  // Valid sport field ID
        sportMatchRequest.setTournament(456);  // Valid tournament ID

        // Act
        sportMatchService.addSportMatch(sportMatchRequest);

        // Assert
        // Exception is expected to be thrown
    }
    @Test(expected = ServiceNotAvailableException.class)
    public void testGetTeamFromTeamService_FeignException() {
        int teamId = 1;
        String errorMessage = "Some FeignException";
        FeignException feignException = mock(FeignException.class);
        when(feignException.getMessage()).thenReturn(errorMessage);
        when(teamClient.getTeamById(teamId)).thenThrow(feignException);

        sportMatchService.getTeamFromTeamService(teamId);
    }

    @Test(expected = ServiceNotAvailableException.class)
    public void testGetTournamentFromTournamentService_FeignException() {
        int tournamentId = 1;
        String errorMessage = "Some FeignException";
        FeignException feignException = mock(FeignException.class);
        when(feignException.getMessage()).thenReturn(errorMessage);
        when(tournamentClient.getTournamentById(tournamentId)).thenThrow(feignException);

        sportMatchService.getTournamentFromTournamentService(tournamentId);
    }

    @Test(expected = ServiceNotAvailableException.class)
    public void testGetSportFieldFromSportFieldService_FeignException() {
        int sportFieldId = 1;
        String errorMessage = "Some FeignException";
        FeignException feignException = mock(FeignException.class);
        when(feignException.getMessage()).thenReturn(errorMessage);
        when(sportFieldClient.getSportFieldById(sportFieldId)).thenThrow(feignException);

        sportMatchService.getSportFieldFromSportFieldService(sportFieldId);
    }









}