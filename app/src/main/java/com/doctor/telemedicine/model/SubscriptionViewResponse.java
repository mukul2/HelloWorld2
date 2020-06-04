package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscriptionViewResponse {

@SerializedName("chat")
@Expose
private Integer chat;
@SerializedName("video")
@Expose
private Integer video;
@SerializedName("one")
@Expose
private Integer one;
@SerializedName("three")
@Expose
private Integer three;
@SerializedName("six")
@Expose
private Integer six;
@SerializedName("year")
@Expose
private Integer year;

public Integer getChat() {
return chat;
}

public void setChat(Integer chat) {
this.chat = chat;
}

public Integer getVideo() {
return video;
}

public void setVideo(Integer video) {
this.video = video;
}

    public Integer getOne() {
        return one;
    }

    public void setOne(Integer one) {
        this.one = one;
    }

    public Integer getThree() {
        return three;
    }

    public void setThree(Integer three) {
        this.three = three;
    }

    public Integer getSix() {
        return six;
    }

    public void setSix(Integer six) {
        this.six = six;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}