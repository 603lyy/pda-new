
package com.yaheen.pdaapp;

import android.app.Instrumentation;
import android.content.Context;
import android.text.TextUtils;

import com.tencent.bugly.crashreport.CrashReport;
import com.yaheen.pdaapp.util.FreeHandSystemUtil;

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
}
