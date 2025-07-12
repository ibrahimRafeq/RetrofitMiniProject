package com.example.retrofitminiproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitminiproject.databinding.ItemAlbumBinding;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Album> albums;
    private OnItemClick onItemClick;

    public AlbumAdapter(Context context, ArrayList<Album> albums, OnItemClick onItemClick) {
        this.context = context;
        this.albums = albums;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAlbumBinding binding = ItemAlbumBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Album album = albums.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.itemAlbumBinding.albumTv.setText(album.getTitle());
        myViewHolder.itemAlbumBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onAlbumClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ItemAlbumBinding itemAlbumBinding;

        public MyViewHolder(ItemAlbumBinding binding) {
            super(binding.getRoot());
            this.itemAlbumBinding = binding;
        }
    }

    interface OnItemClick {
        void onAlbumClicked(int position);
    }
}