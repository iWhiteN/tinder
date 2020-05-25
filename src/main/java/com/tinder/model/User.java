package com.tinder.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
@Builder
public class User {
    private int userId;
    @NonNull
    private String email;
    @NonNull
    private String name;
    private String pass;
    @NonNull
    private Date lastLogin;
    @NonNull
    private String avatarUrl;
}
