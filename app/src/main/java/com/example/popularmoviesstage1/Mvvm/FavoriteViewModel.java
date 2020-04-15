package com.example.popularmoviesstage1.Mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.popularmoviesstage1.Models.Result;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;
    private LiveData<List<Result>> allFavorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);

        movieRepository = new MovieRepository(application);
        allFavorites = movieRepository.getAllFavorites();
    }

    public void insert(Result result) {


        movieRepository.insert(result);
    }

    public void delete(Result result) {


        movieRepository.delete(result);
    }

    public LiveData<List<Result>> getAllFavorites() {


        return allFavorites;
    }



}
