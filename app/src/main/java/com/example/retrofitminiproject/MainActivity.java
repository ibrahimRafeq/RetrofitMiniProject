package com.example.retrofitminiproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.retrofitminiproject.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.Manifest;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String url = "https://jsonplaceholder.typicode.com/";
        users = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApiInterface retrofitApiInterface = retrofit.create(RetrofitApiInterface.class);

        Call<List<User>> allUsers = retrofitApiInterface.getAllUser();
        allUsers.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                for (User user : response.body()) {
                    users.add(user);
                }
                UserAdapter userAdapter = new UserAdapter(MainActivity.this, users, new UserAdapter.OnItemClick() {
                    @Override
                    public void onPhoneClicked(int position) {
                        String phoneNumber = users.get(position).getPhone();
                        String cleanedPhoneNumber = cleanPhoneNumber(phoneNumber);

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + cleanedPhoneNumber));
                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE}, 1);
                        } else {
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onWebSiteClicked(int position) {
                        String webSiteUrl = users.get(position).getWebsite();

                        // تأكد من وجود http/https في الرابط
                        if (!webSiteUrl.startsWith("http://") && !webSiteUrl.startsWith("https://")) {
                            webSiteUrl = "http://" + webSiteUrl;
                        }
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webSiteUrl));
                        startActivity(intent);
                    }

                    @Override
                    public void onLocationClicked(int position) {
                        User user = users.get(position);
                        double lat = user.getAddress().getGeo().getLat();
                        double lng = user.getAddress().getGeo().getLng();
                        String uri = "geo:" + lat + "," + lng + "?q=" + lat + "," + lng;
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setPackage("com.google.android.apps.maps"); // للتأكد أنه يفتح Google Maps
                        startActivity(intent);
                    }

                    @Override
                    public void onCardClicked(int position) {
                        int id = users.get(position).getId();
                        Intent intent = new Intent(MainActivity.this, PostsAndAlbums.class);
                        intent.putExtra("id", id);
                        startActivity(intent);

                    }
                });

                binding.usersRv.setAdapter(userAdapter);
                binding.usersRv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("ApiTest", "onFailure: " + t.getMessage());
            }
        });


    }
    //اضافة تعليق جديد

    public String cleanPhoneNumber(String rawPhone) {
        // إزالة أي شيء غير الأرقام (0–9 أو + في البداية)
        rawPhone = rawPhone.trim();

        // إذا كان يبدأ بـ +، احتفظ به، ثم نظف الباقي
        if (rawPhone.startsWith("+")) {
            return "+" + rawPhone.replaceAll("[^\\d]", "");
        } else {
            return rawPhone.replaceAll("[^\\d]", "");
        }
    }
}