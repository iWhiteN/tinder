package com.tinder.service;

import com.tinder.dao.UserJDBC;
import com.tinder.model.User;

import java.util.List;
import java.util.Optional;

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

    public Optional<User> getUserById (int userId) {
        return userJDBC.getUserById(userId);
    }

    public List<User> getAllLikedUsersByUserId(int userId) {
        return userJDBC.getAllLikedUsersByUserId(userId);
    }

    public List<User> getAllUsersWithoutLikesByUserId(int userId) {
        return userJDBC.getAllUsersWithoutLikesByUserId(userId);
    }
}
