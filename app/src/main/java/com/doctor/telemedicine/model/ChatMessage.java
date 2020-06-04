package com.doctor.telemedicine.model;

/**
 * Created by mkl on 2/17/2019.
 */

public class ChatMessage {
    String senderID,receiverID,message;

    public ChatMessage() {
    }

    public ChatMessage(String senderID, String receiverID, String message) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message = message;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
