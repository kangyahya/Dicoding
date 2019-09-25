
package com.rtikcirebonkota.thecataloguemovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class TvResult implements Parcelable {

    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("first_air_date")
    private String mFirstAirDate;
    @SerializedName("genre_ids")
    private List<Long> mGenreIds;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("origin_country")
    private List<String> mOriginCountry;
    @SerializedName("original_language")
    private String mOriginalLanguage;
    @SerializedName("original_name")
    private String mOriginalName;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("popularity")
    private Double mPopularity;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("vote_average")
    private Double mVoteAverage;
    @SerializedName("vote_count")
    private Long mVoteCount;

    public TvResult(Parcel in) {
        mBackdropPath = in.readString();
        mFirstAirDate = in.readString();
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readLong();
        }
        mName = in.readString();
        mOriginCountry = in.createStringArrayList();
        mOriginalLanguage = in.readString();
        mOriginalName = in.readString();
        mOverview = in.readString();
        if (in.readByte() == 0) {
            mPopularity = null;
        } else {
            mPopularity = in.readDouble();
        }
        mPosterPath = in.readString();
        if (in.readFloat() == 0) {
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

    public static final Creator<TvResult> CREATOR = new Creator<TvResult>() {
        @Override
        public TvResult createFromParcel(Parcel in) {
            return new TvResult(in);
        }

        @Override
        public TvResult[] newArray(int size) {
            return new TvResult[size];
        }
    };

    public TvResult() {

    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public String getFirstAirDate() {
        return mFirstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        mFirstAirDate = firstAirDate;
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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<String> getOriginCountry() {
        return mOriginCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        mOriginCountry = originCountry;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    public String getOriginalName() {
        return mOriginalName;
    }

    public void setOriginalName(String originalName) {
        mOriginalName = originalName;
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
        parcel.writeString(mBackdropPath);
        parcel.writeString(mFirstAirDate);
        if (mId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(mId);
        }
        parcel.writeString(mName);
        parcel.writeStringList(mOriginCountry);
        parcel.writeString(mOriginalLanguage);
        parcel.writeString(mOriginalName);
        parcel.writeString(mOverview);
        if (mPopularity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(mPopularity);
        }
        parcel.writeString(mPosterPath);
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
