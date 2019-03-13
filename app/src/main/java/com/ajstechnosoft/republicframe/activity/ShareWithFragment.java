package com.ajstechnosoft.republicframe.activity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.ajstechnosoft.republicframe.R;
import com.ajstechnosoft.republicframe.adapter.FrameAdapter;
import com.ajstechnosoft.republicframe.adsutility.AdsMobUtility;
import com.ajstechnosoft.republicframe.utility.BitmapUtils;
import com.ajstechnosoft.republicframe.utility.Constant;
import com.ajstechnosoft.republicframe.utility.TouchImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShareWithFragment extends AppCompatActivity implements OnClickListener {
	
	/**
	 * Ads view
	 */
	private AdView adView;

	/**
	 * Bitmap
	 */
	//private Bitmap bitmap;
	//private View mRootView;
	//private Bundle bundle;
    //private File file;
    private String MESSAGE;

    private LinearLayout /*ll_gradute_facebook,*/ ll_gradute_whatsapp, ll_gradute_save;
    private RelativeLayout rl_puppy_certificate;

    private TouchImageView preview_img;
    private ImageView iv_frame;
    private ListView lv_frame;

    private FrameAdapter frameAdapter;

    private PendingAction pendingAction = PendingAction.NONE;
    private ProgressDialog progressDialog;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.share_activity);

