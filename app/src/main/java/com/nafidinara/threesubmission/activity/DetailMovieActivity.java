package com.nafidinara.threesubmission.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.nafidinara.threesubmission.R;
import com.nafidinara.threesubmission.db.MovieDbContract;
import com.nafidinara.threesubmission.db.MovieDbHelper;
import com.nafidinara.threesubmission.db.MovieHelper;
import com.nafidinara.threesubmission.model.Movie;

import java.util.ArrayList;

import static com.nafidinara.threesubmission.db.MovieDbContract.MovieColumns.TABLE_MOVIE_NAME;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String DATA_MOVIE = "data_movie";
    private String img_main_url = "https://image.tmdb.org/t/p/w185";
    private String img_bg_url = "https://image.tmdb.org/t/p/w500";
    TextView title, release, language, vote, rating, storyline , title_toolbar;
    ImageView image_Poster, image_bg;
    ProgressBar progressBar;
    Toolbar toolbar;
    Movie movie;
    ArrayList<Movie>list;

    private MovieHelper movieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final MaterialFavoriteButton favBtn = findViewById(R.id.btn_fav);

        movieHelper = MovieHelper.getInst(getApplicationContext());
        movieHelper.open();
        movie = getIntent().getParcelableExtra(DATA_MOVIE);

        prepare();
        getMovieDetail();
        Log.d("id", String.valueOf(movie.getId()));
        if (Exist(movie.getTitle())){
            favBtn.setFavorite(true);
            favBtn.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite){
                                list = movieHelper.getAllMovies();
                                movieHelper.movieInsert(movie);
                                Toast toast = Toast.makeText(getApplicationContext(),"Ditambahkan Favorite",Toast.LENGTH_SHORT);
                                toast.show();

                            }
                            else {
                                list = movieHelper.getAllMovies();
                                movieHelper.movieDelete(movie.getTitle());
                                Toast toast = Toast.makeText(getApplicationContext(),"Dihapus Favorite",Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });

        }else {
            favBtn.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite){
                                list = movieHelper.getAllMovies();
                                movieHelper.movieInsert(movie);
                                Toast toast = Toast.makeText(getApplicationContext(),"Ditambahkan Favorite",Toast.LENGTH_SHORT);
                                toast.show();

                            }
                            else {
                                list = movieHelper.getAllMovies();
                                movieHelper.movieDelete(movie.getTitle());
                                Toast toast = Toast.makeText(getApplicationContext(),"Dihapus Favorite",Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });
        }

    }

    public boolean Exist(String item) {
        String pilih = MovieDbContract.MovieColumns.TITLE+" =?";
        String[] pilihArg = {item};
        String limit = "1";
        movieHelper = new MovieHelper(this);
        movieHelper.open();
        MovieDbHelper dataBaseHelper = new MovieDbHelper(DetailMovieActivity.this);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        Cursor cursor = database.query(TABLE_MOVIE_NAME, null, pilih, pilihArg, null, null, null, limit);
        boolean exists;
        exists = (cursor.getCount() > 0);
        cursor.close();
//        movieHelper.close();
        return exists;
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
        title_toolbar = findViewById(R.id.detail_title_tolbar);
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

                        title_toolbar.setText(movie.getTitle());
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

                        toolbar = findViewById(R.id.detail_toolbar);
                        toolbar.setNavigationIcon(R.drawable.back_arrow);
                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });

                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        movieHelper.close();
    }
}

