package com.example.popularmoviesstage1.Mvvm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.popularmoviesstage1.Models.Result;


@Database(entities = {Result.class}, version = 2)
public abstract class MovieDatabase extends RoomDatabase {
    private static MovieDatabase instance;

    public abstract MovieDao movieDao();

    static synchronized MovieDatabase getInstance(Context context) {

        if (instance == null) {


            instance = Room.databaseBuilder(context.getApplicationContext(),

                    MovieDatabase.class, "movie_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    ;


}
