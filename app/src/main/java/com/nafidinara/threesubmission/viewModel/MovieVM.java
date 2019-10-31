package com.nafidinara.threesubmission.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.nafidinara.threesubmission.connection.ApiService;
import com.nafidinara.threesubmission.connection.RetroConfig;
import com.nafidinara.threesubmission.model.Movie;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MovieVM extends ViewModel {
    private final static String api = RetroConfig.getApiKey();
    private MutableLiveData<Movie> listMovies;

    public void loadMovies() {
        ApiService service = RetroConfig.getRetrofit().create(ApiService.class);
        Call<Movie> movieCall = service.getMovie(api);
        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                listMovies.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("Failed Fetch Data Movie", t.getMessage());
            }
        });
    }



    public LiveData<Movie> getMovies() {
        if (listMovies == null) {
            listMovies = new MutableLiveData<>();
            loadMovies();

        }
        return listMovies;
    }

}

