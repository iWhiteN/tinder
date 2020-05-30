package com.tinder.service;

import com.tinder.dao.UserJDBC;
import com.tinder.model.Credentials;
import com.tinder.model.User;

import java.util.Base64;
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

    public int addUser (User user) {
        String pass = user.getPass() + "tinder";
        String encodedPass = Base64.getEncoder().encodeToString(pass.getBytes());
        user.setPass(encodedPass);
        return userJDBC.addUser(user);
    }

    public boolean authorizeUser(int userId, Credentials credentials) {
        boolean result = false;
        Optional<User> userById = userJDBC.getUserById(userId);
        if (userById.isPresent()) {
            User user = userById.get();
            String pass = new String(Base64.getDecoder().decode(user.getPass()));
            pass = pass.substring(0, pass.length() - 6);
            if (credentials.getPass().equals(pass) && credentials.getEmail().equals(user.getEmail())) {
                result = true;
            }
        }
        return result;
    }
}
