package com.tinder.websocket;

import com.tinder.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {

    private static ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String encode(Message message) throws EncodeException {
        return mapper.writeValueAsString(message);
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