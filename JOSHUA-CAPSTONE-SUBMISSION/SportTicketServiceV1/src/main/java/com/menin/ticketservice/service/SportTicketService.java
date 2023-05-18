package com.menin.ticketservice.service;


import com.menin.ticketservice.dto.SportTicketDto;
import com.menin.ticketservice.dto.SportTicketRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SportTicketService {

    public SportTicketDto addSportTicket(SportTicketRequest sportTicketRequest);
    public List<SportTicketDto> getSportTickets(String customer, Float price, Integer matchid, Pageable pageable);

    public String editSportTicket(Integer id, SportTicketRequest sportTicketRequest);
    public String deleteSportTicket(Integer id);


}
