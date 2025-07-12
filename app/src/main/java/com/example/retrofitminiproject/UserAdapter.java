package com.example.retrofitminiproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitminiproject.databinding.UsersItemBinding;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<User> users;
    private Context context;
    private UsersItemBinding binding;
    private OnItemClick onItemClick;

    public UserAdapter(Context context, ArrayList<User> users, OnItemClick onItemClick) {
        this.context = context;
        this.users = users;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = UsersItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        User user = users.get(position);

        myViewHolder.usersItemBinding.nameTv.setText(user.getName());
        myViewHolder.usersItemBinding.companyName.setText(user.getCompany().getName());
        myViewHolder.usersItemBinding.streetTv.setText(user.getAddress().getStreet());
        myViewHolder.usersItemBinding.suiteTv.setText(user.getAddress().getSuite());
        myViewHolder.usersItemBinding.cityTv.setText(user.getAddress().getCity());
        myViewHolder.usersItemBinding.zipcode.setText(user.getAddress().getZipcode());

        myViewHolder.usersItemBinding.phoneIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onPhoneClicked(position);
            }
        });
        myViewHolder.usersItemBinding.websiteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onWebSiteClicked(position);
            }
        });
        myViewHolder.usersItemBinding.geoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onLocationClicked(position);
            }
        });
        myViewHolder.usersItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        myViewHolder.usersItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onCardClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        UsersItemBinding usersItemBinding;

        public MyViewHolder(UsersItemBinding binding) {
            super(binding.getRoot());
            this.usersItemBinding = binding;
        }
    }

    public interface OnItemClick {
        void onPhoneClicked(int position);

        void onWebSiteClicked(int position);

        void onLocationClicked(int position);

        void onCardClicked(int position);
    }
}
