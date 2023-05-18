package com.menin.teamservice.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {

    private String firstName;
    private String lastName;
    private String country;
    @JsonIgnore
    private int teamId;


}
