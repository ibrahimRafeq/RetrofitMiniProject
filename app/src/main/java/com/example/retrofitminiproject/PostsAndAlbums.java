package com.example.retrofitminiproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.retrofitminiproject.databinding.ActivityPostsAndAlbumsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class PostsAndAlbums extends AppCompatActivity {
    ActivityPostsAndAlbumsBinding binding;
    private final int count = 2;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostsAndAlbumsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id = getIntent().getIntExtra("id", -1);
        binding.viewPager2.setAdapter(new ViewPagerAdapter(this));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tap, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Post");
                        tab.setIcon(R.drawable.baseline_library_books_24);
                        break;

                    case 1:
                        tab.setText("Album");
                        tab.setIcon(R.drawable.baseline_photo_album_24);
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }

    public class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment = null;

            switch (position) {
                case 0:
                    fragment = new PostFragment().newInstance(id);
                    break;
                case 1:
                    fragment = new AlbumFragment().newInstance(id);
                    break;
            }
            return fragment;
        }

        @Override
        public int getItemCount() {
            return count;
        }
    }
}
