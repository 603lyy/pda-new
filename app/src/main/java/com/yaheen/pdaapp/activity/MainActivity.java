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
import android.widget.Toast;

import com.magicrf.uhfreaderlib.reader.Tools;
import com.yaheen.pdaapp.BaseApp;
import com.yaheen.pdaapp.R;
import com.yaheen.pdaapp.util.DialogUtils;
import com.yaheen.pdaapp.util.dialog.DialogCallback;
import com.yaheen.pdaapp.util.dialog.IDialogCancelCallback;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class MainActivity extends RFIDBaseActivity {

    private final static String SCAN_ACTION = "scan.rcv.message";

    private LinearLayout llBind, llMsg, llManage, llReport, llChangeLocation;

    private String barcodeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llMsg = findViewById(R.id.ll_msg);
        llBind = findViewById(R.id.ll_bind);
        llReport = findViewById(R.id.ll_report);
        llManage = findViewById(R.id.ll_manage);
        llChangeLocation = findViewById(R.id.ll_change_location);

        llBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BindActivity.class);
                startActivity(intent);
            }
        });

        llManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ManageActivity.class);
                startActivity(intent);
            }
        });

        llMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showToast(R.string.main_activity_scan);
//                scanUtils.start();
                startWebActivity(barcodeStr);
            }
        });

        llReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });

        llChangeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebChangeLocationActivity.class);
                startActivity(intent);
            }
        });
    }

    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            byte[] barocode = intent.getByteArrayExtra("barocode");
            int barocodelen = intent.getIntExtra("length", 0);
            barcodeStr = new String(barocode, 0, barocodelen);
            scanUtils.stop();
//            startWebActivity(barcodeStr);
        }

    };

    private void startWebActivity(String code) {
        Intent intent = new Intent(MainActivity.this, WebActivity.class);
        intent.putExtra("shortCode", code);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(SCAN_ACTION);
        registerReceiver(mScanReceiver, filter);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        scanUtils.stop();
        unregisterReceiver(mScanReceiver);
    }

    @Override
    public void onBackPressed() {
        DialogUtils.showDialog(MainActivity.this, "确定要退出该APP吗？", new DialogCallback() {
            @Override
            public void callback() {
                BaseApp.exit();
            }
        }, new IDialogCancelCallback() {
            @Override
            public void cancelCallback() {
            }
        });
    }
}
