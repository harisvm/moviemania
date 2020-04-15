package com.example.popularmoviesstage1.Mvvm;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.popularmoviesstage1.Models.Result;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void add(Result result);

    @Query("DELETE FROM favorites WHERE title =:idd")
    void deleteItem(String idd);

    @Query("SELECT * FROM favorites ")
    LiveData<List<Result>> getFavorites();


}
