package com.rtikcirebonkota.myfavorite.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rtikcirebonkota.myfavorite.DetailFavoriteMovieActivity;
import com.rtikcirebonkota.myfavorite.R;
import com.rtikcirebonkota.myfavorite.model.MovieResult;
import com.rtikcirebonkota.myfavorite.utils.Constanta;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder> {
    private Context context;
    private List<MovieResult> movieList;

    public FavoriteMovieAdapter(Context con) {
        this.context = con;
        this.movieList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(movieList.get(position));

    }

    @Override

    public int getItemCount() {
        return movieList.size();
    }

    private void add(MovieResult item) {
        movieList.add(item);
        notifyItemInserted(movieList.size() - 1);
    }

    public void addAll(List<MovieResult> movieList) {
        for (MovieResult movieResult : movieList) {
            add(movieResult);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem());
        }
    }

    private void remove(MovieResult item) {
        int position = movieList.indexOf(item);
        if (position > -1) {
            movieList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private MovieResult getItem() {
        return movieList.get(0);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_poster)
        ImageView item_poster;
        @BindView(R.id.movie_title)
        TextView item_title;
        @BindView(R.id.movie_rating)
        TextView item_rating;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        void bindView(MovieResult movieResult) {
            item_title.setText(movieResult.getTitle());
            item_rating.setText(movieResult.getVoteAverage().toString());
            Glide.with(itemView)
                    .load(Constanta.URL_POSTER + movieResult.getPosterPath())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .into(item_poster);
            itemView.setOnClickListener(view -> {
                Intent intent;
                intent = new Intent(itemView.getContext(), DetailFavoriteMovieActivity.class);
                intent.putExtra(Constanta.DETAIL_MOVIE, movieResult);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}