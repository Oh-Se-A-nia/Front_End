package com.example.myapplication.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityCircleMenuBinding;
import com.example.myapplication.databinding.ActivityEcoTagBinding;

public class EcoTagActivity extends AppCompatActivity {

    private ActivityEcoTagBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEcoTagBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
