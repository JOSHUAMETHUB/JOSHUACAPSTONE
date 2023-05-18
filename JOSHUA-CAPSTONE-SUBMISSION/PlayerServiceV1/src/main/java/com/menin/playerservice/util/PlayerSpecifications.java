package com.menin.playerservice.util;


import com.menin.playerservice.entity.Player;
import org.springframework.data.jpa.domain.Specification;

public class PlayerSpecifications {

    private PlayerSpecifications(){

    }

    public static Specification<Player> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("firstName")),
                "%" + firstName.toUpperCase() + "%");
    }

    public static Specification<Player> hasLastName(String lastName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("lastName")),
                "%" + lastName.toUpperCase() + "%");
    }

    public static Specification<Player> hasCountry(String country) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("country")),
                "%" + country.toUpperCase() + "%");
    }

    public static Specification<Player> hasTeamId(Integer teamId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("teamId"), teamId);
    }


}
