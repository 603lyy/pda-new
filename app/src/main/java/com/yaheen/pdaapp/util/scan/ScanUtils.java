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

    public void open() {
        if (scanDevice != null && scanDevice.isScanOpened()) {
            scanDevice.startScan();
        }
    }

    public void stop() {
        if (scanDevice != null) {
            scanDevice.stopScan();
        }
    }
}
