package com.menin.fieldservice.service;

import com.menin.fieldservice.constant.ExceptionMessage;
import com.menin.fieldservice.constant.SportFieldMessage;
import com.menin.fieldservice.constant.SportFieldFixedValues;
import com.menin.fieldservice.dto.SportFieldDto;
import com.menin.fieldservice.entity.SportField;
import com.menin.fieldservice.exception.EmptyFieldException;
import com.menin.fieldservice.exception.SportFieldCapacityExceededException;
import com.menin.fieldservice.exception.SportFieldNotFoundException;
import com.menin.fieldservice.repository.SportFieldRepository;
import com.menin.fieldservice.util.ConverterClass;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class SportFieldServiceImpl implements SportFieldService {


    private final SportFieldRepository sportFieldRepository;


    private final ConverterClass converterClass;


    @Autowired
    public SportFieldServiceImpl(SportFieldRepository sportFieldRepository, ConverterClass converterClass) {
        this.sportFieldRepository = sportFieldRepository;
        this.converterClass = converterClass;
    }


    @Override
    public SportFieldDto addSportField(SportFieldDto sportFieldDto) {

        if (StringUtils.isBlank(sportFieldDto.getName()) || StringUtils.isBlank(sportFieldDto.getAddress()) || sportFieldDto.getCapacity() == 0) {//CAUSE
            throw new EmptyFieldException();
        }


        if(sportFieldDto.getCapacity() > SportFieldFixedValues.SPORTFIELD_MAX_CAPACITY){
            throw new SportFieldCapacityExceededException(ExceptionMessage.SPORTFIELD_CAPACITY_EXCEEDED);
        }

        SportField persistData = SportField.builder().name(sportFieldDto.getName()).address(sportFieldDto.getAddress()).capacity(sportFieldDto.getCapacity()).build();

        SportField sportField = sportFieldRepository.save(persistData);
        return converterClass.convertToSportFieldDto(sportField);
    }

    @Override
    public Page<SportFieldDto> getSportFields(String name, Pageable pageable) {

        Page<SportField> sportFields;

        if(name != null){
            sportFields = sportFieldRepository.findByName(name, pageable);

        }else{
            sportFields = sportFieldRepository.findAll(pageable);
        }

        if(sportFields.isEmpty())// this is giving the null pointer
            throw new SportFieldNotFoundException(ExceptionMessage.SPORTFIELD_NOT_FOUND);

        return converterClass.convertToSportFieldDtoPage(sportFields);
    }

    @Override
    public SportFieldDto getSportFieldById(int id) {
        return converterClass.convertToSportFieldDto(sportFieldRepository.findById(id).orElseThrow(()->  new SportFieldNotFoundException(ExceptionMessage.SPORTFIELD_NOT_FOUND)));
    }

    @Override
    public SportFieldDto editSportField(int id, SportFieldDto sportFieldDto) {
        SportField sportField = sportFieldRepository.findById(id).orElseThrow(()->new SportFieldNotFoundException(ExceptionMessage.SPORTFIELD_NOT_FOUND));




        if (sportFieldDto.getName() != null) {
            sportField.setName(sportFieldDto.getName());
        }

        if (sportFieldDto.getAddress() != null) {
            sportField.setAddress(sportFieldDto.getAddress());
        }

        if (sportFieldDto.getCapacity() != 0) {
            sportField.setCapacity(sportFieldDto.getCapacity());
        }

        sportFieldRepository.save(sportField);

        return converterClass.convertToSportFieldDto(sportField);
    }

    @Override
    public String deleteSportFieldById(int id) {
        SportField sportFieldToDelete = sportFieldRepository.findById(id).orElseThrow(()-> new SportFieldNotFoundException(ExceptionMessage.SPORTFIELD_NOT_FOUND));
        sportFieldRepository.delete(sportFieldToDelete);
        return SportFieldMessage.SPORTFIELD_DELETED;
    }




}
