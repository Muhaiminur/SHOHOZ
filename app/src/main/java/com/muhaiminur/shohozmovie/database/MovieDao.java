package com.muhaiminur.shohozmovie.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.muhaiminur.shohozmovie.data.Genres;
import com.muhaiminur.shohozmovie.data.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertmovie(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertallmovie(List<Movie> movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertallgeneres(List<Genres> s);

    @Update
    void updatemovie(Movie movie);

    @Delete
    void deletemovie(Movie m);

    @Query("DELETE FROM movie_table WHERE movieid = :b_id")
    void deletemovie_single(String b_id);

    @Query("DELETE FROM movie_table")
    void deleteAllmovie();

    @Query("SELECT * from movie_table ORDER BY movieid ASC")
    LiveData<List<Movie>> getAllmovie();

    @Query("SELECT * from genres_table ORDER BY gen_name ASC")
    LiveData<List<Genres>> getAllgenres();

    @Query("SELECT * FROM movie_table WHERE movieid =:c")
    Movie findmovie(String c);

    @Query("SELECT * FROM movie_table")
    Movie movie_profile();
}