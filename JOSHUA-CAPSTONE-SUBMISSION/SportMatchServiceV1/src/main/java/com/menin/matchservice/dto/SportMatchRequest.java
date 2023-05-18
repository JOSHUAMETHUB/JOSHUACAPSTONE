package com.menin.matchservice.dto;



import lombok.*;

import java.util.Date;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SportMatchRequest {

    private int sportField;
    private int tournament;
    private List<Integer> teams;
    private List<Integer> participants;
    private Date dateTime;
}
