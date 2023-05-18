package com.menin.playerservice.service;
import com.menin.playerservice.constant.PlayerResponseMessage;
import com.menin.playerservice.dao.PlayerRepository;
import com.menin.playerservice.dto.PlayerDto;
import com.menin.playerservice.entity.Player;
import com.menin.playerservice.exception.EmptyFieldException;
import com.menin.playerservice.exception.PlayerNotFoundException;
import com.menin.playerservice.util.EntityAndDtoConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = PlayerServiceImpl.class)
public class PlayerServiceImplTest {


    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private EntityAndDtoConverter entityAndDtoConverter;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddPlayer() {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setFirstName("John");
        playerDto.setLastName("Doe");
        playerDto.setCountry("USA");

        Player player = new Player();
        player.setId(1);

        when(entityAndDtoConverter.convertToPlayer(playerDto)).thenReturn(player);

        //Mock The repository
        when(playerRepository.save(player)).thenReturn(player);

        String expectedResponse = PlayerResponseMessage.ADDED + player.getId(); // Assuming the ID is 1
        String actualResponse = playerService.addPlayer(playerDto);

        assertEquals(expectedResponse, actualResponse);

        verify(entityAndDtoConverter, times(1)).convertToPlayer(playerDto);
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    public void testUpdatePlayerConditionalStatements() {
        // Create a sample player and playerDto
        Player player = new Player();
        player.setFirstName("John");
        player.setLastName("Doe");
        player.setCountry("USA");
        player.setTeamId(1);

        PlayerDto playerDto = new PlayerDto();
        playerDto.setFirstName(null); // Missed branch
        playerDto.setLastName(null); // Missed branch
        playerDto.setCountry(null); // Missed branch
        playerDto.setTeamId(null); // Missed branch

        // Mock the playerRepository.findById() method to return the sample player
        when(playerRepository.findById(anyInt())).thenReturn(Optional.of(player));

        // Call the updatePlayer() method
        String result = playerService.updatePlayer(1, playerDto);

        String expected = PlayerResponseMessage.UPDATED + "\nFirstname : " + player.getFirstName() + "\nLastname : " + player.getLastName() + "\nCountry : " + player.getCountry() + "\nTeam : " + player.getTeamId();

        // Verify the updated player values
        assertEquals(expected , result);
        assertEquals("John", player.getFirstName()); // The value should remain unchanged
        assertEquals("Doe", player.getLastName()); // The value should be updated
        assertEquals("USA", player.getCountry()); // The value should remain unchanged
        assertEquals(1, player.getTeamId()); // The value should remain unchanged
    }




    @Test(expected = EmptyFieldException.class)
    public void testAddPlayerWithEmptyFields() {
        PlayerDto playerDto = new PlayerDto(); // Empty fields

        playerService.addPlayer(playerDto);
    }




    @Test
    public void testGetPlayers() {
        String firstname = "John";
        String lastname = "Doe";
        String country = "USA";
        Integer teamId = 1;
        Pageable pageable = mock(Pageable.class);

        Player player1 = new Player();
        player1.setId(1);
        player1.setFirstName("John");
        player1.setLastName("Doe");
        player1.setCountry("USA");

        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);

        Page<Player> players = new PageImpl<>(playerList);

        when(playerRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(players);

        PlayerDto playerDto1 = new PlayerDto();
        playerDto1.setFirstName("John");
        playerDto1.setLastName("Doe");
        playerDto1.setCountry("USA");

        List<PlayerDto> expectedResponse = new ArrayList<>();
        expectedResponse.add(playerDto1);

        when(entityAndDtoConverter.convertToPlayerDtoList(playerList)).thenReturn(expectedResponse);

        List<PlayerDto> actualResponse = playerService.getPlayers(firstname, lastname, country, teamId, pageable);

        assertEquals(expectedResponse, actualResponse);

        verify(playerRepository, times(1)).findAll(any(Specification.class), eq(pageable));
        verify(entityAndDtoConverter, times(1)).convertToPlayerDtoList(playerList);
    }

    @Test
    public void testGetPlayersConditionalStatements() {
        String firstname = "John";
        String lastname = "Doe";
        String country = "USA";
        Integer teamId = 1;
        Pageable pageable = mock(Pageable.class);

        Player player1 = new Player();
        player1.setId(1);
        player1.setFirstName("John");
        player1.setLastName("Doe");
        player1.setCountry("USA");

        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);

        Page<Player> players = new PageImpl<>(playerList);

        when(playerRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(players);

        PlayerDto playerDto1 = new PlayerDto();
        playerDto1.setFirstName("John");
        playerDto1.setLastName("Doe");
        playerDto1.setCountry("USA");

        List<PlayerDto> expectedResponse = new ArrayList<>();
        expectedResponse.add(playerDto1);

        when(entityAndDtoConverter.convertToPlayerDtoList(playerList)).thenReturn(expectedResponse);

        List<PlayerDto> result = playerService.getPlayers(null, null, null, null, pageable);

        assertEquals(1, result.size());
        verify(playerRepository, times(1)).findAll(any(Specification.class), eq(pageable));
        verify(entityAndDtoConverter, times(1)).convertToPlayerDtoList(playerList);
    }





    @Test(expected = PlayerNotFoundException.class)
    public void testGetPlayersNotFound() {
        String firstname = "John";
        String lastname = "Doe";
        String country = "USA";
        Integer teamId = 1;
        Pageable pageable = mock(Pageable.class);

        List<Player> playerList = new ArrayList<>();

        Page<Player> players = new PageImpl<>(playerList);

        when(playerRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(players);

        playerService.getPlayers(firstname, lastname, country, teamId, pageable);
    }



    @Test
    public void testGetPlayerById() {
        int playerId = 1;

        Player player = new Player();
        player.setId(playerId);
        player.setFirstName("John");
        player.setLastName("Doe");
        player.setCountry("USA");

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        PlayerDto expectedResponse = new PlayerDto();
        expectedResponse.setFirstName("John");
        expectedResponse.setLastName("Doe");
        expectedResponse.setCountry("USA");

        when(entityAndDtoConverter.convertToPlayerDto(player)).thenReturn(expectedResponse);

        PlayerDto actualResponse = playerService.getPlayerById(playerId);

        assertEquals(expectedResponse, actualResponse);

        verify(playerRepository, times(1)).findById(playerId);
        verify(entityAndDtoConverter, times(1)).convertToPlayerDto(player);
    }

    @Test(expected = PlayerNotFoundException.class)
    public void testGetPlayerByIdNotFound() {
        int playerId = 1;

        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());

        playerService.getPlayerById(playerId);
    }


    @Test
    public void testUpdatePlayer() {
        int playerId = 1;

        PlayerDto playerDto = new PlayerDto();
        playerDto.setFirstName("John");
        playerDto.setLastName("Doe");
        playerDto.setCountry("USA");
        playerDto.setTeamId(2);

        Player player = new Player();
        player.setId(playerId);
        player.setFirstName("OriginalFirstName");
        player.setLastName("OriginalLastName");
        player.setCountry("OriginalCountry");
        player.setTeamId(1);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));


