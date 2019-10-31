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
import com.nafidinara.threesubmission.activity.DetailMovieActivity;
import com.nafidinara.threesubmission.model.Movie;

import java.util.ArrayList;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.ViewHolderMovie> {
    private Context context;
    private ArrayList<Movie> list;

    public AdapterMovie (Context context, ArrayList<Movie>list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolderMovie onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,viewGroup,false);
        return new ViewHolderMovie(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMovie.ViewHolderMovie viewHolderMovie, int i) {
        viewHolderMovie.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderMovie extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image_main;
        TextView title, release, genre;
        public ViewHolderMovie(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.txt_title_items);
            release = itemView.findViewById(R.id.txt_release_items);
            image_main = itemView.findViewById(R.id.img_items);
            genre = itemView.findViewById(R.id.txt_genre_items);

            itemView.setOnClickListener(this);
        }

         void bind(Movie movie) {
            title.setText(movie.getTitle());
            release.setText(movie.getReleaseDate());
            genre.setText(String.valueOf(movie.getVoteCount()));

            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                    .into(image_main);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movie movie = list.get(position);
            Intent intent = new Intent(view.getContext(), DetailMovieActivity.class);
            intent.putExtra(DetailMovieActivity.DATA_MOVIE,movie);
            view.getContext().startActivity(intent);
        }
    }
}