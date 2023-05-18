package com.menin.matchservice.util;


import com.menin.matchservice.entity.SportMatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class SportMatchSpecification {
    private SportMatchSpecification(){}

    public static Specification<SportMatch> matchesByField(Integer field) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("sportField"), field);
    }

    public static Specification<SportMatch> matchesByTournament(Integer tournament) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("tournament"), tournament);
    }

    public static Specification<SportMatch> matchesByPlayers(List<String> players) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String playerId : players) {
                predicates.add(criteriaBuilder.like(root.get("participantsId"), "%" + playerId + "%"));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<SportMatch> matchesByTeams(List<String> teams) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String teamId : teams) {
                predicates.add(criteriaBuilder.like(root.get("teamsId"), "%" + teamId + "%"));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<SportMatch> matchesByDate(Date date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("dateTime"), date);
    }

    public static Specification<SportMatch> matchesByPageable(Pageable pageable) {
        log.info(pageable.toString());
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("dateTime")));
            return null;
        };
    }




    public static Specification<SportMatch> matchesByCriteria(Integer field, Integer tournament, List<String> players, List<String> teams, Date date, Pageable pageable) {
        Specification<SportMatch> spec = Specification.where(null);

        if (field != null) {
            spec = spec.and(matchesByField(field));
        }

        if (tournament != null) {
            spec = spec.and(matchesByTournament(tournament));
        }

        if (players != null && !players.isEmpty()) {
            spec = spec.and(matchesByPlayers(players));
        }

        if (teams != null && !teams.isEmpty()) {
            spec = spec.and(matchesByTeams(teams));
        }

        if (date != null) {
            spec = spec.and(matchesByDate(date));
        }

        if (pageable != null) {
            spec = spec.and(matchesByPageable(pageable));
        }



        return spec;
    }
}
