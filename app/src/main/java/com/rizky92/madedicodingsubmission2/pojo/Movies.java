package com.rizky92.madedicodingsubmission2.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "movies_table")
public class Movies implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "movie_id")
    private int movieId;

    @NonNull
    private String title;

    @NonNull
    @ColumnInfo(name = "description")
    private String desc;

    @NonNull
    private String date;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @NonNull
    private String language;

    @NonNull
    private String popularity;

    @NonNull
    @ColumnInfo(name = "vote_avg")
    private float voteAverage;

    @NonNull
    @ColumnInfo(name = "vote_count")
    private int voteCount;

    @NonNull
    @ColumnInfo(name = "isAdult")
    private boolean adult;

    public Movies() {
    }

    private Movies(Parcel in) {
        title = Objects.requireNonNull(in.readString());
        desc = Objects.requireNonNull(in.readString());
        date = Objects.requireNonNull(in.readString());
        posterPath = Objects.requireNonNull(in.readString());
        language = Objects.requireNonNull(in.readString());
        popularity = Objects.requireNonNull(in.readString());
        voteAverage = in.readFloat();
        voteCount = in.readInt();
        id = in.readInt();
        movieId = in.readInt();
        adult = in.readByte() != 0;
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
        dest.writeInt(id);
        dest.writeInt(movieId);
        dest.writeByte((byte) (adult ? 1 : 0));
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

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDesc() {
        return desc;
    }

    public void setDesc(@NonNull String desc) {
        this.desc = desc;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @NonNull
    public String getLanguage() {
        return language;
    }

    public void setLanguage(@NonNull String language) {
        this.language = language;
    }

    @NonNull
    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(@NonNull String popularity) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
