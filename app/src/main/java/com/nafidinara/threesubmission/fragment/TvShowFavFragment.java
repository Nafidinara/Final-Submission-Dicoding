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
import com.nafidinara.threesubmission.adapter.AdapterFavTv;
import com.nafidinara.threesubmission.db.TvHelper;
import com.nafidinara.threesubmission.model.TvShow;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavFragment extends Fragment {
    private ArrayList<TvShow> list;
    private TvHelper tvHelper;
    private AdapterFavTv adapter;

    public TvShowFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        tvHelper = TvHelper.getInst(view.getContext());
        tvHelper.open();

        RecyclerView rv = view.findViewById(R.id.rvTvFav);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new AdapterFavTv(view.getContext());
        rv.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        list = tvHelper.getAllMovies();
        adapter.setList(list);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        tvHelper.close();
    }

}
