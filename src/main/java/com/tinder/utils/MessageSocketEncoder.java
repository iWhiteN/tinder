package com.tinder.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tinder.model.MessageSocket;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageSocketEncoder implements Encoder.Text<MessageSocket> {

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public String encode(MessageSocket message) {
        String resp = "";
        try {
            resp = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resp;
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