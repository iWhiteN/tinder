package com.tinder.dao;

public interface LikesDAO {
    List<User> getUsersLikes();
    boolean setLike(User userFrom, User userTo, TypeLikes type);
}
