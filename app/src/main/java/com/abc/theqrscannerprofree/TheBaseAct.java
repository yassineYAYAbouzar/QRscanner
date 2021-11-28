package com.abc.theqrscannerprofree;

import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.abc.theqrscannerprofree.helper.NamedFactoryHlp;
import com.abc.theqrscannerprofree.utils.SharedPreferencesUtilsLikeFlutter;
import com.onesignal.OneSignal;


public class TheBaseAct extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .autoPromptLocation(true)

                .init();

        SharedPreferencesUtilsLikeFlutter.initSharedReferences(this);
        NamedFactoryHlp.setHelper(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
