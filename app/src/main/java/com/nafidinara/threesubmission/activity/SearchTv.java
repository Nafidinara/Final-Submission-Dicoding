package com.nafidinara.threesubmission.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.nafidinara.threesubmission.R;
import com.nafidinara.threesubmission.adapter.AdapterTvShow;
import com.nafidinara.threesubmission.model.TvShow;
import com.nafidinara.threesubmission.viewModel.SearchTvVM;

public class SearchTv extends AppCompatActivity {
    public static final String EXTRA_SEARCH_TV="extra_query";
    private AdapterTvShow adapter;
    private ProgressBar progressBar;
    private SearchTvVM movieVM;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tv);
        String query = getIntent().getStringExtra(EXTRA_SEARCH_TV);

        rv = findViewById(R.id.rv_search_tv);
        rv.setLayoutManager(new LinearLayoutManager(SearchTv.this));

        progressBar = findViewById(R.id.progressBar_search_tv);
        movieVM = ViewModelProviders.of(this).get(SearchTvVM.class);
        movieVM.searchMovieget(query).observe(this,getMovie);
        movieVM.searchMovies(query);

        showLoading(true);
    }
    private Observer<TvShow> getMovie = new Observer<TvShow>() {
        @Override
        public void onChanged(@Nullable TvShow tvShow) {
            adapter = new AdapterTvShow(SearchTv.this,tvShow.getResults());
            rv.setAdapter(adapter);
            showLoading(false);
        }
    };


    private void showLoading(boolean b) {
        if (b){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
