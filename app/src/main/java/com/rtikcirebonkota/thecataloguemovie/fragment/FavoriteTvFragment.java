package com.rtikcirebonkota.thecataloguemovie.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rtikcirebonkota.thecataloguemovie.R;
import com.rtikcirebonkota.thecataloguemovie.adapter.FavoriteTvAdapter;
import com.rtikcirebonkota.thecataloguemovie.database.MyTMDB;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment{
    @BindView(R.id.rv_favtv)
    RecyclerView recyclerView;
    private ArrayList<Object> IDList, TitleList, PosterList,OverviewList, FirstAiringList;
    public void onAttach(Context context){
        super.onAttach(context);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_tv, container, false);
        ButterKnife.bind(this, view);
        IDList = new ArrayList<>();
        TitleList = new ArrayList<>();
        PosterList = new ArrayList<>();
        OverviewList = new ArrayList<>();
        FirstAiringList = new ArrayList<>();
        initView();
        getData();
        return view;
    }
    private void initView(){
        RecyclerView.Adapter adapter = new FavoriteTvAdapter(IDList, TitleList, PosterList, OverviewList, FirstAiringList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    @SuppressLint("Recycle")
    private void getData(){
        Uri uri = Uri.parse(MyTMDB.CONTENT_TV.toString());
        Cursor cursor = getContext().getContentResolver().query(uri,null,null,null,null);
        cursor.moveToFirst();
        for(int i=0; i< cursor.getCount();i++){
            cursor.moveToPosition(i);
            IDList.add(cursor.getString(0));
            TitleList.add(cursor.getString(1));
            PosterList.add(cursor.getString(2));
            OverviewList.add(cursor.getString(4));
            FirstAiringList.add(cursor.getString(5));

        }

    }
}
