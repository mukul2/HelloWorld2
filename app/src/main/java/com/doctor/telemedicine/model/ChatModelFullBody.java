package com.doctor.telemedicine.model;

public class ChatModelFullBody {
    String sender_id,recever_id,message_body,message_type,time,sender_name,sender_photo,receiver_name,receiver_photo;

    public ChatModelFullBody() {
    }

    public ChatModelFullBody(String sender_id, String recever_id, String message_body, String message_type, String time, String sender_name, String sender_photo, String receiver_name, String receiver_photo) {
        this.sender_id = sender_id;
        this.recever_id = recever_id;
        this.message_body = message_body;
        this.message_type = message_type;
        this.time = time;
        this.sender_name = sender_name;
        this.sender_photo = sender_photo;
        this.receiver_name = receiver_name;
        this.receiver_photo = receiver_photo;
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

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_photo() {
        return sender_photo;
    }

    public void setSender_photo(String sender_photo) {
        this.sender_photo = sender_photo;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_photo() {
        return receiver_photo;
    }

    public void setReceiver_photo(String receiver_photo) {
        this.receiver_photo = receiver_photo;
    }
}
