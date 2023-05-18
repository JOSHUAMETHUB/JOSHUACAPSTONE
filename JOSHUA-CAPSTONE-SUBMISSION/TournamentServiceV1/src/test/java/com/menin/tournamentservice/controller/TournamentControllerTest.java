package com.menin.tournamentservice.controller;

import com.menin.tournamentservice.dto.TournamentDto;
import com.menin.tournamentservice.service.TournamentService;
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

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TournamentControllerTest {

    @Mock
    private TournamentService tournamentService;

    @InjectMocks
    private TournamentController tournamentController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testAddTournament() {
        TournamentDto tournamentDto = new TournamentDto();
        when(tournamentService.addTournament(tournamentDto)).thenReturn(tournamentDto);

        ResponseEntity<TournamentDto> responseEntity = tournamentController.addTournament(tournamentDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(tournamentDto, responseEntity.getBody());
        verify(tournamentService, times(1)).addTournament(tournamentDto);
    }

    @Test
    public void testGetTournamentById() {
        int id = 1;
        TournamentDto tournamentDto = new TournamentDto();
        when(tournamentService.getTournamentById(id)).thenReturn(tournamentDto);

        ResponseEntity<TournamentDto> responseEntity = tournamentController.getTournamentById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(tournamentDto, responseEntity.getBody());
        verify(tournamentService, times(1)).getTournamentById(id);
    }

    @Test
    public void testGetTournaments() {
        String name = "Tournament 1";
        String category = "Category 1";
        String style = "Style 1";
        List<TournamentDto> tournamentList = Collections.singletonList(new TournamentDto());
        Pageable pageable = mock(Pageable.class);
        when(tournamentService.getTournaments(name, category, style, pageable)).thenReturn(tournamentList);

        List<TournamentDto> result = tournamentController.getTournaments(name, category, style, pageable);

        assertEquals(tournamentList, result);
        verify(tournamentService, times(1)).getTournaments(name, category, style, pageable);
    }

    @Test
    public void testUpdateTournament() {
        int id = 1;
        TournamentDto tournamentDto = new TournamentDto();
        String expectedResult = "Tournament updated successfully";
        when(tournamentService.updateTournament(id, tournamentDto)).thenReturn(expectedResult);

        ResponseEntity<String> responseEntity = tournamentController.updateTournament(id, tournamentDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResult, responseEntity.getBody());
        verify(tournamentService, times(1)).updateTournament(id, tournamentDto);
    }

    @Test
    public void testDeleteTournament() {
        int id = 1;
        String expectedResult = "Tournament deleted successfully";
        when(tournamentService.deleteTournament(id)).thenReturn(expectedResult);

        ResponseEntity<String> responseEntity = tournamentController.deleteTournament(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResult, responseEntity.getBody());
        verify(tournamentService, times(1)).deleteTournament(id);
    }
}
