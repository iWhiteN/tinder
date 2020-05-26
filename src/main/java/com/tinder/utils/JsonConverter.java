package com.tinder.utils;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonConverter<T> {
    String toJson(T t) throws JsonProcessingException;

    T jsonParse(String json) throws JsonProcessingException;
}
