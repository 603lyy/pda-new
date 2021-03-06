
package com.yaheen.pdaapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.magicrf.uhfreaderlib.reader.UhfReader;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.yaheen.pdaapp.util.FreeHandSystemUtil;

import org.xutils.x;

public class BaseApp extends android.app.Application {
    private static BaseApp instance;

    private static Thread mUIThread;

    // 安全可靠的设备唯一码
    private String safeUUid;

    public BaseApp() {
    }

    /**
     * Create main application
     * 
     * @param context
     */
    public BaseApp(final Context context) {
        this();
        attachBaseContext(context);
    }

    /**
     * Create main application
     * 
     * @param instrumentation
     */
    public BaseApp(final Instrumentation instrumentation) {
        this();
        attachBaseContext(instrumentation.getTargetContext());
    }

    public static BaseApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 生成设备唯一ID
        safeUUid = FreeHandSystemUtil.getSafeUUID(this);
        mUIThread = Thread.currentThread();

        //bugly崩溃收集
        CrashReport.initCrashReport(getApplicationContext(), "fc6c41d098", true);

        //网络请求
        x.Ext.init(this);
        x.Ext.setDebug(false); //输出debug日志，开启会影响性能

        //RFID
        UhfReader.setPortPath("/dev/ttyS2");

        //x5内核
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + b);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    public String getSafeUUid() {
        if (TextUtils.isEmpty(safeUUid))
            safeUUid = FreeHandSystemUtil.getSafeUUID(this);
        return safeUUid;
    }

    // protected abstract Object getRootModule();

    /**
     * 获取UI线程
     * 
     * @return
     */
    public static Thread getUIThread() {
        return mUIThread;
    }


    /**
     * 应用退出，结束所有的activity,没有考虑到多线程环境下的退出
     */
    public static void exit() {
        System.exit(0);
    }
}
