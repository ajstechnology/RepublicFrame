package com.ajstechnosoft.republicframe.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdView;
import com.ajstechnosoft.republicframe.R;
import com.ajstechnosoft.republicframe.adsutility.AdsMobUtility;
import com.ajstechnosoft.republicframe.adsutility.StartAppAdsUtility;
import com.ajstechnosoft.republicframe.fragment.SecondScreenFragment;
import com.ajstechnosoft.republicframe.utility.ProjectUtils;
import com.startapp.android.publish.adsCommon.StartAppAd;


/**
 * Created by hirenk on 1/18/16.
 * Information related to fragment back stack:- https://github.com/rathodchintan/Fragment-Back-Stack
 * http://stackoverflow.com/questions/18305945/how-to-resume-fragment-from-backstack-if-exists
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    /* Image view */
    private ImageView lv_footer_share, lv_footer_rate, lv_footer_more;

    private AdView ads_gallery_adsView;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        StartAppAdsUtility.initStartAppAds(MainActivity.this);
        StartAppAd.disableSplash();
        StartAppAdsUtility.showSplashScreen(MainActivity.this, savedInstanceState);

        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};


        if(!hasPermissions(MainActivity.this, PERMISSIONS)){
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, 1);
        }

        setContentView(R.layout.drawer_layout);
        /* Initialization */
        initViewController();
        
        SecondScreenFragment secondScreenFragment = new SecondScreenFragment();
        replaceFragment(secondScreenFragment);

       AdsMobUtility.initAdsMob(MainActivity.this);
        AdsMobUtility.bannerAd(MainActivity.this, ads_gallery_adsView);
        /* Register click event */
        lv_footer_share.setOnClickListener(this);
        lv_footer_rate.setOnClickListener(this);
        lv_footer_more.setOnClickListener(this);

    }

    /* Initialization all view controller */
    private void initViewController() {

        ads_gallery_adsView = (AdView) findViewById(R.id.ads_gallery_adsView);
        lv_footer_share = (ImageView) findViewById(R.id.lv_footer_share);
        lv_footer_rate = (ImageView) findViewById(R.id.lv_footer_rate);
        lv_footer_more = (ImageView) findViewById(R.id.lv_footer_more);

    }

    /**
     * On Acitivty result
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed(){

        StartAppAdsUtility.startAppOnBack(MainActivity.this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){

            Intent intent = new Intent(Intent.ACTION_MAIN);
    	    intent.addCategory(Intent.CATEGORY_HOME);
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		startActivity(intent);
    		return;
            
        } else {

            super.onBackPressed();
        }
    }

    /**
     * Replace current selected fragment
     * @param fragment
     */
    public void replaceFragment (Fragment fragment){

        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_container, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    @Override
    public void onClick(View v) {

        AdsMobUtility.initAdsMob(MainActivity.this);
        switch (v.getId()) {

            case R.id.lv_footer_share:
                ProjectUtils.shareLink(MainActivity.this);
                break;

            case R.id.lv_footer_rate:
                ProjectUtils.giveFeedBack(MainActivity.this);
                /*Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), " unable to find market app", Toast.LENGTH_LONG).show();
                }*/
                break;

            case R.id.lv_footer_more:

                ProjectUtils.moreApp(getApplicationContext());
                break;
        }
    }

    /**
     * Check permission
     * @param context
     * @param permissions
     * @return
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}

