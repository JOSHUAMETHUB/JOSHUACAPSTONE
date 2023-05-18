package com.menin.fieldservice.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.menin.fieldservice.dto.SportFieldDto;
import com.menin.fieldservice.service.SportFieldService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SportFieldControllerTest {

    private static final String API_VERSION = "/api/v1";
    private static final String ADD_SPORT_FIELD_URL = API_VERSION + "/sport-fields/add";
    private static final String GET_SPORT_FIELDS_URL = API_VERSION + "/sport-fields";
    private static final String GET_SPORT_FIELD_BY_ID_URL = API_VERSION + "/sport-fields/{id}";
    private static final String UPDATE_SPORT_FIELD_URL = API_VERSION + "/sport-fields/update/{id}";
    private static final String DELETE_SPORT_FIELD_URL = API_VERSION + "/sport-fields/delete/{id}";

    @InjectMocks
    private SportFieldController sportFieldController;

    private MockMvc mockMvc;

    @Mock
    private SportFieldService sportFieldService;

    @Mock
    private Pageable pageable;

    private List<SportFieldDto> sportFields;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sportFieldController).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(sportFieldController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();


    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void addSportField_returnsOk() throws Exception {
        // Create a valid sportFieldDto object
        SportFieldDto sportFieldDto = new SportFieldDto();
        sportFieldDto.setName("Field 1");
        sportFieldDto.setAddress("Address 1");
        sportFieldDto.setCapacity(10);

        // Mock the behavior of the sportFieldService.addSportField method
        when(sportFieldService.addSportField(any(SportFieldDto.class))).thenReturn(sportFieldDto);

        mockMvc.perform(post(ADD_SPORT_FIELD_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sportFieldDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(sportFieldDto)))
                .andReturn();
    }



    @Test
    public void getSportFields_returnsOk() throws Exception {
        List<SportFieldDto> sportFieldList = sportFields;
        Page<SportFieldDto> sportFieldPage = mock(Page.class);

        when(sportFieldService.getSportFields(anyString(), any(Pageable.class))).thenReturn(sportFieldPage);
        when(sportFieldPage.getContent()).thenReturn(sportFieldList);

        mockMvc.perform(MockMvcRequestBuilders.get(GET_SPORT_FIELDS_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void getSportFieldById_returnsOk() throws Exception {
        SportFieldDto sportField1 = new SportFieldDto();
        sportField1.setName("Field 1");
        sportField1.setAddress("Address 1");
        sportField1.setCapacity(10);
        int sportFieldId = 1;

        when(sportFieldService.getSportFieldById(sportFieldId)).thenReturn(sportField1);

        mockMvc.perform(get(GET_SPORT_FIELD_BY_ID_URL, sportFieldId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(sportField1)))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void updateSportField_returnsOk() throws Exception {
        SportFieldDto sportField1 = new SportFieldDto();
        sportField1.setName("Field 1");
        sportField1.setAddress("Address 1");
        sportField1.setCapacity(10);
        int sportFieldId = 1;

        when(sportFieldService.editSportField(sportFieldId, sportField1)).thenReturn(sportField1);
        mockMvc.perform(put(UPDATE_SPORT_FIELD_URL, sportFieldId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sportField1)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(sportField1)))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void deleteSportField_returnsOk() throws Exception {
        int sportFieldId = 1;

        mockMvc.perform(delete(DELETE_SPORT_FIELD_URL, sportFieldId)
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
