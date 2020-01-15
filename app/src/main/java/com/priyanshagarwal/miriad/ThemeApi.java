package com.priyanshagarwal.miriad;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ThemeApi {
    @GET("2d11bac232b2d304b9a7b3e35a385483/raw/45e1fb8d376bb1a9cfc6e2df680e43679d06acf7/ThemeNames.json")
    Call<List<ThemeData>> getThemeData();
}
