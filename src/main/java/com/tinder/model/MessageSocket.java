package com.tinder.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MessageSocket {
    private int from;
    private int to;
    private String content;
    private Timestamp datetime;
}
