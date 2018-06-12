package com.example.kitri.project1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

public class ShutterActivity extends FragmentActivity{
    private static final String TAG = "kdw_shutter activity";
    public static BluetoothDevice EXTRA_DEVICE = null;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothService mBluetoothService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"shutter onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shutter);

        Intent intent = getIntent();
        BluetoothDevice device = intent.getParcelableExtra("SHUTTER_DEVICE");

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Log.d(TAG, ""+device.getName() + " " + device.getAddress());

        EXTRA_DEVICE = device;

        //mBluetoothService = new BluetoothService(this, null);
        //mBluetoothService.connect(device);
        //connect하려면 대상이 accept()를 해야함

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothShutterFragment fragment = BluetoothShutterFragment.newInstance();
            transaction.replace(R.id.shutter_content_fragment, fragment);
            transaction.commit();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop shutter activity");
    }
}
