package com.yaheen.pdaapp.util.scan;

import android.device.ScanDevice;

public class ScanUtils {

    private ScanDevice scanDevice;

    public ScanUtils() {
        if (scanDevice == null) {
            init();
        }
    }

    private void init() {
        scanDevice = new ScanDevice();
        scanDevice.setOutScanMode(0); //接收广播
    }

    /**
     * 设置开启设备扫描功能
     */
    public void open() {
        if (scanDevice != null) {
            scanDevice.openScan();
        }
    }

    /**
     * 设置关闭设备扫描功能
     */
    public void close() {
        if (scanDevice != null) {
            scanDevice.closeScan();
        }
    }

    /**
     * 打开扫描
     */
    public void start() {
        if (scanDevice != null) {
            scanDevice.startScan();
        }
    }

    /**
     * 停止扫描
     */
    public void stop() {
        if (scanDevice != null) {
            scanDevice.setScanLaserMode(8);
            scanDevice.stopScan();
        }
    }
}
