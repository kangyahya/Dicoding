package com.rtikcirebonkota.myfavorite;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.navigation)
    BottomNavigationView botNavline;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.navigation_movie:
                fragment = new FavoriteMovieFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.viewpager, fragment, fragment.getClass().getSimpleName()).commit();
                return true;
            case R.id.navigation_tv:
                fragment = new FavoriteTvFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.viewpager, fragment, fragment.getClass().getSimpleName()).commit();
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        botNavline.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            botNavline.setSelectedItemId(R.id.navigation_movie);
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_exit) {
            showDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.dialog_exit));
        alertDialog.setIcon(R.drawable.logo_tmdb).setCancelable(false)
                .setPositiveButton(getString(R.string.yes_), (dialogInterface, i) -> MainActivity.this.finish()).setNegativeButton(getString(R.string.no_), (dialogInterface, i) -> dialogInterface.cancel());
        alertDialog.show();
    }


}
