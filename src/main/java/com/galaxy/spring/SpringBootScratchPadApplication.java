package com.galaxy.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.galaxy.spring.component.StreamPad;
import com.galaxy.spring.jpa.IUserRepository;

@SpringBootApplication
public class SpringBootScratchPadApplication {

	@Autowired
	IUserRepository userRepository;
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootScratchPadApplication.class, args);
		StreamPad streamPad = (StreamPad) context.getBean("StreamPad");
//		streamPad.streamTimer();
//		streamPad.streamFilter();
		streamPad.streamSort();
//		streamPad.checkIfStreamIsEmpty();
//		streamPad.intArithmaticOperations();
//		streamPad.printPrimeNumbers();
	}

}
