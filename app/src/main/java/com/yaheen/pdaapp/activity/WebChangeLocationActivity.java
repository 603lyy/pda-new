package com.yaheen.pdaapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yaheen.pdaapp.R;
import com.yaheen.pdaapp.bean.CheckBean;
import com.yaheen.pdaapp.util.DialogUtils;
import com.yaheen.pdaapp.util.ProgersssDialog;
import com.yaheen.pdaapp.util.dialog.DialogCallback;
import com.yaheen.pdaapp.util.dialog.IDialogCancelCallback;
import com.yaheen.pdaapp.widget.WebJavaScriptProvider;
import com.yaheen.pdaapp.widget.X5WebView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class WebChangeLocationActivity extends BaseActivity {

    private final static String SCAN_ACTION = "scan.rcv.message";

    private Gson gson = new Gson();

    private ViewGroup mViewParent;

    private X5WebView mWebView;

    private String checkUrl = "http://shortlink.cn/eai/getShortLinkCompleteInformation.do";

    private String url = baseUrl + "tool/toUpdateLocation.do?shortLinkCode=";

    private String webBaseUrl = baseUrl + "tool/toUpdateLocation.do";

//    private String url = "https://lyl.tunnel.echomod.cn/whnsubhekou/tool/toUpdateLocation.do?shortLinkCode=";
//
//    private String baseUrl = "https://lyl.tunnel.echomod.cn/whnsubhekou/tool/toUpdateLocation.do";

    private String shortCode = "";

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mViewParent = findViewById(R.id.web_parent);

        init();
        showLoadingDialog();
        loadUrl();
    }

    private void loadUrl() {
//        mWebView.loadUrl("file:///android_asset/web.html");
        shortCode = shortCode.substring(shortCode.lastIndexOf("/") + 1);
        if (!TextUtils.isEmpty(shortCode)) {
            mWebView.loadUrl(url + shortCode);
        } else {
            mWebView.loadUrl(webBaseUrl);
        }
    }

    private void init() {
        mWebView = new X5WebView(this, null);

        mViewParent.addView(mWebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT));

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = ContextCompat.checkSelfPermission(WebChangeLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(WebChangeLocationActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                ActivityCompat.requestPermissions(WebChangeLocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                initWebViewSetting();
            }
        }
    }

    /**
     * init WebView
     */
    private void initWebViewSetting() {
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);

        //手机屏幕适配
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setUseWideViewPort(true);

        //禁止放大
        webSetting.setBuiltInZoomControls(false);
        webSetting.setSupportZoom(false);
        webSetting.setDisplayZoomControls(false);

        //启用数据库
        webSetting.setDatabaseEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);//支持JavaScriptEnabled
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        //启用地理定位
        webSetting.setGeolocationEnabled(true);
        //设置定位的数据库路径
        webSetting.setGeolocationDatabasePath(dir);
        //最重要的方法，一定要设置，这就是出不来的主要原因
        webSetting.setDomStorageEnabled(true);

        mWebView.setWebChromeClient(webChromeClient);

        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();

        mWebView.addJavascriptInterface(new FetchProvider(this, this), "android");
    }

    class FetchProvider extends WebJavaScriptProvider {

        public FetchProvider(Context ctx, BaseActivity activity) {
            super(ctx, activity);
        }

        @JavascriptInterface
        public void openFetch(String mark) {
            scanUtils.start();
        }

        @JavascriptInterface
        public void showNormalToast() {
            showGoDialog(R.string.web_activity_not_bind_short_link_normal, false);
        }

        @JavascriptInterface
        public void showRefreshToast() {
            showGoDialog(R.string.web_activity_not_bind_short_link_refresh, true);
        }

        @JavascriptInterface
        public void showRefreshLocationToast() {
            shortCode = "";
            loadUrl();
        }

        @JavascriptInterface
        public void back() {
            finish();
        }

    }

    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            byte[] barocode = intent.getByteArrayExtra("barocode");
            int barocodelen = intent.getIntExtra("length", 0);
            shortCode = new String(barocode, 0, barocodelen);
            showLoadingDialog();
            scanUtils.stop();
            check(shortCode);
        }

    };

    private void check(String slink) {

        if (TextUtils.isEmpty(slink)) {
            Toast.makeText(this, R.string.bind_activity_short_link_empty, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(WebChangeLocationActivity.this, R.string.scan_not, Toast.LENGTH_SHORT).show();
                    cancelLoadingDialog();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(WebChangeLocationActivity.this, R.string.scan_fail, Toast.LENGTH_SHORT).show();
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

    private void checkShortLink(CheckBean.EntityBean date) {

        if (date == null) {
            Toast.makeText(this, R.string.scan_fail, Toast.LENGTH_SHORT).show();
            cancelLoadingDialog();
            return;
        }

        //长链接不为空
        if (!TextUtils.isEmpty(date.getLink())) {
            //门牌ID为空,即已经录入数据，重新加载界面
            if (TextUtils.isEmpty(date.getNote())) {
                loadUrl();
//                showGoDialog(R.string.web_activity_not_bind_short_link, false);
            }
            //门牌ID不为空，即门牌已经可以正常使用
            else {
                loadUrl();
            }
        } else {
            //门牌ID为空,提示短链接未被使用
            if (TextUtils.isEmpty(date.getNote())) {
                Toast.makeText(this, R.string.web_activity_short_link_unuse, Toast.LENGTH_SHORT).show();
            }
            //门牌ID不为空，提示未录入数据
            else {
                Toast.makeText(this, R.string.web_activity_need_data, Toast.LENGTH_SHORT).show();
            }
            cancelLoadingDialog();
        }
    }

    private void showGoDialog(int string, final boolean refresh) {
        DialogUtils.showNormalDialog(this, getString(string),
                new DialogCallback() {
                    @Override
                    public void callback() {
                        if (refresh) {
                            mWebView.loadUrl("javascript:myrefresh()");
                        }
                        showToast("chenggong");
                    }
                },
                new IDialogCancelCallback() {
                    @Override
                    public void cancelCallback() {
                        if (refresh) {
                            mWebView.loadUrl("javascript:myrefresh()");
                        }
                    }
                }, getString(R.string.web_activity_go), getString(R.string.web_activity_not_go),
                getString(R.string.web_activity_dialog_title));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        initWebViewSetting();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                cancelLoadingDialog();
            }
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String s, GeolocationPermissionsCallback geolocationPermissionsCallback) {
            geolocationPermissionsCallback.invoke(s, true, true);
            super.onGeolocationPermissionsShowPrompt(s, geolocationPermissionsCallback);
        }
    };

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
