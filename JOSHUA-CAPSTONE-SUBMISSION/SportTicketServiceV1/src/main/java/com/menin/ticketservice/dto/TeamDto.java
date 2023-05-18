package com.menin.ticketservice.dto;

import lombok.*;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamDto {
    private String name;
    private List<PlayerDto> players;
}
