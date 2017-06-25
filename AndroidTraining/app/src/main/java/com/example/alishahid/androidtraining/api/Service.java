package com.example.alishahid.androidtraining.api;


import com.example.alishahid.androidtraining.model.ListItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service
{
    @GET("/search/users?q=language:java+location:faisalabad")
    Call<ListItem> getList();

}
