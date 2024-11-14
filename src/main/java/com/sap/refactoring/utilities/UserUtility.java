package com.sap.refactoring.utilities;

import com.sap.refactoring.entities.UserEntity;
import com.sap.refactoring.exception.UserException;
import com.sap.refactoring.input.UserEntityInput;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserUtility {

    private boolean hasUserRole(UserEntityInput user) {
        return (user.getRoles() != null && !user.getRoles().isEmpty()) ;
    }

    private boolean hasUniqueEmail(UserEntityInput user, List<String> existingEmails) {
        return !existingEmails.contains(user.getEmail());
    }

    public void checkUserValidity(UserEntityInput user, List<String> existingEmails, boolean isUpdate) throws UserException {
        if(!isUpdate && !hasUniqueEmail(user, existingEmails)) {
            throw new UserException("Email provided is already in use. Please provide a unique email id.");
        }
        if(!hasUserRole(user)) {
            throw new UserException("User should have at least one role defined.");
        }
    }

    public UserEntity toUserEntity(UserEntityInput user) {
        return new UserEntity(user.getId(), user.getName(), user.getEmail(), user.getRoles());
    }
}
