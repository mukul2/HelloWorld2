package com.doctor.telemedicine.model;

public class ChatModel {
    String sender_id,recever_id,message_body,message_type,time;

    public ChatModel(String sender_id, String recever_id, String message_body, String message_type, String time) {
        this.sender_id = sender_id;
        this.recever_id = recever_id;
        this.message_body = message_body;
        this.message_type = message_type;
        this.time = time;
    }

    public ChatModel() {
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getRecever_id() {
        return recever_id;
    }

    public void setRecever_id(String recever_id) {
        this.recever_id = recever_id;
    }

    public String getMessage_body() {
        return message_body;
    }

    public void setMessage_body(String message_body) {
        this.message_body = message_body;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
