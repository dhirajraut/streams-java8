package com.galaxy.spring.component;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalLong;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxy.spring.entity.UserEntity;
import com.galaxy.spring.jpa.IUserRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component("StreamPad")
public class StreamPad {

	@Autowired
	IUserRepository userRepository;
	
	public void printPrimeNumbers() {
		
		IntStream.range(1, 100).filter(p -> {
			return BigInteger.valueOf(p).isProbablePrime(1);
		}).forEach(log::info);;
	}
	
	public void intArithmaticOperations() {
		
		log.info("Listing numbers divisible by 4");
		List<Integer> intList = Arrays.asList(40, 70, 30, 10, 50, 60, 20, 80, 90, 100);
		intList.parallelStream().filter(p -> p % 4 == 0).forEach(log::info);

		log.info("Listing Sum");
		int sum = intList.parallelStream().peek(log::info).mapToInt(Integer::intValue).sum();
		log.info("Sum = " + sum);
		
		log.info("Asc Sorting");
		intList.stream().sorted().forEach(log::info);
		
		log.info("Desc Sorting");
		intList.stream().sorted(Comparator.reverseOrder()).forEach(log::info);
	}
	
	public void checkIfStreamIsEmpty() {
		
		LongStream nonNullLongStream = LongStream.of(10L, 20L, 30L, 40L, 50L);
		OptionalLong nonNullElement = nonNullLongStream.findAny();
		if (nonNullElement.isPresent()) {
			log.info("Non Null Stream");
		}
		else {
			log.info("Null Stream");
		}

		LongStream nullLongStream = LongStream.of();
		OptionalLong nullElement = nullLongStream.findAny();
		if (nullElement.isPresent()) {
			log.info("Non Null Stream");
		}
		else {
			log.info("Null Stream");
		}
	}
	
	public void streamSort() {
		List<UserEntity> usersList = userRepository.findAll();
		usersList.sort(Comparator.comparing(UserEntity::getFirstName).thenComparing(UserEntity::getLastName).reversed());
		usersList.forEach(log::info);
	}
	
	public void streamFilter() {
		
		List<UserEntity> usersList = userRepository.findAll();
		
		/* Count total records. */
		log.info(usersList.stream().count());

		/* Get the user, for which the email is provided. */
		usersList.stream()
			.filter(user -> user.getEmail().equals("simona@morasca.com"))
			.forEach(log::info);
		
		/* Get the users, starting with given character. */
		usersList.stream()
			.filter(user -> user.getFirstName().startsWith("B"))
			.forEach(log::info);
	}
	
	
	public void streamTimer() {
		List<UserEntity> usersList = userRepository.findAll();

		long forLoopStart = System.currentTimeMillis();
		for (UserEntity user : usersList) {
			log.debug(user);
		}
		log.info("For loop took " + (System.currentTimeMillis() - forLoopStart) + " ms.");
		
		long sequentialStreamStart = System.currentTimeMillis();
		usersList.stream()
					.forEach(log::debug);
		log.info("Sequential Stream took " + (System.currentTimeMillis() - sequentialStreamStart) + " ms.");

		long parallelStreamStart = System.currentTimeMillis();
		usersList.parallelStream()
					.forEach(log::debug);
		log.info("Parallel Stream took " + (System.currentTimeMillis() - parallelStreamStart) + " ms.");
	}
}
