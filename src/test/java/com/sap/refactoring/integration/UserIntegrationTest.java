package com.sap.refactoring.integration;

import com.sap.refactoring.entities.UserEntity;
import com.sap.refactoring.input.UserEntityInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest
{
	@Autowired
	private TestRestTemplate testRestTemplate;

	
	@Test
	public void addUserTest() {
		UserEntityInput user = UserEntityInput.builder().name("name").email("emailToAdd").roles(List.of("master")).build();
		UserEntity response = testRestTemplate.postForObject("/users/add/", user, UserEntity.class);
		assertNotNull(response);
		assertNotNull(response.getId());
		assertEquals("name",response.getName());
		assertEquals("emailToAdd",response.getEmail());
		assertEquals(1, response.getRoles().size());
		testRestTemplate.exchange("/users/"+response.getId(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class); //to clear data
	}

	@Test
	public void searchUserTest() {
		List users = testRestTemplate.getForObject("/users/users/", List.class);
		assertNotNull(users);
	}

	
	@Test
	public void deleteUserTest() {
		UserEntityInput user = UserEntityInput.builder().name("nameToDelete").email("emailToDelete").roles(List.of("master")).build();
		UserEntity response = testRestTemplate.postForObject("/users/add/", user, UserEntity.class);
		ResponseEntity<Void> deleteResponse = testRestTemplate.exchange("/users/"+response.getId(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
		assertNotNull(deleteResponse);
	}

	
	@Test
	public void userValidityTest_duplicateEmail_add() {
		UserEntityInput existingUser = UserEntityInput.builder().name("name2").email("emailForAdd").roles(List.of("master")).build();
		UserEntity userAdded = testRestTemplate.postForObject("/users/add/", existingUser, UserEntity.class);
		UserEntityInput inValidUser = UserEntityInput.builder().name("invalid").email("emailForAdd").roles(List.of("master")).build();
		String response = testRestTemplate.postForObject("/users/add/", inValidUser, String.class);
		assertEquals("Email provided is already in use. Please provide a unique email id.", response);
		testRestTemplate.exchange("/users/"+userAdded.getId(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class); //to clear data
	}

	
	@Test
	public void userValidityTest_emptyRole_update() {
		UserEntityInput inValidUser = UserEntityInput.builder().name("nameEmptyRole").email("emailEmptyRole").build();
		String response = testRestTemplate.postForObject("/users/add/", inValidUser, String.class);
		assertEquals("User should have at least one role defined.", response);
	}
}
