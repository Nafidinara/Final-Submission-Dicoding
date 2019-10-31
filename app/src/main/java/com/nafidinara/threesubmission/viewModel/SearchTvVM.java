package com.nafidinara.threesubmission.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.nafidinara.threesubmission.connection.ApiService;
import com.nafidinara.threesubmission.connection.RetroConfig;
import com.nafidinara.threesubmission.model.Movie;
import com.nafidinara.threesubmission.model.TvShow;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTvVM extends ViewModel {
    private final static String api = RetroConfig.getApiKey();
    private MutableLiveData<TvShow> listMovies;
    public void searchMovies(String query) {
        ApiService service = RetroConfig.getRetrofit().create(ApiService.class);
        Call<TvShow> movieCall = service.getTvSearch(api,query);
        Log.d("movie", String.valueOf(service));
        movieCall.enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                listMovies.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TvShow> call, Throwable t) {

            }
        });
    }
    public LiveData<TvShow> searchMovieget(String quer){
        if (listMovies == null) {
            listMovies = new MutableLiveData<>();
//            loadMovies();
            searchMovies(quer);
        }else {
            listMovies = new MutableLiveData<>();
//            loadMovies();
            searchMovies(quer);
        }
        return listMovies;
    }
}
