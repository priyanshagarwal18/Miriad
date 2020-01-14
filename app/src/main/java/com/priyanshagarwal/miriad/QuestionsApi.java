package com.priyanshagarwal.miriad;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuestionsApi {
    @GET("46e2dd285328d7f38479e07177e85c4c/raw/567ea569f3ed3b98a8a3d789bb8bf5c536db45c1/themeBasedAppQuestions.json")
    Call<List<Questions>> getQuestions();
}
