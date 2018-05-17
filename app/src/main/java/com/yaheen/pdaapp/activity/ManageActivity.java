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

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.yaheen.pdaapp.util.nfc.NFCUtils.ByteArrayToHexString;
import static com.yaheen.pdaapp.util.nfc.NFCUtils.toStringHex;

public class ManageActivity extends BaseActivity {

    private NfcB nfcbTag;
    private Tag tagFromIntent;
    private NfcAdapter mNfcAdapter;
    private PendingIntent mNfcPendingIntent;
    private IntentFilter[] mNdefExchangeFilters;

    private ListView listView;

    private TextView tvCommit, tvLoad;

    private LinearLayout llBack, llLoading;

    private ManageMsgAdapter msgAdapter;

    private ProgersssDialog progersssDialog;

    private Gson gson = new Gson();

    //加载图标是否显示
    private boolean isLoading = false;

    private String url = "http://lyl.tunnel.echomod.cn/whnsubhekou/houseNumberRelation/getAllHouseNumberByChip.do";

    private String updateUrl = "http://lyl.tunnel.echomod.cn/whnsubhekou/houseNumberRelation/updateAppByJson.do";

    private String ex_id = "", types = "";

    //是否可以读芯片
    private boolean load = false;

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
        initNFC();

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
                load = true;
                tvLoad.setBackground(getResources().getDrawable(R.drawable.btn_gary_round));
//                Toast.makeText(ManageActivity.this, R.string.msg_get_chip_id_request, Toast.LENGTH_SHORT).show();
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

