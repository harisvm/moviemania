package com.example.popularmoviesstage1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.popularmoviesstage1.Adapters.MovieAdapter;
import com.example.popularmoviesstage1.Models.BaseResponse;
import com.example.popularmoviesstage1.Mvvm.Result;
import com.example.popularmoviesstage1.Mvvm.FavoriteViewModel;
import com.example.popularmoviesstage1.R;
import com.example.popularmoviesstage1.RetrofitAndApis.ApiInterface;
import com.example.popularmoviesstage1.RetrofitAndApis.RetroBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    MovieAdapter adapter;
    List<Result> movieList;
    ApiInterface retroInterface;
    private FavoriteViewModel favoriteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retroInterface = RetroBuilder.getRetrofitInstance()
                .create(ApiInterface.class);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);

        recyclerView = findViewById(R.id.movie_list);
        Call<BaseResponse> call = retroInterface.getPopularMovies();

        getMovieDetails(call);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {


            case R.id.item1:


                Call<BaseResponse> call = retroInterface.getPopularMovies();
                getMovieDetails(call);
                Toast.makeText(this, "Most Popular", Toast.LENGTH_SHORT).show();

                return true;

            case R.id.item2:


                Call<BaseResponse> call2 = retroInterface.getHighestRated();
                getMovieDetails(call2);
                Toast.makeText(this, "Highest Rated", Toast.LENGTH_SHORT).show();

                return true;


            case R.id.item3:


                favoriteViewModel.getAllFavorites().observe(this, new Observer<List<Result>>() {
                    @Override
                    public void onChanged(List<Result> results) {
                        adapter = new MovieAdapter(results, MainActivity.this);
                        gridLayoutManager = new GridLayoutManager(MainActivity.this, 3);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.setAdapter(adapter);

                    }
                });

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void getMovieDetails(Call call) {


        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call,
                                   Response<BaseResponse> response) {

                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    movieList = baseResponse.getResults();

                    adapter = new MovieAdapter(movieList, MainActivity.this);
                    gridLayoutManager = new GridLayoutManager(MainActivity.this, 3);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

    }
}
