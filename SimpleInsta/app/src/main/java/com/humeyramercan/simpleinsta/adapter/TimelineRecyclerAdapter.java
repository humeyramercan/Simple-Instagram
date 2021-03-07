package com.humeyramercan.simpleinsta.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.humeyramercan.simpleinsta.R;
import com.humeyramercan.simpleinsta.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TimelineRecyclerAdapter extends RecyclerView.Adapter<TimelineRecyclerAdapter.PostHolder> {
    ArrayList<Post> posts;
    public TimelineRecyclerAdapter(ArrayList<Post> posts) {
        this.posts = posts;
    }
    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycler_row,parent,false);
        return new PostHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.userEmailTextView.setText(posts.get(position).getUserEmail());
        holder.userCommentTextView.setText(posts.get(position).getUserComment());
        Picasso.get().load(posts.get(position).getDowloandUrl()).into(holder.userPostImageView);
    }
    @Override
    public int getItemCount() {
        return posts.size();
    }
    class PostHolder extends RecyclerView.ViewHolder{
        ImageView userPostImageView;
        TextView userCommentTextView,userEmailTextView;
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            userPostImageView=itemView.findViewById(R.id.userPostImageView);
            userCommentTextView=itemView.findViewById(R.id.userCommentTextView);
            userEmailTextView=itemView.findViewById(R.id.userEmailTextView);
        }
    }
}
