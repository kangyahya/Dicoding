package com.rtikcirebonkota.thecataloguemovie;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rtikcirebonkota.thecataloguemovie.fragment.HomeFavoriteFragment;
import com.rtikcirebonkota.thecataloguemovie.fragment.MovieHomeFragment;
import com.rtikcirebonkota.thecataloguemovie.fragment.TvFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.navigation)
    BottomNavigationView botNavline;

    Fragment fragment;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        botNavline.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.navigation_movie:
                    fragment = new MovieHomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.viewpager,fragment,fragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_tv:
                    fragment = new TvFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.viewpager,fragment,fragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_favorite:
                    fragment = new HomeFavoriteFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.viewpager,fragment,fragment.getClass().getSimpleName()).commit();
                    return true;
            }

            return false;
        });
        if(savedInstanceState==null){
            botNavline.setSelectedItemId(R.id.navigation_movie);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int get = item.getItemId();
        if(get==R.id.exit){
            showDialog();
            return true;
        }else if(get==R.id.about){
            showAbout();
        }
        return super.onOptionsItemSelected(item);
    }
    public void showDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.exit));
        alertDialog.setIcon(R.drawable.logo_tmdb).setCancelable(false)
                .setPositiveButton("Yes", (dialogInterface, i) -> MainActivity.this.finish()).setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel());
        alertDialog.show();
    }
    public void showAbout(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("About");
        alertDialog.setIcon(R.drawable.logo_tmdb).setMessage("This application is the last submission in MADE class").setNeutralButton("OK", (dialogInterface, i) -> dialogInterface.cancel());
        alertDialog.show();
    }
}
