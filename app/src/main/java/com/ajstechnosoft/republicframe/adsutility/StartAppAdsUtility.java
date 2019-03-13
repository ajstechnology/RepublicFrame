package com.ajstechnosoft.republicframe.adsutility;

import android.app.Activity;
import android.os.Bundle;

import com.startapp.android.publish.ads.nativead.NativeAdPreferences;
import com.startapp.android.publish.ads.nativead.StartAppNativeAd;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

/**
 * Created by Rid's Patel on 20-07-2017.
 */

public class StartAppAdsUtility {

    private static String APP_ID = "206984552";

    //Start App ads
    public static void initStartAppAds(Activity context) {
        StartAppSDK.init(context, APP_ID , true);
    }

    public static void showSplashScreen(Activity activity, Bundle savedInstanceState) {

      /*  StartAppAd.showSplash(activity, savedInstanceState, new SplashConfig()
                .setTheme(SplashConfig.Theme.USER_DEFINED)
                .setCustomScreen(R.layout.splash_screen));*/
    }

    public static void startAppOnBack(Activity activity) {
        StartAppAd.onBackPressed(activity);
    }

    public static void standardInterstitials(Activity activity) {
        StartAppAd.showAd(activity);
    }

    public static void offerWall(Activity activity) {
        StartAppAd startAppAd = new StartAppAd(activity);
        startAppAd.loadAd(StartAppAd.AdMode.OFFERWALL);
    }

    public static void showAd(Activity activity) {
        StartAppAd startAppAd = new StartAppAd(activity);
        startAppAd.showAd(activity);
    }
    public static void nativeAds(Activity activity) {
        StartAppNativeAd startAppNativeAd = new StartAppNativeAd(activity);
        startAppNativeAd.loadAd(new NativeAdPreferences());
    }
}
