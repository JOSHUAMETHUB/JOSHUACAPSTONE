package com.menin.matchservice.dto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SportTicketDto {

    private String customerName;
    private float ticketPrice;
    private SportMatchRequest sportMatch;
}
