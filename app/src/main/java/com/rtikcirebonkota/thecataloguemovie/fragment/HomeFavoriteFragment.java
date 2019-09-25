package com.rtikcirebonkota.thecataloguemovie.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.rtikcirebonkota.thecataloguemovie.R;
import com.rtikcirebonkota.thecataloguemovie.adapter.ViewPagerAdapter;
import com.rtikcirebonkota.thecataloguemovie.utils.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFavoriteFragment extends Fragment {
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_favorite, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this,view);
        setupViewPagar(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        return view;
    }
    private void setupViewPagar(ViewPager viewPager){
        ViewPagerAdapter adapter =new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FavoriteMovieFragment(),getString(R.string.movie));
        adapter.addFragment(new FavoriteTvFragment(),getString(R.string.tv));
        viewPager.setAdapter(adapter);
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu,menu);
        menu.findItem(R.id.search).setVisible(false);
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
