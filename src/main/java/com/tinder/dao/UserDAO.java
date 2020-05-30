package com.tinder.dao;

import com.tinder.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    Optional<User> getUserById(int userId);

    List<User> getAllLikedUsersByUserId(int userId);

    List<User> getAllUsersWithoutLikesByUserId(int userId);

    int addUser(User user);
}
