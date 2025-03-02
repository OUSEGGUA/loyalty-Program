package com.loyalty;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;



@ContextConfiguration(classes = LoyaltyApplication.class)
@EntityScan("com.loyalty.model")
@SpringBootTest(classes = LoyaltyApplication.class)
class LoyaltyApplicationTests {

	@Test
	void contextLoads() {
	}

}
