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
import com.yaheen.pdaapp.bean.BindBean;
import com.yaheen.pdaapp.util.ProgersssDialog;
import com.yaheen.pdaapp.util.nfc.AESUtils;
import com.yaheen.pdaapp.util.nfc.Converter;
import com.yaheen.pdaapp.util.nfc.NfcVUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

import static com.yaheen.pdaapp.util.nfc.NFCUtils.ByteArrayToHexString;
import static com.yaheen.pdaapp.util.nfc.NFCUtils.toStringHex;

public class BindActivity extends BaseActivity {

    private final static String SCAN_ACTION = "scan.rcv.message";

    private Gson gson = new Gson();

    private TextView tvFetch, tvFetchShow, tvScan, tvScanShow, tvCommit;

    private LinearLayout llBack;

    private String url = "http://shortlink.cn/eai/updateLongLink.do";

    private String barcodeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        setTitleContent(R.string.bind_activity_title_content);

        llBack = findViewById(R.id.back);
        tvScan = findViewById(R.id.tv_scan);
        tvFetch = findViewById(R.id.tv_fetch);
        tvCommit = findViewById(R.id.tv_commit);
        tvScanShow = findViewById(R.id.tv_scan_show);
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
                tvFetch.setBackground(getResources().getDrawable(R.drawable.btn_gary_round));
            }
        });

        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingDialog();
                bind();
            }
        });

    }

    private void bind() {

        String slink = tvScanShow.getText().toString();
        String chip = tvFetchShow.getText().toString();

        if (TextUtils.isEmpty(slink)) {
            showToast(R.string.bind_activity_short_link_empty);
            cancelLoadingDialog();
            return;
        }

        if (TextUtils.isEmpty(chip)) {
            showToast(R.string.bind_activity_chip_empty);
            cancelLoadingDialog();
            return;
        }

        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("key", "7zbQUBNY0XkEcUoushaJD7UcKyWkc91q");
        params.addQueryStringParameter("shortLinkCode", barcodeStr);
        params.addQueryStringParameter("note", tvFetchShow.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BindBean bindBean = gson.fromJson(result, BindBean.class);
                if (bindBean != null && bindBean.isResult()) {
                    showToast(R.string.bind_activity_bind_success);
                } else {
                    showToast(R.string.bind_activity_bind_fail);
                }
                clearData();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                showToast(R.string.bind_activity_bind_fail);
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

    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            byte[] barocode = intent.getByteArrayExtra("barocode");
            int barocodelen = intent.getIntExtra("length", 0);
            byte temp = intent.getByteExtra("barcodeType", (byte) 0);
            android.util.Log.i("debug", "----codetype--" + temp);
            barcodeStr = new String(barocode, 0, barocodelen);
            tvScanShow.setText(barcodeStr);
            scanUtils.stop();
        }

    };

    private void clearData() {
        tvScanShow.setText("");
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
}
