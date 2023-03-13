package com.example.serverapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ContactApi {
    @GET("/contacts/{id}")
    Call<Contact> getById(@Path("id") int id);

    @GET("/contacts")
    Call<List<Contact>> getAll();

    @POST("/contacts")
    Call<Contact> save(@Body Contact contact);

    @DELETE("/contacts/{id}")
    Call<Void> deleteContact(@Path("id") int id);
}
