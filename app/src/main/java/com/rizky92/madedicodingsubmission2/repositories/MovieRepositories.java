package com.rizky92.madedicodingsubmission2.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.rizky92.madedicodingsubmission2.interfaces.MovieDao;
import com.rizky92.madedicodingsubmission2.pojo.Movies;

import java.util.ArrayList;

public class MovieRepositories {

    private MovieDao mMovieDao;
    private LiveData<ArrayList<Movies>> allMovies;

    MovieRepositories(Application application) {

    }
}
