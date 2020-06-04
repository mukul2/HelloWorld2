package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotiModel {

@SerializedName("to")
@Expose
private String to;
@SerializedName("data")
@Expose
private Data data;

public String getTo() {
return to;
}

public void setTo(String to) {
this.to = to;
}

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

    public NotiModel(String to, Data data) {
        this.to = to;
        this.data = data;
    }
}