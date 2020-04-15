package com.example.popularmoviesstage1.RetrofitAndApis;


import com.example.popularmoviesstage1.Models.BaseResponse;
import com.example.popularmoviesstage1.Models.BaseResponseReview;
import com.example.popularmoviesstage1.Models.BaseResponseVideo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("movie/popular?api_key=96f554c3bc2d93fa042b548ff694053c")
    Call<BaseResponse> getPopularMovies();

    @GET("movie/top_rated?api_key=96f554c3bc2d93fa042b548ff694053c")
    Call<BaseResponse> getHighestRated();

    @GET("movie/{id}/videos?api_key=96f554c3bc2d93fa042b548ff694053c")
    Call<BaseResponseVideo> getVideos(@Path("id") long id);

    @GET("movie/{id}/reviews?api_key=96f554c3bc2d93fa042b548ff694053c")
    Call<BaseResponseReview> getReviews(@Path("id") long id);

}
