package com.example.retrofitminiproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitminiproject.databinding.ItemAlbumBinding;
import com.example.retrofitminiproject.databinding.ItemCommentBinding;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Comment> comments;

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding binding = ItemCommentBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.itemCommentBinding.nameTv.setText(comment.getName());
        myViewHolder.itemCommentBinding.emailTv.setText(comment.getEmail());
        myViewHolder.itemCommentBinding.commentBodyTv.setText(comment.getBody());

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ItemCommentBinding itemCommentBinding;

        public MyViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.itemCommentBinding = binding;
        }
    }
}