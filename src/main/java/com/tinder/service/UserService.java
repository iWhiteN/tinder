package com.tinder.service;

import com.tinder.dao.UserJDBC;
import com.tinder.model.User;

import java.util.List;

public class UserService {
    private static UserService userService;
    private final UserJDBC userJDBC;

    private UserService() {
        userJDBC = UserJDBC.getInstance();
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public List<User> getAllUsersWithoutLikesByUserId(int userId) {
        return userJDBC.getAllUsersWithoutLikesByUserId(userId);
    }
}
