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

@SpringBootTest(classes = SportFieldServiceImpl.class)
public class SportFieldServiceImplTest {



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

    @Test
    public void testGetSportFieldsWithNameParam() {
        String name = "John";
        Pageable pageable = mock(Pageable.class);

        SportField sportField = new SportField();
        sportField.setId(1);
        sportField.setName("John");

        List<SportField> sportFieldList = new ArrayList<>();
        sportFieldList.add(sportField);

        Page<SportField> sportFields = new PageImpl<>(sportFieldList);

        when(sportFieldRepository.findByName(name, pageable)).thenReturn(sportFields);
        when(sportFieldRepository.findAll(pageable)).thenReturn(sportFields);

        SportFieldDto sportFieldDto = new SportFieldDto();
        sportFieldDto.setName("John");

        List<SportFieldDto> expectedResponse = new ArrayList<>();
        expectedResponse.add(sportFieldDto);

        when(converterClass.convertToSportFieldDtoPage(sportFields)).thenReturn(new PageImpl<>(expectedResponse));

        Page<SportFieldDto> actualResponse = sportFieldService.getSportFields(name, pageable);

        assertEquals(expectedResponse, actualResponse.getContent());

        if (name != null) {
            verify(sportFieldRepository, times(1)).findByName(name, pageable);
        } else {
            verify(sportFieldRepository, times(1)).findAll(pageable);
        }
        verify(converterClass, times(1)).convertToSportFieldDtoPage(sportFields);
    }


    @Test(expected = EmptyFieldException.class)
    public void testAddSportFieldWithEmptyFields() {
        SportFieldDto sportFieldDto = new SportFieldDto(); // Empty fields

        sportFieldService.addSportField(sportFieldDto);
    }



    @Test(expected = SportFieldCapacityExceededException.class)
    public void testAddSportFieldWithExceededCapacity() {
        SportFieldDto sportFieldDto = new SportFieldDto();
        sportFieldDto.setName("Field 1");
        sportFieldDto.setAddress("Address 1");
        sportFieldDto.setCapacity(SportFieldFixedValues.SPORTFIELD_MAX_CAPACITY + 1); // Exceeded capacity

        sportFieldService.addSportField(sportFieldDto);
    }

    // ...

    @Test
    public void testGetSportFieldById() {
        int sportFieldId = 1;
        SportField sportField = new SportField();
        when(sportFieldRepository.findById(sportFieldId)).thenReturn(Optional.of(sportField));
        when(converterClass.convertToSportFieldDto(sportField)).thenReturn(new SportFieldDto());

        SportFieldDto result = sportFieldService.getSportFieldById(sportFieldId);

        assertNotNull(result);
        verify(sportFieldRepository, times(1)).findById(sportFieldId);
        verify(converterClass, times(1)).convertToSportFieldDto(sportField);
    }

    @Test(expected = SportFieldNotFoundException.class)
    public void testGetSportFieldByIdNotFound() {
        int sportFieldId = 1;
        when(sportFieldRepository.findById(sportFieldId)).thenReturn(Optional.empty());

        sportFieldService.getSportFieldById(sportFieldId);
    }
    @Test
    public void testGetSportFields() {
        String name = null;
        Pageable pageable = mock(Pageable.class);

        SportField sportField = new SportField();
        sportField.setId(1);
        sportField.setName("John");

        List<SportField> sportFieldList = new ArrayList<>();
        sportFieldList.add(sportField);

        Page<SportField> sportFields = new PageImpl<>(sportFieldList);

        when(sportFieldRepository.findByName(name, pageable)).thenReturn(sportFields);
        when(sportFieldRepository.findAll(pageable)).thenReturn(sportFields);

        SportFieldDto sportFieldDto = new SportFieldDto();
        sportFieldDto.setName("John");

        List<SportFieldDto> expectedResponse = new ArrayList<>();
        expectedResponse.add(sportFieldDto);

        when(converterClass.convertToSportFieldDtoPage(sportFields)).thenReturn(new PageImpl<>(expectedResponse));

        Page<SportFieldDto> actualResponse = sportFieldService.getSportFields(name, pageable);

        assertEquals(expectedResponse, actualResponse.getContent());

        verify(sportFieldRepository, times(1)).findAll(pageable);
        verify(converterClass, times(1)).convertToSportFieldDtoPage(sportFields);
    }

    @Test(expected = SportFieldNotFoundException.class)
    public void testGetSportFieldsEmpty() {
        String name = null;
        Pageable pageable = mock(Pageable.class);

        when(sportFieldRepository.findByName(name, pageable)).thenReturn(Page.empty());
        when(sportFieldRepository.findAll(pageable)).thenReturn(Page.empty());

        sportFieldService.getSportFields(name, pageable);
    }

