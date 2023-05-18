package com.menin.ticketservice.repository;


import com.menin.ticketservice.entity.SportTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SportTicketRepository extends JpaRepository<SportTicket, Integer> {

    Page<SportTicket> findAll(Specification<SportTicket> spec, Pageable pageable);
}
