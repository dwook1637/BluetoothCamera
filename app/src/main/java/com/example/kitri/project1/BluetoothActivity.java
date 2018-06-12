package com.example.kitri.project1;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothActivity extends Activity {
    private static final String TAG = "kdw_bluetooth_activity";

    //intent request code
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    //레이아웃
    private ListView lvBluetooth;
    private ProgressBar pb_discover;

    //멤버
    private BluetoothListAdapter mArrayAdapter;
    private BluetoothService mBluetoothService;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    Log.d(TAG, "state changed message handler called");
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                        case BluetoothService.STATE_CONNECTING:
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    Toast.makeText(BluetoothActivity.this, msg.getData().getString(Constants.TOAST), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d(TAG, "디바이스 찾음 : " + device.getName());
                mArrayAdapter.add(new BluetoothVO(device, device.getName(), device.getAddress()));
            }
//            else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
//                setTitle(R.string.select_device);
//                if(mArrayAdapter.getCount() == 0){
//                    //noDevice();
//                }
//            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_list);
        Log.d(TAG, "onCreate BluetoothList");

        setting();
        doDiscovery();

        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);
        filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if(pairedDevices.size() > 0){
            for (BluetoothDevice device : pairedDevices) {
                mArrayAdapter.add(new BluetoothVO(device, device.getName(), device.getAddress()));
            }
        }
//        else {
//            String noDevices = getResources().getText(R.string.none_paired).toString();
//            //noDevice();
//        }

    }

    //********************************초기 설정*********************************************
    private void setting(){
        lvBluetooth = findViewById(R.id.lv_bluetooth);
        pb_discover = findViewById(R.id.pb_bluetooth_discover);

        mArrayAdapter = new BluetoothListAdapter(this, R.layout.bluetooth_item);
        lvBluetooth.setAdapter(mArrayAdapter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);

        mBluetoothService = new BluetoothService(this, mHandler);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop Bluetooth");
        mBluetoothAdapter.cancelDiscovery();
        //mBluetoothAdapter.disable();
        unregisterReceiver(mReceiver);
    }

    private  void doDiscovery(){
        //progressBar 처리
        //pb_discover.animate();
        //pb_discover.setVisibility(View.VISIBLE);

        if(mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
        }

        Log.d(TAG, "스캔시작 " + mBluetoothAdapter);
        mBluetoothAdapter.startDiscovery();
    }


}

