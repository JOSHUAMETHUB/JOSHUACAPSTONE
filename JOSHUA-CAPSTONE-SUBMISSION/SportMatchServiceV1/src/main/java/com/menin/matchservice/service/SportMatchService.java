package com.menin.matchservice.service;


import com.menin.matchservice.dto.SportMatchRequest;
import com.menin.matchservice.dto.SportMatchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface SportMatchService {


    public SportMatchResponse addSportMatch(SportMatchRequest sportMatchRequest);
    Page<SportMatchResponse> getSportMatches(Integer field, Integer tournament, List<String> players, List<String> teams, Date date, Pageable pageable);
    public SportMatchResponse getSportMatchById(Integer id);
    public SportMatchResponse updateSportMatch(int id, SportMatchRequest sportMatchRequest);
    public String deleteSportMatch(int id);


}
