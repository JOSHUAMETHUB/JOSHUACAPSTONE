package com.menin.tournamentservice.util;


import com.menin.tournamentservice.entity.Tournament;
import org.springframework.data.jpa.domain.Specification;

public class TournamentSpecifications {

    private TournamentSpecifications(){}

    public static Specification<Tournament> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("name")),
                "%" + name.toUpperCase() + "%");
    }

    public static Specification<Tournament> hasCategory(String category) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("category")),
                "%" + category.toUpperCase() + "%");
    }

    public static Specification<Tournament> hasStyle(String style) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("style")),
                "%" + style.toUpperCase() + "%");
    }


}
