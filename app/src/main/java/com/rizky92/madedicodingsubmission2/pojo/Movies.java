package com.rizky92.madedicodingsubmission2.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {

    private String title, desc, date, length, rating, trailer, genre, homepage, posterPath;
    private int poster;

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

    private Movies(Parcel in) {
        title = in.readString();
        desc = in.readString();
        date = in.readString();
        length = in.readString();
        rating = in.readString();
        trailer = in.readString();
        poster = in.readInt();
        genre = in.readString();
        homepage = in.readString();
        posterPath = in.readString();
    }

    public Movies() {
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
        parcel.writeString(length);
        parcel.writeString(rating);
        parcel.writeString(trailer);
        parcel.writeInt(poster);
        parcel.writeString(genre);
        parcel.writeString(homepage);
        parcel.writeString(posterPath);
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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPoster_path(String posterPath) {
        this.posterPath = posterPath;
    }
}
