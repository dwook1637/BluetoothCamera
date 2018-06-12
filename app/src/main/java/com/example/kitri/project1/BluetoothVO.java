package com.example.kitri.project1;

import android.bluetooth.BluetoothDevice;

public class BluetoothVO {
    private BluetoothDevice device;
    private String name;
    private String macAddress;

    public BluetoothVO() {
    }

    public BluetoothVO(String name, String macAddress) {
        this.name = name;
        this.macAddress = macAddress;
    }

    public BluetoothVO(BluetoothDevice device, String name, String macAddress) {
        this.device = device;
        this.name = name;
        this.macAddress = macAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }
}
