package com.example.retrofitminiproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AlbumFragment extends Fragment {


    private static final String USER_ID = "userId";

    private int userId;
    private ArrayList<Album> albums;

    public AlbumFragment() {
    }

    public static AlbumFragment newInstance(int userId) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putInt(USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        String url = "https://jsonplaceholder.typicode.com/";
        albums = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.albumRv);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApiInterface retrofitApiInterface = retrofit.create(RetrofitApiInterface.class);
        retrofitApiInterface.getAlbumByUserId(userId).enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                for (Album album : response.body()) {
                    albums.add(album);
                }
                AlbumAdapter albumAdapter = new AlbumAdapter(getActivity(), albums, new AlbumAdapter.OnItemClick() {
                    @Override
                    public void onAlbumClicked(int position) {
                        int albumId = albums.get(position).getId();
                        Intent intent = new Intent(getActivity(), Photos.class);
                        intent.putExtra("albumId", albumId);
                        startActivity(intent);

                    }
                });

                recyclerView.setAdapter(albumAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });

        return view;
    }
}