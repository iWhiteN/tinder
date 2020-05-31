package com.tinder.service;

import com.tinder.dao.UserDAOImpl;
import com.tinder.model.Credentials;
import com.tinder.model.User;

import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

public class UserService {
    private static UserService userService;
    private final UserDAOImpl userDAOImpl;

    private UserService() {
        userDAOImpl = UserDAOImpl.getInstance();
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public Optional<User> getUserById (int userId) throws SQLException {
        return userDAOImpl.getUserById(userId);
    }

    public List<User> getAllLikedUsersByUserId(int userId) throws SQLException {
        return userDAOImpl.getAllLikedUsersByUserId(userId);
    }

    public List<User> getAllUsersWithoutLikesByUserId(int userId) throws SQLException {
        return userDAOImpl.getAllUsersWithoutLikesByUserId(userId);
    }

    public int addUser (User user) throws SQLException {
        String pass = user.getPass() + "tinder";
        String encodedPass = Base64.getEncoder().encodeToString(pass.getBytes());
        user.setPass(encodedPass);
        return userDAOImpl.addUser(user);
    }

    public int authorizeUser(Credentials credentials) throws SQLException {
        String pass = credentials.getPass() + "tinder";
        String encodedPass = Base64.getEncoder().encodeToString(pass.getBytes());
        credentials.setPass(encodedPass);

        return userDAOImpl.getUserByCredentials(credentials);
    }
}
