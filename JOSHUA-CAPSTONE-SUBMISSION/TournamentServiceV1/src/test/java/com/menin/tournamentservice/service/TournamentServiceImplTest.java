package com.menin.tournamentservice.service;

import com.menin.tournamentservice.constant.TournamentMsg;
import com.menin.tournamentservice.dto.TournamentDto;
import com.menin.tournamentservice.entity.Tournament;
import com.menin.tournamentservice.exception.EmptyFieldException;
import com.menin.tournamentservice.exception.TournamentNotFoundException;
import com.menin.tournamentservice.repository.TournamentRepository;
import com.menin.tournamentservice.util.ConverterClass;
import com.menin.tournamentservice.util.TournamentSpecifications;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TournamentServiceImplTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private ConverterClass converterClass;

    @InjectMocks
    private TournamentServiceImpl tournamentService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tournamentService = new TournamentServiceImpl(converterClass,tournamentRepository);
    }

    @Test
    public void testAddTournament_Success() {
        // Given
        TournamentDto tournamentDto = new TournamentDto("Tournament 1", "Category 1", "Style 1");
        Tournament tournament = new Tournament(1, "Tournament 1", "Category 1", "Style 1");
        when(converterClass.convertToTournament(tournamentDto)).thenReturn(tournament);
        when(converterClass.convertToTournamentDto(tournament)).thenReturn(tournamentDto);
        when(tournamentRepository.save(tournament)).thenReturn(tournament);

        // When
        TournamentDto result = tournamentService.addTournament(tournamentDto);

        // Then
        verify(tournamentRepository, times(1)).save(tournament);
        assertEquals(tournamentDto, result);
    }

    @Test(expected = EmptyFieldException.class)
    public void testAddTournament_ThrowsEmptyFieldException() {
        // Given
        TournamentDto tournamentDto = new TournamentDto("", "Category 1", "Style 1");

        // When
        tournamentService.addTournament(tournamentDto);

        // Then
        // EmptyFieldException should be thrown
    }

    @Test
    public void testGetTournaments_Success() {
        // Given
        String name = "Tournament";
        String category = "Category";
        String style = "Style";
        Tournament tournament1 = new Tournament(1, "Tournament 1", "Category 1", "Style 1");
        Tournament tournament2 = new Tournament(2, "Tournament 2", "Category 2", "Style 2");
        List<Tournament> tournamentList = Arrays.asList(tournament1, tournament2);
        TournamentDto tournamentDto1 = new TournamentDto("Tournament 1", "Category 1", "Style 1");
        TournamentDto tournamentDto2 = new TournamentDto("Tournament 2", "Category 2", "Style 2");
        List<TournamentDto> expectedList = Arrays.asList(tournamentDto1, tournamentDto2);
        Page<Tournament> tournamentPage = new PageImpl<>(tournamentList);
        Pageable pageable = mock(Pageable.class);
        when(tournamentRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(tournamentPage);
        when(converterClass.convertToTournamentDtoList(tournamentList)).thenReturn(expectedList);

        // When
        List<TournamentDto> result = tournamentService.getTournaments(name, category, style, pageable);

        // Then
        assertEquals(expectedList, result);
    }

    @Test(expected = TournamentNotFoundException.class)
    public void testGetTournaments_ThrowsTournamentNotFoundException() {
        // Given
        String name = "Tournament";
        String category = "Category";
        String style = "Style";
        List<Tournament> tournamentList = Arrays.asList();
        Page<Tournament> tournamentPage = new PageImpl<>(tournamentList);
        Pageable pageable = mock(Pageable.class);
        when(tournamentRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(tournamentPage);

        // When
        tournamentService.getTournaments(name, category, style, pageable);

        // Then
        // TournamentNotFoundException should be thrown
    }

    @Test
    public void testGetTournamentById_Success() {
        // Given
        int id = 1;
        Tournament tournament = new Tournament(1, "Tournament 1", "Category 1", "Style 1");
        TournamentDto tournamentDto = new TournamentDto("Tournament 1", "Category 1", "Style 1");
        when(tournamentRepository.findById(id)).thenReturn(Optional.of(tournament));
        when(converterClass.convertToTournamentDto(tournament)).thenReturn(tournamentDto);

        // When
        TournamentDto result = tournamentService.getTournamentById(id);

        // Then
        assertEquals(tournamentDto, result);
    }

    @Test(expected = TournamentNotFoundException.class)
    public void testGetTournamentById_ThrowsTournamentNotFoundException() {
        // Given
        int id = 1;
        when(tournamentRepository.findById(id)).thenReturn(Optional.empty());

        // When
        tournamentService.getTournamentById(id);

        // Then
        // TournamentNotFoundException should be thrown
    }

    @Test
    public void testUpdateTournament_Success() {
        // Given
        int id = 1;
        TournamentDto tournamentDto = new TournamentDto("Updated Tournament", "Updated Category", "Updated Style");
        Tournament tournament = new Tournament(1, "Tournament 1", "Category 1", "Style 1");
        when(tournamentRepository.findById(id)).thenReturn(Optional.of(tournament));
        when(tournamentRepository.save(tournament)).thenReturn(tournament);

        // When
        String result = tournamentService.updateTournament(id, tournamentDto);

        // Then
        verify(tournamentRepository, times(1)).save(tournament);
        assertEquals(TournamentMsg.UPDATED + tournament.getId(), result);
    }

    @Test(expected = TournamentNotFoundException.class)
    public void testUpdateTournament_ThrowsTournamentNotFoundException() {
        // Given
        int id = 1;
        TournamentDto tournamentDto = new TournamentDto("Updated Tournament", "Updated Category", "Updated Style");
        when(tournamentRepository.findById(id)).thenReturn(Optional.empty());

        // When
        tournamentService.updateTournament(id, tournamentDto);

        // Then
        // TournamentNotFoundException should be thrown
    }

    @Test
    public void testDeleteTournament_Success() {
        // Given
        int id = 1;
        Tournament tournament = new Tournament(1, "Tournament 1", "Category 1", "Style 1");
        when(tournamentRepository.findById(id)).thenReturn(Optional.of(tournament));

        // When
        String result = tournamentService.deleteTournament(id);

        // Then
        verify(tournamentRepository, times(1)).delete(tournament);
        assertEquals(TournamentMsg.DELETED + id, result);
    }

    @Test(expected = TournamentNotFoundException.class)
    public void testDeleteTournament_ThrowsTournamentNotFoundException() {
        // Given
        int id = 1;
        when(tournamentRepository.findById(id)).thenReturn(Optional.empty());

        // When
        tournamentService.deleteTournament(id);

        // Then
        // TournamentNotFoundException should be thrown
    }
}
