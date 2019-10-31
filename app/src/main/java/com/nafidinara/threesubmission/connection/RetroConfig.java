package com.nafidinara.threesubmission.connection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroConfig {
    private final static String API_KEY = "8a9be9d05bd9f9d5ca154aaf42504303";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String SEARCH_MOVIE_URL = "https://api.themoviedb.org/3/search/";
    public static final String SEARCH_TV_URL = "https://api.themoviedb.org/3/search/";

    public static Retrofit retrofit;

    public static String getApiKey(){
        return API_KEY;
    }

    public static Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}