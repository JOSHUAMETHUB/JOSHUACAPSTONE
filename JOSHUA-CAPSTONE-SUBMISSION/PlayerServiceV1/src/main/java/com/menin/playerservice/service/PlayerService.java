package com.menin.playerservice.service;

import com.menin.playerservice.dto.PlayerDto;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PlayerService {

    public String addPlayer(PlayerDto playerDto);
    public List<PlayerDto> getPlayers(String firstname, String lastname, String country, Integer teamId, Pageable pageable);
    public PlayerDto getPlayerById(int id);
    public String updatePlayer(int id, PlayerDto playerDto);
    public String deletePlayer(int id);


}
