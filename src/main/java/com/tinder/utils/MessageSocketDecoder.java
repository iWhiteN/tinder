package com.tinder.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinder.model.MessageSocket;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageSocketDecoder implements Decoder.Text<MessageSocket> {

    private static ObjectMapper mapper = new ObjectMapper();


    @Override
    public MessageSocket decode(String s) {
        MessageSocket message = null;
        try {
            message = mapper.readValue(s, MessageSocket.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return message;
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
