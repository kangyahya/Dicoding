package com.rtikcirebonkota.myfavorite.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MovieResult implements Parcelable {

    public static final Creator<MovieResult> CREATOR = new Creator<MovieResult>() {
        @Override
        public MovieResult createFromParcel(Parcel in) {
            return new MovieResult(in);
        }

        @Override
        public MovieResult[] newArray(int size) {
            return new MovieResult[size];
        }
    };
    @SerializedName("adult")
    private Boolean mAdult;
    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("genre_ids")
    private List<Long> mGenreIds;
    @SerializedName("id")
    private Long mId;
    @SerializedName("original_language")
    private String mOriginalLanguage;
    @SerializedName("original_title")
    private String mOriginalTitle;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("popularity")
    private Double mPopularity;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("video")
    private Boolean mVideo;
    @SerializedName("vote_average")
    private Double mVoteAverage;
    @SerializedName("vote_count")
    private Long mVoteCount;

    public MovieResult(Parcel in) {
        byte tmpMAdult = in.readByte();
        mAdult = tmpMAdult == 0 ? null : tmpMAdult == 1;
        mBackdropPath = in.readString();
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readLong();
        }
        mOriginalLanguage = in.readString();
        mOriginalTitle = in.readString();
        mOverview = in.readString();
        if (in.readByte() == 0) {
            mPopularity = null;
        } else {
            mPopularity = in.readDouble();
        }
        mPosterPath = in.readString();
        mReleaseDate = in.readString();
        mTitle = in.readString();
        byte tmpMVideo = in.readByte();
        mVideo = tmpMVideo == 0 ? null : tmpMVideo == 1;
        if (in.readByte() == 0) {
            mVoteAverage = null;
        } else {
            mVoteAverage = in.readDouble();
        }
        if (in.readByte() == 0) {
            mVoteCount = null;
        } else {
            mVoteCount = in.readLong();
        }
    }

    public MovieResult() {

    }

    public Boolean getAdult() {
        return mAdult;
    }

    public void setAdult(Boolean adult) {
        mAdult = adult;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public List<Long> getGenreIds() {
        return mGenreIds;
    }

    public void setGenreIds(List<Long> genreIds) {
        mGenreIds = genreIds;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(Double popularity) {
        mPopularity = popularity;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Boolean getVideo() {
        return mVideo;
    }

    public void setVideo(Boolean video) {
        mVideo = video;
    }

    public Double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        mVoteAverage = voteAverage;
    }

    public Long getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(Long voteCount) {
        mVoteCount = voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (mAdult == null ? 0 : mAdult ? 1 : 2));
        parcel.writeString(mBackdropPath);
        if (mId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(mId);
        }
        parcel.writeString(mOriginalLanguage);
        parcel.writeString(mOriginalTitle);
        parcel.writeString(mOverview);
        if (mPopularity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(mPopularity);
        }
        parcel.writeString(mPosterPath);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mTitle);
        parcel.writeByte((byte) (mVideo == null ? 0 : mVideo ? 1 : 2));
        if (mVoteAverage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(mVoteAverage);
        }
        if (mVoteCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(mVoteCount);
        }
    }
}
