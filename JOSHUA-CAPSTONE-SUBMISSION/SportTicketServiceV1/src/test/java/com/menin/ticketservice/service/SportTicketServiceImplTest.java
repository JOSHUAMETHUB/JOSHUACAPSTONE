package com.menin.ticketservice.service;

import com.menin.ticketservice.constant.TicketMessage;
import com.menin.ticketservice.dto.SportMatchDto;
import com.menin.ticketservice.dto.SportTicketDto;
import com.menin.ticketservice.dto.SportTicketRequest;
import com.menin.ticketservice.entity.SportTicket;
import com.menin.ticketservice.exception.EmptyFieldException;
import com.menin.ticketservice.exception.ServiceNotAvailableException;
import com.menin.ticketservice.exception.TicketMatchNotFoundException;
import com.menin.ticketservice.feign.SportMatchClient;
import com.menin.ticketservice.repository.SportTicketRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SportTicketServiceImplTest {

    @Mock
    private SportMatchClient sportMatchClient;

    @Mock
    private SportTicketRepository sportTicketRepository;


    @InjectMocks
    private SportTicketServiceImpl sportTicketService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        sportTicketService = new SportTicketServiceImpl(sportMatchClient, sportTicketRepository);
    }

    @Test
    public void testAddSportTicket() {
        // Mock the request
        SportTicketRequest request = new SportTicketRequest();
        request.setCustomerName("John Doe");
        request.setTicketPrice(10.0f);
        request.setSportMatch(1);

        // Mock the response
        SportMatchDto mockSportMatchDto = SportMatchDto.builder()
                .sportField(null)
                .tournament(null)
                .teams(null)
                .participants(null)
                .dateTime(null)
                .build();
        when(sportMatchClient.getSportMatchById(anyInt())).thenReturn(mockSportMatchDto);

        // Mock the repository save operation
        SportTicket savedSportTicket = new SportTicket();
        when(sportTicketRepository.save(any(SportTicket.class))).thenReturn(savedSportTicket);

        // Call the method under test
        SportTicketDto result = sportTicketService.addSportTicket(request);

        // Verify the result
        assertEquals(request.getCustomerName(), result.getCustomerName());
        assertEquals(request.getTicketPrice(), result.getTicketPrice(), 0.001f);
        assertEquals(mockSportMatchDto, result.getSportMatch());
    }

    @Test(expected = EmptyFieldException.class)
    public void testAddSportTicketWithEmptyFields() {
        // Mock the request with empty fields
        SportTicketRequest request = new SportTicketRequest();

        // Call the method under test
        sportTicketService.addSportTicket(request);
    }

    @Test(expected = TicketMatchNotFoundException.class)
    public void testAddSportTicketWithMatchNotFound() {
        // Mock the request
        SportTicketRequest request = new SportTicketRequest();
        request.setCustomerName("John Doe");
        request.setTicketPrice(10.0f);
        request.setSportMatch(1);

        // Mock the exception
        when(sportMatchClient.getSportMatchById(anyInt())).thenThrow(new TicketMatchNotFoundException("match not found"));

        // Call the method under test
        sportTicketService.addSportTicket(request);
    }

    @Test(expected = ServiceNotAvailableException.class)
    public void testAddSportTicketWithServiceNotAvailable() {
        // Mock the request
        SportTicketRequest request = new SportTicketRequest();
        request.setCustomerName("John Doe");
        request.setTicketPrice(10.0f);
        request.setSportMatch(1);

        // Mock the exception
        when(sportMatchClient.getSportMatchById(anyInt())).thenThrow(new ServiceNotAvailableException("Error occurred while fetching match details"));

        // Call the method under test
        sportTicketService.addSportTicket(request);
    }


    @Test
    public void testGetSportTickets() {
        // Mock the parameters
        String customer = "John Doe";
        Float price = 10.0f;
        Integer matchId = 1;
        int pageNumber = 0;
        int pageSize = 10;
        Sort sort = Sort.by(Sort.Direction.ASC, "id"); // Replace "id" with the actual field to sort by
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Mock the repository find operation
        List<SportTicket> sportTickets = new ArrayList<>();
        when(sportTicketRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(new PageImpl<>(sportTickets));

        // Call the method under test
        List<SportTicketDto> result = sportTicketService.getSportTickets(customer, price, matchId, pageable);

        // Verify the result
        assertEquals(0, result.size());
    }

    @Test
    public void testEditSportTicket() {
        // Mock the parameters
        Integer id = 1;
        SportTicketRequest request = new SportTicketRequest();
        request.setCustomerName("John Doe");

        // Mock the repository find operation
        SportTicket sportTicket = new SportTicket();
        when(sportTicketRepository.findById(anyInt())).thenReturn(java.util.Optional.of(sportTicket));

        // Mock the repository save operation
        SportTicket savedSportTicket = new SportTicket();
        when(sportTicketRepository.save(any(SportTicket.class))).thenReturn(savedSportTicket);

        // Call the method under test
        String result = sportTicketService.editSportTicket(id, request);

        // Verify the result
        assertEquals(TicketMessage.UPDATED, result);
    }

    @Test
    public void testDeleteSportTicket() {
        // Mock the parameter
        Integer id = 1;

        // Mock the repository find operation
        SportTicket sportTicket = new SportTicket();
        when(sportTicketRepository.findById(anyInt())).thenReturn(java.util.Optional.of(sportTicket));

        // Call the method under test
        String result = sportTicketService.deleteSportTicket(id);

        // Verify the result
        assertEquals(TicketMessage.SPORT_TICKET_DELETED, result);
    }
}