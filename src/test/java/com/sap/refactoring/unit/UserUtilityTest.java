package com.sap.refactoring.unit;

import com.sap.refactoring.exception.UserException;
import com.sap.refactoring.input.UserEntityInput;
import com.sap.refactoring.utilities.UserUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserUtilityTest {

    UserUtility utility = new UserUtility();
    UserEntityInput user;

    @BeforeEach
    public void setUp() {
        user =  UserEntityInput.builder().name("name").email("fake@email.com").build();
       }

    @Test
    public void checkUserValidityTest_noRoles() {
        try {
            utility.checkUserValidity(user, new ArrayList<>(), false);
            fail();
        } catch (UserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void checkUserValidityTest_duplicateEmail() {
        user.setRoles(List.of("Admin"));
        try {
            utility.checkUserValidity(user, List.of("fake@email.com", "new@email.com"), false);
            fail();
        } catch (UserException e) {
            assertTrue(true);
        }
    }

}
