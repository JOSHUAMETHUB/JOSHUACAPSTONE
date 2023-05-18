package com.menin.matchservice.service;

import com.menin.matchservice.constant.ExceptionMessage;
import com.menin.matchservice.constant.SportMatchMsg;
import com.menin.matchservice.dto.*;
import com.menin.matchservice.entity.SportMatch;
import com.menin.matchservice.exception.*;
import com.menin.matchservice.feign.PlayerClient;
import com.menin.matchservice.feign.SportFieldClient;
import com.menin.matchservice.feign.TeamClient;
import com.menin.matchservice.feign.TournamentClient;
import com.menin.matchservice.repository.SportMatchRepository;
import com.menin.matchservice.util.SportMatchSpecification;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class SportMatchServiceImpl implements SportMatchService {


    private final TeamClient teamClient;


    private final PlayerClient playerClient;


    private final SportFieldClient sportFieldClient;


    private final TournamentClient tournamentClient;


    private final SportMatchRepository sportMatchRepository;

    @Autowired
    public SportMatchServiceImpl(TeamClient teamClient, PlayerClient playerClient, SportFieldClient sportFieldClient, TournamentClient tournamentClient, SportMatchRepository sportMatchRepository) {
        this.teamClient = teamClient;
        this.playerClient = playerClient;
        this.sportFieldClient = sportFieldClient;
        this.tournamentClient = tournamentClient;
        this.sportMatchRepository = sportMatchRepository;
    }


    @Override
    public SportMatchResponse addSportMatch(SportMatchRequest sportMatchRequest) {

        validateSportMatchRequest(sportMatchRequest);

        SportMatchResponse sportMatchResponse = new SportMatchResponse();


        SportFieldDto sportFieldToAdd = getSportFieldFromSportFieldService(sportMatchRequest.getSportField());
        if (sportFieldToAdd == null)
            throw new MatchSportFieldNotFoundException(SportMatchMsg.MATCH_NOT_EXIST);

        TournamentDto tournamentDto = getTournamentFromTournamentService(sportMatchRequest.getTournament());
        if (tournamentDto == null)
            throw new MatchTournamentdNotFoundException(SportMatchMsg.TOURNAMENT_NOT_EXIST);

        List<TeamDto> teamsToAdd = new ArrayList<>();
        List<PlayerDto> participantsToAdd = new ArrayList<>();

        if (sportMatchRequest.getTeams() != null) {
            for (Integer teamId : sportMatchRequest.getTeams()) {
                TeamDto teamDto = getTeamFromTeamService(teamId);
                if (teamDto == null)
                    throw new MatchTeamNotFoundException(SportMatchMsg.TEAM_NOT_EXIST + teamId);

                teamsToAdd.add(teamDto);
            }
        } else {
            teamsToAdd = Collections.emptyList();
        }

        if (sportMatchRequest.getParticipants() != null) {
            for (Integer playerId : sportMatchRequest.getParticipants()) {

                PlayerDto playerDto = getPlayerFromPlayerService(playerId);
                if (playerDto == null)
                    throw new MatchPlayerNotFoundException(SportMatchMsg.PLAYER_NOT_EXIST + playerId);

                participantsToAdd.add(playerDto);
            }
        } else {
            participantsToAdd = Collections.emptyList();
        }


        sportMatchResponse.setSportField(sportFieldToAdd);
        sportMatchResponse.setTournament(tournamentDto);
        sportMatchResponse.setTeams(teamsToAdd);
        sportMatchResponse.setParticipants(participantsToAdd);
        sportMatchResponse.setDateTime(sportMatchRequest.getDateTime());

        SportMatch.SportMatchBuilder sportMatchBuilder = SportMatch.builder()
                .dateTime(sportMatchRequest.getDateTime())
                .sportField(sportMatchRequest.getSportField())
                .tournament(sportMatchRequest.getTournament());

        if (sportMatchRequest.getTeams() != null) {
            sportMatchBuilder.teamsId(sportMatchRequest.getTeams().toString().replaceAll(SportMatchMsg.BRACKET_REGEX, ""));
        }

        if (sportMatchRequest.getParticipants() != null) {
            sportMatchBuilder.participantsId(sportMatchRequest.getParticipants().toString().replaceAll(SportMatchMsg.BRACKET_REGEX, ""));
        }

        SportMatch sportMatch = sportMatchBuilder.build();

        sportMatchRepository.save(sportMatch);

        return sportMatchResponse;
    }


    @Override
    public Page<SportMatchResponse> getSportMatches(Integer field, Integer tournament, List<String> players, List<String> teams, Date date, Pageable pageable) {

        Specification<SportMatch> spec = SportMatchSpecification.matchesByCriteria(field, tournament, players, teams, date, pageable);


        Page<SportMatch> matches = sportMatchRepository.findAll(spec, pageable);

        List<SportMatchResponse> matchResponses = new ArrayList<>();

        matches.forEach(fetchedMatches -> {
            SportFieldDto sportFieldToAdd = getSportFieldFromSportFieldService(fetchedMatches.getSportField());
            TournamentDto tournamentToAdd = getTournamentFromTournamentService(fetchedMatches.getTournament());
            List<TeamDto> teamsToAdd = fetchedMatches.getTeamsId() == null ? new ArrayList<>() : Arrays.stream(fetchedMatches.getTeamsId().split(",")).filter(StringUtils::hasText).map(String::trim).map(Integer::valueOf).map(this::getTeamFromTeamService).collect(Collectors.toList());
            List<PlayerDto> participantsToAdd = fetchedMatches.getParticipantsId() == null ? new ArrayList<>() : Arrays.stream(fetchedMatches.getParticipantsId().split(",")).filter(StringUtils::hasText).map(String::trim).map(Integer::valueOf).map(this::getPlayerFromPlayerService).collect(Collectors.toList());


            //Remove null values
            teamsToAdd.removeIf(Objects::isNull);
            participantsToAdd.removeIf(Objects::isNull);


            matchResponses.add(new SportMatchResponse(sportFieldToAdd, tournamentToAdd, teamsToAdd, participantsToAdd, fetchedMatches.getDateTime()));
        });

        return new PageImpl<>(matchResponses, pageable, matches.getTotalElements());
    }

    @Override
    public SportMatchResponse getSportMatchById(Integer id) {

        SportMatch match = sportMatchRepository.findById(id).orElseThrow(() -> new MatchNotFoundException(ExceptionMessage.SPORTMATCH_NOT_FOUND));

        SportMatchResponse response = new SportMatchResponse();

        SportFieldDto sportFieldToAdd = getSportFieldFromSportFieldService(match.getSportField());
        TournamentDto tournamentToAdd = getTournamentFromTournamentService(match.getTournament());
        List<TeamDto> teamsToAdd = match.getTeamsId() == null ? new ArrayList<>() : Arrays.stream(match.getTeamsId().split(",")).filter(StringUtils::hasText).map(String::trim).map(Integer::valueOf).map(this::getTeamFromTeamService).collect(Collectors.toList());

        List<PlayerDto> participantsToAdd = match.getParticipantsId() == null ? new ArrayList<>() : Arrays.stream(match.getParticipantsId().split(",")).filter(StringUtils::hasText).map(String::trim).map(Integer::valueOf).map(this::getPlayerFromPlayerService).collect(Collectors.toList());


        //Remove null values
        teamsToAdd.removeIf(Objects::isNull);
        participantsToAdd.removeIf(Objects::isNull);


        //Setting the data for the return
        response.setSportField(sportFieldToAdd);
        response.setTournament(tournamentToAdd);
        response.setTeams(teamsToAdd);
        response.setParticipants(participantsToAdd);
        response.setDateTime(match.getDateTime());
        return response;
    }


    @Override
    public SportMatchResponse updateSportMatch(int id, SportMatchRequest sportMatchRequest) {
        SportMatch sportMatch = sportMatchRepository.findById(id).orElseThrow(() -> new SportMatchNotFoundException(ExceptionMessage.SPORTMATCH_NOT_FOUND));
        SportMatchResponse sportMatchResponse = new SportMatchResponse();




        // Set new values if they are not null or empty
        if (sportMatchRequest.getSportField() > 0) {

            SportFieldDto sportFieldToAdd = getSportFieldFromSportFieldService(sportMatchRequest.getSportField());
            if (sportFieldToAdd == null)
                throw new MatchSportFieldNotFoundException("SportField you were trying to add doesnt exist!");

            sportMatch.setSportField(sportMatchRequest.getSportField());
            sportMatchResponse.setSportField(sportFieldToAdd);
        }

        if (sportMatchRequest.getTournament() > 0) {

            TournamentDto tournamentDto = getTournamentFromTournamentService(sportMatchRequest.getTournament());
            if (tournamentDto == null)
                throw new MatchTournamentdNotFoundException("Tournament you were trying to add doesnt exist!");

            sportMatch.setTournament(sportMatchRequest.getTournament());
            sportMatchResponse.setTournament(tournamentDto);
        }
        if (sportMatchRequest.getDateTime() != null) {
            sportMatch.setDateTime(sportMatchRequest.getDateTime());
        }

        // Update teams and participants only if they are not null or empty
        if (sportMatchRequest.getTeams() != null && !sportMatchRequest.getTeams().isEmpty()) {
            List<TeamDto> teams = sportMatchRequest.getTeams().stream().map(this::getTeamFromTeamService).collect(Collectors.toList());

            if (teams.stream().anyMatch(Objects::isNull))
                throw new MatchTeamNotFoundException("You are trying to add Team/s that does not exist. Please check again.");


            sportMatch.setTeamsId(sportMatchRequest.getTeams().toString().replaceAll(SportMatchMsg.BRACKET_REGEX, ""));
            sportMatchResponse.setTeams(teams);
        }
        if (sportMatchRequest.getParticipants() != null && !sportMatchRequest.getParticipants().isEmpty()) {
            List<PlayerDto> players = sportMatchRequest.getParticipants().stream().map(this::getPlayerFromPlayerService).collect(Collectors.toList());
            if (players.stream().anyMatch(Objects::isNull))
                throw new MatchTeamNotFoundException("You are trying to add Player/s that does not exist. Please check again.");

            sportMatch.setParticipantsId(sportMatchRequest.getParticipants().toString().replaceAll(SportMatchMsg.BRACKET_REGEX, ""));
            sportMatchResponse.setParticipants(players);
        }

        sportMatchRepository.save(sportMatch);
        return sportMatchResponse;
    }

    @Override
    public String deleteSportMatch(int id) {
        SportMatch sportMatch = sportMatchRepository.findById(id).orElseThrow(() -> new SportMatchNotFoundException(ExceptionMessage.SPORTMATCH_NOT_FOUND));
        sportMatchRepository.delete(sportMatch);
        return SportMatchMsg.SPORTMATCH_DELETED;
    }

    public TournamentDto getTournamentFromTournamentService(int tournament) {
        try {
            return tournamentClient.getTournamentById(tournament).getBody();
        } catch (FeignException e) {
            if (e.getMessage().contains("TournamentNotFoundException")) {
                return null;
            } else {
                throw new ServiceNotAvailableException(SportMatchMsg.SERVICE_ERROR);
            }
        }
    }


    public SportFieldDto getSportFieldFromSportFieldService(int sportFieldId) {

        try {
            return sportFieldClient.getSportFieldById(sportFieldId).getBody();
        } catch (FeignException e) {
            if (e.getMessage().contains("SportFieldNotFoundException")) {
                return null;
            } else {
                throw new ServiceNotAvailableException(SportMatchMsg.SERVICE_ERROR);
            }
        }


    }

    public TeamDto getTeamFromTeamService(int teamId) {
        try {
            return teamClient.getTeamById(teamId);
        } catch (FeignException e) {
            if (e.getMessage().contains("TeamNotFoundException")) {
                return null;
            } else {
                throw new ServiceNotAvailableException(SportMatchMsg.SERVICE_ERROR);
            }
        }
    }

    public PlayerDto getPlayerFromPlayerService(int playerId) {
        try {
            return playerClient.getPlayerById(playerId).getBody();
        } catch (FeignException e) {
            if (e.getMessage().contains("PlayerNotFoundException")) {

                return null;

            } else {
                throw new ServiceNotAvailableException("Error occurred while fetching Player details. It has nothing to do with this service.");
            }
        }
    }

    public void validateSportMatchRequest(SportMatchRequest sportMatchRequest) {
        if (sportMatchRequest == null) {
            throw new EmptyFieldException();
        }

        if (org.apache.commons.lang.StringUtils.isBlank(sportMatchRequest.getSportField() + "") ||
                org.apache.commons.lang.StringUtils.isBlank(sportMatchRequest.getTournament() + "") ||
                (sportMatchRequest.getTeams() == null && sportMatchRequest.getParticipants() == null) ||
                sportMatchRequest.getDateTime() == null) {
            throw new EmptyFieldException();
        }
    }

}
