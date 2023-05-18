package com.menin.fieldservice;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SportFieldServiceV1Application.class)
class SportFieldServiceV1ApplicationTests {

	@Test
	void contextLoads() {
		Assert.assertEquals(31, 21+10);
	}

}