    private void initNFC() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);// 设备注册
        if (mNfcAdapter == null) {
            // 判断设备是否可用
            Toast.makeText(this, "该设备不支持NFC功能", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!mNfcAdapter.isEnabled()) {
            Toast.makeText(this, "请在系统设置中先启用NFC功能！", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        }
        mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndefDetected = new IntentFilter(
                NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndefDetected.addDataType("*/*");// text/plain
        } catch (IntentFilter.MalformedMimeTypeException e) {
        }

        IntentFilter td = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ttech = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        mNdefExchangeFilters = new IntentFilter[]{ndefDetected, ttech, td};
    }

    private void getInformation(String chipId) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("chipId", chipId);
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
        if (!msgAdapter.hasId()) {
            Toast.makeText(ManageActivity.this, R.string.msg_get_information_request, Toast.LENGTH_SHORT).show();
            return;
        }
        progersssDialog = new ProgersssDialog(ManageActivity.this);
        RequestParams requestParams = new RequestParams(updateUrl);
        requestParams.addParameter("json", Base64.encode(msgAdapter.getMsg().getBytes()));
        requestParams.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MsgUpdateBean updateBean = gson.fromJson(result, MsgUpdateBean.class);
                if (updateBean != null || updateBean.isResult()) {
                    Toast.makeText(ManageActivity.this, R.string.msg_update_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageActivity.this, R.string.msg_update_fail, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(ManageActivity.this, R.string.msg_update_fail, Toast.LENGTH_SHORT).show();
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

    private void resolvIntent(Intent intent) {
        if (!load) {
            return;
        }
        String action = intent.getAction();
        //toast(action);
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            tagFromIntent = getIntent()
                    .getParcelableExtra(NfcAdapter.EXTRA_TAG);
            getresult(tagFromIntent);
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            // 处理该intent
            tagFromIntent = getIntent()
                    .getParcelableExtra(NfcAdapter.EXTRA_TAG);
            getresult(tagFromIntent);

        } else if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            types = "Tag";
            tagFromIntent = getIntent()
                    .getParcelableExtra(NfcAdapter.EXTRA_TAG);
            getresult(tagFromIntent);
        }
    }

    void getresult(Tag tag) {
        ArrayList<String> list = new ArrayList<String>();
        types = "";
        for (String string : tag.getTechList()) {
            list.add(string);
            types += string.substring(string.lastIndexOf(".") + 1, string.length()) + ",";
        }
        types = types.substring(0, types.length() - 1);
        if (list.contains("android.nfc.tech.MifareUltralight")) {
            String str = readTagUltralight(tag);
            setNoteBody(str);
        } else if (list.contains("android.nfc.tech.NfcV")) {//完成
            NfcV tech = NfcV.get(tag);
            if (tech != null) {
                try {
                    tech.connect();
                    if (tech.isConnected()) {
                        NfcVUtil nfcVUtil = new NfcVUtil(tech);
                        String str = "";
                        byte[] by = str.getBytes();
//                        nfcVUtil.writeBlock(5,by);
                        str = nfcVUtil.readBlocks(0, 27);
                        tech.close();
                        setNoteBody(str);
                    }
                } catch (IOException e) {

                }
            }
        } else if (list.contains("android.nfc.tech.NdefFormatable")) {
            NdefMessage[] messages = getNdefMessages(getIntent());
            byte[] payload = messages[0].getRecords()[0].getPayload();
            setNoteBody(new String(payload));
        }
    }

    NdefMessage[] getNdefMessages(Intent intent) {
        //读取nfc数据
        // Parse the intent

        NdefMessage[] msgs = null;
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                // Unknown tag type
                byte[] empty = new byte[]{};
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
                NdefMessage msg = new NdefMessage(new NdefRecord[]{
                        record
                });
                msgs = new NdefMessage[]{
                        msg
                };
            }
        } else {
            finish();
        }
        return msgs;
    }

    public String readTagUltralight(Tag tag) {
        MifareUltralight mifare = MifareUltralight.get(tag);
        try {
            mifare.connect();
            StringBuffer sb = new StringBuffer();
            byte[] no10 = new byte[4];  //校验芯片
            byte[] no11 = new byte[4];  //数据块数量

            byte[] readTag = mifare.readPages(10);

            byte[] readCount = mifare.readPages(11);

            if (readTag.length >= 4) {

                for (int i = 0; i < 4; i++) {
                    no10[i] = readTag[i];
                }

                String tagStr = toStringHex(ByteArrayToHexString(no10));

                if (tagStr.equals("YAHN")) {
                    for (int i = 0; i < 4; i++) {
                        no11[i] = readCount[i];
                    }

                    String countStr = toStringHex(ByteArrayToHexString(no11));
                    int count = Integer.valueOf(countStr.trim());

                    for (int i = 12; i < (count); i++) {
                        byte[] readResult = mifare.readPages(i);
                        if (i % 4 == 0) {
                            if (i == count) {
                                byte[] codeEnd = new byte[4];
                                for (int j = 0; j < 4; j++) {
                                    codeEnd[j] = readResult[j];
                                }
                                sb.append(ByteArrayToHexString(codeEnd));
                            } else {
                                sb.append(ByteArrayToHexString(readResult));
                            }
                        }
                    }
                }
            }
            //  String  str=toStringHex(sb.toString());

            String finalResult = AESUtils.decryptToString(toStringHex(sb.toString()), "X2Am6tVLnwMMX8kVgdDk5w==");
//            String finalResult = toStringHex(sb.toString());

            return finalResult;

        } catch (IOException e) {
//            Log.e(TAG, "IOException while writing MifareUltralight message...", e);
            return "";
        } catch (Exception ee) {
//            Log.e(TAG, "IOException while writing MifareUltralight message...", ee);
            return "";
        } finally {
            if (mifare != null) {
                try {
                    mifare.close();
                } catch (IOException e) {
//                    Log.e(TAG, "Error closing tag...", e);
                }
            }
        }
    }

    private void setNoteBody(final String body) {

        if (!TextUtils.isEmpty(body)) {
            load = false;
            progersssDialog = new ProgersssDialog(ManageActivity.this);
            getInformation(body);
        } else {
            Toast.makeText(ManageActivity.this, "读取芯片失败", Toast.LENGTH_SHORT).show();
        }
        tvLoad.setBackground(getResources().getDrawable(R.drawable.btn_blue_round));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // NDEF exchange mode
        // 读取uidgetIntent()
        byte[] myNFCID = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
        ex_id = Converter.getHexString(myNFCID, myNFCID.length);
        // 读取uidgetIntent()
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mNfcAdapter == null || !mNfcAdapter.isEnabled()) {
            return;
        }

        //nfc自动读取芯片内容后调用activity的onResume
        if (mNfcAdapter != null) {
            mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, null, null);
            resolvIntent(getIntent());
        }
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
