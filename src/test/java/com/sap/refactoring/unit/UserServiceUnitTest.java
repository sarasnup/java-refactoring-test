package com.sap.refactoring.unit;

import com.sap.refactoring.entities.UserEntity;
import com.sap.refactoring.exception.UserException;
import com.sap.refactoring.input.UserEntityInput;
import com.sap.refactoring.repository.UserRepository;
import com.sap.refactoring.service.UserService;
import com.sap.refactoring.utilities.UserUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceUnitTest
{
	UserService userService;
	UserRepository mockRepo = Mockito.mock(UserRepository.class);

	@BeforeEach
	public void setup() {
		userService = new UserService(new UserUtility(), mockRepo);
		}

	@Test
	public void getUsersTest() {
		UserEntity user = UserEntity.builder().name("fake Name").email("fake@email.com").roles(List.of("master")).build();
		when(mockRepo.getUsers()).thenReturn(List.of(user));
		ResponseEntity response = userService.getUsers();
		assertThat(response.getStatusCode().value()).isEqualTo(200);
	}

	@Test
	public void addUsersTest() throws UserException {
		UserEntity user = UserEntity.builder().name("fake Name").email("fake@email.com").roles(List.of("master")).build();
		when(mockRepo.save(any())).thenReturn(user);
		UserEntityInput userInput = UserEntityInput.builder().name("fake Name").email("fake@email.com").roles(List.of("master")).build();
		ResponseEntity response = userService.addUser(userInput);
		assertThat(response.getStatusCode().value()).isEqualTo(200);
	}

	@Test
	public void addUsersTest_dupEmails() {
	when(mockRepo.getExistingEmails()).thenReturn(List.of("fake@email.com", "new@email.com"));
		try{
			UserEntityInput userInput = UserEntityInput.builder().name("fake Name").email("fake@email.com").roles(List.of("master")).build();
			userService.addUser(userInput);
            fail();
		} catch (UserException e) {
			assertTrue(true);
		}
	}
}