    @Test
    public void testAddSportField() {
        SportFieldDto sportFieldDto = new SportFieldDto();
        sportFieldDto.setName("Field A");
        sportFieldDto.setAddress("Address A");
        sportFieldDto.setCapacity(500);

        SportField persistedSportField = new SportField();
        persistedSportField.setId(1);
        persistedSportField.setName("Field A");
        persistedSportField.setAddress("Address A");
        persistedSportField.setCapacity(500);

        when(sportFieldRepository.save(any(SportField.class))).thenReturn(persistedSportField);
        when(converterClass.convertToSportFieldDto(persistedSportField)).thenReturn(sportFieldDto);

        SportFieldDto actualResponse = sportFieldService.addSportField(sportFieldDto);

        assertEquals(sportFieldDto, actualResponse);
        verify(sportFieldRepository, times(1)).save(any(SportField.class));
        verify(converterClass, times(1)).convertToSportFieldDto(persistedSportField);
    }











    @Test
    public void testEditSportField() {
        int id = 1;
        SportFieldDto sportFieldDto = new SportFieldDto();
        sportFieldDto.setName("Updated Field");
        sportFieldDto.setAddress("Updated Address");
        sportFieldDto.setCapacity(1000);

        SportField sportField = new SportField();
        sportField.setId(1);
        sportField.setName("Initial Field");
        sportField.setAddress("Initial Address");
        sportField.setCapacity(500);

        // Mock the repository method calls
        when(sportFieldRepository.findById(id)).thenReturn(Optional.of(sportField));
        when(sportFieldRepository.save(any(SportField.class))).thenReturn(sportField);
        when(converterClass.convertToSportFieldDto(sportField)).thenReturn(sportFieldDto);

        // Call the method under test
        SportFieldDto actualResult = sportFieldService.editSportField(id, sportFieldDto);

        // Assert the expected and actual results
        assertEquals(sportFieldDto, actualResult);
        assertEquals("Updated Field", sportField.getName());
        assertEquals("Updated Address", sportField.getAddress());
        assertEquals(1000, sportField.getCapacity());

        // Verify the method invocations
        verify(sportFieldRepository, times(1)).findById(id);
        verify(sportFieldRepository, times(1)).save(any(SportField.class));
        verify(converterClass, times(1)).convertToSportFieldDto(sportField);
    }


    @Test
    public void testEditSportFieldNulls() {
        int id = 1;
        SportFieldDto sportFieldDto = new SportFieldDto();
        sportFieldDto.setName(null);
        sportFieldDto.setAddress(null);
        sportFieldDto.setCapacity(0);

        SportField sportField = new SportField();
        sportField.setId(1);
        sportField.setName("Initial Field");
        sportField.setAddress("Initial Address");
        sportField.setCapacity(500);

        // Mock the repository method calls
        when(sportFieldRepository.findById(id)).thenReturn(Optional.of(sportField));
        when(sportFieldRepository.save(any(SportField.class))).thenReturn(sportField);
        when(converterClass.convertToSportFieldDto(sportField)).thenReturn(sportFieldDto);

        // Call the method under test
        SportFieldDto actualResult = sportFieldService.editSportField(id, sportFieldDto);

        // Assert the expected and actual results
        assertEquals(sportFieldDto, actualResult);
        assertEquals("Initial Field", sportField.getName());
        assertEquals("Initial Address", sportField.getAddress());
        assertEquals(500, sportField.getCapacity());

        // Verify the method invocations
        verify(sportFieldRepository, times(1)).findById(id);
        verify(sportFieldRepository, times(1)).save(any(SportField.class));
        verify(converterClass, times(1)).convertToSportFieldDto(sportField);
    }



    @Test(expected = SportFieldNotFoundException.class)
    public void testUpdateSportFieldNotFound() {
        int sportFieldId = 1;
        SportFieldDto sportFieldDto = new SportFieldDto();



        when(sportFieldRepository.findById(sportFieldId)).thenReturn(Optional.empty());

        sportFieldService.editSportField(sportFieldId,sportFieldDto);
    }

    @Test
    public void testDeleteSportField() {
        int sportFieldId = 1;
        SportField sportField = new SportField();

        when(sportFieldRepository.findById(sportFieldId)).thenReturn(Optional.of(sportField));

        sportFieldService.deleteSportFieldById(sportFieldId);

        verify(sportFieldRepository, times(1)).findById(sportFieldId);
        verify(sportFieldRepository, times(1)).delete(sportField);
    }

    @Test(expected = SportFieldNotFoundException.class)
    public void testDeleteSportFieldNotFound() {
        int sportFieldId = 1;

        when(sportFieldRepository.findById(sportFieldId)).thenReturn(Optional.empty());

        sportFieldService.deleteSportFieldById(sportFieldId);
    }
}



