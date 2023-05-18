package com.menin.tournamentservice.repository;

import com.menin.tournamentservice.entity.Tournament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TournamentRepository extends JpaRepository<Tournament,Integer> {
    List<Tournament> findByName(String name);

    Page<Tournament> findAll(Specification<Tournament> spec, Pageable pageable);
}
