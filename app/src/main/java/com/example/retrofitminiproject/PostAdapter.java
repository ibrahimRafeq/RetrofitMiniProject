package com.example.retrofitminiproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitminiproject.databinding.ItemPostBinding;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Post> posts;
    private OnItemClick onItemClick;

    public PostAdapter(Context context, ArrayList<Post> posts, OnItemClick onItemClick) {
        this.context = context;
        this.posts = posts;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Post post = posts.get(position);

        myViewHolder.itemPostBinding.postTv.setText(post.getBody());
        myViewHolder.itemPostBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onPostClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ItemPostBinding itemPostBinding;

        public MyViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.itemPostBinding = binding;
        }
    }

    interface OnItemClick {
        void onPostClicked(int position);
    }
}
