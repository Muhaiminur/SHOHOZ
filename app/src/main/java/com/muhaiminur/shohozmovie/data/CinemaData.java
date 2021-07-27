package com.muhaiminur.shohozmovie.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CinemaData {
    @SerializedName("genres")
    @Expose
    private List<String> genres = null;
    @SerializedName("movies")
    @Expose
    private List<Movie> movies = null;

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}