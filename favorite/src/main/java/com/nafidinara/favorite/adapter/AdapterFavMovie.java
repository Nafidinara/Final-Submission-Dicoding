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
import com.nafidinara.favorite.activity.DetailMovieActivity;
import com.nafidinara.favorite.model.Movie;

import java.util.ArrayList;

public class AdapterFavMovie extends RecyclerView.Adapter<AdapterFavMovie.ViewHolderFavMovie>{

    private Context context;
    Cursor cursor;

    public AdapterFavMovie(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }
    private Movie getItems(int Position){
        if (!cursor.moveToPosition(Position)){
            throw new IllegalStateException("Invalid");
        }
        return new Movie(cursor);
    }
    @NonNull
    @Override
    public AdapterFavMovie.ViewHolderFavMovie onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,viewGroup,false);
        return new AdapterFavMovie.ViewHolderFavMovie(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFavMovie.ViewHolderFavMovie viewHolderFavMovie, int i) {
        Movie movie = getItems(i);
        viewHolderFavMovie.bind(movie);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    public class ViewHolderFavMovie extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image_main;
        TextView title, release, vote;
        public ViewHolderFavMovie(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_title_items);
            release = itemView.findViewById(R.id.txt_release_items);
            image_main = itemView.findViewById(R.id.img_items);
            vote = itemView.findViewById(R.id.txt_genre_items);

            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            title.setText(movie.getTitle());
            release.setText(movie.getReleaseDate());
            vote.setText(String.valueOf(movie.getVoteCount()));

            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                    .into(image_main);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movie movie = getItems(position);
            Intent intent = new Intent(view.getContext(), DetailMovieActivity.class);
            intent.putExtra(DetailMovieActivity.DATA_MOVIE,movie);
            view.getContext().startActivity(intent);
        }
    }
}