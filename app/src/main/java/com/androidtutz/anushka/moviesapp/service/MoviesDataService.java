package com.androidtutz.anushka.moviesapp.service;

import com.androidtutz.anushka.moviesapp.model.MovieDBResponse;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by K. A. ANUSHKA MADUSANKA on 7/4/2018.
 */
public interface MoviesDataService {

    @GET("movie/popular")
    Call<MovieDBResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Observable<MovieDBResponse> getPopularMoviesUsingRx(@Query("api_key") String apiKey);
}
