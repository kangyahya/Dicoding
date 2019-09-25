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
import com.rtikcirebonkota.myfavorite.DetailFavoriteTvActivity;
import com.rtikcirebonkota.myfavorite.R;
import com.rtikcirebonkota.myfavorite.model.TvResult;
import com.rtikcirebonkota.myfavorite.utils.Constanta;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteTvAdapter extends RecyclerView.Adapter<FavoriteTvAdapter.ViewHolder> {
    private Context context;
    private List<TvResult> tvResultList;

    public FavoriteTvAdapter(Context context) {
        this.context = context;
        this.tvResultList = new ArrayList<>();
    }

    public FavoriteTvAdapter() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvAdapter.ViewHolder holder, int position) {
        holder.bindView(tvResultList.get(position));

    }

    @Override
    public int getItemCount() {
        return tvResultList.size();
    }

    private void add(TvResult item) {
        tvResultList.add(item);
        notifyItemInserted(tvResultList.size() - 1);
    }

    public void addAll(List<TvResult> tvList) {
        for (TvResult tvResult : tvList) {
            add(tvResult);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem());
        }
    }

    private void remove(TvResult item) {
        int position = tvResultList.indexOf(item);
        if (position > -1) {
            tvResultList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private TvResult getItem() {
        return tvResultList.get(0);
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
        void bindView(TvResult tvResult) {
            item_title.setText(tvResult.getName());
            item_rating.setText(tvResult.getVoteAverage().toString());
            Glide.with(itemView)
                    .load(Constanta.URL_POSTER + tvResult.getPosterPath())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .into(item_poster);
            itemView.setOnClickListener(view -> {
                Intent intent;
                intent = new Intent(itemView.getContext(), DetailFavoriteTvActivity.class);
                intent.putExtra(Constanta.DETAIL_TV, tvResult);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}