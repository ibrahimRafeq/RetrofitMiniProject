package com.example.retrofitminiproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.retrofitminiproject.databinding.ActivityCommentsBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Comments extends AppCompatActivity {

    ActivityCommentsBinding binding;
    private ArrayList<Comment> comments;
    private int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        postId = getIntent().getIntExtra("postId", -1);
        String url = "https://jsonplaceholder.typicode.com/";
        comments = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApiInterface retrofitApiInterface = retrofit.create(RetrofitApiInterface.class);
        retrofitApiInterface.getCommentByPostId(postId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                for (Comment comment : response.body()) {
                    comments.add(comment);
                }
                CommentAdapter commentAdapter = new CommentAdapter(Comments.this, comments);
                binding.commentRv.setAdapter(commentAdapter);
                binding.commentRv.setLayoutManager(new LinearLayoutManager(Comments.this));
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });


    }
}