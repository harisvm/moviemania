package com.example.popularmoviesstage1.Mvvm;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.popularmoviesstage1.Models.Result;

import java.util.List;

public class MovieRepository {

    private MovieDao movieDao;

    private LiveData<List<Result>> allFavorites;

    public MovieRepository(Application application) {

        MovieDatabase movieDatabase = MovieDatabase.getInstance(application);
        movieDao = movieDatabase.movieDao();
        allFavorites = movieDao.getFavorites();
    }

    public void insert(Result result) {

        new InsertMovieAsyncTask(movieDao).execute(result);
    }

    public void delete(Result result) {
        new DeleteMovieAsyncTask(movieDao).execute(result);


    }

    public LiveData<List<Result>> getAllFavorites() {

        return allFavorites;
    }



    private static class InsertMovieAsyncTask extends AsyncTask<Result, Void, Void> {

        private MovieDao movieDao;

        private InsertMovieAsyncTask(MovieDao movieDao) {

            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Result... results) {
            movieDao.add(results[0]);
            return null;
        }
    }





    private static class DeleteMovieAsyncTask extends AsyncTask<Result, Void, Void> {

        private MovieDao movieDao;

        private DeleteMovieAsyncTask(MovieDao movieDao) {

            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Result... results) {
            movieDao.deleteItem(results[0].getTitle());

            return null;
        }
    }
}
