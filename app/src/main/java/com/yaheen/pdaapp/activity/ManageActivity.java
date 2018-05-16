package com.yaheen.pdaapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yaheen.pdaapp.R;
import com.yaheen.pdaapp.adapter.ManageMsgAdapter;
import com.yaheen.pdaapp.bean.MsgBean;
import com.yaheen.pdaapp.util.ProgersssDialog;
import com.yaheen.pdaapp.util.nfc.Base64;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class ManageActivity extends BaseActivity {

    private ListView listView;

    private TextView tvCommit, tvLoad;

    private LinearLayout llBack, llLoading;

    private ManageMsgAdapter msgAdapter;

    private ProgersssDialog progersssDialog;

    private Gson gson = new Gson();

    private boolean isLoading = false;

    private String url = "http://lyl.tunnel.echomod.cn/whnsubhekou/houseNumberRelation/getAllHouseNumberByChip.do";

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
//                msgAdapter.getMsg();
            }
        });

        tvLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progersssDialog = new ProgersssDialog(ManageActivity.this);
                getInformation();
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

    private void getInformation() {
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("chipId", "123");
        requestParams.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MsgBean msgBean = gson.fromJson(result, MsgBean.class);
                if (msgBean != null) {
                    List<MsgBean.EntityBean> beanList = new ArrayList<>();
                    beanList.add(msgBean.getEntity());
                    msgAdapter.setDatas(beanList);
                    msgAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                progersssDialog.dismiss();
            }
        });
    }

    private void changeInformation() {
        RequestParams requestParams = new RequestParams("http://lyl.tunnel.echomod.cn/whnsubhekou/houseNumberRelation/updateAppByJson.do");
        requestParams.addParameter("json",Base64.encode(msgAdapter.getMsg().getBytes()));
        requestParams.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
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
