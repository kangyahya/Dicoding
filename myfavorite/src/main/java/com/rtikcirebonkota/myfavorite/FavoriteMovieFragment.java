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

import com.rtikcirebonkota.myfavorite.adapter.FavoriteMovieAdapter;
import com.rtikcirebonkota.myfavorite.database.MyTMDB;
import com.rtikcirebonkota.myfavorite.model.MovieResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {
    @BindView(R.id.rv_favmovie)
    RecyclerView recyclerView;
    private FavoriteMovieAdapter mAdapter;

    public FavoriteMovieFragment() {
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        ButterKnife.bind(this, view);
        initView();
        getData();
        return view;
    }

    private void initView() {
        mAdapter = new FavoriteMovieAdapter(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @SuppressLint("Recycle")
    private void getData() {
        Uri uri = Uri.parse(MyTMDB.CONTENT_URI.toString());
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null, null);
        List<MovieResult> movieResultList = new ArrayList<>();
        MovieResult movieResult;
        if (cursor.moveToFirst()) {
            do {
                movieResult = new MovieResult();
                movieResult.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.idMovie)));
                movieResult.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.titleMovie)));
                movieResult.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.posterMovie)));
                movieResult.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.backdropMovie)));
                movieResult.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.overviewMovie)));
                movieResult.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.releaseMovie)));
                movieResult.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.averageMovie)));
                movieResult.setVoteCount(cursor.getLong(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.countMovie)));
                movieResult.setPopularity(cursor.getDouble(cursor.getColumnIndexOrThrow(MyTMDB.MovieColumns.popularityMovie)));
                movieResultList.add(movieResult);
            } while (cursor.moveToNext());
        }
        mAdapter.clear();
        mAdapter.addAll(movieResultList);
    }

}
