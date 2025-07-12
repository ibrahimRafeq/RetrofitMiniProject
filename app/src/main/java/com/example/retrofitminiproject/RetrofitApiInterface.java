package com.example.retrofitminiproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitApiInterface {

    @GET("users")
    Call<List<User>> getAllUser();

    @GET("posts")
    Call<List<Post>> getPostByUserId(@Query("userId") int id);

    @GET("albums")
    Call<List<Album>> getAlbumByUserId(@Query("userId") int userId);

    @GET("comments")
    Call<List<Comment>> getCommentByPostId(@Query("postId") int postId);

    @GET("photos")
    Call<List<Photo>> getPhotoByAlbumId(@Query("albumId") int albumId);

}
