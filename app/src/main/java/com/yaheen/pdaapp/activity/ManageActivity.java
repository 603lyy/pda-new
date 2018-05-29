package com.yaheen.pdaapp.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yaheen.pdaapp.R;
import com.yaheen.pdaapp.adapter.ManageMsgAdapter;
import com.yaheen.pdaapp.bean.MsgBean;
import com.yaheen.pdaapp.bean.MsgUpdateBean;
import com.yaheen.pdaapp.util.ProgersssDialog;
import com.yaheen.pdaapp.util.nfc.AESUtils;
import com.yaheen.pdaapp.util.nfc.Base64;
import com.yaheen.pdaapp.util.nfc.Converter;
import com.yaheen.pdaapp.util.nfc.NfcVUtil;
import com.yaheen.pdaapp.util.toast.ToastUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.yaheen.pdaapp.util.nfc.NFCUtils.ByteArrayToHexString;
import static com.yaheen.pdaapp.util.nfc.NFCUtils.toStringHex;

public class ManageActivity extends RFIDBaseActivity {

    private ListView listView;

    private TextView tvCommit, tvLoad;

    private LinearLayout llBack, llLoading;

    private ManageMsgAdapter msgAdapter;

    private Gson gson = new Gson();

    //加载图标是否显示
    private boolean isLoading = false;

    private String url = "https://lhhk.020szsq.com/houseNumberRelation/getAllHouseNumberByChip.do";

    private String updateUrl = "https://lhhk.020szsq.com/houseNumberRelation/updateAppByJson.do";

//    private String url = "http://lyl.tunnel.echomod.cn/whnsubhekou/houseNumberRelation/getAllHouseNumberByChip.do";
//
//    private String updateUrl = "http://lyl.tunnel.echomod.cn/whnsubhekou/houseNumberRelation/updateAppByJson.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        setTitleContent(R.string.title_bar_msg_text);

        llLoading = findViewById(R.id.common_loading_view);
        tvCommit = findViewById(R.id.tv_commit);
        tvLoad = findViewById(R.id.tv_load);
        listView = findViewById(R.id.lv_msg);
        llBack = findViewById(R.id.back);

        msgAdapter = new ManageMsgAdapter(this);
        listView.setAdapter(msgAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_TOUCH_SCROLL:
                        msgAdapter.setScrolling(true);
                        break;
                    case SCROLL_STATE_FLING:
                        break;
                    case SCROLL_STATE_IDLE:
                        msgAdapter.setScrolling(false);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        listView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                if (!msgAdapter.getChangeState()) {
                    msgAdapter.setChangeState(true);
                }
            }
        });

        initLocalMsg();

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeInformation();
            }
        });

        tvLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setLoadState(true);
                setCanRead(true);
                tvLoad.setBackground(getResources().getDrawable(R.drawable.btn_gary_round));
            }
        });
    }

    private void initLocalMsg() {
        MsgBean.EntityBean entityBean = new MsgBean.EntityBean();
        List<MsgBean.EntityBean> beanList = new ArrayList<>();
        beanList.add(entityBean);
        msgAdapter.setDatas(beanList);
        msgAdapter.notifyDataSetChanged();
    }

    private void getInformation(String chipId) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("chipId", chipId);
        requestParams.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MsgBean msgBean = gson.fromJson(result, MsgBean.class);
                if (msgBean != null && msgBean.getEntity() != null) {
                    List<MsgBean.EntityBean> beanList = new ArrayList<>();
                    beanList.add(msgBean.getEntity());
                    msgAdapter.setDatas(beanList);
                    msgAdapter.notifyDataSetChanged();
                } else {
                    showToast(R.string.msg_get_information_fail_2);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                showToast(R.string.msg_get_information_fail);
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

    private void changeInformation() {
        if (!msgAdapter.hasId()) {
            showToast(R.string.msg_get_information_request);
            return;
        }
        showLoadingDialog();
        RequestParams requestParams = new RequestParams(updateUrl);
        requestParams.addParameter("json", Base64.encode(msgAdapter.getMsg().getBytes()));
        requestParams.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MsgUpdateBean updateBean = gson.fromJson(result, MsgUpdateBean.class);
                if (updateBean != null && updateBean.isResult()) {
                    showToast(R.string.msg_update_success);
                } else {
                    showToast(updateBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                showToast(R.string.msg_update_fail);
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

    @Override
    public void setNoteBody(String body) {
        super.setNoteBody(body);
        if (!TextUtils.isEmpty(body)) {
            showLoadingDialog();
            getInformation(body);
        } else {
            showToast(R.string.fetch_chip_fail);
        }
        tvLoad.setBackground(getResources().getDrawable(R.drawable.btn_blue_round));
    }

    @Override
    public void onBackPressed() {
        if (isLoading) {
            llLoading.setVisibility(View.GONE);
            isLoading = false;
            return;
        }
        super.onBackPressed();
    }
}
