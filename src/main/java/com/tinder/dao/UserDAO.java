package com.tinder.dao;

import com.tinder.model.Credentials;
import com.tinder.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    Optional<User> getUserById(int userId) throws SQLException;

    List<User> getAllLikedUsersByUserId(int userId) throws SQLException;

    List<User> getAllUsersWithoutLikesByUserId(int userId) throws SQLException;

    int addUser(User user) throws SQLException;

    int getUserByCredentials(Credentials credentials) throws SQLException;
}
