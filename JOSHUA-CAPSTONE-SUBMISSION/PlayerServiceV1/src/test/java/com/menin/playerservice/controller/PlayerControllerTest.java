package com.menin.playerservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.menin.playerservice.constant.PlayerResponseMessage;
import com.menin.playerservice.dao.PlayerRepository;
import com.menin.playerservice.dto.PlayerDto;
import com.menin.playerservice.entity.Player;
import com.menin.playerservice.service.PlayerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerControllerTest {

    private static final String API_VERSION = "/api/v1";
    private static final String ADD_PLAYER_URL = API_VERSION + "/players/add";
    private static final String GET_PLAYERS_URL = API_VERSION + "/players";
    private static final String GET_PLAYER_BY_ID_URL = API_VERSION + "/players/{id}";
    private static final String UPDATE_PLAYER_URL = API_VERSION + "/players/update/{id}";
    private static final String DELETE_PLAYER_URL = API_VERSION + "/players/delete/{id}";


    @InjectMocks
    private PlayerController playerController;

    private MockMvc mockMvc;

    @Autowired
    PlayerRepository playerRepository;

    @Mock
    private PlayerService playerService;

    private List<PlayerDto> players;
    private PlayerDto player1;
    private PlayerDto player2;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(playerController).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();

        player1 = new PlayerDto("John", "Doe", "USA", 3);
        player2 = new PlayerDto("Jane", "Doe", "USA", 3);
        players = Arrays.asList(player1, player2);
    }

//    @Test
//    @WithMockUser(authorities = {"ADMIN"})
//    public void addPlayer_returnsOk() throws Exception {
//        when(playerService.addPlayer(any(PlayerDto.class))).thenReturn(PlayerResponseMessage.ADDED + 0);
//
//        mockMvc.perform(post(ADD_PLAYER_URL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(player1))).andExpect(status().isOk()).andExpect(content().string(PlayerResponseMessage.ADDED + 0)).andReturn();
//    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void addPlayer_returnsOk() throws Exception {
        // Fetch the latest ID from the database
        int latestId = playerRepository.findLatestPlayerId().orElse(0);

        int playerId = latestId + 1; // Set the desired player ID as the latest ID + 1

        when(playerService.addPlayer(any(PlayerDto.class))).thenReturn(PlayerResponseMessage.ADDED + playerId);

        mockMvc.perform(post(ADD_PLAYER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(player1)))
                .andExpect(status().isOk())
                .andExpect(content().string(PlayerResponseMessage.ADDED + playerId))
                .andReturn();
    }


    @Test
    public void getPlayers_returnsOk() throws Exception {

        List<PlayerDto> playerList = players;

        when(playerService.getPlayers(anyString(), anyString(), anyString(), anyInt(), any(Pageable.class)))
                .thenReturn(playerList);

        mockMvc.perform(MockMvcRequestBuilders.get(GET_PLAYERS_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }



    @Test
    public void getPlayerById_returnsOk() throws Exception {

        PlayerDto playerTest = player1;
        int latestId = playerRepository.findLatestPlayerId().orElse(0);
        when(playerService.getPlayerById(latestId)).thenReturn(playerTest);

        mockMvc.perform(get(GET_PLAYER_BY_ID_URL, latestId).
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json(asJsonString(playerTest))).andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void updatePlayer_returnsOk() throws Exception {

        PlayerDto playerTest = player1;
        int latestId = playerRepository.findLatestPlayerId().orElse(0) ;

        System.out.println(latestId);
        when(playerService.updatePlayer(latestId, playerTest)).thenReturn(PlayerResponseMessage.UPDATED
                + "\nFirstname : "
                + playerTest.getFirstName()
                + "\nLastname : "
                + playerTest.getLastName()
                + "\nCountry : "
                + playerTest.getCountry()
                + "\nTeam : " + playerTest.getTeamId());
        mockMvc.perform(put(UPDATE_PLAYER_URL, latestId).
                contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(playerTest)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(PlayerResponseMessage.UPDATED
                                + "\nFirstname : "
                                + playerTest.getFirstName()
                                + "\nLastname : "
                                + playerTest.getLastName()
                                + "\nCountry : "
                                + playerTest.getCountry()
                                + "\nTeam : " + playerTest.getTeamId())).andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void deletePlayer_returnsOk() throws Exception {
        int latestId = playerRepository.findLatestPlayerId().orElse(0) ;
        when(playerService.deletePlayer(latestId)).thenReturn(PlayerResponseMessage.DELETED);
        mockMvc.perform(delete(DELETE_PLAYER_URL, latestId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(PlayerResponseMessage.DELETED)).andReturn();
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}