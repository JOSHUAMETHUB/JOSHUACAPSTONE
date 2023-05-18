package com.menin.playerservice.service;


import com.menin.playerservice.constant.PlayerExceptionMsg;
import com.menin.playerservice.constant.PlayerResponseMessage;
import com.menin.playerservice.dao.PlayerRepository;
import com.menin.playerservice.dto.PlayerDto;
import com.menin.playerservice.entity.Player;
import com.menin.playerservice.exception.EmptyFieldException;
import com.menin.playerservice.exception.PlayerNotFoundException;
import com.menin.playerservice.util.EntityAndDtoConverter;
import com.menin.playerservice.util.PlayerSpecifications;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    RestTemplate restTemplate;




    private final PlayerRepository playerRepository;

    private final EntityAndDtoConverter entityAndDtoConverter;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, EntityAndDtoConverter entityAndDtoConverter) {
        this.playerRepository = playerRepository;
        this.entityAndDtoConverter = entityAndDtoConverter;
    }

    @Override
    public String addPlayer(PlayerDto playerDto) {

        if (StringUtils.isBlank(playerDto.getFirstName()) || StringUtils.isBlank(playerDto.getLastName()) || StringUtils.isBlank(playerDto.getCountry()))
            throw new EmptyFieldException();

        return PlayerResponseMessage.ADDED + playerRepository.save(entityAndDtoConverter.convertToPlayer(playerDto)).getId();
    }


    @Override
    public List<PlayerDto> getPlayers(String firstname, String lastname, String country, Integer teamId, Pageable pageable) {

        Specification<Player> spec = Specification.where(null);

        if (StringUtils.isNotBlank(firstname)) {
            spec = spec.and(PlayerSpecifications.hasFirstName(firstname));
        }

        if (StringUtils.isNotBlank(lastname)) {
            spec = spec.and(PlayerSpecifications.hasLastName(lastname));
        }

        if (StringUtils.isNotBlank(country)) {
            spec = spec.and(PlayerSpecifications.hasCountry(country));
        }

        if (teamId != null) {

            spec = spec.and(PlayerSpecifications.hasTeamId(teamId));
        }

        Page<Player> players = playerRepository.findAll(spec, pageable);

        if (players.getContent().isEmpty()) {
            throw new PlayerNotFoundException(PlayerExceptionMsg.PLAYER_NOT_FOUND);
        }

        List<Player> playerList = players.getContent();
        return entityAndDtoConverter.convertToPlayerDtoList(playerList);
    }

    @Override
    public PlayerDto getPlayerById(int id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(PlayerExceptionMsg.PLAYER_NOT_FOUND));


        return entityAndDtoConverter.convertToPlayerDto(player);
    }


    @Override
    public String updatePlayer(int id, PlayerDto playerDto) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(PlayerExceptionMsg.PLAYER_NOT_FOUND));

        if (playerDto.getFirstName() != null) player.setFirstName(playerDto.getFirstName());
        if (playerDto.getLastName() != null) player.setLastName(playerDto.getLastName());
        if (playerDto.getCountry() != null) player.setCountry(playerDto.getCountry());
        if (playerDto.getTeamId() !=null ) player.setTeamId(playerDto.getTeamId());


        entityAndDtoConverter.convertToPlayer(playerDto);


        playerRepository.save(player);

        return PlayerResponseMessage.UPDATED + "\nFirstname : " + player.getFirstName() + "\nLastname : " + player.getLastName() + "\nCountry : " + player.getCountry() + "\nTeam : " + player.getTeamId();
    }

    @Override
    public String deletePlayer(int id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(PlayerExceptionMsg.PLAYER_NOT_FOUND));
        playerRepository.delete(player);
        return PlayerResponseMessage.DELETED;
    }


}
