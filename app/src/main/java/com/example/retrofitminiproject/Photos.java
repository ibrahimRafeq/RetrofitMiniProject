package com.example.retrofitminiproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.retrofitminiproject.databinding.ActivityPhotosBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Photos extends AppCompatActivity {

    ActivityPhotosBinding binding;
    private ArrayList<Photo> photoArrayList;
    private int albumId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhotosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String url = "https://jsonplaceholder.typicode.com/";
        albumId = getIntent().getIntExtra("albumId", -1);
        photoArrayList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(RetrofitApiInterface.class).getPhotoByAlbumId(albumId).enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {

                for (Photo pho : response.body()) {
                    photoArrayList.add(pho);
                }
                PhotoAdapter photoAdapter = new PhotoAdapter(Photos.this, photoArrayList);
                binding.photoRv.setAdapter(photoAdapter);
                binding.photoRv.setLayoutManager(new GridLayoutManager(Photos.this, 3));
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {

            }
        });


    }
}