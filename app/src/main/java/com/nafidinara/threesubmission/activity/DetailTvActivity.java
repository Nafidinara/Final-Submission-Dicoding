package com.nafidinara.threesubmission.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.nafidinara.threesubmission.R;
import com.nafidinara.threesubmission.db.TvDbContract;
import com.nafidinara.threesubmission.db.TvDbHelper;
import com.nafidinara.threesubmission.db.TvHelper;
import com.nafidinara.threesubmission.model.TvShow;

import java.util.ArrayList;

import static com.nafidinara.threesubmission.db.TvDbContract.TvColumns.TABLE_TV_NAME;

public class DetailTvActivity extends AppCompatActivity {
    public static final String DATA_TVSHOW = "data_tvshow";
    private String img_main_url = "https://image.tmdb.org/t/p/w185";
    private String img_bg_url = "https://image.tmdb.org/t/p/w500";
    TextView title, release, language, vote, rating, storyline, title_toolbar;
    ImageView image_Poster, image_bg;
    ProgressBar progressBar;
    Toolbar toolbar;
    TvShow tvShow;
    ArrayList<TvShow> list;

    private TvHelper tvHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);
        final MaterialFavoriteButton favBtn = findViewById(R.id.btn_fav1);

        tvHelper = TvHelper.getInst(getApplicationContext());
        tvHelper.open();
        tvShow = getIntent().getParcelableExtra(DATA_TVSHOW);

        prepare();
        getTVShowDetail();

        if (Exist(tvShow.getTitle())){
            favBtn.setFavorite(true);
            favBtn.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite){
                                list = tvHelper.getAllMovies();
                                tvHelper.tvInsert(tvShow);
                                Toast toast = Toast.makeText(getApplicationContext(),"Ditambahkan Favorite",Toast.LENGTH_SHORT);
                                toast.show();

                            }
                            else {
                                list = tvHelper.getAllMovies();
                                tvHelper.tvDelete(tvShow.getTitle());
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
                                list = tvHelper.getAllMovies();
                                tvHelper.tvInsert(tvShow);
                                Toast toast = Toast.makeText(getApplicationContext(),"Ditambahkan Favorite",Toast.LENGTH_SHORT);
                                toast.show();

                            }
                            else {
                                list = tvHelper.getAllMovies();
                                tvHelper.tvDelete(tvShow.getTitle());
                                Toast toast = Toast.makeText(getApplicationContext(),"Dihapus Favorite",Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });

        }

    }

    public boolean Exist(String item) {
        String pilih = TvDbContract.TvColumns.TITLE+" =?";
        String[] pilihArg = {item};
        String limit = "1";
        tvHelper = new TvHelper(this);
        tvHelper.open();
        TvDbHelper dataBaseHelper = new TvDbHelper(DetailTvActivity.this);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        Cursor cursor = database.query(TABLE_TV_NAME, null, pilih, pilihArg, null, null, null, limit);
        boolean exists;
        exists = (cursor.getCount() > 0);
        cursor.close();
//        movieHelper.close();
        return exists;
    }

    private void prepare() {
        title = findViewById(R.id.detail_tv_title1);
        language = findViewById(R.id.detail_tv_language1);
        release = findViewById(R.id.detail_tv_release1);
        vote = findViewById(R.id.detail_tv_vote1);
        rating = findViewById(R.id.detail_tv_rating1);
        storyline = findViewById(R.id.detail_tv_storyline1);
        image_bg = findViewById(R.id.detail_poster_background1);
        image_Poster = findViewById(R.id.detail_poster1);
        progressBar = findViewById(R.id.progressBarDetail1);
        title_toolbar = findViewById(R.id.detail_title_tolbar1);
    }

    private void getTVShowDetail() {
        progressBar.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TvShow tvShow = getIntent().getParcelableExtra(DATA_TVSHOW);

                        title_toolbar.setText(tvShow.getTitle());
                        title.setText(tvShow.getTitle());
                        rating.setText(String.valueOf(tvShow.getVoteAverage()));
                        vote.setText(String.valueOf(tvShow.getVoteCount()));
                        language.setText(tvShow.getOriginalLanguage());
                        release.setText(tvShow.getReleaseDate());
                        storyline.setText(tvShow.getOverview());

                        Glide.with(DetailTvActivity.this)
                                .load(img_main_url + tvShow.getPosterPath())
                                .into(image_Poster);
                        Glide.with(DetailTvActivity.this)
                                .load(img_bg_url + tvShow.getBackdropPath())
                                .into(image_bg);

                        toolbar = findViewById(R.id.detail_toolbar1);
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
//        tvHelper.close();
    }
}
