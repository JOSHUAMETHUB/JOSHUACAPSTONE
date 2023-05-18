package com.menin.matchservice.dto;


import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SportMatchResponse {

    private SportFieldDto sportField;
    private TournamentDto tournament;
    private List<TeamDto> teams;
    private List<PlayerDto> participants;
    private Date dateTime;
}
