package com.example.popularmoviesstage1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesstage1.Models.ReviewData;
import com.example.popularmoviesstage1.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    List<ReviewData> reviewDataList;
    Context context;

    public ReviewAdapter(List<ReviewData> reviewDataList, Context context) {
        this.reviewDataList = reviewDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        ReviewData reviewData = reviewDataList.get(position);

        holder.author.setText(reviewData.getAuthor());
        holder.content.setText(reviewData.getContent());

    }

    @Override
    public int getItemCount() {
        return reviewDataList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView author, content;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.auther);
            content = itemView.findViewById(R.id.contenttext);

        }
    }
}
