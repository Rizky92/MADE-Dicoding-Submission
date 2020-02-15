package com.rizky92.madedicodingsubmission2.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.rizky92.madedicodingsubmission2.pojo.Movies;

import java.util.ArrayList;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies_table ORDER BY id ASC")
    LiveData<ArrayList<Movies>> getMovies();

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Movies movies);

    @Update
    void update(Movies movies);

    @Delete
    void delete(Movies movies);
}
