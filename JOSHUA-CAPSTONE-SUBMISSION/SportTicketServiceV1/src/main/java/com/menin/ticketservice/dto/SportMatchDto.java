package com.menin.ticketservice.dto;

import lombok.*;

import java.util.Date;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SportMatchDto {

    private SportFieldDto sportField;
    private TournamentDto tournament;
    private List<TeamDto> teams;
    private List<PlayerDto> participants;
    private Date dateTime;


}
