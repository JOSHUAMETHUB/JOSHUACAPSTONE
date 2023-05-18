package com.menin.tournamentservice.service;


import com.menin.tournamentservice.dto.TournamentDto;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TournamentService {

    public TournamentDto addTournament(TournamentDto tournamentDto);
    public List<TournamentDto> getTournaments(String name, String category, String style, Pageable pageable);

    public TournamentDto getTournamentById(int id);
    public String updateTournament(Integer id, TournamentDto tournamentDto);
    public String deleteTournament(Integer id);

}
