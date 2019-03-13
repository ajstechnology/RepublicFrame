package com.ajstechnosoft.republicframe.adsutility;

import android.app.Activity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.reward.RewardedVideoAd;

/**
 * Created by Rid's Patel on 21-07-2017.
 */

public class AdsMobUtility {

    public static final String APP_ID = "ca-app-pub-3704545923813865~8697811974";
    public static final String INTERSTITAL_ADS_UNIT = "ca-app-pub-3704545923813865/3472748912";

    public static void initAdsMob(Activity activity) {
        MobileAds.initialize(activity, APP_ID);
    }

    public static void bannerAd(Activity activity, AdView adView) {

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);
    }

    public static void nativeExpressAd(Activity activity, NativeExpressAdView adView) {

        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);
    }

    public static InterstitialAd interstitialAd(Activity activity) {

        InterstitialAd mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(INTERSTITAL_ADS_UNIT);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
        return mInterstitialAd;

    }

    /*public static RewardedVideoAd rewardedVideoAds(Activity activity) {

        final RewardedVideoAd mAd = MobileAds.getRewardedVideoAdInstance(activity);
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                mAd.loadAd(REWARDED_VIDEO_ADS_UNIT,
                        new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //mAd.show();

            }
        };
        return mAd;
    }*/

    public static void rewardedVideoShow(RewardedVideoAd rewardedVideoAd) {
        if(rewardedVideoAd != null && rewardedVideoAd.isLoaded())
            rewardedVideoAd.show();
    }
}
