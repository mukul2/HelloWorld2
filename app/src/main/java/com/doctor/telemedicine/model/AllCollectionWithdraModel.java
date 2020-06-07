package com.doctor.telemedicine.model;

import java.util.List;

public class AllCollectionWithdraModel {
    float total_bill;
    Integer all_widthdraw;
    List<PaymentModel> bill_details;
    List<WidthdrawModel> widthdraw_details;

    public float getTotal_bill() {
        return total_bill;
    }

    public void setTotal_bill(float total_bill) {
        this.total_bill = total_bill;
    }

    public Integer getAll_widthdraw() {
        return all_widthdraw;
    }

    public void setAll_widthdraw(Integer all_widthdraw) {
        this.all_widthdraw = all_widthdraw;
    }

    public List<PaymentModel> getBill_details() {
        return bill_details;
    }

    public void setBill_details(List<PaymentModel> bill_details) {
        this.bill_details = bill_details;
    }

    public List<WidthdrawModel> getWidthdrawModel() {
        return widthdraw_details;
    }

    public void setWidthdrawModel(List<WidthdrawModel> widthdrawModel) {
        this.widthdraw_details = widthdrawModel;
    }
}
