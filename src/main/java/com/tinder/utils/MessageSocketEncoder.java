package com.tinder.utils;

import com.google.gson.Gson;
import com.tinder.model.MessageSocket;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageSocketEncoder implements Encoder.Text<MessageSocket> {
    private final Gson gson = new Gson();

    @Override
    public String encode(MessageSocket message) {
        return gson.toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        //Custom initialization logic
    }

    @Override
    public void destroy() {
        //Close resources
    }
}