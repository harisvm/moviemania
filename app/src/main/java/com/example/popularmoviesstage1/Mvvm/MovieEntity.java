package com.example.popularmoviesstage1.Mvvm;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class MovieEntity {


    @PrimaryKey(autoGenerate = true)
    int id;

    int movieId;
    String title;
    String path;

    public MovieEntity(int movieId, String title, String path) {
        this.movieId = movieId;
        this.title = title;
        this.path = path;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
