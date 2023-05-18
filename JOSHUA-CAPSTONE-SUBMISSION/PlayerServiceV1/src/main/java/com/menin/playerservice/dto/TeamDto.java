package com.menin.playerservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import java.util.List;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {


    private String name;

    @JsonIgnore
    private List<PlayerDto> players;


}
