package com.rtikcirebonkota.thecataloguemovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.rtikcirebonkota.thecataloguemovie.adapter.MovieAdapter;
import com.rtikcirebonkota.thecataloguemovie.adapter.TvAdapter;
import com.rtikcirebonkota.thecataloguemovie.api.BaseApiService;
import com.rtikcirebonkota.thecataloguemovie.api.RetrofitClient;
import com.rtikcirebonkota.thecataloguemovie.model.MovieResult;
import com.rtikcirebonkota.thecataloguemovie.model.ResponseMovie;
import com.rtikcirebonkota.thecataloguemovie.model.ResponseTv;
import com.rtikcirebonkota.thecataloguemovie.model.TvResult;
import com.rtikcirebonkota.thecataloguemovie.utils.Constanta;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rtikcirebonkota.thecataloguemovie.utils.Constanta.API_KEY;
import static com.rtikcirebonkota.thecataloguemovie.utils.Constanta.INTENT_DETAIL;
import static com.rtikcirebonkota.thecataloguemovie.utils.Constanta.INTENT_TAG;

public class ResultSearchActivity extends AppCompatActivity {
    @BindView(R.id.recycler_search)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar_search)
    Toolbar toolbar;

    int columns = 0;
    MovieAdapter movieAdapter;
    TvAdapter tvAdapter;

    List<MovieResult> movieList;
    List<TvResult> tvList;
    BaseApiService movieService;
    Call<ResponseMovie> movieCall;
    Call<ResponseTv> tvCall;
    MovieResult movieResult;
    TvResult tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        movieList = new ArrayList<>();
        movieResult = new MovieResult();
        tvList = new ArrayList<>();
        tvResult = new TvResult();
        initView();
        if(getIntent() != null){
            if(getIntent().getStringExtra(INTENT_TAG).equals("search")){
                if(savedInstanceState!=null){
                    ArrayList<MovieResult> list;
                    list = savedInstanceState.getParcelableArrayList("now_movie");
                    movieAdapter.setMovieResult(list);
                    recyclerView.setAdapter(movieAdapter);
                }else{
                    String q = getIntent().getStringExtra(Constanta.INTENT_SEARCH);
                    getMovies(q);
                }
            }else if(getIntent().getStringExtra(INTENT_TAG).equals("search_tv")){
                if(savedInstanceState!=null){
                    ArrayList<TvResult> list;
                    list = savedInstanceState.getParcelableArrayList("now_tv");
                    tvAdapter.setTvResult(list);
                    recyclerView.setAdapter(tvAdapter);
                }else{
                    String q = getIntent().getStringExtra(Constanta.INTENT_SEARCH);
                    getTv(q);
                }
            }
        }
    }
    void initView(){
        columns = getResources().getInteger(R.integer.collumn_count);
        movieAdapter = new MovieAdapter(this);
        tvAdapter = new TvAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, columns));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    private void getMovies(final String q){
        movieService = RetrofitClient.getClient().create(BaseApiService.class);
        movieCall = movieService.getMovieBySearch(q, API_KEY);

        movieCall.enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                movieList = response.body().getResults();
                Log.v("Matt", "Number of movie with  = "+response.body().getTotalResults());
                getSupportActionBar().setSubtitle(getString(R.string.texthintresult, response.body()
                        .getTotalResults().toString(), q));

                movieAdapter.setMovieResult(movieList);
                recyclerView.setAdapter(movieAdapter);
            }
            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {
                Toast.makeText(ResultSearchActivity.this, "Something went wrong"
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getTv(final String q){
        movieService = RetrofitClient.getClient().create(BaseApiService.class);
        tvCall = movieService.getTvBySearch(q, API_KEY);

        tvCall.enqueue(new Callback<ResponseTv>() {
            @Override
            public void onResponse(@NotNull Call<ResponseTv> call, @NotNull Response<ResponseTv> response) {
                tvList = response.body().getResults();
                Log.v("Matt", "Number of tv with  = "+response.body().getTotalResults());
                if(getSupportActionBar()!=null){
                    getSupportActionBar().setSubtitle(getString(R.string.texthintresulttv, response.body()
                        .getTotalResults().toString(), q));
                }

                tvAdapter.setTvResult(tvList);
                recyclerView.setAdapter(tvAdapter);
            }
            @Override
            public void onFailure(@NotNull Call<ResponseTv> call, Throwable t) {
                Toast.makeText(ResultSearchActivity.this, "Something went wrong"
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(getSupportActionBar()!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setSubtitle(getString(R.string.texthintresult));
        }
        outState.putParcelableArrayList("now_movie", new ArrayList<>(movieAdapter.getList()));
        outState.putParcelableArrayList("now_tv", new ArrayList<>(tvAdapter.getList()));
    }
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            super.onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
