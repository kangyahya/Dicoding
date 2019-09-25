package com.rtikcirebonkota.thecataloguemovie.adapter;

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
import com.rtikcirebonkota.thecataloguemovie.DetailMovieActivity;
import com.rtikcirebonkota.thecataloguemovie.R;
import com.rtikcirebonkota.thecataloguemovie.model.MovieResult;
import com.rtikcirebonkota.thecataloguemovie.utils.Constanta;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<MovieResult> movieResult = new ArrayList<>();
    private Context context;
    public MovieAdapter(Context context){
        this.context = context;
    }

    public MovieAdapter() {

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new MovieViewHolder(mView);
    }

    public void setMovieResult(List<MovieResult> movieResult) {
        this.movieResult = movieResult;
    }

    public List<MovieResult> getList(){
        return movieResult;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bindView(movieResult.get(position));
    }

    @Override
    public int getItemCount() {
        return movieResult.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_poster) ImageView item_poster;
        @BindView(R.id.icon_share) ImageView item_share;
        @BindView(R.id.movie_title) TextView item_title;
        @BindView(R.id.movie_rating) TextView item_rating;

        MovieViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
        @SuppressLint({"SetTextI18n", "StringFormatInvalid"})
        void bindView(final MovieResult movieResult){
            item_title.setText(movieResult.getTitle());
            item_rating.setText(movieResult.getVoteAverage().toString());
            Glide.with(itemView.getContext()).load(Constanta.URL_POSTER +movieResult.getPosterPath()).into(item_poster);
            itemView.setOnClickListener((View view) -> {
                Intent intent;
                intent = new Intent(itemView.getContext(), DetailMovieActivity.class);
                intent.putExtra(Constanta.DETAIL_MOVIE, movieResult);
                itemView.getContext().startActivity(intent);
            });
            item_share.setOnClickListener(v -> {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        context.getString(R.string.share, movieResult.getTitle()));
                sendIntent.setType("text/plain");
                itemView.getContext().startActivity(sendIntent);
            });

        }
    }
}
