package com.menin.tournamentservice.dto;


import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class TournamentDto {

    private String name;
    private String category;
    private String style;
}
