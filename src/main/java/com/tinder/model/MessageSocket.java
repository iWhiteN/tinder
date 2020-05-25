package com.tinder.model;

import lombok.Data;

@Data
public class MessageSocket {
    private int from;
    private int to;
    private String content;
}
