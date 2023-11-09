package com.example.myapplication.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMenuBinding;
import com.example.myapplication.view.fragment.ChartFragment;
import com.example.myapplication.view.fragment.CommunityFragment;
import com.example.myapplication.view.fragment.MapFragment;
import com.example.myapplication.view.fragment.ProfileFragment;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Granularity;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;

public class MenuActivity extends AppCompatActivity {

    private ActivityMenuBinding binding;
    private static final int GPS_UTIL_LOCATION_PERMISSION_REQUEST_CODE = 100;
    private static final int GPS_UTIL_LOCATION_RESOLUTION_REQUEST_CODE = 101;



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

        checkLocationPermission();
        clickCameraButton();
    }

    private void clickCameraButton(){
        binding.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CircleMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void checkLocationPermission(){
        int accessLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(accessLocation == PackageManager.PERMISSION_GRANTED){
            //checkLocationSetting();
        } else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                    , GPS_UTIL_LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GPS_UTIL_LOCATION_RESOLUTION_REQUEST_CODE){
            if(resultCode == RESULT_OK){
               // checkLocationSetting();
            }
            else{
                finish();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == GPS_UTIL_LOCATION_PERMISSION_REQUEST_CODE){
            for(int i = 0; i < permissions.length; i++){
                if(Manifest.permission.ACCESS_FINE_LOCATION.equals(permissions[i])){
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        //checkLocationSetting();
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                        builder.setTitle("위치 권한이 꺼져있습니다");
                        builder.setMessage("위치 정보 권한이 필요합니다.\\n\\n[설정]->[권한]에서 '위치' 항목을 사용으로 설정해 주세요.");
                        builder.setPositiveButton("설정으로 가기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        }).setNegativeButton("종료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    break;
                }
            }
        }
    }

}
