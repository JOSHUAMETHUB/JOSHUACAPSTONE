package com.menin.fieldservice.dto;


import lombok.*;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SportMatchDto {

    private SportFieldDto sportField;
    private TournamentDto tournament;
    private String teamIds;
    private String participantIds;
    private Date dateTime;
}
