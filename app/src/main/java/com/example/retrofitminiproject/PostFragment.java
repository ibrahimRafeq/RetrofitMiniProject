package com.example.retrofitminiproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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


public class PostFragment extends Fragment {


    private ArrayList<Post> posts;
    private static final String USER_ID = "userId";

    private int userId;

    public PostFragment() {
    }


    public static PostFragment newInstance(int userId) {
        PostFragment fragment = new PostFragment();
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
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        String url = "https://jsonplaceholder.typicode.com/";
        RecyclerView recyclerView = view.findViewById(R.id.postRv);
        posts = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApiInterface retrofitApiInterface = retrofit.create(RetrofitApiInterface.class);
        retrofitApiInterface.getPostByUserId(userId).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                for (Post post : response.body()) {
                    posts.add(post);
                }
                PostAdapter postAdapter = new PostAdapter(getActivity(), posts, new PostAdapter.OnItemClick() {
                    @Override
                    public void onPostClicked(int position) {
                        int postId = posts.get(position).getId();
                        Intent intent = new Intent(getActivity(), Comments.class);
                        intent.putExtra("postId", postId);
                        startActivity(intent);

                    }
                });
                recyclerView.setAdapter(postAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d("TAGgg", "onFailure: " + t.getMessage());
            }
        });

        return view;
    }
}