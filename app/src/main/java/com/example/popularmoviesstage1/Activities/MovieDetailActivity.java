package com.example.popularmoviesstage1.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmoviesstage1.Adapters.ReviewAdapter;
import com.example.popularmoviesstage1.Models.BaseResponseReview;
import com.example.popularmoviesstage1.Models.BaseResponseVideo;
import com.example.popularmoviesstage1.Mvvm.Result;
import com.example.popularmoviesstage1.Models.ReviewData;
import com.example.popularmoviesstage1.Mvvm.FavoriteViewModel;
import com.example.popularmoviesstage1.R;
import com.example.popularmoviesstage1.RetrofitAndApis.ApiInterface;
import com.example.popularmoviesstage1.RetrofitAndApis.RetroBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    TextView releaseDate, descriptiontextView, voteTextView;
    ImageView imageView;
    Button trailer;
    ApiInterface apiInterface;
    WebView webView;
    RecyclerView reviewRecycler;
    ReviewAdapter adapter;
    ImageButton imageButtonFavorite, imageButtonUnfavorite;
    FavoriteViewModel favoriteViewModel;
    SharedPreferences sharedpreferences;
    Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        releaseDate = findViewById(R.id.release_date);
        descriptiontextView = findViewById(R.id.description);
        imageView = findViewById(R.id.image_detail);
        voteTextView = findViewById(R.id.vote);
        trailer = findViewById(R.id.trailer_button);
        imageButtonFavorite = findViewById(R.id.favorite);
        imageButtonUnfavorite = findViewById(R.id.unfavorite);
        imageButtonUnfavorite.setVisibility(View.GONE);

        webView = findViewById(R.id.webview);
        webView.setVisibility(View.GONE);
        reviewRecycler = findViewById(R.id.reviews);
        apiInterface = RetroBuilder.getRetrofitInstance().create(ApiInterface.class);
        result = getIntent().getParcelableExtra("movieobject");
        favoriteViewModel = ViewModelProviders.of(MovieDetailActivity.this).get(FavoriteViewModel.class);

        sharedpreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);

        if (sharedpreferences.getInt(result.getId().toString(), 0) == result.getId()) {
            imageButtonFavorite.setVisibility(View.GONE);
            imageButtonUnfavorite.setVisibility(View.VISIBLE);

        }


        imageButtonFavorite.setOnClickListener(v -> {
            sharedpreferences.edit().putInt(result.getId().toString(), result.getId()).commit();
            favoriteViewModel.insert(result);
            imageButtonUnfavorite.setVisibility(View.VISIBLE);
            imageButtonFavorite.setVisibility(View.GONE);
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();


        });

        imageButtonUnfavorite.setOnClickListener(v -> {
            sharedpreferences.edit().putInt(result.getId().toString(), 1).apply();

            favoriteViewModel.delete(result);
            imageButtonUnfavorite.setVisibility(View.GONE);
            imageButtonFavorite.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();

        });
        favoriteViewModel.getAllFavorites().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {


            }
        });
        getReviews(result.getId());
        getSupportActionBar().setTitle(result.getTitle());


        Picasso.get().load(getString(R.string.image_url) + result.getPosterPath()).resize(700, 700).into(imageView);

        releaseDate.append(" : " + result.getReleaseDate());
        voteTextView.setText("Avg Rating : " + result.getVoteAverage());
        descriptiontextView.setText("Plot : " + result.getOverview());


        trailer.setOnClickListener(v -> {

            webView.setVisibility(View.VISIBLE);
            ProgressDialog mDialog = new ProgressDialog(MovieDetailActivity.this);
            mDialog.setMessage("Please Wait");
            mDialog.show();
            Call<BaseResponseVideo> call = apiInterface.getVideos(result.getId());
            call.enqueue(new Callback<BaseResponseVideo>() {
                @Override
                public void onResponse(Call<BaseResponseVideo> call, Response<BaseResponseVideo> response) {


                    if (response.isSuccessful()) {


                        try {

                            BaseResponseVideo baseResponseVideo = response.body();
                            String key = baseResponseVideo.getResults().get(0).getKey();
                            mDialog.dismiss();
                            webView.loadUrl("https://www.youtube.com/watch?v=" + key);


                        } catch (Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(MovieDetailActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    }
                }

                @Override
                public void onFailure(Call<BaseResponseVideo> call, Throwable t) {
                    mDialog.dismiss();

                }
            });


        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.setVisibility(View.GONE);
        if (sharedpreferences.getInt(result.getId().toString(), 0) == result.getId()) {
            imageButtonFavorite.setVisibility(View.GONE);
            imageButtonUnfavorite.setVisibility(View.VISIBLE);

        }


    }

    public void getReviews(int id) {
        Call<BaseResponseReview> call = apiInterface.getReviews(id);
        call.enqueue(new Callback<BaseResponseReview>() {
            @Override
            public void onResponse(Call<BaseResponseReview> call, Response<BaseResponseReview> response) {

                if (response.isSuccessful()) {
                    reviewRecycler.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this, RecyclerView.VERTICAL, false));


                    BaseResponseReview baseResponseReview = response.body();

                    List<ReviewData> reviewDataList = baseResponseReview.getResults();

                    adapter = new ReviewAdapter(reviewDataList, MovieDetailActivity.this);

                    reviewRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<BaseResponseReview> call, Throwable t) {

            }
        });


    }
}
