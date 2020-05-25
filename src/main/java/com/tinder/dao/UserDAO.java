package com.tinder.dao;

import com.tinder.model.User;

import java.util.List;

public interface UserDAO {
    User getUserById();

    List<User> getAllLikedUsersByUserId(int userId);

    List<User> getAllUsersWithoutLikesByUserId(int userId);

    void addUser(User user);
}
