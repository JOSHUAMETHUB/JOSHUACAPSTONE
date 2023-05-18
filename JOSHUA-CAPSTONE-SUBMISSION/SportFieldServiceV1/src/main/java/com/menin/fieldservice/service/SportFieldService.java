package com.menin.fieldservice.service;

import com.menin.fieldservice.dto.SportFieldDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SportFieldService {

    public SportFieldDto addSportField(SportFieldDto sportFIeldDto);

    public Page<SportFieldDto> getSportFields(String name, Pageable pageable);

    public SportFieldDto getSportFieldById(int id);


    public SportFieldDto editSportField(int id, SportFieldDto sportFieldDto);

    public String deleteSportFieldById(int id);



}
