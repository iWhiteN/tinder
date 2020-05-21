package com.tinder.enums;

public enum TypeLikes {
    LIKE("like"),
    DISLIKE("dislike");

    private final String title;

    TypeLikes(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}