package com.menin.matchservice.repository;

import com.menin.matchservice.entity.SportMatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SportMatchRepository extends JpaRepository<SportMatch, Integer> {


    Page<SportMatch> findAll(Specification<SportMatch> spec, Pageable pageable);
}
