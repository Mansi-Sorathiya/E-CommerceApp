package com.example.retrofitdemo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Instance_Class
{
    public static API_Interface callAPI()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mansisite.000webhostapp.com/mySite/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API_Interface service = retrofit.create(API_Interface.class);
        return service;
    }
}
