package com.nafidinara.favorite.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nafidinara.favorite.R;
import com.nafidinara.favorite.activity.DetailTvActivity;
import com.nafidinara.favorite.model.Movie;
import com.nafidinara.favorite.model.TvShow;

import java.util.ArrayList;

public class AdapterFavTv extends RecyclerView.Adapter<AdapterFavTv.ViewHolderFavTv>{
    private Context context;
    Cursor cursor;
    public AdapterFavTv(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    private TvShow getItems(int Position){
        if (!cursor.moveToPosition(Position)){
            throw new IllegalStateException("Invalid");
        }
        return new TvShow(cursor);
    }

    @NonNull
    @Override
    public AdapterFavTv.ViewHolderFavTv onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,viewGroup,false);
        return new AdapterFavTv.ViewHolderFavTv(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFavTv.ViewHolderFavTv viewHolderFavTv, int i) {
        TvShow movie = getItems(i);
        viewHolderFavTv.bind(movie);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
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
            TvShow tvShow = getItems(i);
            Intent intent = new Intent(v.getContext(), DetailTvActivity.class);
            intent.putExtra(DetailTvActivity.DATA_TVSHOW,tvShow);
            v.getContext().startActivity(intent);
        }
    }
}
