package com.menin.matchservice.dto;


import lombok.*;

import java.util.Date;


@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SportMatchDto {
    private int sportField;
    private int tournament;
    private String teamsId;
    private String participantsId;
    private Date dateTime;
}
