package com.rtikcirebonkota.thecataloguemovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.rtikcirebonkota.thecataloguemovie.utils.Constanta;
import com.rtikcirebonkota.thecataloguemovie.database.MyTMDB;
import com.rtikcirebonkota.thecataloguemovie.model.TvResult;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
public class DetailTvActivity extends AppCompatActivity {
    @BindView(R.id.detail_overview_tv)
    TextView tvOverview;
    @BindView(R.id.image_detail)
    ImageView backDrop;
    @BindView(R.id.item_date_detail)
    TextView tvDate;
    @BindView(R.id.item_title_detail)
    TextView tvTitle;
    @BindView(R.id.movie_poster_detail)
    ImageView poster;
    @BindView(R.id.item_rating_detail)
    TextView tvRating;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    String id, title, pposter,backdrop, overview, firstAiring;
    Long vote_count;
    Double popularity, vote_avg;
    TvResult tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tv = getIntent().getParcelableExtra(Constanta.DETAIL_TV);
        updateUI(tv);
        id = tv.getId().toString();
        title = tv.getName();
        pposter = tv.getPosterPath();
        backdrop = tv.getBackdropPath();
        overview = tv.getOverview();
        firstAiring = tv.getFirstAirDate();
        vote_avg = tv.getVoteAverage();
        vote_count = tv.getVoteCount();
        popularity = tv.getPopularity();
        if (isRecordExists(tv.getId().toString())) {
            if (floatingActionButton != null) {
                floatingActionButton.setImageResource(R.drawable.ic_favorite_black_24dp);
            }
        }
        if(floatingActionButton != null){
            floatingActionButton.setOnClickListener(v -> {
                if(!isRecordExists(tv.getId().toString())){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MyTMDB.TvColumns.IDTv, id);
                    contentValues.put(MyTMDB.TvColumns.TitleTv, title);
                    contentValues.put(MyTMDB.TvColumns.PosterTv, pposter);
                    contentValues.put(MyTMDB.TvColumns.BackdropTv, backdrop);
                    contentValues.put(MyTMDB.TvColumns.OverviewTv, overview);
                    contentValues.put(MyTMDB.TvColumns.FirstAiring, firstAiring);
                    contentValues.put(MyTMDB.TvColumns.AverageTv, vote_avg);
                    contentValues.put(MyTMDB.TvColumns.CountTv, vote_count);
                    contentValues.put(MyTMDB.TvColumns.PopularityTv, popularity);

                    getContentResolver().insert(MyTMDB.CONTENT_TV, contentValues);
                    floatingActionButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                    Snackbar.make(v, "This tv show has been add to your favorite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Uri uri = MyTMDB.CONTENT_TV;
                    uri = uri.buildUpon().appendPath(id).build();

                    getContentResolver().delete(uri, null, null);
                    floatingActionButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Snackbar.make(v, "This tv show has been remove from your favorite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    void updateUI(TvResult tv){
        getSupportActionBar().setTitle(tv.getName());

        Glide.with(this)
                .load(Constanta.URL_BACKDROP+tv.getBackdropPath())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(backDrop);
        Glide.with(this)
                .load(Constanta.URL_POSTER+tv.getPosterPath())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(poster);

        tvTitle.setText(tv.getName());
        tvOverview.setText(tv.getOverview());
        tvDate.setText(getResources().getString(R.string.airing,
                Constanta.getDateDay(tv.getFirstAirDate())));
        tvRating.setText(getResources().getString(R.string.rating,tv.getVoteAverage().toString()));
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            super.onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean isRecordExists(String id) {
        String selection = " tv_id = ?";
        String[] selectionArgs = { id };
        String[] projection = {MyTMDB.TvColumns.IDTv};
        Uri uri = MyTMDB.CONTENT_TV;
        uri = uri.buildUpon().appendPath(id).build();
        Cursor cursor = getContentResolver().query(uri, projection ,
                selection, selectionArgs, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}
