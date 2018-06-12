package com.example.kitri.project1;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class CameraActivity extends FragmentActivity {
    public static BluetoothDevice EXTRA_DEVICE = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("kdw_camera activity","camera onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_camera);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothCameraFragment fragment = BluetoothCameraFragment.newInstance();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }
}
