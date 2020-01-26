package com.rizky92.madedicodingsubmission2.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Tvs implements Parcelable {

    public static final Creator<Tvs> CREATOR = new Creator<Tvs>() {
        @Override
        public Tvs createFromParcel(Parcel in) {
            return new Tvs(in);
        }

        @Override
        public Tvs[] newArray(int size) {
            return new Tvs[size];
        }
    };
    private String title, desc, date, posterPath, language, popularity;
    private double voteAverage;
    private int voteCount, tvId;

    public Tvs() {
    }

    private Tvs(Parcel in) {
        title = in.readString();
        desc = in.readString();
        date = in.readString();
        posterPath = in.readString();
        language = in.readString();
        popularity = in.readString();
        voteAverage = in.readDouble();
        voteCount = in.readInt();
        tvId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(desc);
        parcel.writeString(date);
        parcel.writeString(posterPath);
        parcel.writeString(language);
        parcel.writeString(popularity);
        parcel.writeDouble(voteAverage);
        parcel.writeInt(voteCount);
        parcel.writeInt(tvId);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getTvId() {
        return tvId;
    }

    public void setTvId(int tvId) {
        this.tvId = tvId;
    }
}
