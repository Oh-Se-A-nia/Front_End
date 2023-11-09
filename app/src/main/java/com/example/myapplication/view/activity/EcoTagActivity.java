package com.example.myapplication.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.databinding.ActivityCircleMenuBinding;
import com.example.myapplication.databinding.ActivityEcoTagBinding;
import com.example.myapplication.view.Constants;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Granularity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EcoTagActivity extends AppCompatActivity {

    private ActivityEcoTagBinding binding;
    public static final int DEFAULT_LOCATION_REQUEST_PRIORITY = Priority.PRIORITY_BALANCED_POWER_ACCURACY;
    public static final long DEFAULT_LOCATION_REQUEST_INTERVAL = 20000L;
    private static final int GPS_UTIL_LOCATION_RESOLUTION_REQUEST_CODE = 101;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;

    private double latitude;
    private double longitude;
    private List<Address> addressList = null;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEcoTagBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkRegisterType();
        getOtherImage();
    }

    private void getOtherImage(){
        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerImageWithCamera();
            }
        });
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
                        getCurrentLocation();
                    }
                }
            }
    );

    private void getCurrentLocation(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try{
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if(location !=null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                setCurrentAddress();

                System.out.println(latitude);
                System.out.println(longitude);
                System.out.println(address);
            }


        }catch(SecurityException e){
            e.printStackTrace();
        }
    }

    private void setCurrentAddress(){
        Geocoder geocoder = new Geocoder(this);
        try{
            addressList = geocoder.getFromLocation(latitude, longitude, 10);
        } catch(IOException e){
            e.printStackTrace();
        }

        if(addressList!=null){
            if(addressList.size() == 0){
                Log.d("주소오류", "GetUserLocation: 주소 찾기 오류");
            } else{
                address = addressList.get(0).getAddressLine(0);
            }
        }
    }
}
