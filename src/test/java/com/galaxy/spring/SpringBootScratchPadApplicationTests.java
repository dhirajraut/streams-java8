package com.galaxy.spring;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.galaxy.spring.service.UserStreamTest;

/* To inject Spring behavior */
@RunWith(SpringJUnit4ClassRunner.class)
/* To find autowire candidates */
@SpringBootTest(classes = {UserStreamTest.class, SpringBootScratchPadApplication.class})
//@EnableJpaRepositories(basePackages = {"com.galaxy.spring.jpa"}) 
class SpringBootScratchPadApplicationTests {

	@Test
	void contextLoads() {
	}
}
