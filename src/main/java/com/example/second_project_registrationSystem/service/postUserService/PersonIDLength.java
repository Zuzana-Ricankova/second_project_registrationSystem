package com.example.second_project_registrationSystem.service.postUserService;

import org.springframework.stereotype.Service;

@Service
public class PersonIDLength {

    public boolean validateLength(String personID) {
        if (personID == null || personID.length() != 12) {
            return false;
        }
        return true;
    }
}