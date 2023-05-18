package com.menin.playerservice.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlayerDto {


    private String firstName;

    private  String lastName;


    private  String country;


    private Integer teamId;
}
