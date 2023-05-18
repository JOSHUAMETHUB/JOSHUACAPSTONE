package com.menin.ticketservice.dto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TournamentDto {

    private String name;
    private String category;
    private String style;
}
