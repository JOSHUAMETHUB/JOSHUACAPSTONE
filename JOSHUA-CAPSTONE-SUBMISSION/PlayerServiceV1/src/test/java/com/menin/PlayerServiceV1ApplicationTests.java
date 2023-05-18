package com.menin;

import com.menin.playerservice.PlayerServiceV1Application;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PlayerServiceV1Application.class)
public class PlayerServiceV1ApplicationTests {

	@Test
	public void contextLoads() {

		Assert.assertEquals(31, 21+10);
	}

}
