package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("intent")
    @Expose
    private String intent;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("targetUserType")
    @Expose
    private String targetUserType;

    public String getTargetUserType() {
        return targetUserType;
    }

    public void setTargetUserType(String targetUserType) {
        this.targetUserType = targetUserType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Data(String title, String body, String intent, String image, String targetUserType) {
        this.title = title;
        this.body = body;
        this.intent = intent;
        this.image = image;
        this.targetUserType = targetUserType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}