package com.menin.ticketservice.util;


import com.menin.ticketservice.entity.SportTicket;
import org.springframework.data.jpa.domain.Specification;

public class SportTicketSpecification {

    private SportTicketSpecification() {
    }

    public static Specification<SportTicket> ticketsByCustomerName(String customerName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("customerName"), customerName);
    }

    public static Specification<SportTicket> ticketsByPrice(float ticketPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ticketPrice"), ticketPrice);
    }

    public static Specification<SportTicket> ticketsBySportMatch(int sportMatchId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("sportMatch"), sportMatchId);
    }

    public static Specification<SportTicket> ticketsByCriteria(String customerName, Float ticketPrice, Integer sportMatchId) {
        Specification<SportTicket> spec = Specification.where(null);

        if (customerName != null) {
            spec = spec.and(ticketsByCustomerName(customerName));
        }

        if (ticketPrice != null) {
            spec = spec.and(ticketsByPrice(ticketPrice));
        }

        if (sportMatchId != null) {
            spec = spec.and(ticketsBySportMatch(sportMatchId));
        }

        return spec;
    }
}

