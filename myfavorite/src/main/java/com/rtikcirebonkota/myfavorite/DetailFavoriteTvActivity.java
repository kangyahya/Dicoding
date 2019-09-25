package com.rtikcirebonkota.myfavorite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.rtikcirebonkota.myfavorite.adapter.FavoriteTvAdapter;
import com.rtikcirebonkota.myfavorite.database.MyTMDB;
import com.rtikcirebonkota.myfavorite.model.TvResult;
import com.rtikcirebonkota.myfavorite.utils.Constanta;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFavoriteTvActivity extends AppCompatActivity {
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
    String id, title, pposter, backdrop, overview, release, vote_avg, vote_count, popularity;
    TvResult tv;
    FavoriteTvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorite_movie);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tv = getIntent().getParcelableExtra(Constanta.DETAIL_TV);
        updateUI(tv);
        id = tv.getId().toString();
        title = tv.getName();
        pposter = tv.getPosterPath();
        backdrop = tv.getBackdropPath();
        overview = tv.getOverview();
        release = tv.getFirstAirDate();
        vote_avg = tv.getVoteAverage().toString();
        vote_count = tv.getVoteCount().toString();
        popularity = tv.getPopularity().toString();
        if (isRecordExists(id)) {
            if (floatingActionButton != null) {
                floatingActionButton.setImageResource(R.drawable.ic_favorite_black_24dp);
            }
        }

        if (floatingActionButton != null) {
            floatingActionButton.setOnClickListener(v -> {
                if (!isRecordExists(id)) {
                    ContentValues contentValues = new ContentValues();
                    // Put the task description and selected mPriority into the ContentValues
                    contentValues.put(MyTMDB.TvColumns.idTv, id);
                    contentValues.put(MyTMDB.TvColumns.titleTv, title);
                    contentValues.put(MyTMDB.TvColumns.posterTv, pposter);
                    contentValues.put(MyTMDB.TvColumns.backdropTv, backdrop);
                    contentValues.put(MyTMDB.TvColumns.overviewTv, overview);
                    contentValues.put(MyTMDB.TvColumns.firstAiring, release);
                    contentValues.put(MyTMDB.TvColumns.averageTv, vote_avg);
                    contentValues.put(MyTMDB.TvColumns.countTv, vote_count);
                    contentValues.put(MyTMDB.TvColumns.popularityTv, popularity);
                    // Insert the content values via a ContentResolver
                    getContentResolver().insert(MyTMDB.CONTENT_TV, contentValues);
                    floatingActionButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                    Snackbar.make(v, getString(R.string.has_been_add), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Uri uri = MyTMDB.CONTENT_TV;
                    uri = uri.buildUpon().appendPath(id).build();
                    getContentResolver().delete(uri, null, null);
                    floatingActionButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Snackbar.make(v, getString(R.string.has_been_remove), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    void updateUI(TvResult tvResult) {
        getSupportActionBar().setTitle(tvResult.getName());
        Glide.with(this)
                .load(Constanta.URL_POSTER + tvResult.getPosterPath())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(poster);
        Glide.with(this)
                .load(Constanta.URL_BACKDROP + tvResult.getBackdropPath())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(backDrop);
        tvTitle.setText(tvResult.getName());
        tvOverview.setText(tvResult.getOverview());
        tvRating.setText(getResources().getString(R.string.rating, tvResult.getVoteAverage().toString()));
        tvDate.setText(getResources().getString(R.string.airing_date,
                Constanta.getDateDay(tvResult.getFirstAirDate())));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isRecordExists(String id) {
        String selection = " id_tv = ?";
        String[] selectionArgs = {id};
        String[] projection = {MyTMDB.TvColumns.idTv};
        Uri uri = MyTMDB.CONTENT_TV;
        uri = uri.buildUpon().appendPath(id).build();
        Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}
