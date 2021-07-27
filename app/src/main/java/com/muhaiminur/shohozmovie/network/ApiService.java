package com.muhaiminur.shohozmovie.network;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    //1 Movie List
    @GET("db.json")
    Call<JsonElement> get_movie_list();
}
