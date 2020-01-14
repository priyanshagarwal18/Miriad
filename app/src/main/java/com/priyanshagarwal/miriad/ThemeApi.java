package com.priyanshagarwal.miriad;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ThemeApi {
    @GET("2d11bac232b2d304b9a7b3e35a385483/raw/270ea38210a0a698ffd75b889cfc7b6e8e1becaf/ThemeNames.json")
    Call<List<ThemeData>> getThemeData();
}
