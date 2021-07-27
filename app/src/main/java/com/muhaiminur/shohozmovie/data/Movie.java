package com.muhaiminur.shohozmovie.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "movie_table")
public class Movie {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieid")
    @SerializedName("id")
    @Expose
    private Integer id;
    @NonNull
    @ColumnInfo(name = "movietittle")
    @SerializedName("title")
    @Expose
    private String title;
    @ColumnInfo(name = "year")
    @SerializedName("year")
    @Expose
    private String year;
    @ColumnInfo(name = "runtime")
    @SerializedName("runtime")
    @Expose
    private String runtime;
    @ColumnInfo(name = "genreslist")
    @SerializedName("genres")
    @Expose
    private List<String> genres = null;
    @ColumnInfo(name = "director")
    @SerializedName("director")
    @Expose
    private String director;
    @ColumnInfo(name = "actors")
    @SerializedName("actors")
    @Expose
    private String actors;
    @ColumnInfo(name = "plot")
    @SerializedName("plot")
    @Expose
    private String plot;
    @ColumnInfo(name = "posturl")
    @SerializedName("posterUrl")
    @Expose
    private String posterUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", runtime='" + runtime + '\'' +
                ", genres=" + genres +
                ", director='" + director + '\'' +
                ", actors='" + actors + '\'' +
                ", plot='" + plot + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                '}';
    }
}