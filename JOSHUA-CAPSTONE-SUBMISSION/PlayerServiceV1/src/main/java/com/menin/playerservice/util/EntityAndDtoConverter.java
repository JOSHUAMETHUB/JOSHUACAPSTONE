package com.menin.playerservice.util;


import com.menin.playerservice.dto.PlayerDto;
import com.menin.playerservice.entity.Player;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class EntityAndDtoConverter {
    private final ModelMapper modelMapper;
    @Autowired
    public EntityAndDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

    }

    public PlayerDto convertToPlayerDto(Player sportField) {
        return modelMapper.map(sportField, PlayerDto.class);
    }

    public Player convertToPlayer(PlayerDto sportFieldDto) {
        return modelMapper.map(sportFieldDto, Player.class);
    }

    public List<PlayerDto> convertToPlayerDtoList(List<Player> players) {
        TypeToken<List<PlayerDto>> typeToken = new TypeToken<List<PlayerDto>>() {
        };
        return modelMapper.map(players, typeToken.getType());
    }


    public Page<PlayerDto> convertToPlayerDtoPage(Page<Player> players) {
        TypeToken<Page<PlayerDto>> typeToken = new TypeToken<Page<PlayerDto>>() {
        };
        return modelMapper.map(players, typeToken.getType());
    }

}
