package com.example.second_project_registrationSystem.service.getUserService;

import com.example.second_project_registrationSystem.entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserExtract extends User{

    public static List<Map<String, Object>> extractUserDetails(List<User> userList, boolean detail) {
        return userList.stream()
                .map(user -> {
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("id", user.getID());
                    userMap.put("name", user.getName());
                    userMap.put("surname", user.getSurname());
                    if (detail) {
                        userMap.put("personID", user.getPersonID());
                        userMap.put("uuid", user.getUuid());
                    }
                    return userMap;
                })
                .collect(Collectors.toList());
    }

    public static Map<String, Object> extractOneUserCompleteDetails(User user, boolean detail) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getID());
        userMap.put("name", user.getName());
        userMap.put("surname", user.getSurname());
        if (detail) {
        userMap.put("personID", user.getPersonID());
        userMap.put("uuid", user.getUuid());
        }
        return userMap;
    }
}