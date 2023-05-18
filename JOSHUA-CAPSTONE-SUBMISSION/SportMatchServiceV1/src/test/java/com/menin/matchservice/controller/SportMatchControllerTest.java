package com.menin.matchservice.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.menin.matchservice.dto.SportMatchRequest;
import com.menin.matchservice.dto.SportMatchResponse;
import com.menin.matchservice.service.SportMatchService;
import com.menin.matchservice.service.SportMatchServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SportMatchControllerTest {

    private static final String API_VERSION = "/api/v1";
    private static final String ADD_SPORT_MATCH_URL = API_VERSION + "/sport-match/add";
    private static final String GET_SPORT_MATCHES_URL = API_VERSION + "/sport-match";
    private static final String GET_SPORT_MATCH_BY_ID_URL = API_VERSION + "/sport-match/{id}";
    private static final String UPDATE_SPORT_MATCH_URL = API_VERSION + "/sport-match/update/{id}";
    private static final String DELETE_SPORT_MATCH_URL = API_VERSION + "/sport-match/delete/{id}";

    @InjectMocks
    private SportMatchController sportMatchController;

    private MockMvc mockMvc;

    @Mock
    private SportMatchService sportMatchService;

    @Mock
    private Pageable pageable;

    private List<SportMatchResponse> sportMatches;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sportMatchController).build();

        this.mockMvc = MockMvcBuilders.standaloneSetup(sportMatchController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void addSportMatch_returnsOk() throws Exception {
        // Create a valid sportMatchRequest object
        SportMatchRequest sportMatchRequest = new SportMatchRequest();
        // Set the properties of the sportMatchRequest object

        // Mock the behavior of the sportMatchService.addSportMatch method
        when(sportMatchService.addSportMatch(any(SportMatchRequest.class))).thenReturn(new SportMatchResponse());

        mockMvc.perform(post(ADD_SPORT_MATCH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sportMatchRequest)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getSportMatches_returnsOk() throws Exception {
        List<SportMatchResponse> sportMatchList = sportMatches;
        Page<SportMatchResponse> sportMatchPage = mock(Page.class);

        when(sportMatchService.getSportMatches(anyInt(), anyInt(), anyList(), anyList(), any(Date.class), any(Pageable.class))).thenReturn(sportMatchPage);
        when(sportMatchPage.getContent()).thenReturn(sportMatchList);

        mockMvc.perform(MockMvcRequestBuilders.get(GET_SPORT_MATCHES_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void getSportMatchById_returnsOk() throws Exception {
        SportMatchResponse sportMatch = new SportMatchResponse();
        // Set the properties of the sportMatch object
        int sportMatchId = 1;

        when(sportMatchService.getSportMatchById(sportMatchId)).thenReturn(sportMatch);

        mockMvc.perform(get(GET_SPORT_MATCH_BY_ID_URL, sportMatchId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(sportMatch)))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void updateSportMatch_returnsOk() throws Exception {
        SportMatchRequest sportMatchRequest = new SportMatchRequest();
        // Set the properties of the sportMatchRequest object
        int sportMatchId = 1;

        when(sportMatchService.updateSportMatch(sportMatchId, sportMatchRequest)).thenReturn(new SportMatchResponse());

        mockMvc.perform(put(UPDATE_SPORT_MATCH_URL, sportMatchId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sportMatchRequest)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void deleteSportMatch_returnsOk() throws Exception {
        int sportMatchId = 1;

        mockMvc.perform(delete(DELETE_SPORT_MATCH_URL, sportMatchId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
