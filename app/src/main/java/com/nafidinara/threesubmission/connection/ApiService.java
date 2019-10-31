package com.nafidinara.threesubmission.connection;
import com.nafidinara.threesubmission.model.Movie;
import com.nafidinara.threesubmission.model.TvShow;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET ("discover/movie")
    Call<Movie> getMovie(@Query("api_key")String apiKey);

    @GET ("discover/tv")
    Call<TvShow> getShow(@Query("api_key")String apiKey);
    @GET ("search/movie")
    Call<Movie> getMovieSearch(@Query("api_key")String apiKey, @Query("query") String query);
    @GET ("search/tv")
    Call<TvShow> getTvSearch(@Query("api_key")String apiKey, @Query("query") String query);
    @GET ("discover/movie")
    Call<Movie> getRelease(@Query("api_key")String apiKey,@Query("primary_release_date.gte") String date,@Query("primary_release_date.lte") String last_date);
}