//        ActionBar actionBar = getActionBar();
//        if(actionBar != null)
//            actionBar.hide();


        MESSAGE = getString(R.string.ShareText) + getPackageName();

        //Initial
        initView();
        //bundle = new Bundle();
        //bundle = getArguments();
        //file = new File(bundle.getString("path"));

        /**
         * Initialize banner ads
         */
        AdsMobUtility.bannerAd(ShareWithFragment.this, adView);

        interstitialAd = AdsMobUtility.interstitialAd(ShareWithFragment.this);

        fillFramedAdapter();

        /**
         * Get the bitmap from
         */
        //byte[] byteArray = bundle.getByteArray("Photo");
        //bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        //preview_img.setImageURI(Uri.fromFile(new File(Constant.APP_DIR+Constant.TEMP_PHOTO)));//Bitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));

        preview_img.setImageBitmap(BitmapFactory.decodeFile(Constant.APP_DIR+Constant.TEMP_PHOTO));

        // Register click event
        //ll_gradute_facebook.setOnClickListener(this);
        ll_gradute_whatsapp.setOnClickListener(this);
        ll_gradute_save.setOnClickListener(this);
    }

    /*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRootView = inflater.inflate(R.layout.share_activity, container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		*//**
		 * Initialize facebook SDK
		 *//*
		MESSAGE = getString(R.string.ShareText) + getActivity().getPackageName();

        //Initial
        initView();
		bundle = new Bundle();
		bundle = getArguments();
		//file = new File(bundle.getString("path"));
		
		*//**
		 * Initialize banner ads
		 *//*
		AdsMobUtility.bannerAd(getActivity(), adView);

        fillFramedAdapter();

		*//**
		 * Get the bitmap from 
		 *//*
		byte[] byteArray = bundle.getByteArray("Photo");
		//bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        preview_img.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
		

        // Register click event
        //ll_gradute_facebook.setOnClickListener(this);
        ll_gradute_whatsapp.setOnClickListener(this);
        ll_gradute_save.setOnClickListener(this);

		return mRootView; 
	}*/

	/**
	 * Initialize view controller
	 */
	private void initView() {

        lv_frame = (ListView) findViewById(R.id.lv_frame);
        rl_puppy_certificate = (RelativeLayout) findViewById(R.id.rl_puppy_certificate);

		//ll_gradute_facebook = (LinearLayout) mRootView.findViewById(R.id.ll_gradute_facebook);
		ll_gradute_whatsapp = (LinearLayout)findViewById(R.id.ll_gradute_whatsapp);
		ll_gradute_save = (LinearLayout) findViewById(R.id.ll_gradute_save);

        preview_img = (TouchImageView) findViewById(R.id.preview_img);
        iv_frame = (ImageView) findViewById(R.id.iv_frame);

		adView = (AdView)findViewById(R.id.adView);
		
	}

	@Override
	public void onClick(View v) {

	    if(interstitialAd != null && interstitialAd.isLoaded())
            interstitialAd.show();

        rl_puppy_certificate.buildDrawingCache();
		switch (v.getId()) {

           /* case R.id.ll_gradute_facebook:
				onClickPostPhoto();
				break;*/
				
			case R.id.ll_gradute_whatsapp:
				shareWhatsApp();
				break;
				
			case R.id.ll_gradute_save:
                BitmapUtils.storePhoto(ShareWithFragment.this, rl_puppy_certificate.getDrawingCache(), Constant.APP_DIR+Constant.FRAMED_DIR);
                Toast.makeText(ShareWithFragment.this, "Photo Saved! ",Toast.LENGTH_LONG).show();
				break;

		}
	}
	
	
	/**
	 * Shar e into facebook
	 * @author Jayshree Krishna
	 *
	 */
	 private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
	 }
	 

	 
	 

	/**
	 * share photo into whatsapp
	 */
	private void shareWhatsApp() {

        File file = new File( BitmapUtils.storePhoto(ShareWithFragment.this, rl_puppy_certificate.getDrawingCache(), Constant.APP_DIR+Constant.FRAMED_DIR));

        Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_TEXT, MESSAGE);
		intent.setType("text/plain");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(ShareWithFragment.this, getPackageName()+".fileProvider", file);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
        } else {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        }

		intent.setType("image/jpeg");
		intent.setPackage("com.whatsapp");
		startActivity(intent);
	}
	
	/**
	 * Share button press
	 */
	public void shareButtonPressed() {
		
        // uri to the image you want to share
        //Uri path = Uri.fromFile(file);
        Resources resources = getResources();

        // create email intent first to remove bluetooth + others options
        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_TEXT, MESSAGE);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
	//	emailIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
        emailIntent.setType("image/jpeg");
        //Intent openInChooser = Intent.createChooser(emailIntent, "Select");

        // Check for other packages that open our mime type
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/jpeg");
		sendIntent.putExtra(Intent.EXTRA_TEXT, MESSAGE);
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
		//sendIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));

        PackageManager pm = getPackageManager();
        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();

		for(int i = 0; i < resInfo.size(); i++) {

            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if(packageName.contains("android.email")) {

                emailIntent.setPackage(packageName);

            } else if(packageName.contains("com.instagram.android") ||
					packageName.contains("com.twitter.android") ||
					packageName.contains("com.whatsapp") ||
					packageName.contains("mms") ||
					packageName.contains("android.gm")) {

                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
				intent.putExtra(Intent.EXTRA_TEXT, MESSAGE);
				intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
				Uri picUri;
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
					intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				//picUri = FileProvider.getUriForFile(getActivity(), "com.skillBrighter.makemesantaprank.fileProvider", file);
				} else {
					//picUri = Uri.fromFile(file);
				}

				//intent.putExtra(Intent.EXTRA_STREAM, picUri);
                intent.setType("image/jpeg");

                if(packageName.contains("twitter")){
                	
                    intent.putExtra(Intent.EXTRA_TEXT, MESSAGE);
                    
                } else if(packageName.contains("mms")) {
                	
                    intent.putExtra(Intent.EXTRA_TEXT, "Awesome App!!!");
                    
                } else if(packageName.contains("android.gm")) {
                	
                    intent.putExtra(Intent.EXTRA_TEXT, MESSAGE);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Awesome App!!!");
                }
                
                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
            }
        }

		startActivityForResult(sendIntent, 1);
	}

	private void fillFramedAdapter() {

        frameAdapter = new FrameAdapter(ShareWithFragment.this);
        lv_frame.setAdapter(frameAdapter);
        lv_frame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                iv_frame.setImageResource(Constant.FRAMED_ID[position]);
            }
        });

    }
}
