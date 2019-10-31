package com.nafidinara.favorite.fragment;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nafidinara.favorite.R;
import com.nafidinara.favorite.adapter.AdapterFavTv;

import java.util.ArrayList;
import java.util.Objects;

import static com.nafidinara.favorite.db.TvDbContract.TvColumns.TV_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavFragment extends Fragment {
    Cursor cursor;
    private AdapterFavTv adapter;

    public TvShowFavFragment() {
        // Required empty public constructor
    }
    RecyclerView rv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        rv= view.findViewById(R.id.rvTvFav);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));


        new TV().execute();
    }


    private class TV extends AsyncTask<Void,Void,Cursor> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Cursor doInBackground(Void... voids) {
            return Objects.requireNonNull(getContext()).getContentResolver().query(TV_URI,null,null,null);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            adapter = new AdapterFavTv(getContext());
            adapter.setCursor(cursor);
            adapter.notifyDataSetChanged();
            rv.setAdapter(adapter);
        }

    }
}
