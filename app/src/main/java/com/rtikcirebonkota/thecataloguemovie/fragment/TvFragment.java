package com.rtikcirebonkota.thecataloguemovie.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rtikcirebonkota.thecataloguemovie.R;
import com.rtikcirebonkota.thecataloguemovie.ResultSearchActivity;
import com.rtikcirebonkota.thecataloguemovie.adapter.TvAdapter;
import com.rtikcirebonkota.thecataloguemovie.api.BaseApiService;
import com.rtikcirebonkota.thecataloguemovie.model.ResponseTv;
import com.rtikcirebonkota.thecataloguemovie.model.TvResult;
import com.rtikcirebonkota.thecataloguemovie.api.RetrofitClient;
import com.rtikcirebonkota.thecataloguemovie.utils.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rtikcirebonkota.thecataloguemovie.utils.Constanta.API_KEY;
import static com.rtikcirebonkota.thecataloguemovie.utils.Constanta.INTENT_SEARCH;
import static com.rtikcirebonkota.thecataloguemovie.utils.Constanta.INTENT_TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {
    @BindView(R.id.rv_tv)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    private List<TvResult> tvList;
    private TvAdapter tvAdapter;

    public void onAttach(Context context){
        super.onAttach(context);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        tvList = new ArrayList<>();
        initView();
        if(savedInstanceState!=null){
            ArrayList<TvResult> list;
            list = savedInstanceState.getParcelableArrayList("tv");
            tvAdapter.setTvResult(list);
            recyclerView.setAdapter(tvAdapter);
        }else{
            getTv();
        }

        return view;
    }
    private void initView(){
        int columns = getResources().getInteger(R.integer.collumn_count);
        tvAdapter = new TvAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columns));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    private void getTv(){
        showPbar();
        BaseApiService tvService = RetrofitClient.getClient().create(BaseApiService.class);
        Call<ResponseTv> tvCall = tvService.getTv(API_KEY);

        tvCall.enqueue(new Callback<ResponseTv>() {
            @Override
            public void onResponse(Call<ResponseTv> call, Response<ResponseTv> response) {
                    tvList = response.body().getResults();
                    tvAdapter.setTvResult(tvList);
                    recyclerView.setAdapter(tvAdapter);
                hidePbar();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTv> call, @NonNull Throwable t) {
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
        outState.putParcelableArrayList("tv", new ArrayList<>(tvAdapter.getList()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            ArrayList<TvResult> list;
            list = savedInstanceState.getParcelableArrayList("tv");
            tvAdapter.setTvResult(list);
            recyclerView.setAdapter(tvAdapter);
        }
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu,menu);
        menu.findItem(R.id.search).setVisible(true);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.clearFocus();
        searchView.setQueryHint(getString(R.string.search_tv));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getContext(), ResultSearchActivity.class);
                intent.putExtra(INTENT_SEARCH, query);
                intent.putExtra(INTENT_TAG, "search_tv");
                startActivity(intent);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int get = item.getItemId();
        if(get==R.id.action_settings){
            Intent mIntent = new Intent(getContext(), SettingsActivity.class);
            startActivity(mIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
