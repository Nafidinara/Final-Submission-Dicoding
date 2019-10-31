package com.nafidinara.threesubmission.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nafidinara.threesubmission.R;
import com.nafidinara.threesubmission.activity.DetailTvActivity;
import com.nafidinara.threesubmission.model.TvShow;

import java.util.ArrayList;

public class AdapterFavTv extends RecyclerView.Adapter<AdapterFavTv.ViewHolderFavTv>{
    private Context context;
    private ArrayList<TvShow> list;

    public AdapterFavTv(Context context) {
        this.context = context;
    }

    public ArrayList<TvShow> getList() {
        return list;
    }

    public void setList(ArrayList<TvShow> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterFavTv.ViewHolderFavTv onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,viewGroup,false);
        return new AdapterFavTv.ViewHolderFavTv(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFavTv.ViewHolderFavTv viewHolderFavTv, int i) {
        viewHolderFavTv.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderFavTv extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image_main;
        TextView title, release, vote;
        public ViewHolderFavTv(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_title_items);
            release = itemView.findViewById(R.id.txt_release_items);
            image_main = itemView.findViewById(R.id.img_items);
            vote = itemView.findViewById(R.id.txt_genre_items);

            itemView.setOnClickListener(this);
        }

        public void bind(TvShow tvShow) {
            title.setText(tvShow.getTitle());
            release.setText(tvShow.getReleaseDate());
            vote.setText(String.valueOf(tvShow.getVoteCount()));

            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + tvShow.getPosterPath())
                    .into(image_main);
        }
        @Override
        public void onClick(View v) {
            int i = getAdapterPosition();
            TvShow tvShow = list.get(i);
            Intent intent = new Intent(v.getContext(), DetailTvActivity.class);
            intent.putExtra(DetailTvActivity.DATA_TVSHOW,tvShow);
            v.getContext().startActivity(intent);
        }
    }
}
