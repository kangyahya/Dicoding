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
import com.rtikcirebonkota.thecataloguemovie.DetailTvActivity;
import com.rtikcirebonkota.thecataloguemovie.R;
import com.rtikcirebonkota.thecataloguemovie.utils.Constanta;
import com.rtikcirebonkota.thecataloguemovie.model.TvResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder>{
    private List<TvResult> tvResult = new ArrayList<>();
    private Context context;
    public TvAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv,parent,false);
        return new TvViewHolder(mView);
    }

    public void setTvResult(List<TvResult> tvResult) {
        this.tvResult = tvResult;
    }
    public List<TvResult> getList(){
        return tvResult;
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        holder.bindView(tvResult.get(position));
    }

    @Override
    public int getItemCount() {
        return tvResult.size();
    }

    class TvViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_poster)
        ImageView item_poster;
        @BindView(R.id.icon_share) ImageView item_share;
        @BindView(R.id.tv_title)
        TextView item_title;
        @BindView(R.id.tv_rating) TextView item_rating;

        TvViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        @SuppressLint("SetTextI18n")
        void bindView(final TvResult tvResult){
            item_title.setText(tvResult.getName());
            item_rating.setText(tvResult.getVoteAverage().toString());
            Glide.with(itemView.getContext()).load(Constanta.URL_POSTER +tvResult.getPosterPath()).into(item_poster);
            itemView.setOnClickListener((View view) -> {
                Intent intent;
                intent = new Intent(itemView.getContext(), DetailTvActivity.class);
                intent.putExtra(Constanta.DETAIL_TV, tvResult);
                itemView.getContext().startActivity(intent);
            });
            item_share.setOnClickListener(v -> {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) final Intent intent = sendIntent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share, tvResult.getName()));
                sendIntent.setType("text/plain");
                itemView.getContext().startActivity(sendIntent);
            });

        }
    }
}
