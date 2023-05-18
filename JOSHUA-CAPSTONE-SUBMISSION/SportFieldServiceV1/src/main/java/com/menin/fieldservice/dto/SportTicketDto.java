package com.menin.fieldservice.dto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SportTicketDto {

    private String customerName;
    private float ticketPrice;
    private SportMatchDto sportMatch;
}
