package com.menin.matchservice.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "matches")
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class SportMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private int id;



    @Column(name = "field_id")
    private int sportField;


    @Column(name = "tournament_id")
    private int tournament;


    @Column(name = "teams_id")
    private String teamsId;


    @Column(name = "participants_id")
    private String participantsId;

    @Column(name = "date_time")
    private Date dateTime;
}
