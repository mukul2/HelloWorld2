package com.doctor.telemedicine.model;

/**
 * Created by mukul on 8/2/2019.
 */

public class QueryModel {
    int id;
    String message_body;
    int message_sender_id;
    int message_receiver_id;
    int status;
    String created_at;

    public QueryModel() {
    }

    public QueryModel(int id, String message_body, int message_sender_id, int message_receiver_id, int status, String created_at) {
        this.id = id;
        this.message_body = message_body;
        this.message_sender_id = message_sender_id;
        this.message_receiver_id = message_receiver_id;
        this.status = status;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage_body() {
        return message_body;
    }

    public void setMessage_body(String message_body) {
        this.message_body = message_body;
    }

    public int getMessage_sender_id() {
        return message_sender_id;
    }

    public void setMessage_sender_id(int message_sender_id) {
        this.message_sender_id = message_sender_id;
    }

    public int getMessage_receiver_id() {
        return message_receiver_id;
    }

    public void setMessage_receiver_id(int message_receiver_id) {
        this.message_receiver_id = message_receiver_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
