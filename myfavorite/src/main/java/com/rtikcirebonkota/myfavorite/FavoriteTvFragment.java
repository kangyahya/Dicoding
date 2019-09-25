package com.rtikcirebonkota.myfavorite;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rtikcirebonkota.myfavorite.adapter.FavoriteTvAdapter;
import com.rtikcirebonkota.myfavorite.database.MyTMDB;
import com.rtikcirebonkota.myfavorite.model.TvResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment {
    @BindView(R.id.rv_fatv)
    RecyclerView recyclerView;
    FavoriteTvAdapter mAdapter;

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_tv, container, false);
        ButterKnife.bind(this, view);
        initView();
        getData();
        return view;
    }

    private void initView() {
        mAdapter = new FavoriteTvAdapter(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    @SuppressLint("Recycle")
    private void getData() {
        Uri uri = Uri.parse(MyTMDB.CONTENT_TV.toString());
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null, null);
        List<TvResult> tvResultList = new ArrayList<>();
        TvResult tvResult;
        if (cursor.moveToFirst()) {
            do {
                tvResult = new TvResult();
                tvResult.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MyTMDB.TvColumns.idTv)));
                tvResult.setName(cursor.getString(cursor.getColumnIndexOrThrow(MyTMDB.TvColumns.titleTv)));
                tvResult.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(MyTMDB.TvColumns.posterTv)));
                tvResult.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(MyTMDB.TvColumns.backdropTv)));
                tvResult.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(MyTMDB.TvColumns.overviewTv)));
                tvResult.setFirstAirDate(cursor.getString(cursor.getColumnIndexOrThrow(MyTMDB.TvColumns.firstAiring)));
                tvResult.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(MyTMDB.TvColumns.averageTv)));
                tvResult.setVoteCount(cursor.getLong(cursor.getColumnIndexOrThrow(MyTMDB.TvColumns.countTv)));
                tvResult.setPopularity(cursor.getDouble(cursor.getColumnIndexOrThrow(MyTMDB.TvColumns.popularityTv)));
                tvResultList.add(tvResult);
            } while (cursor.moveToNext());
        }
        mAdapter.clear();
        mAdapter.addAll(tvResultList);
    }

}
