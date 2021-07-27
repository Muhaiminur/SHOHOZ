package com.muhaiminur.shohozmovie.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.muhaiminur.shohozmovie.data.Genres;
import com.muhaiminur.shohozmovie.data.Movie;
import com.muhaiminur.shohozmovie.repository.MovieRepo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MovielistPageViewModel extends AndroidViewModel {
    private MovieRepo repo;
    private LiveData<List<Movie>> moData;
    private LiveData<List<Genres>> genData;

    public MovielistPageViewModel(@NonNull @NotNull Application application) {
        super(application);
        repo = new MovieRepo(application);
    }

    public void init() {
        moData = repo.getmovieData();
        genData = repo.getgenData();
    }

    public void getmovielistonline() {
        repo.get_movie_list();
    }

    public LiveData<List<Movie>> getmovieResponseLiveData() {
        return moData;
    }

    public LiveData<List<Genres>> getgenResponseLiveData() {
        return genData;
    }
}