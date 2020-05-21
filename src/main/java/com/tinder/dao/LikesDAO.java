package com.tinder.dao;

import com.tinder.enums.TypeLikes;

public interface LikesDAO {
    List<User> getUsersLikes();
    boolean setLike(User userFrom, User userTo, TypeLikes type);
}
