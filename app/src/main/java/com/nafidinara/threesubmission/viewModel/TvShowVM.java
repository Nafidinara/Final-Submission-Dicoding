package com.nafidinara.threesubmission.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.nafidinara.threesubmission.connection.ApiService;
import com.nafidinara.threesubmission.connection.RetroConfig;
import com.nafidinara.threesubmission.model.TvShow;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowVM extends ViewModel {
    private final static String api = RetroConfig.getApiKey();
    private MutableLiveData<TvShow> listTvShow;

    public void loadTvShow(){
        ApiService service = RetroConfig.getRetrofit().create(ApiService.class);
        Call<TvShow> tvShowCall = service.getShow(api);
        tvShowCall.enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                listTvShow.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TvShow> call, Throwable t) {
                Log.e("Failed to Fetch TvShow", t.getMessage());
            }
        });

    }
    public LiveData<TvShow> getTvShow(){
        if (listTvShow == null) {
            listTvShow = new MutableLiveData<>();
            loadTvShow();
        }
        return listTvShow;
    }

}
