package com.example.kitri.project1;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BluetoothListAdapter extends ArrayAdapter {
    private Activity context;
    private int itemLayout;
    private List<BluetoothVO> deviceList;

    public BluetoothListAdapter(@NonNull Context context, int resource) {
        super(context, resource);

        this.context = (Activity)context;
        itemLayout = resource;
        deviceList = new ArrayList<>();
    }

    private class EnableDeviceHolder {
        //private ImageView imgDeviceType;
        private TextView textDeviceName;
        private TextView textDeviceAddress;
        private Button btnPair;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        EnableDeviceHolder holder;

        if(convertView==null){
            convertView = context.getLayoutInflater().inflate(itemLayout, null);
            holder = new EnableDeviceHolder();
            //holder.imgDeviceType = convertView.findViewById(R.id.img_device_type);
            holder.textDeviceName = convertView.findViewById(R.id.text_device_name);
            holder.textDeviceAddress = convertView.findViewById(R.id.text_device_address);
            holder.btnPair = convertView.findViewById(R.id.btn_pair);

            convertView.setTag(holder);
        }else {
            holder = (EnableDeviceHolder) convertView.getTag();
        }

        BluetoothVO d = deviceList.get(position);
        holder.textDeviceName.setText(d.getName());
        holder.textDeviceAddress.setText(d.getMacAddress());
        //holder.imgDeviceType.setImageURI(Uri.parse(d.getDeviceType()));

        final BluetoothDevice device = d.getDevice();

        //페어링
        holder.btnPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("kdw_paring", "페어링 버튼 누름 : " + device.getName() + "  " + device.getAddress());

                Intent intent = new Intent(context, ShutterActivity.class);
                intent.putExtra("SHUTTER_DEVICE", device);
                context.startActivity(intent);

            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return deviceList.size();
    }

    @Override
    public void add(@Nullable Object object) {
        if(deviceList.isEmpty() || deviceList == null){
            deviceList = new ArrayList<>();
        }
        deviceList.add((BluetoothVO)object);
        Log.d("kdw", "어댑터에서 추가 "+((BluetoothVO)object).getName());
        notifyDataSetChanged();

        super.add(object);
    }

}

