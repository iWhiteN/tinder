package com.tinder.dao;

import com.tinder.enums.TypeLikes;
import com.tinder.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface LikesDAO {
    Optional<List<User>> getUsersLikes();

    void setLike(int from, int to, TypeLikes type) throws SQLException;
}
