package com.muhaiminur.shohozmovie.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.muhaiminur.shohozmovie.data.Genres;
import com.muhaiminur.shohozmovie.data.Movie;

@Database(entities = {Movie.class, Genres.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class SHOHOZ_DATABASE extends RoomDatabase {
    public abstract MovieDao movieDao();

    private static SHOHOZ_DATABASE INSTANCE;

    public static SHOHOZ_DATABASE getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SHOHOZ_DATABASE.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SHOHOZ_DATABASE.class, "shohoz_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}