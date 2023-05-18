package com.menin.fieldservice.controller;


import com.menin.fieldservice.dto.SportFieldDto;
import com.menin.fieldservice.service.SportFieldService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sport-fields")
@AllArgsConstructor
public class SportFieldController {

    @Autowired
    private final SportFieldService sportFieldService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SportFieldDto> addSportField(@RequestBody SportFieldDto sportFieldDto){
        return ResponseEntity.ok(sportFieldService.addSportField(sportFieldDto));
    }

    @GetMapping
    public  ResponseEntity<Page<SportFieldDto>> getSportFields(
            @RequestParam(required = false) String name,
            Pageable pageable){
        return ResponseEntity.ok(sportFieldService.getSportFields(name, pageable));
    }


    @GetMapping("/{id}")
    public  ResponseEntity<SportFieldDto> getSportFieldById(
            @PathVariable int id){
        return ResponseEntity.ok(sportFieldService.getSportFieldById(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public  ResponseEntity<SportFieldDto> editSportField(
            @PathVariable int id, @RequestBody SportFieldDto sportFieldDto){
        return ResponseEntity.ok(sportFieldService.editSportField(id, sportFieldDto));
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public  ResponseEntity<String> deleteSportFieldById(
            @PathVariable int id){
        return ResponseEntity.ok(sportFieldService.deleteSportFieldById(id));
    }
}
