package com.yaheen.pdaapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yaheen.pdaapp.R;
import com.yaheen.pdaapp.bean.BindBean;
import com.yaheen.pdaapp.bean.BindUpdataBean;
import com.yaheen.pdaapp.bean.CheckBean;
import com.yaheen.pdaapp.util.nfc.Base64Utils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class BindActivity extends RFIDBaseActivity {

    private final static String SCAN_ACTION = "scan.rcv.message";

    private Gson gson = new Gson();

    private TextView tvFetch, tvFetchShow, tvScan, tvScanShow, tvCommit;

    private LinearLayout llBack;

    private String url = "http://shortlink.cn/eai/updateLongLink.do";

//    private String updateUrl = "https://lyl.tunnel.echomod.cn/whnsubhekou/tool/houseNumbers/update.do";

    private String updateUrl = baseUrl + "houseNumbers/updateFormDataManagement.do";

    private String checkUrl = "http://shortlink.cn/eai/getShortLinkCompleteInformation.do";

    private String barcodeStr;

    private String hNumber;

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
//                setLoadState(true);
                setCanRead(true);
                tvFetchShow.setText("");
                tvFetch.setBackground(getResources().getDrawable(R.drawable.btn_gary_round));
            }
        });

        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingDialog();
                check();
            }
        });

    }

    private void check() {
        String slink = tvScanShow.getText().toString();

        if (TextUtils.isEmpty(slink)) {
            showToast(R.string.bind_activity_short_link_empty);
            cancelLoadingDialog();
            return;
        }
        slink = slink.substring(slink.lastIndexOf("/") + 1);

        RequestParams params = new RequestParams(checkUrl);
        params.addQueryStringParameter("key", "7zbQUBNY0XkEcUoushaJD7UcKyWkc91q");
        params.addQueryStringParameter("shortLinkCode", slink);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CheckBean checkBean = gson.fromJson(result, CheckBean.class);
                if (checkBean != null && checkBean.isResult()) {
                    checkShortLink(checkBean.getEntity());
                } else {
                    showToast(R.string.bind_activity_bind_fail);
                    cancelLoadingDialog();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                showToast(R.string.bind_activity_bind_fail);
                cancelLoadingDialog();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
            }
        });
    }

    /**
     * 是否同步更新门牌系统
     *
     * @param update
     */
    private void bind(final boolean update) {

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
        slink = slink.substring(slink.lastIndexOf("/") + 1);

        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("key", "7zbQUBNY0XkEcUoushaJD7UcKyWkc91q");
        params.addQueryStringParameter("shortLinkCode", slink);
        params.addQueryStringParameter("note", tvFetchShow.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BindBean bindBean = gson.fromJson(result, BindBean.class);
                if (bindBean != null && bindBean.isResult()) {
                    if (update) {
                        update(tvFetchShow.getText().toString(), hNumber);
                    } else {
                        showToast(R.string.bind_activity_bind_success);
                        cancelLoadingDialog();
                    }
                } else {
                    showToast(R.string.bind_activity_bind_fail);
                    cancelLoadingDialog();
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

    private void update(String chipId, String id) {

        if (TextUtils.isEmpty(chipId)) {
            showToast(R.string.main_activity_scan);
            return;
        }

        chipId = chipId.substring(chipId.lastIndexOf("/") + 1);
        id = id.substring(id.lastIndexOf("=") + 1);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("chipId", chipId);

        RequestParams params = new RequestParams(updateUrl);
        params.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
        params.addQueryStringParameter("json", Base64Utils.encode(jsonObject.toString().getBytes()));
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BindUpdataBean data = gson.fromJson(result, BindUpdataBean.class);
                if (data != null && data.isResult()) {
                    showToast(R.string.bind_activity_bind_success);
                } else {
                    showToast(R.string.bind_activity_bind_fail);
                }
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

    private void checkShortLink(CheckBean.EntityBean date) {

        if (date == null) {
            showToast(R.string.scan_fail);
            cancelLoadingDialog();
            return;
        }

        //长链接为空，直接请求短链接系统绑定
        if (TextUtils.isEmpty(date.getLink())) {
            bind(false);
        }
        //长链接不为空且门牌ID不为空,提示已被绑定
        else if (!TextUtils.isEmpty(date.getLink()) && !TextUtils.isEmpty(date.getNote())) {
            showToast(R.string.bind_activity_short_link_bind);
            cancelLoadingDialog();
        }
        //长链接不为空且门牌ID为空,请求短链接系统和门牌系统
        else if (!TextUtils.isEmpty(date.getLink()) && TextUtils.isEmpty(date.getNote())) {
            hNumber = date.getLink();
            bind(true);
        }
    }

    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            byte[] barocode = intent.getByteArrayExtra("barocode");
            int barocodelen = intent.getIntExtra("length", 0);
            barcodeStr = new String(barocode, 0, barocodelen);
            if (barcodeStr.contains("http://shortlink.cn/")) {
                tvScanShow.setText(barcodeStr);
            } else {
                Toast.makeText(BindActivity.this, R.string.scan_not, Toast.LENGTH_SHORT).show();
            }
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
        } else {
            showToast(R.string.fetch_chip_fail);
        }
        tvFetch.setBackground(getResources().getDrawable(R.drawable.btn_blue_round));
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//        scanUtils.stop();
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
