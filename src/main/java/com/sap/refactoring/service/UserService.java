package com.sap.refactoring.service;

import com.sap.refactoring.entities.UserEntity;
import com.sap.refactoring.exception.UserException;
import com.sap.refactoring.input.UserEntityInput;
import com.sap.refactoring.repository.UserRepository;
import com.sap.refactoring.utilities.UserUtility;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public UserRepository repository;

    UserUtility userUtility;

    @Autowired
    public UserService(UserUtility userUtility, UserRepository repository) {
        this.userUtility = userUtility;
        this.repository = repository;
    }

    public ResponseEntity getUsers()  {
        List<UserEntity> users = repository.getUsers();
        return ResponseEntity.ok(users);
    }

    public ResponseEntity searchUser(String name)  {
        List<UserEntity> user = repository.searchUsers(name);
        return ResponseEntity.ok(user);
    }

    @Transactional
    public ResponseEntity deleteUser(Integer id) {
        repository.deleteById(id);
        return ResponseEntity.ok("User deleted");
    }

    @Transactional
    public ResponseEntity updateUser(UserEntityInput user) throws UserException {
        userUtility.checkUserValidity(user, repository.getExistingEmails(), false);
        Optional<UserEntity> existingUser = repository.findById(user.getId());
        if(existingUser.isEmpty()) {
            throw new UserException("User "+user.getName()+ " not found.");
        }
        userUtility.checkUserValidity(user, repository.getExistingEmails(), true);
        repository.save(userUtility.toUserEntity(user));
        return ResponseEntity.ok(user);
    }

    @Transactional
    public ResponseEntity addUser(UserEntityInput user) throws UserException {
        userUtility.checkUserValidity(user, repository.getExistingEmails(), false);
        UserEntity savedUser = repository.save(userUtility.toUserEntity(user));
        return ResponseEntity.ok(savedUser);
    }
}
