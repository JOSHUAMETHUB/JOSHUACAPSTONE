package com.menin.matchservice.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SportFieldDto {

    private String name;
    private String address;
    private int capacity;
}
