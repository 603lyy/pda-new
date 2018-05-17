package com.yaheen.pdaapp.activity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanDevice;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yaheen.pdaapp.R;
import com.yaheen.pdaapp.bean.ReportBean;
import com.yaheen.pdaapp.util.ProgersssDialog;
import com.yaheen.pdaapp.util.nfc.AESUtils;
import com.yaheen.pdaapp.util.nfc.Base64;
import com.yaheen.pdaapp.util.nfc.Converter;
import com.yaheen.pdaapp.util.nfc.NfcVUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

import static com.yaheen.pdaapp.util.nfc.NFCUtils.ByteArrayToHexString;
import static com.yaheen.pdaapp.util.nfc.NFCUtils.toStringHex;

public class ReportActivity extends BaseActivity {

    private final static String SCAN_ACTION = "scan.rcv.message";

    private ScanDevice sm;

    private Gson gson = new Gson();

    private TextView tvScan, tvAddress, tvFetch, tvFetchShow, tvCommit;

    private LinearLayout llBack;

    private String barcodeStr;

    private String url = "http://lyl.tunnel.echomod.cn/whnsubhekou/tool/reportByApp.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        setTitleContent(R.string.report_title_content);

        llBack = findViewById(R.id.back);
        tvScan = findViewById(R.id.tv_scan);
        tvFetch = findViewById(R.id.tv_fetch);
        tvCommit = findViewById(R.id.tv_commit);
        tvAddress = findViewById(R.id.tv_address);
        tvFetchShow = findViewById(R.id.tv_fetch_show);

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tvScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanUtils.start();
            }
        });

        tvFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLoadState(true);
                tvFetchShow.setText("");
                tvFetch.setBackground(getResources().getDrawable(R.drawable.btn_gary_round));
            }
        });

        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit();
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
            tvAddress.setText(barcodeStr);
            scanUtils.stop();
        }

    };

    private void commit() {
        String chipId = tvFetchShow.getText().toString();
        String shortLinkCode = tvAddress.getText().toString();

        if (TextUtils.isEmpty(chipId) && TextUtils.isEmpty(shortLinkCode)) {
            showToast(R.string.report_empty_toast);
            return;
        }

        showLoadingDialog();

        RequestParams requestParams = new RequestParams(url);
        if (!TextUtils.isEmpty(chipId)) {
            requestParams.addParameter("chipId", chipId);
        }
        if (!TextUtils.isEmpty(shortLinkCode)) {
            shortLinkCode = shortLinkCode.substring(shortLinkCode.lastIndexOf("/") + 1);
            requestParams.addParameter("shortLinkCode", shortLinkCode);
        }
        requestParams.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ReportBean reportBean = gson.fromJson(result, ReportBean.class);
                if (reportBean != null && reportBean.isResult()) {
                    clearData();
                    showToast(R.string.report_success);
                } else {
                    showToast(R.string.report_fail);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                showToast(R.string.report_fail);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                cancelLoadingDialog();
            }
        });
    }

    private void clearData() {
        tvAddress.setText("");
        tvFetchShow.setText("");
    }

    @Override
    public void setNoteBody(final String body) {
        super.setNoteBody(body);
        if (!TextUtils.isEmpty(body)) {
            tvFetchShow.setText(body);
            setLoadState(false);
        } else {
            showToast(R.string.fetch_chip_fail);
        }
        tvFetch.setBackground(getResources().getDrawable(R.drawable.btn_blue_round));
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        scanUtils.stop();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
