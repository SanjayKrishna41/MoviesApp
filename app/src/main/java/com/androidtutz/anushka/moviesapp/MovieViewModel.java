package com.androidtutz.anushka.moviesapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.androidtutz.anushka.moviesapp.model.Movie;
import com.androidtutz.anushka.moviesapp.model.MovieRepository;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);

    }

    public LiveData<List<Movie>> getAllMovies(){
        return  movieRepository.getListMutableLiveData();
    }
}
