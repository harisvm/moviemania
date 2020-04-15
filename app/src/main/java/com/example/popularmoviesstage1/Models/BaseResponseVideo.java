package com.example.popularmoviesstage1.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseResponseVideo {
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<VideoData> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<VideoData> getResults() {
        return results;
    }

    public void setResults(List<VideoData> results) {
        this.results = results;
    }
}
