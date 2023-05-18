package com.menin.teamservice.entity;


import com.menin.teamservice.dto.PlayerDto;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity(name = "teams")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class Team implements Comparable<Team>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int id;

    @Column(name = "team_name")
    private String name;


    @Transient
    private transient List<PlayerDto> players;

    @Override
    public int compareTo(Team o) {

        return (getName().compareTo(o.getName()));

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, players);
    }
}
