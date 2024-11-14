package com.sap.refactoring.web.controller;

import com.sap.refactoring.exception.UserException;
import com.sap.refactoring.input.UserEntityInput;
import com.sap.refactoring.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("add/")
    public ResponseEntity addUser(@RequestBody UserEntityInput user) {
        try {
            return userService.addUser(user);
        } catch (UserException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("update/")
    public ResponseEntity updateUser(@RequestBody UserEntityInput user) {
        try {
            return userService.updateUser(user);
        } catch (UserException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }

    @GetMapping("users/")
    public ResponseEntity getUsers() {
        return userService.getUsers();
    }

    @GetMapping("searchUser/")
    public ResponseEntity searchUsers(@RequestParam("name") String name) {
        return userService.searchUser(name);
    }
}
