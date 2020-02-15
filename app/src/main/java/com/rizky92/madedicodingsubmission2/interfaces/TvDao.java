package com.rizky92.madedicodingsubmission2.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.rizky92.madedicodingsubmission2.pojo.Tvs;

import java.util.ArrayList;

@Dao
public interface TvDao {

    @Query("SELECT * FROM tvs_table ORDER BY id ASC")
    LiveData<ArrayList<Tvs>> getTvs();

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Tvs tvs);

    @Update
    void update(Tvs tvs);

    @Delete
    void delete(Tvs tvs);
}
