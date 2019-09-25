package com.rtikcirebonkota.myfavorite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.rtikcirebonkota.myfavorite.adapter.FavoriteMovieAdapter;
import com.rtikcirebonkota.myfavorite.database.MyTMDB;
import com.rtikcirebonkota.myfavorite.model.MovieResult;
import com.rtikcirebonkota.myfavorite.utils.Constanta;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFavoriteMovieActivity extends AppCompatActivity {
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
    MovieResult movie;
    FavoriteMovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorite_movie);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        movie = getIntent().getParcelableExtra(Constanta.DETAIL_MOVIE);
        updateUI(movie);
        id = movie.getId().toString();
        title = movie.getTitle();
        pposter = movie.getPosterPath();
        backdrop = movie.getBackdropPath();
        overview = movie.getOverview();
        release = movie.getReleaseDate();
        vote_avg = movie.getVoteAverage().toString();
        vote_count = movie.getVoteCount().toString();
        popularity = movie.getPopularity().toString();
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
                    contentValues.put(MyTMDB.MovieColumns.idMovie, id);
                    contentValues.put(MyTMDB.MovieColumns.titleMovie, title);
                    contentValues.put(MyTMDB.MovieColumns.posterMovie, pposter);
                    contentValues.put(MyTMDB.MovieColumns.backdropMovie, backdrop);
                    contentValues.put(MyTMDB.MovieColumns.overviewMovie, overview);
                    contentValues.put(MyTMDB.MovieColumns.releaseMovie, release);
                    contentValues.put(MyTMDB.MovieColumns.averageMovie, vote_avg);
                    contentValues.put(MyTMDB.MovieColumns.countMovie, vote_count);
                    contentValues.put(MyTMDB.MovieColumns.popularityMovie, popularity);
                    // Insert the content values via a ContentResolver
                    getContentResolver().insert(MyTMDB.CONTENT_URI, contentValues);
                    floatingActionButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                    Snackbar.make(v, "This movie has been add to your favorite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    adapter = new FavoriteMovieAdapter(getBaseContext());
                    adapter.notifyDataSetChanged();
                } else {
                    Uri uri = MyTMDB.CONTENT_URI;
                    uri = uri.buildUpon().appendPath(id).build();
                    getContentResolver().delete(uri, null, null);
                    floatingActionButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Snackbar.make(v, "This movie has been remove from your favorite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    void updateUI(MovieResult movie) {
        getSupportActionBar().setTitle(movie.getTitle());
        Glide.with(this)
                .load(Constanta.URL_POSTER + movie.getPosterPath())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(poster);
        Glide.with(this)
                .load(Constanta.URL_BACKDROP + movie.getBackdropPath())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(backDrop);
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvRating.setText(getResources().getString(R.string.rating, movie.getVoteAverage().toString()));
        tvDate.setText(getResources().getString(R.string.release_date,
                Constanta.getDateDay(movie.getReleaseDate())));

    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isRecordExists(String id) {
        String selection = " id_movie = ?";
        String[] selectionArgs = {id};
        String[] projection = {MyTMDB.MovieColumns.idMovie};
        Uri uri = MyTMDB.CONTENT_URI;
        uri = uri.buildUpon().appendPath(id).build();
        Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}
