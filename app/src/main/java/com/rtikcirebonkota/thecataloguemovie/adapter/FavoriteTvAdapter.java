package com.rtikcirebonkota.thecataloguemovie.adapter;

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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteTvAdapter extends RecyclerView.Adapter<FavoriteTvAdapter.ViewHolder> {

    private ArrayList IDList, TitleList, PosterList, OverviewList, FirstAirList;

    public FavoriteTvAdapter(ArrayList IDList, ArrayList TitleList, ArrayList PosterList, ArrayList OverviewList, ArrayList FirstAirList){
        this.IDList=IDList;
        this.TitleList = TitleList;
        this.PosterList = PosterList;
        this.OverviewList = OverviewList;
        this.FirstAirList = FirstAirList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_favorite,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String ID = (String) IDList.get(position);
        final String Title = (String) TitleList.get(position);
        final String Poster = (String) PosterList.get(position);
        final String OverView = (String) OverviewList.get(position);
        final String Release = (String) FirstAirList.get(position);
        holder.item_title.setText(Title);
        holder.item_date.setText( Release);
        holder.item_detail.setText(OverView);
        Glide.with(holder.itemView)
                .load(Constanta.URL_POSTER+Poster)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(holder.item_poster);
    }

    @Override
    public int getItemCount() {
        return IDList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_poster)
        ImageView item_poster;
        //@BindView(R.id.icon_share) ImageView item_share;
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
