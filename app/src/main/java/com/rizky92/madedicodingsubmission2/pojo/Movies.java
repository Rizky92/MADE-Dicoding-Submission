package com.rizky92.madedicodingsubmission2.pojo;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
    private String title, desc, date, posterPath, language, popularity;
    private double voteAverage;
    private int voteCount, movieId;
    private boolean adult;

    public Movies() {
    }

    private Movies(Parcel in) {
        title = in.readString();
        desc = in.readString();
        date = in.readString();
        posterPath = in.readString();
        language = in.readString();
        popularity = in.readString();
        voteAverage = in.readDouble();
        voteCount = in.readInt();
        movieId = in.readInt();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            adult = in.readBoolean();
        }
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
        parcel.writeInt(movieId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            parcel.writeBoolean(adult);
        }
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

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }
}
