package com.example.retrofitminiproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitminiproject.databinding.ItemPhotoBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Photo> photos;

    public PhotoAdapter(Context context, ArrayList<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPhotoBinding binding = ItemPhotoBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Photo photo = photos.get(position);
        Picasso.get().load(photo.getUrl()).into(myViewHolder.itemPhotoBinding.photoIv);
        myViewHolder.itemPhotoBinding.titleIvTv.setText(photo.getTitle());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemPhotoBinding itemPhotoBinding;

        public MyViewHolder(ItemPhotoBinding binding) {
            super(binding.getRoot());
            this.itemPhotoBinding = binding;
        }
    }
}
