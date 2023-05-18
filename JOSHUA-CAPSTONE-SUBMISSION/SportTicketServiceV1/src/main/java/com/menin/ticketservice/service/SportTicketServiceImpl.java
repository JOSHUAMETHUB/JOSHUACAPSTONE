package com.menin.ticketservice.service;

import com.menin.ticketservice.dto.SportMatchDto;
import com.menin.ticketservice.dto.SportTicketDto;
import com.menin.ticketservice.dto.SportTicketRequest;
import com.menin.ticketservice.entity.SportTicket;
import com.menin.ticketservice.exception.*;
import com.menin.ticketservice.constant.TicketMessage;
import com.menin.ticketservice.feign.SportMatchClient;
import com.menin.ticketservice.repository.SportTicketRepository;
import com.menin.ticketservice.util.SportTicketSpecification;
import feign.FeignException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SportTicketServiceImpl implements SportTicketService {


    private final SportMatchClient sportMatchClient;

    private final SportTicketRepository sportTicketRepository;

    @Autowired
    public SportTicketServiceImpl(SportMatchClient sportMatchClient, SportTicketRepository sportTicketRepository) {
        this.sportMatchClient = sportMatchClient;
        this.sportTicketRepository = sportTicketRepository;
    }

    @Override
    public SportTicketDto addSportTicket(SportTicketRequest sportTicketRequest) {

        if (StringUtils.isBlank(sportTicketRequest.getCustomerName()) || StringUtils.isBlank(String.valueOf(sportTicketRequest.getTicketPrice())) || sportTicketRequest.getSportMatch() == 0) {
            throw new EmptyFieldException();
        }

        SportTicket sportTicket = new SportTicket();
        sportTicket.setTicketPrice(sportTicketRequest.getTicketPrice());
        sportTicket.setCustomerName(sportTicketRequest.getCustomerName());

        SportMatchDto sportMatchDto = new SportMatchDto();

        try {
            SportMatchDto fromClient = sportMatchClient.getSportMatchById(sportTicketRequest.getSportMatch());
            sportMatchDto.setSportField(fromClient.getSportField());
            sportMatchDto.setTournament(fromClient.getTournament());
            sportMatchDto.setTeams(fromClient.getTeams());
            sportMatchDto.setParticipants(fromClient.getParticipants());
            sportMatchDto.setDateTime(fromClient.getDateTime());

        } catch (FeignException e) {


            if (e.getMessage().contains("MatchNotFoundException")) {
                throw new TicketMatchNotFoundException("match not found");
            } else if (e.getMessage().contains("DataIntegrityViolationException")) {
                throw new TicketMatchNotFoundException("The Match with that Id does not exist!");
            } else {
                throw new ServiceNotAvailableException("Error occurred while fetching match details");
            }
        }

        sportTicket.setSportMatch(sportTicketRequest.getSportMatch());

        SportTicketDto sportTicketDto = new SportTicketDto();


        sportTicketDto.setSportMatch(sportMatchDto);
        sportTicketDto.setCustomerName(sportTicketRequest.getCustomerName());
        sportTicketDto.setTicketPrice(sportTicketRequest.getTicketPrice());

        sportTicketRepository.save(sportTicket);

        return sportTicketDto;
    }

    @Override
    public List<SportTicketDto> getSportTickets(String customer, Float price, Integer matchid, Pageable pageable) {
        Specification<SportTicket> spec = SportTicketSpecification.ticketsByCriteria(customer, price, matchid);

        Page<SportTicket> tickets = sportTicketRepository.findAll(spec, pageable);

        List<SportTicketDto> ticketDtos = new ArrayList<>();

        tickets.forEach(fetchedTicket -> {

            SportMatchDto sportMatchDto = new SportMatchDto();


            try {
                SportMatchDto fromClient = sportMatchClient.getSportMatchById(fetchedTicket.getSportMatch());
                sportMatchDto.setSportField(fromClient.getSportField());
                sportMatchDto.setTournament(fromClient.getTournament());
                sportMatchDto.setTeams(fromClient.getTeams());
                sportMatchDto.setParticipants(fromClient.getParticipants());
                sportMatchDto.setDateTime(fromClient.getDateTime());

            } catch (FeignException e) {


                if (e.getMessage().contains("MatchNotFoundException")) {
                    throw new TicketMatchNotFoundException("match not found");
                } else if (e.getMessage().contains("DataIntegrityViolationException")) {
                    throw new TicketMatchNotFoundException("The Match with that Id does not exist!");
                } else {
                    throw new ServiceNotAvailableException("Error occurred while fetching match details");
                }
            }

            SportTicketDto ticketDtoToAdd = new SportTicketDto(fetchedTicket.getCustomerName(), fetchedTicket.getTicketPrice(), sportMatchDto);

            ticketDtos.add(ticketDtoToAdd);
        });

        return new PageImpl<>(ticketDtos, pageable, tickets.getTotalElements()).getContent();
    }


    @Override
    public String editSportTicket(Integer id, SportTicketRequest sportTicketRequest) {

        SportTicket sportTicket = sportTicketRepository.findById(id).orElseThrow(()->new SportTicketNotFoundException(TicketMessage.TICKET_NOT_FOUND));

        if(sportTicketRequest.getSportMatch() > 0){
            sportTicket.setTicketPrice(sportTicketRequest.getSportMatch());
        }
        if(sportTicketRequest.getTicketPrice()  > 0.0f && Float.isNaN(sportTicketRequest.getTicketPrice())){
            sportTicket.setTicketPrice(sportTicketRequest.getTicketPrice());
        }
        if(sportTicketRequest.getCustomerName() != null && StringUtils.isNotBlank(sportTicketRequest.getCustomerName())){
            sportTicket.setCustomerName(sportTicketRequest.getCustomerName());
        }

        sportTicketRepository.save(sportTicket);
        return TicketMessage.UPDATED;
    }


    @Override
    public String deleteSportTicket(Integer id) {

        SportTicket sportTicket =sportTicketRepository.findById(id).orElseThrow(()->new SportTicketNotFoundException(TicketMessage.TICKET_NOT_FOUND));

        sportTicketRepository.delete(sportTicket);
        return TicketMessage.SPORT_TICKET_DELETED;
    }
}
