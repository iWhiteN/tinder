package com.tinder.utils;

import com.google.gson.Gson;
import com.tinder.model.MessageSocket;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageSocketDecoder implements Decoder.Text<MessageSocket> {
    private final Gson gson = new Gson();


    @Override
    public MessageSocket decode(String s) {
        return gson.fromJson(s, MessageSocket.class);
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
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
