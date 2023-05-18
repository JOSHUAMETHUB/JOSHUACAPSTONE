package com.menin.playerservice.dao;

import com.menin.playerservice.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {

    Page<Player> findByFirstNameAndCountryAndTeamId(String firstname, String country, int teamid, Pageable pageable);

    Page<Player> findByLastNameAndCountryAndTeamId(String lastname, String country, int teamid, Pageable pageable);

    Page<Player> findByFirstNameAndTeamId(String firstname, int teamid, Pageable pageable);

    Page<Player> findByLastNameAndTeamId(String lastname, int teamid, Pageable pageable);




    Page<Player> findByFirstNameAndCountry(String firstname, String country, Pageable pageable);

    Page<Player> findByLastNameAndCountry(String lastname, String country, Pageable pageable);

    Page<Player> findByTeamId(int teamid, Pageable pageable);

    Page<Player> findByCountry(String country, Pageable pageable);

    Page<Player> findByLastName(String lastname, Pageable pageable);

    Page<Player> findByFirstName(String firstname, Pageable pageable);

    Page<Player> findAll(Specification<Player> spec, Pageable pageable);



    Page<Player> findByCountryAndTeamId(String fieldValue, Integer teamId, Pageable pageable);

    @Query(value = "SELECT MAX(id) FROM players")
    Optional<Integer> findLatestPlayerId();
}
