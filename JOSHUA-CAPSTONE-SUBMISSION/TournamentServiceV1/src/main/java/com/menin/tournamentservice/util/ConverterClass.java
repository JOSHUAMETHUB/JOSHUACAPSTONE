package com.menin.tournamentservice.util;


import com.menin.tournamentservice.dto.TournamentDto;
import com.menin.tournamentservice.entity.Tournament;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;



@Component
public class ConverterClass {


    private final ModelMapper modelMapper;


    @Autowired
    public ConverterClass(ModelMapper modelMapper) {

        this.modelMapper = modelMapper;
    }



    public TournamentDto convertToTournamentDto(Tournament tournament) {
        return modelMapper.map(tournament, TournamentDto.class);
    }

        public Tournament convertToTournament(TournamentDto tournamentDto) {
        return modelMapper.map(tournamentDto, Tournament.class);
    }

    public List<TournamentDto> convertToTournamentDtoList(List<Tournament> tournaments) {
        TypeToken<List<TournamentDto>> typeToken = new TypeToken<List<TournamentDto>>() {
        };
        return modelMapper.map(tournaments, typeToken.getType());
    }


    public Page<TournamentDto> convertToTournamentDtoPage(Page<Tournament> tournaments) {
        TypeToken<Page<TournamentDto>> typeToken = new TypeToken<Page<TournamentDto>>() {
        };
        return modelMapper.map(tournaments, typeToken.getType());
    }





}
