package com.priyanshagarwal.miriad;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuestionsApi {
    @GET("46e2dd285328d7f38479e07177e85c4c/raw/8d3156091ead014e08349c3a60eb5ee631d24e85/themeBasedAppQuestions.json")
    Call<List<Questions>> getQuestions();
}
