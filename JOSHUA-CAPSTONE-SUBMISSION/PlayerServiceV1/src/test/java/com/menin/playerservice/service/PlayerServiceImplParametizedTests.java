package com.menin.playerservice.service;


import com.menin.playerservice.dao.PlayerRepository;
import com.menin.playerservice.dto.PlayerDto;
import com.menin.playerservice.exception.EmptyFieldException;
import com.menin.playerservice.util.EntityAndDtoConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertThrows;

@RunWith(Parameterized.class)
@SpringBootTest(classes = PlayerServiceImpl.class)
public class PlayerServiceImplParametizedTests {


    @Parameterized.Parameters(name = "{index} : testAddPlayerWithBlankFields")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"Josh", null, "PH", EmptyFieldException.class},
                {"Jane", "Doe", null, EmptyFieldException.class},
                {null, "Doe", "PH", EmptyFieldException.class}
        });
    }


    private String fnameT;
    private String lnameT;
    private String countryT;
    private Class<? extends Exception> expectedException;


    public PlayerServiceImplParametizedTests(String fnameT, String lnameT, String countryT, Class<? extends Exception> expectedException) {
        this.fnameT = fnameT;
        this.lnameT = lnameT;
        this.countryT = countryT;
        this.expectedException = expectedException;
    }


    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private EntityAndDtoConverter entityAndDtoConverter;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddPlayerWithBlankFields() {
        // Create a PlayerDto with blank fields
        PlayerDto playerDto = new PlayerDto();
        playerDto.setFirstName(fnameT);
        playerDto.setLastName(lnameT);
        playerDto.setCountry(countryT);

        // Call the addPlayer method, expecting it to throw EmptyFieldException
        assertThrows(expectedException, () -> playerService.addPlayer(playerDto));
    }
}
