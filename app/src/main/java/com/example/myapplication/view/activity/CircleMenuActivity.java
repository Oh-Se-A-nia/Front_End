package com.example.myapplication.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityCircleMenuBinding;
import com.example.myapplication.view.Constants;
import com.ramotion.circlemenu.CircleMenuView;


public class CircleMenuActivity extends AppCompatActivity {

    private ActivityCircleMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCircleMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        clickCircleMenuButton();
    }

    private void clickCircleMenuButton(){
        binding.circleMenu.setEventListener(new CircleMenuView.EventListener(){
            @Override
            public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int buttonIndex){
                super.onButtonClickAnimationStart(view, buttonIndex);

                switch(buttonIndex){
                    case 0:
                        Toast.makeText(CircleMenuActivity.this, "Camera Button Clicked", Toast.LENGTH_SHORT).show();
                        intentEcoTagActivity(buttonIndex);
                        break;
                    case 1:
                        Toast.makeText(CircleMenuActivity.this, "Chart Button Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(CircleMenuActivity.this, "원하는 사진을 선택해주세요", Toast.LENGTH_SHORT).show();
                        intentEcoTagActivity(buttonIndex);
                        break;
                }
            }
        });
    }

    //등록 액티비티로 넘어가는 코드
    private void intentEcoTagActivity(int buttonIndex){
        Intent intent = new Intent(CircleMenuActivity.this, EcoTagActivity.class);

        if(buttonIndex == 0){
            intent.putExtra("타입", Constants.REGISTER_WITH_CAMERA);
        } else if(buttonIndex == 2){
            intent.putExtra("타입", Constants.REGISTER_WITH_ALBUM);
        }

        startActivity(intent);
        finish();
    }
}
