package com.yaheen.pdaapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaheen.pdaapp.R;

public class MainActivity extends BaseActivity {

    private final static String SCAN_ACTION = "scan.rcv.message";

    private ScanDevice sm;

    private TextView tvBind, tvMsg, tvManage, tvReport;

    private LinearLayout llBind, llMsg, llManage, llReport;

    private String barcodeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llMsg = findViewById(R.id.ll_msg);
        tvBind = findViewById(R.id.tv_bind);
        llBind = findViewById(R.id.ll_bind);
        llReport = findViewById(R.id.ll_report);
        llManage = findViewById(R.id.ll_manage);

        llBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sm.startScan();
                Intent intent = new Intent(MainActivity.this, BindActivity.class);
                startActivity(intent);
            }
        });
    }

}