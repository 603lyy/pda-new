
package com.yaheen.pdaapp;

import android.app.Instrumentation;
import android.content.Context;
import android.text.TextUtils;

import com.yaheen.pdaapp.util.FreeHandSystemUtil;

abstract public class Application extends android.app.Application {
    private static Application instance;

    private static Thread mUIThread;

    // 安全可靠的设备唯一码
    private String safeUUid;

    public Application() {
    }

    /**
     * Create main application
     * 
     * @param context
     */
    public Application(final Context context) {
        this();
        attachBaseContext(context);
    }

    /**
     * Create main application
     * 
     * @param instrumentation
     */
    public Application(final Instrumentation instrumentation) {
        this();
        attachBaseContext(instrumentation.getTargetContext());
    }

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 生成设备唯一ID
        safeUUid = FreeHandSystemUtil.getSafeUUID(this);
        mUIThread = Thread.currentThread();
        // Perform injection
        // FacadeInjector.init(getRootModule(), this);
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
