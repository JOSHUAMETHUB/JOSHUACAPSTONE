package com.menin.fieldservice.entity;


import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity(name = "sportfields")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class SportField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id")
    private int id;

    @Column(name = "f_name")
    private String name;


    @Column(name = "f_address")
    private String address;

    @Column(name = "f_capacity")
    private int capacity;
}
