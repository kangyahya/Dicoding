
package com.rtikcirebonkota.thecataloguemovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ResponseMovie implements Parcelable {

    @SerializedName("page")
    private Long mPage;
    @SerializedName("results")
    private List<MovieResult> mMovieResults;
    @SerializedName("total_pages")
    private Long mTotalPages;
    @SerializedName("total_results")
    private Long mTotalResults;

    protected ResponseMovie(Parcel in) {
        if (in.readByte() == 0) {
            mPage = null;
        } else {
            mPage = in.readLong();
        }
        mMovieResults = in.createTypedArrayList(MovieResult.CREATOR);
        if (in.readByte() == 0) {
            mTotalPages = null;
        } else {
            mTotalPages = in.readLong();
        }
        if (in.readByte() == 0) {
            mTotalResults = null;
        } else {
            mTotalResults = in.readLong();
        }
    }

    public static final Creator<ResponseMovie> CREATOR = new Creator<ResponseMovie>() {
        @Override
        public ResponseMovie createFromParcel(Parcel in) {
            return new ResponseMovie(in);
        }

        @Override
        public ResponseMovie[] newArray(int size) {
            return new ResponseMovie[size];
        }
    };

    public Long getPage() {
        return mPage;
    }

    public void setPage(Long page) {
        mPage = page;
    }

    public List<MovieResult> getResults() {
        return mMovieResults;
    }

    public void setResults(List<MovieResult> movieResults) {
        mMovieResults = movieResults;
    }

    public Long getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(Long totalPages) {
        mTotalPages = totalPages;
    }

    public Long getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(Long totalResults) {
        mTotalResults = totalResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (mPage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(mPage);
        }
            parcel.writeTypedList(mMovieResults);
        if (mTotalPages == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(mTotalPages);
        }
        if (mTotalResults == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(mTotalResults);
        }
    }
}