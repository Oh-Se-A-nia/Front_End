package com.example.myapplication.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityCircleMenuBinding;
import com.example.myapplication.databinding.ActivityEcoTagBinding;
import com.example.myapplication.view.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EcoTagActivity extends AppCompatActivity {

    private ActivityEcoTagBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEcoTagBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkRegisterType();
    }

    //카메라, 앨범 경로를 구분해주는 함수
    private void checkRegisterType(){
        Intent intent = getIntent();
        int type = intent.getIntExtra("타입", 0);

        if(type == Constants.REGISTER_WITH_ALBUM){
            registerImageWithAlbum();
        } else if(type == Constants.REGISTER_WITH_CAMERA){
            Toast.makeText(EcoTagActivity.this, "카메라로 접속", Toast.LENGTH_SHORT).show();
            registerImageWithCamera();
        }
    }

    private void registerImageWithAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        registerAlbum.launch(intent);
    }

    ActivityResultLauncher<Intent> registerAlbum = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        Uri uri = result.getData().getData();
                        try{
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            binding.imageView.setImageBitmap(bitmap);
                        } catch(FileNotFoundException e){
                            e.printStackTrace();
                        } catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    private void registerImageWithCamera(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        registerCamera.launch(cameraIntent);
    }

    ActivityResultLauncher<Intent> registerCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        Bundle extras = result.getData().getExtras();
                        Bitmap bitmap = (Bitmap) extras.get("data");
                        binding.imageView.setImageBitmap(bitmap);
                    }
                }
            }
    );
}
