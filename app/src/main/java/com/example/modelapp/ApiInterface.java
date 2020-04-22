package com.example.modelapp;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("register.php")
    abstract Call<String> getUserRegi(
            @Field("name") String name,
            @Field("username") String uname,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<String> getUserLogin(

            @Field("username") String uname,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("customers.php")
    Call<List<Post>> getPosts(
            @Field("userid") String usid
    );

    @FormUrlEncoded
    @POST("customerregister.php")
    abstract Call<String> getCustomerRegi(
            @Field("userid") String tid,
            @Field("customername") String cname,
            @Field("customernumber") String cnum
    );

    @FormUrlEncoded
    @POST("transactions.php")
    Call<List<PostT>> getPostT(
            @Field("userid") String usid,
            @Field("custid") String cid
    );

    @FormUrlEncoded
    @POST("transactionregister.php")
    abstract Call<String> getTransdet(
            @Field("userid") String tid,
            @Field("custid") String cid,
            @Field("type") String cname,
            @Field("amount") String cnum,
            @Field("reason") String reason
    );

}
