package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.databinding.ActivityMenuBinding;
import com.example.myapplication.fragment.ChartFragment;
import com.example.myapplication.fragment.CommunityFragment;
import com.example.myapplication.fragment.MapFragment;
import com.example.myapplication.fragment.ProfileFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MenuActivity extends AppCompatActivity {

    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.community) {
                    replaceFragment(new CommunityFragment());
                } else if (itemId == R.id.map) {
                    replaceFragment(new MapFragment());
                } else if(itemId == R.id.chart){
                    replaceFragment(new ChartFragment());
                } else if (itemId == R.id.profile){
                    replaceFragment(new ProfileFragment());
                }

                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
