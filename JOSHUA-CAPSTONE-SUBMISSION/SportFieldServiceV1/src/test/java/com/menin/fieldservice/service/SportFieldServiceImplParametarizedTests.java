package com.menin.fieldservice.service;

import com.menin.fieldservice.constant.SportFieldFixedValues;
import com.menin.fieldservice.dto.SportFieldDto;
import com.menin.fieldservice.entity.SportField;
import com.menin.fieldservice.exception.EmptyFieldException;
import com.menin.fieldservice.exception.SportFieldCapacityExceededException;
import com.menin.fieldservice.exception.SportFieldNotFoundException;
import com.menin.fieldservice.repository.SportFieldRepository;
import com.menin.fieldservice.util.ConverterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
@SpringBootTest(classes = SportFieldServiceImpl.class)
public class SportFieldServiceImplParametarizedTests {


    private String nameT;
    private String addressT;
    private int capacityT;
    private Class<? extends Exception> expectedException;

    public SportFieldServiceImplParametarizedTests(String nameT, String addressT, int capacityT, Class<? extends Exception> expectedException) {
        this.nameT = nameT;
        this.addressT = addressT;
        this.capacityT = capacityT;
        this.expectedException = expectedException;
    }

    @Mock
    private SportFieldRepository sportFieldRepository;

    @Mock
    private ConverterClass converterClass;

    @InjectMocks
    private SportFieldServiceImpl sportFieldService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }



    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"Address 1", null, 100, EmptyFieldException.class},
                {"Address 1", "Address 1", 0, EmptyFieldException.class},
                {null, "Address 1", 1001, EmptyFieldException.class}
        });
    }

    @Test
    public void testAddSportFieldAnyNull() {
        SportFieldDto sportFieldDto = new SportFieldDto();
        sportFieldDto.setName(nameT);
        sportFieldDto.setAddress(addressT);
        sportFieldDto.setCapacity(capacityT);

        assertThrows(expectedException, () -> sportFieldService.addSportField(sportFieldDto));
    }

}



