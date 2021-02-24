package com.androidtutz.anushka.moviesapp.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.androidtutz.anushka.moviesapp.R;
import com.androidtutz.anushka.moviesapp.service.MoviesDataService;
import com.androidtutz.anushka.moviesapp.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieRepository {

    private Application application;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MutableLiveData<List<Movie>> listMutableLiveData = new MutableLiveData<>();
    private ArrayList<Movie> movies;
    private Observable movieDBResponseObservable;

    public MovieRepository(Application application) {
        this.application = application;

        movies = new ArrayList<>();
        MoviesDataService getMoviesDataService = RetrofitInstance.getService();
        //declare an observable to get movies
        movieDBResponseObservable = getMoviesDataService.getPopularMoviesUsingRx(application.getApplicationContext().getString(R.string.api_key));
        //lets add a observer to this method
        movieDBResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<MovieDBResponse, Observable<Movie>>) movieDBResponse -> Observable.fromArray(movieDBResponse.getMovies().toArray(new Movie[0])))
                .subscribeWith(new DisposableObserver<Movie>() {
                    @Override
                    public void onNext(@NonNull Movie movie) {
                        movies.add(movie);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        listMutableLiveData.postValue(movies);
                    }
                });
    }

    public MutableLiveData<List<Movie>> getListMutableLiveData() {
        return listMutableLiveData;
    }
}
