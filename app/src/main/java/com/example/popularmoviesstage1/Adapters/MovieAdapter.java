package com.example.popularmoviesstage1.Adapters;//package com.example.popularmoviesstage1.Adapters;
//

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesstage1.Activities.MovieDetailActivity;
import com.example.popularmoviesstage1.Mvvm.Result;
import com.example.popularmoviesstage1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends
        RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    Context context;
    List<Result> movieModelList;

    public MovieAdapter(List<Result> movieModelList, Context context) {
        this.movieModelList = movieModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        String path = context.getString(R.string.image_url) + movieModelList.get(position).getPosterPath();
        Picasso.get().load(path).placeholder(R.drawable.ic_launcher_background)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(v -> {

            Intent intent = new Intent(context, MovieDetailActivity.class);

            intent.putExtra("movieobject", movieModelList.get(position));



            context.startActivity(intent);

        });


    }

    @Override
    public int getItemCount() {

        if (movieModelList!=null){
            return movieModelList.size();

        }

        else
            return 0;

    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}


