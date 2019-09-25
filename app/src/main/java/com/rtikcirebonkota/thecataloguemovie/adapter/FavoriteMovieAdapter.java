package com.rtikcirebonkota.thecataloguemovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rtikcirebonkota.thecataloguemovie.R;
import com.rtikcirebonkota.thecataloguemovie.utils.Constanta;
import com.rtikcirebonkota.thecataloguemovie.model.MovieResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder> {
    private Context context;
    private List<MovieResult> movieList;
    public FavoriteMovieAdapter(Context con){
        this.context = con;
        this.movieList = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_favorite,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MovieResult movieResult = movieList.get(position);
        holder.item_title.setText(movieResult.getTitle());
        holder.item_date.setText(movieResult.getReleaseDate());
        holder.item_detail.setText(movieResult.getOverview());
        Glide.with(holder.itemView)
                .load(Constanta.URL_POSTER+movieResult.getPosterPath())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(holder.item_poster);
    }

    @Override

    public int getItemCount() {
        return movieList.size();
    }
    private void add(MovieResult item){
        movieList.add(item);
        notifyItemInserted(movieList.size() -1);
    }
    public void addAll(List<MovieResult> movieList){
        for(MovieResult movieResult : movieList){
            add(movieResult);
        }
    }
    public void clear() {
        while (getItemCount()>0){
            remove(getItem());
        }
    }
    private void remove(MovieResult item){
        int position = movieList.indexOf(item);
        if(position>-1){
            movieList.remove(position);
            notifyItemRemoved(position);
        }
    }
    private MovieResult getItem(){
        return movieList.get(0);
    }

    public Context getContext() {
        return context;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_poster)
        ImageView item_poster;
        @BindView(R.id.movie_title)
        TextView item_title;
        @BindView(R.id.item_date_detail) TextView item_date;
        @BindView(R.id.detail) TextView item_detail;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
