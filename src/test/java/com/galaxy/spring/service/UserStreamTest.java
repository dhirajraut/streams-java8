package com.galaxy.spring.service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.galaxy.spring.SpringBootScratchPadApplication;
import com.galaxy.spring.entity.UserEntity;
import com.galaxy.spring.jpa.IUserRepository;

@Transactional
/* To inject Spring behavior */
@RunWith(SpringJUnit4ClassRunner.class)
/* To find autowire candidates */
@SpringBootTest(classes = SpringBootScratchPadApplication.class)
public class UserStreamTest {

	@Autowired
	IUserRepository userRepository;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	/**
	 * Set required environment.
	 */
	@Before
	public void setUp() {
		// Keeping it blank for this exercise.
	}

	/**
	 * Teardown required environment.
	 */
	@After
	public void tearDown() {
		// Keeping it blank for this exercise.
	}
	
	@Test
	public void testUserNameExactMatch() {
		
		List<UserEntity> userList = userRepository.findAll();
		
		Predicate<UserEntity> condition = user -> user.getFirstName().equals("Fletcher");
		
		UserEntity userEntity = userList.stream()
			.filter(condition)
			.findFirst().get();
		
		Assert.assertTrue(userEntity.getFirstName().equals("Fletcher"));
	}
	
	@Test
	public void testSortedOnFirstName () {
		List<UserEntity> userList = userRepository.findAll();
		
		UserEntity firstUserEntity = userList.stream()
			.sorted(Comparator.comparing(UserEntity::getFirstName))
			.findFirst().get();
		
		Assert.assertTrue(firstUserEntity.getFirstName().equals("Abel"));

		UserEntity lastUserEntity = userList.stream()
				.sorted(Comparator.comparing(UserEntity::getFirstName))
				.skip(499).findFirst().get();

		Assert.assertTrue(lastUserEntity.getFirstName().equals("Zona"));
	}

	@Test
	public void testCountUsersWithAOLEmail() {
		List<UserEntity> userList = userRepository.findAll();
		
		Predicate<UserEntity> condition = user -> user.getEmail().contains("@aol.com");
		
		long count = userList.stream()
			.filter(condition)
			.count();
		
		Assert.assertTrue(count == 64);
	}
	
	@Test
	public void testUserToFirstNameStringListConversion () {
		List<UserEntity> userList = userRepository.findAll();
		
		userList.stream().map(user -> user.getFirstName());
	}
	
	@Test
	public void testUserToStringListConversion () {
		List<UserEntity> userList = userRepository.findAll();
		
		userList.stream().map(user -> user.toString()).forEach(System.out::println);
	}
	
	/* People from NY state, but not from New York city. */
	@Test
	public void testDoubleFilter () {
		Predicate<UserEntity> nyStateFilter = user -> user.getState().equals("NY");
		Predicate<UserEntity> nyCityFilter = user -> !user.getCity().equalsIgnoreCase("New York");
		
		List<UserEntity> userList = userRepository.findAll();
		userList.stream()
			.filter(nyStateFilter.and(nyCityFilter))
			.forEach(System.out::println);;
	}
}
