package com.example.myapplication;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityCircleMenuBinding;


public class CircleMenuActivity extends AppCompatActivity {

    private ActivityCircleMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCircleMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
