package com.muhaiminur.shohozmovie.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "genres_table")
public class Genres {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "gen_name")
    String gen_name;


    public Genres() {
    }

    public Genres(@NonNull String gen_name) {
        this.gen_name = gen_name;
    }

    public String getGen_name() {
        return gen_name;
    }

    public void setGen_name(String gen_name) {
        this.gen_name = gen_name;
    }

    @Override
    public String toString() {
        return gen_name;
    }
}
