package com.example.second_project_registrationSystem.controller;

import com.example.second_project_registrationSystem.entity.User;
import com.example.second_project_registrationSystem.exception.HttpResponseException;
import com.example.second_project_registrationSystem.repository.UserRepository;
import com.example.second_project_registrationSystem.service.postUserService.PersonIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonIDService personIDService;

    @PostMapping("user")
    public ResponseEntity<User> addUser(@RequestBody User user) throws HttpResponseException {
        if (personIDService.validateAndControlPersonID(user.getPersonID())){
            User userObj = userRepository.save(user);
            return new ResponseEntity<>(userObj, HttpStatus.OK);
        }
        else throw new HttpResponseException(HttpStatus.BAD_REQUEST, "Unexpected error during user creation. Check if you wrote name, surname and specific personID");
    }

    @PutMapping("user")
    public ResponseEntity<User> updateUser (@RequestBody User newUserData) throws HttpResponseException{
        Long userId = Optional.ofNullable(newUserData.getID())
                .orElseThrow(() -> new HttpResponseException(HttpStatus.BAD_REQUEST, "User ID is missing"));

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new HttpResponseException(HttpStatus.NOT_FOUND, "User with ID " + userId + " not found"));

        existingUser.setName(newUserData.getName());
        existingUser.setSurname(newUserData.getSurname());

        User updatedUser = userRepository.save(existingUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("user/{ID}")
    public ResponseEntity<HttpStatus>deleteUserByID (@PathVariable Long ID) throws HttpResponseException{
            if (!userRepository.existsById(ID)) {
                throw new HttpResponseException(HttpStatus.NOT_FOUND, "User with this ID " + ID + " doesn't exist.");
            }
            userRepository.deleteById(ID);
            return new ResponseEntity<>(HttpStatus.OK);
        }

    @DeleteMapping("user/")
    public ResponseEntity<String> handleMissingId() {
        return new ResponseEntity<>("User ID is required.", HttpStatus.BAD_REQUEST);
    }
    }