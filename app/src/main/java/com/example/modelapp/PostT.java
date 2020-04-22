package com.example.modelapp;

import com.google.gson.annotations.SerializedName;

public class PostT {
    @SerializedName("type")
    private String type;
    @SerializedName("amount")
    private String amount;
    @SerializedName("reason")
    private String reason;
    @SerializedName("datatime")
    private String datatime;

    public PostT(String type, String amount, String reason, String datatime) {
        this.type = type;
        this.amount = amount;
        this.reason = reason;
        this.datatime = datatime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }
}
