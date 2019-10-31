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
import com.nafidinara.threesubmission.adapter.AdapterMovie;
import com.nafidinara.threesubmission.model.Movie;
import com.nafidinara.threesubmission.viewModel.SearchMovieVM;

public class SearchMovie extends AppCompatActivity {
    public static final String EXTRA_SEARCH_MOVIE="extra_query";
    private AdapterMovie adapter;
    private ProgressBar progressBar;
    private SearchMovieVM movieVM;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String query = getIntent().getStringExtra(EXTRA_SEARCH_MOVIE);

        rv = findViewById(R.id.rv_search_movie);
        rv.setLayoutManager(new LinearLayoutManager(SearchMovie.this));

        progressBar = findViewById(R.id.progressBar_search_movie);
        //getdata from viewmodel
        movieVM = ViewModelProviders.of(this).get(SearchMovieVM.class);
        movieVM.searchMovieget(query).observe(this,getMovie);
        movieVM.searchMovies(query);

        showLoading(true);
    }
    private Observer<Movie> getMovie = new Observer<Movie>() {
        @Override
        public void onChanged(@Nullable Movie movie) {
            adapter = new AdapterMovie(SearchMovie.this,movie.getResults());
            rv.setAdapter(adapter);
            showLoading(false);
        }
    };
    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
