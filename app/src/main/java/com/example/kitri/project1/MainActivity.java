package com.example.kitri.project1;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "kdw_main";

    //intent request code
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    //레이아웃
    private Button btnCamera;
    private Button btnBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate Main");

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 3);

        setBtnCamera();
        setBtnBluetooth();
    }

    //////////////////////////////////////////////////////카메라 역할///////////////////////////////////////////////////
    private void setBtnCamera(){
        btnCamera = findViewById(R.id.btn_camera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click camera button");
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivityForResult(intent, 111);
            }
        });
    }

    //////////////////////////////////////////////////////셔터 역할///////////////////////////////////////////////////
    private void setBtnBluetooth(){
        btnBluetooth = findViewById(R.id.btn_bluetooth);

        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click bluetooth button");
                Intent intent = new Intent(MainActivity.this, BluetoothActivity.class);
                startActivityForResult(intent, 101);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop Main");
    }
}
