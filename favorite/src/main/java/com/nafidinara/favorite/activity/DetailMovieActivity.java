package com.nafidinara.favorite.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.nafidinara.favorite.R;
import com.nafidinara.favorite.model.Movie;


import java.util.ArrayList;

import static com.nafidinara.favorite.db.MovieDbContract.MovieColumns.MOVIE_URI;


public class DetailMovieActivity extends AppCompatActivity {
    public static final String DATA_MOVIE = "data_movie";
    private String img_main_url = "https://image.tmdb.org/t/p/w185";
    private String img_bg_url = "https://image.tmdb.org/t/p/w500";
    TextView title, release, language, vote, rating, storyline , title_toolbar;
    ImageView image_Poster, image_bg;
    ProgressBar progressBar;

    Movie movie;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final MaterialFavoriteButton favBtn = findViewById(R.id.btn_fav);

        movie = getIntent().getParcelableExtra(DATA_MOVIE);
        Log.d("id", String.valueOf(movie.getId()));
        prepare();
        getMovieDetail();
        setTitle(movie.getTitle());
        if (movie.getId() >0){
            favBtn.setFavorite(true);

        }
        favBtn.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {

                            getContentResolver().delete(Uri.parse(MOVIE_URI+"/"+movie.getId()),null,null);
                            startActivity(new Intent(DetailMovieActivity.this,MainActivity.class));

                    }
                });

    }

    private void prepare(){
        title = findViewById(R.id.detail_tv_title);
        language = findViewById(R.id.detail_tv_language);
        release = findViewById(R.id.detail_tv_release);
        vote = findViewById(R.id.detail_tv_vote);
        rating = findViewById(R.id.detail_tv_rating);
        storyline = findViewById(R.id.detail_tv_storyline);
        image_bg = findViewById(R.id.detail_poster_background);
        image_Poster = findViewById(R.id.detail_poster);
        progressBar = findViewById(R.id.progressBarDetail);

    }

    private void getMovieDetail(){
        progressBar.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        title.setText(movie.getTitle());
                        rating.setText(String.valueOf(movie.getVoteAverage()));
                        vote.setText(String.valueOf(movie.getVoteCount()));
                        language.setText(movie.getOriginalLanguage());
                        release.setText(movie.getReleaseDate());
                        storyline.setText(movie.getOverview());

                        Glide.with(DetailMovieActivity.this)
                                .load(img_main_url+movie.getPosterPath())
                                .into(image_Poster);
                        Glide.with(DetailMovieActivity.this)
                                .load(img_bg_url+movie.getBackdropPath())
                                .into(image_bg);


                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }
}

