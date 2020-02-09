package com.rizky92.madedicodingsubmission2.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {

    private String title, desc, date, posterPath, language, popularity;
    private float voteAverage;
    private int voteCount, id, movieId;
    private boolean adult, favorite;

    public Movies() {
    }

    private Movies(Parcel in) {
        title = in.readString();
        desc = in.readString();
        date = in.readString();
        posterPath = in.readString();
        language = in.readString();
        popularity = in.readString();
        voteAverage = in.readFloat();
        voteCount = in.readInt();
        movieId = in.readInt();
        id = in.readInt();
        adult = in.readByte() != 0;
        favorite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(date);
        dest.writeString(posterPath);
        dest.writeString(language);
        dest.writeString(popularity);
        dest.writeFloat(voteAverage);
        dest.writeInt(voteCount);
        dest.writeInt(movieId);
        dest.writeInt(id);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeByte((byte) (favorite ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

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

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
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

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public boolean isFavorite() { return favorite; }

    public void setFavorite(boolean favorite) { this.favorite = favorite; }
}
