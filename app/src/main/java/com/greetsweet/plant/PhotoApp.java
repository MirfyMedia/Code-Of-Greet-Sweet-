package com.greetsweet.plant;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;


import com.google.firebase.analytics.FirebaseAnalytics;
import com.greetsweet.plant.constantclasses.storeSharedPref;
import com.greetsweet.plant.greetapp.WriteName.WriteWebsiteNameActivity;


public class PhotoApp extends Application {
    private static PhotoApp sPhotoApp;
    private static final String TAG = PhotoApp.class.getSimpleName();
    private static PhotoApp mInstance;
    public static FirebaseAnalytics mFirebaseAnalytics;
    public void showToast(String tecxt)
    {
        Toast.makeText(this,tecxt, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sPhotoApp = this;
        mInstance = this;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        MultiDex.install(this);
     /*   FontRequest fontRequest = new FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                "Noto Color Emoji Compat",
                R.array.com_google_android_gms_fonts_certs);

        EmojiCompat.Config config = new FontRequestEmojiCompatConfig(this, fontRequest)
                .setReplaceAll(true)
                //    .setEmojiSpanIndicatorEnabled(true)
                //     .setEmojiSpanIndicatorColor(Color.GREEN)
                .registerInitCallback(new EmojiCompat.InitCallback() {
                    @Override
                    public void onInitialized() {
                        super.onInitialized();
                        Log.e(TAG, "Success");
                    }

                    @Override
                    public void onFailed(@Nullable Throwable throwable) {
                        super.onFailed(throwable);
                        Log.e(TAG, "onFailed: " + throwable.getMessage());
                    }
                });

     //   BundledEmojiCompatConfig bundledEmojiCompatConfig = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);*/
    }

    public static synchronized PhotoApp getInstance() {

        return mInstance;
    }

    public static PhotoApp getPhotoApp() {
        return sPhotoApp;
    }

    public Context getContext() {
        return sPhotoApp.getContext();
    }

    private storeSharedPref sharedpref;

    public storeSharedPref getstoreSharedpref() {
        if (sharedpref == null)
            sharedpref = new storeSharedPref(this);
        return sharedpref;

    }

    public void createLog(String j) {
        Log.i("Greetings", j);

    }
    public void ShowToast(String msg) {
        Toast.makeText(this, msg,
                Toast.LENGTH_SHORT).show();
    }

}
