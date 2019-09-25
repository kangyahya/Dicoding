package com.rtikcirebonkota.thecataloguemovie.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rtikcirebonkota.thecataloguemovie.R;
import com.rtikcirebonkota.thecataloguemovie.adapter.MovieAdapter;
import com.rtikcirebonkota.thecataloguemovie.api.BaseApiService;
import com.rtikcirebonkota.thecataloguemovie.api.RetrofitClient;
import com.rtikcirebonkota.thecataloguemovie.model.MovieResult;
import com.rtikcirebonkota.thecataloguemovie.model.ResponseMovie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rtikcirebonkota.thecataloguemovie.utils.Constanta.API_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingMovieFragment extends Fragment {
    @BindView(R.id.rv_movie)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private List<MovieResult> movieList;
    private MovieAdapter movieAdapter;

    public void onAttach(Context context){
        super.onAttach(context);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_movie, container, false);
        ButterKnife.bind(this, view);
        movieList = new ArrayList<>();
        initView();
        if(savedInstanceState!=null){
            ArrayList<MovieResult> list;
            list = savedInstanceState.getParcelableArrayList("upcoming");
            movieAdapter.setMovieResult(list);
            recyclerView.setAdapter(movieAdapter);
        }else{
            getMovies();
        }
        return view;
    }
    private void initView(){
        int columns = getResources().getInteger(R.integer.collumn_count);
        movieAdapter = new MovieAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columns));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    private void getMovies(){
        showPbar();
        BaseApiService movieService = RetrofitClient.getClient().create(BaseApiService.class);
        Call<ResponseMovie> movieCall = movieService.getUpcomingMovie(API_KEY);

        movieCall.enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMovie> call, @NonNull Response<ResponseMovie> response) {
                assert response.body() != null;
                movieList = response.body().getResults();
                Log.v("Matt", "Number of movie with  = "+response.body().getTotalResults());
                movieAdapter.setMovieResult(movieList);
                recyclerView.setAdapter(movieAdapter);
                hidePbar();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMovie> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Please Check Your Connection"
                        , Toast.LENGTH_SHORT).show();
                hidePbar();
            }
        });
    }
    private void showPbar(){
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hidePbar(){
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("upcoming", new ArrayList<>(movieAdapter.getList()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            ArrayList<MovieResult> list;
            list = savedInstanceState.getParcelableArrayList("upcoming");
            movieAdapter.setMovieResult(list);
            recyclerView.setAdapter(movieAdapter);
        }
    }
}
