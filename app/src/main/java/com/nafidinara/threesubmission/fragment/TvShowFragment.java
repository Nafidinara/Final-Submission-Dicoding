package com.nafidinara.threesubmission.fragment;


import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.nafidinara.threesubmission.R;
import com.nafidinara.threesubmission.activity.SearchMovie;
import com.nafidinara.threesubmission.activity.SearchTv;
import com.nafidinara.threesubmission.adapter.AdapterTvShow;
import com.nafidinara.threesubmission.model.TvShow;
import com.nafidinara.threesubmission.viewModel.TvShowVM;

import java.util.Objects;

import static com.nafidinara.threesubmission.activity.SearchMovie.EXTRA_SEARCH_MOVIE;
import static com.nafidinara.threesubmission.activity.SearchTv.EXTRA_SEARCH_TV;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private AdapterTvShow adapter;
    private ProgressBar progressBar;
    private TvShowVM tvShowVM;
    private RecyclerView rv;


    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tv_show,container,false);
        setHasOptionsMenu(true);
        rv = v.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));

        progressBar = v.findViewById(R.id.progressBar2);
        tvShowVM = ViewModelProviders.of(this).get(TvShowVM.class);
        tvShowVM.getTvShow().observe(this,getTvShow);

        showLoading(true);

        return v;

    }

    private Observer<TvShow> getTvShow = new Observer<TvShow>() {
        @Override
        public void onChanged(@Nullable TvShow tvShow) {
            adapter = new AdapterTvShow(getContext(),tvShow.getResults());
            rv.setAdapter(adapter);
            showLoading(false);
        }
    };

    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        Log.d("test","Test");

        inflater.inflate(R.menu.menu,menu);
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);
        if(searchManager != null){
            SearchView searchView = (SearchView) (menu.findItem(R.id.action_search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.d("test","Test");
                    Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                    Intent search = new Intent(getContext(), SearchTv.class);
                    search.putExtra(EXTRA_SEARCH_TV,query);
                    startActivity(search);
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.d("test",newText);
                    return false;
                }
            });
        }
    }

}
