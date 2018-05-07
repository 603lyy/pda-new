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

//        sm = new ScanDevice();
//        sm.setOutScanMode(0); //接收广播

        llBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sm.startScan();
                Intent intent = new Intent(MainActivity.this, BindActivity.class);
                startActivity(intent);
            }
        });
    }

    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            byte[] barocode = intent.getByteArrayExtra("barocode");
            int barocodelen = intent.getIntExtra("length", 0);
            byte temp = intent.getByteExtra("barcodeType", (byte) 0);
            android.util.Log.i("debug", "----codetype--" + temp);
            barcodeStr = new String(barocode, 0, barocodelen);
            tvBind.setText(barcodeStr);
            sm.stopScan();
        }

    };

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (sm != null) {
            sm.stopScan();
        }
        unregisterReceiver(mScanReceiver);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SCAN_ACTION);
        registerReceiver(mScanReceiver, filter);
    }

}
