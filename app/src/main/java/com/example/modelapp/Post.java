package com.example.modelapp;

import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("userid")
    private String userid;
    @SerializedName("customerid")
    private String customerid;
    @SerializedName("customername")
    private String customername;
    @SerializedName("customernumber")
    private String customernumber;

    public Post(String customerid, String customername, String customernumber) {
        this.customerid = customerid;
        this.customername = customername;
        this.customernumber = customernumber;
    }

    public String getUserid() {
        return userid;
    }
    public String getCustomerid() {
        return customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public String setUserid() {
        return userid;
    }

    public String getCustomernumber() {
        return customernumber;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public void setCustomernumber(String customernumber) {
        this.customernumber = customernumber;
    }
}
