package com.example.second_project_registrationSystem.service.postUserService;

import com.example.second_project_registrationSystem.exception.HttpResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import static com.example.second_project_registrationSystem.settings.Settings.FILENAME_DATAPERSONID;

@Service
public class LoadAllPersonIDForChec {

    String filenameDataPersonID = FILENAME_DATAPERSONID;

    public boolean controlPersonIDFromFile(String personID) throws HttpResponseException {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filenameDataPersonID)))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (personID.equals(line)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            throw new HttpResponseException(HttpStatus.NOT_FOUND, "Error checking PersonID. PersonID whitelist file not found.");
        }
        return false;
    }
}
