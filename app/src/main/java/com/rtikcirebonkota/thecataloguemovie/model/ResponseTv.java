
package com.rtikcirebonkota.thecataloguemovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@SuppressWarnings("unused")
public class ResponseTv implements Parcelable {
    @SerializedName("page")
    @Expose
    private Long mPage;
    @SerializedName("results")
    @Expose
    private List<TvResult> mTvResults;
    @SerializedName("total_pages")
    @Expose
    private Long mTotalPages;
    @SerializedName("total_results")
    @Expose
    private Long mTotalResults;

    protected ResponseTv(Parcel in) {
        if (in.readByte() == 0) {
            mPage = null;
        } else {
            mPage = in.readLong();
        }
        mTvResults = in.createTypedArrayList(TvResult.CREATOR);
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
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mPage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mPage);
        }
        dest.writeTypedList(mTvResults);
        if (mTotalPages == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mTotalPages);
        }
        if (mTotalResults == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mTotalResults);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResponseTv> CREATOR = new Creator<ResponseTv>() {
        @Override
        public ResponseTv createFromParcel(Parcel in) {
            return new ResponseTv(in);
        }

        @Override
        public ResponseTv[] newArray(int size) {
            return new ResponseTv[size];
        }
    };

    public Long getPage() {
        return mPage;
    }

    public void setPage(Long page) {
        mPage = page;
    }

    public List<TvResult> getResults() {
        return mTvResults;
    }

    public void setResults(List<TvResult> results) {
        mTvResults = results;
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

}
