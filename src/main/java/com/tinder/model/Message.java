package com.tinder.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
@Builder
public class Message {
    @NonNull
    private User from;
    @NonNull
    private User to;
    @NonNull
    private String content;
    @NonNull
    private Date datetimeSend;
    private int messagesId;
}
