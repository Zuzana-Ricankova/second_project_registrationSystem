package com.example.second_project_registrationSystem.service.postUserService;

import com.example.second_project_registrationSystem.entity.User;
import com.example.second_project_registrationSystem.exception.HttpResponseException;
import com.example.second_project_registrationSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonIDService {

    @Autowired
    public PersonIDLength personIDLength;

    @Autowired
    public LoadAllPersonIDForChec loadAllPersonIDForChec;

    @Autowired
    private UserRepository userRepository;

    public boolean validateAndControlPersonID(String personID) throws HttpResponseException {

          if (!personIDLength.validateLength(personID)) {
            throw new HttpResponseException(HttpStatus.BAD_REQUEST, "PersonID must be exactly 12 characters long.");
        }

        if (!loadAllPersonIDForChec.controlPersonIDFromFile(personID)) {
            throw new HttpResponseException(HttpStatus.NOT_FOUND, "PersonID must be from the list in the file 'dataPersonID'.");
        }

        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            if (user.getPersonID().equals(personID)) {
                throw new HttpResponseException(HttpStatus.CONFLICT, "This personID is already assigned to another person in the database. Choose different one.");
            }
        }
        return true;
    }
    }