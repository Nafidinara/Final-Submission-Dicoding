package com.nafidinara.threesubmission.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nafidinara.threesubmission.R;
import com.nafidinara.threesubmission.adapter.AdapterFavMovie;
import com.nafidinara.threesubmission.db.MovieHelper;
import com.nafidinara.threesubmission.model.Movie;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment {
    private ArrayList<Movie> list;
    private MovieHelper movieHelper;
    private AdapterFavMovie adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        movieHelper = MovieHelper.getInst(view.getContext());
        movieHelper.open();

        RecyclerView rv = view.findViewById(R.id.rvMovieFav);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new AdapterFavMovie(view.getContext());
        rv.setAdapter(adapter);


    }

    @Override
    public void onResume(){
        super.onResume();
        list = movieHelper.getAllMovies();
        adapter.setList(list);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        movieHelper.close();
    }

}
