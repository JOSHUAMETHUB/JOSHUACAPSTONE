package com.menin.ticketservice.controller;


import com.menin.ticketservice.dto.SportTicketDto;
import com.menin.ticketservice.dto.SportTicketRequest;
import com.menin.ticketservice.service.SportTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class SportTicketController {

    @Autowired
    SportTicketService sportTicketService;


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SportTicketDto addSportTicket(@RequestBody SportTicketRequest sportTicketRequest) {
        return sportTicketService.addSportTicket(sportTicketRequest);
    }


    @GetMapping
    public List<SportTicketDto> getSportTickets(

            @RequestParam(required = false) String customer,
            @RequestParam(required = false) Float price,
            @RequestParam(required = false) Integer matchid,
            Pageable pageable) {
        return sportTicketService.getSportTickets(customer, price, matchid, pageable);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> editSportTicket(@PathVariable Integer id, @RequestBody SportTicketRequest sportTicketRequest) {
        return ResponseEntity.ok(sportTicketService.editSportTicket(id, sportTicketRequest));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteSportTicket(@PathVariable Integer id) {
        return ResponseEntity.ok(sportTicketService.deleteSportTicket(id));
    }


}
