package com.menin.ticketservice.controller;

import com.menin.ticketservice.dto.SportTicketDto;
import com.menin.ticketservice.dto.SportTicketRequest;
import com.menin.ticketservice.service.SportTicketService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SportTicketControllerTest {

    @Mock
    private SportTicketService sportTicketService;

    @InjectMocks
    private SportTicketController sportTicketController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddSportTicket() {
        // Mock the request and response
        SportTicketRequest sportTicketRequest = new SportTicketRequest();
        SportTicketDto expectedResponse = new SportTicketDto();
        when(sportTicketService.addSportTicket(sportTicketRequest)).thenReturn(expectedResponse);

        // Call the controller method
        SportTicketDto actualResponse = sportTicketController.addSportTicket(sportTicketRequest);

        // Verify the service method was called and the response is correct
        verify(sportTicketService, times(1)).addSportTicket(sportTicketRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetSportTickets() {
        // Mock the request parameters and response
        String customer = "John Doe";
        Float price = 10.0f;
        Integer matchId = 1;
        List<SportTicketDto> expectedResponse = new ArrayList<>();
        Pageable pageable = null;
        when(sportTicketService.getSportTickets(customer, price, matchId, pageable)).thenReturn(expectedResponse);

        // Call the controller method
        List<SportTicketDto> actualResponse = sportTicketController.getSportTickets(customer, price, matchId, pageable);

        // Verify the service method was called and the response is correct
        verify(sportTicketService, times(1)).getSportTickets(customer, price, matchId, pageable);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testEditSportTicket() {
        // Mock the request and response
        Integer id = 1;
        SportTicketRequest sportTicketRequest = new SportTicketRequest();
        String expectedResponse = "Success";
        when(sportTicketService.editSportTicket(id, sportTicketRequest)).thenReturn(expectedResponse);

        // Call the controller method
        ResponseEntity<String> actualResponse = sportTicketController.editSportTicket(id, sportTicketRequest);

        // Verify the service method was called and the response is correct
        verify(sportTicketService, times(1)).editSportTicket(id, sportTicketRequest);
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    public void testDeleteSportTicket() {
        // Mock the request and response
        Integer id = 1;
        String expectedResponse = "Success";
        when(sportTicketService.deleteSportTicket(id)).thenReturn(expectedResponse);

        // Call the controller method
        ResponseEntity<String> actualResponse = sportTicketController.deleteSportTicket(id);

        // Verify the service method was called and the response is correct
        verify(sportTicketService, times(1)).deleteSportTicket(id);
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
    }
}
