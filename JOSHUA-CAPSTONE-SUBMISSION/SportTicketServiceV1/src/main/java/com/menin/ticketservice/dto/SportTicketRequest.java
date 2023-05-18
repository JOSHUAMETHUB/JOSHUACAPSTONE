package com.menin.ticketservice.dto;

import lombok.*;


@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SportTicketRequest {

    private String customerName;
    private float ticketPrice;
    private int sportMatch;
}
