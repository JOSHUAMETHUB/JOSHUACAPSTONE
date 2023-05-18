package com.menin.ticketservice.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity(name = "tickets")
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class SportTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "ticket_price")
    private float ticketPrice;


    @Column(name = "match_id")
    private int sportMatch;
}
