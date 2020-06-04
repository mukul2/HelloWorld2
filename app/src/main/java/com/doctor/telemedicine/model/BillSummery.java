package com.doctor.telemedicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillSummery {

@SerializedName("total")
@Expose
private Integer total;
@SerializedName("paid")
@Expose
private Integer paid;
@SerializedName("due")
@Expose
private Integer due;

public Integer getTotal() {
return total;
}

public void setTotal(Integer total) {
this.total = total;
}

public Integer getPaid() {
return paid;
}

public void setPaid(Integer paid) {
this.paid = paid;
}

public Integer getDue() {
return due;
}

public void setDue(Integer due) {
this.due = due;
}

}