        String expectedResponse = PlayerResponseMessage.UPDATED + "\nFirstname : " + playerDto.getFirstName() + "\nLastname : " + playerDto.getLastName() + "\nCountry : " + playerDto.getCountry() + "\nTeam : " + playerDto.getTeamId();


        String actualResponse = playerService.updatePlayer(playerId, playerDto);

        assertEquals(expectedResponse, actualResponse);
        assertEquals("John", player.getFirstName());
        assertEquals("Doe", player.getLastName());
        assertEquals("USA", player.getCountry());
        assertEquals(2, (int) player.getTeamId());

        verify(playerRepository, times(1)).findById(playerId);
        verify(playerRepository, times(1)).save(player);
    }

    @Test(expected = PlayerNotFoundException.class)
    public void testUpdatePlayerNotFound() {
        int playerId = 1;

        PlayerDto playerDto = new PlayerDto();
        playerDto.setFirstName("John");
        playerDto.setLastName("Doe");
        playerDto.setCountry("USA");
        playerDto.setTeamId(2);

        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());

        playerService.updatePlayer(playerId, playerDto);
    }

    @Test
    public void testDeletePlayer() {
        int playerId = 1;

        Player player = new Player();
        player.setId(playerId);
        player.setFirstName("John");
        player.setLastName("Doe");
        player.setCountry("USA");

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        String expectedResponse = PlayerResponseMessage.DELETED;

        String actualResponse = playerService.deletePlayer(playerId);

        assertEquals(expectedResponse, actualResponse);

        verify(playerRepository, times(1)).findById(playerId);
        verify(playerRepository, times(1)).delete(player);
    }

    @Test(expected = PlayerNotFoundException.class)
    public void testDeletePlayerNotFound() {
        int playerId = 1;

        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());

        playerService.deletePlayer(playerId);
    }



}