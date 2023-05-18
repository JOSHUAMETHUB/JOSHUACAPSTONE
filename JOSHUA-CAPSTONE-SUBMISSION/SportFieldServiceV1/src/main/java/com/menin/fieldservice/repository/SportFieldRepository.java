package com.menin.fieldservice.repository;

import com.menin.fieldservice.entity.SportField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SportFieldRepository extends JpaRepository<SportField, Integer> {

    Page<SportField> findByName(String name, Pageable pageable);
}
