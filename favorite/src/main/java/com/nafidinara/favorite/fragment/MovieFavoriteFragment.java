package com.nafidinara.favorite.fragment;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nafidinara.favorite.R;
import com.nafidinara.favorite.adapter.AdapterFavMovie;
import com.nafidinara.favorite.model.Movie;

import java.util.Objects;

import static android.support.constraint.Constraints.TAG;
import static com.nafidinara.favorite.db.MovieDbContract.MovieColumns.MOVIE_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment {

    AdapterFavMovie adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }
    RecyclerView rv;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        rv = view.findViewById(R.id.rvMovieFav);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));



        new MOVIE().execute();
    }


    private class MOVIE extends AsyncTask<Void,Void, Cursor> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Cursor doInBackground(Void... voids) {
            return Objects.requireNonNull(getContext()).getContentResolver().query(MOVIE_URI,null,null,null);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
//            Log.d("cursor", String.valueOf(cursor));
            adapter = new AdapterFavMovie(getContext());
            adapter.setCursor(cursor);
            adapter.notifyDataSetChanged();
            rv.setAdapter(adapter);

        }
    }
}
