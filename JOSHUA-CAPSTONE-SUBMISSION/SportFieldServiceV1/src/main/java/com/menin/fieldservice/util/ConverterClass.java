package com.menin.fieldservice.util;

import com.menin.fieldservice.dto.SportFieldDto;
import com.menin.fieldservice.entity.SportField;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


@Component
public class ConverterClass {


    private final ModelMapper modelMapper;


    @Autowired
    public ConverterClass(ModelMapper modelMapper, ModelMapper modelMapper1) {


        this.modelMapper = modelMapper1;
    }


    public SportFieldDto convertToSportFieldDto(SportField sportField) {

        return modelMapper.map(sportField, SportFieldDto.class);
    }

    public SportField convertToSportField(SportFieldDto sportFieldDto) {
        return modelMapper.map(sportFieldDto, SportField.class);
    }


    public Page<SportFieldDto> convertToSportFieldDtoPage(Page<SportField> sportFields) {
        TypeToken<Page<SportFieldDto>> typeToken = new TypeToken<Page<SportFieldDto>>() {
        };
        return modelMapper.map(sportFields, typeToken.getType());
    }


}
