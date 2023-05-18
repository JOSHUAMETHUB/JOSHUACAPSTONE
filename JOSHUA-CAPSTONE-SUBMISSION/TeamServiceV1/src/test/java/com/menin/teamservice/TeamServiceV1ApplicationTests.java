package com.menin.teamservice;


import com.menin.teamservice.exception.TeamAlreadyExistException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TeamAlreadyExistException.class)
class TeamServiceV1ApplicationTests {

	@Test
	void contextLoads() {
		Assert.assertEquals(3,1+2);
	}

}
