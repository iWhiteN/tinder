package com.tinder.model;

import lombok.Data;
import lombok.NonNull;

import java.time.Instant;

@Data
public class User {
    @NonNull
    private int userId;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String pass;
    @NonNull
    private Instant lastLogin;
    @NonNull
    private String avatarUrl;
}
