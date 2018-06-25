package com.gxg.demo8.mydemo8.tinker;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.socks.library.KLog;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by gaoxuge on 2017/11/2.
 *
 */
@DefaultLifeCycle(application = ".MyApp",//而这个类是真正的自定义的application,别忘了在AndroidManifest.xml中使用
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag =true)
public class MyApplicationLike extends ApplicationLike {
    public MyApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        TinkerInstaller.install(this);

    }
    @Override
    public void onCreate() {
        super.onCreate();
        KLog.e("sss 初始化");
    }
}
