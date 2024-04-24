package com.example.second_project_registrationSystem.controller;

import com.example.second_project_registrationSystem.entity.User;
import com.example.second_project_registrationSystem.exception.HttpResponseException;
import com.example.second_project_registrationSystem.repository.UserRepository;
import com.example.second_project_registrationSystem.service.getUserService.UserExtract;
import com.example.second_project_registrationSystem.service.postUserService.PersonIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class UserGetController{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonIDService personIDService;

    @GetMapping("users")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers (
            @RequestParam(value = "detail", required = false, defaultValue = "false")
            boolean detail) {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        }
        List<Map<String, Object>> userExtracts = UserExtract.extractUserDetails(userList, detail);
        return new ResponseEntity<>(userExtracts, HttpStatus.OK);
    }

    @GetMapping("user/{ID}")
    public ResponseEntity<?> getUserByID(@PathVariable Long ID, @RequestParam(
            value = "detail", required = false, defaultValue = "false") Boolean detail) throws HttpResponseException {
        Optional<User> userOptional = userRepository.findById(ID);
        if (!userOptional.isPresent()) {
            throw new HttpResponseException(HttpStatus.NOT_FOUND, "User with ID '" + ID + "' not found.");}

        User user = userOptional.get();
        Map<String, Object> userDetails = UserExtract.extractOneUserCompleteDetails(user, detail);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @GetMapping("user/")
    public ResponseEntity<String> handleMissingId() {
        return new ResponseEntity<>("User ID is required.", HttpStatus.BAD_REQUEST);
    }
}