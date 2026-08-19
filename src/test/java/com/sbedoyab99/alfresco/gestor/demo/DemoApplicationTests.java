package com.sbedoyab99.alfresco.gestor.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"alfresco.username=test-user",
		"alfresco.password=test-password"
})
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
