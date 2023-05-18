package com.menin.tournamentservice.service;

import com.menin.tournamentservice.constant.TournamentMsg;
import com.menin.tournamentservice.dto.TournamentDto;
import com.menin.tournamentservice.entity.Tournament;
import com.menin.tournamentservice.exception.EmptyFieldException;
import com.menin.tournamentservice.exception.TournamentNotFoundException;
import com.menin.tournamentservice.repository.TournamentRepository;
import com.menin.tournamentservice.util.ConverterClass;
import com.menin.tournamentservice.util.TournamentSpecifications;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService {


    private final ConverterClass converterClass;

    private final TournamentRepository tournamentRepository;

    @Autowired
    public TournamentServiceImpl(ConverterClass converterClass, TournamentRepository tournamentRepository) {
        this.converterClass = converterClass;
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public TournamentDto addTournament(TournamentDto tournamentDto) {

        if (StringUtils.isBlank(tournamentDto.getName()) || StringUtils.isBlank(tournamentDto.getCategory()) || StringUtils.isBlank(tournamentDto.getStyle())) {
            throw new EmptyFieldException();
        }

        return converterClass.convertToTournamentDto(tournamentRepository.save(converterClass.convertToTournament(tournamentDto)));
    }

    @Override
    public List<TournamentDto> getTournaments(String name, String category, String style, Pageable pageable) {
        Specification<Tournament> spec = Specification.where(null);

        if (StringUtils.isNotBlank(name)) {
            spec = spec.and(TournamentSpecifications.hasName(name));
        }

        if (StringUtils.isNotBlank(category)) {
            spec = spec.and(TournamentSpecifications.hasCategory(category));
        }

        if (StringUtils.isNotBlank(style)) {
            spec = spec.and(TournamentSpecifications.hasStyle(style));
        }

        Page<Tournament> tournaments = tournamentRepository.findAll(spec, pageable);

        if (tournaments.getContent().isEmpty()) {
            throw new TournamentNotFoundException(TournamentMsg.TOURNAMENT_NOT_FOUND);
        }

        List<Tournament> tournamentList = tournaments.getContent();

        return converterClass.convertToTournamentDtoList(tournamentList);
    }

    @Override
    public TournamentDto getTournamentById(int id) {

        Tournament tournament = tournamentRepository.findById(id).orElseThrow(() -> new TournamentNotFoundException(TournamentMsg.TOURNAMENT_NOT_FOUND));
        return converterClass.convertToTournamentDto(tournament);
    }


    @Override
    public String updateTournament(Integer id, TournamentDto tournamentDto) {
        Tournament tournament = tournamentRepository.findById(id).orElseThrow(()-> new TournamentNotFoundException(TournamentMsg.TOURNAMENT_NOT_FOUND));

        if(tournamentDto.getName() != null)
            tournament.setName(tournamentDto.getName());
        if(tournamentDto.getCategory() != null)
            tournament.setCategory(tournamentDto.getCategory());
        if(tournamentDto.getStyle() != null)
            tournament.setStyle(tournamentDto.getStyle());


        return TournamentMsg.UPDATED + tournamentRepository.save(tournament).getId();
    }

    @Override
    public String deleteTournament(Integer id) {

        Tournament tournament = tournamentRepository.findById(id).orElseThrow(()-> new TournamentNotFoundException(TournamentMsg.TOURNAMENT_NOT_FOUND));

        tournamentRepository.delete(tournament);

        return TournamentMsg.DELETED + id;
    }
}
