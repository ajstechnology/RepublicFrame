package com.ajstechnosoft.republicframe.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.ajstechnosoft.republicframe.R;
import com.ajstechnosoft.republicframe.activity.ShareWithFragment;
import com.ajstechnosoft.republicframe.adapter.SlideShowPagerAdapter;
import com.ajstechnosoft.republicframe.adsutility.AdsMobUtility;
import com.ajstechnosoft.republicframe.utility.Constant;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class SecondScreenFragment extends Fragment implements OnClickListener {

	protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
	protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

	/**
	 * Image view 
	 */
	private ImageView iv_main_camera, iv_main_gallery;

    //Textvew
    private TextView tv_second_title;
	
	/**
	 * Bitmap
	 */
	private Bitmap humanBitmap;
	
	/**
	 * Ads view
	 */
	private AdView ads_second_adsView;
	
	/**
	 * InterstitialAd
	 */
	private AutoScrollViewPager autoScrollViewPager;
	private View mRootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRootView = inflater.inflate(R.layout.second_screen, container, false);

		//Initialization
		setUpView();
        //setAnimation();
		AdsMobUtility.bannerAd(getActivity(), ads_second_adsView);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"VeryChristmess.ttf");
        //tv_second_title.setTypeface(tf, Typeface.BOLD);

        autoScrollViewPager.setAdapter(new SlideShowPagerAdapter(getActivity()));
        autoScrollViewPager.startAutoScroll();
        autoScrollViewPager.setInterval(1000*3);
        autoScrollViewPager.setCycle(true);
        autoScrollViewPager.setBorderAnimation(true);


		
		/**
		 * Register click  event
		 */
		iv_main_camera.setOnClickListener(this);
		iv_main_gallery.setOnClickListener(this);
		tv_second_title.setOnClickListener(this);

		return mRootView;
	}
	
	/***
	 * Initialize all view controller
	 */
	private void setUpView() {
		
		/**
		 * Image view
		 */
		iv_main_camera = (ImageView) mRootView.findViewById(R.id.iv_main_camera);
		iv_main_gallery = (ImageView) mRootView.findViewById(R.id.iv_main_gallery);
		
		/**
		 * ad view 
		 */
		ads_second_adsView = (AdView) mRootView.findViewById(R.id.ads_second_adsView);

        //View pager
        autoScrollViewPager = (AutoScrollViewPager) mRootView.findViewById(R.id.autoScrollViewPager);

        //Text view
        tv_second_title = (TextView) mRootView.findViewById(R.id.tv_second_title);
	}



	/***
	 * Onclick
	 */
	@Override
	public void onClick(View v) {

        //interstitialAds.show();

        if(checkCameraPermission() && checkReadPermission() && checkWritePermission()) {

			switch (v.getId()) {

							case R.id.iv_main_camera:
			                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							startActivityForResult(captureIntent, 1);
								break;

							case R.id.iv_main_gallery:

								Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
								startActivityForResult(i, 2);
								break;
            }
        }

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == getActivity().RESULT_OK) {

			if (requestCode == 1) {

				humanBitmap = data.getParcelableExtra("data");

		    } else if(requestCode == 2) {


				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();


				try {
					//BitmapFactory.decodeFile(picturePath);
					humanBitmap = BitmapFactory.decodeFile(picturePath);;//getBitmapFromUri(selectedImage);
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }

            storeImage();

			/*ByteArrayOutputStream stream = new ByteArrayOutputStream();
			humanBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
			byte[] byteArray = stream.toByteArray();

			Bundle imageEditBundle = new Bundle();
			imageEditBundle.putByteArray("Photo", byteArray);*/

			startActivity(new Intent(getActivity(), ShareWithFragment.class));
			//ShareWithFragment imageEditFragment = new ShareWithFragment();
			//imageEditFragment.setArguments(imageEditBundle);
			//((MainActivity)getActivity()).replaceFragment(imageEditFragment);


		}
	}

	private Bitmap getBitmapFromUri(Uri uri) throws IOException {

		ParcelFileDescriptor parcelFileDescriptor = getActivity().getContentResolver().openFileDescriptor(uri, "r");
		FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
		Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
		parcelFileDescriptor.close();
		return image;
	}

	private boolean checkReadPermission(){
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			return true;
		}
		if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {
			requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,"Storage read permission is needed to pick files.",
					REQUEST_STORAGE_READ_ACCESS_PERMISSION);
			return false;
		}
		return true;
	}

	private boolean checkWritePermission(){
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			return true;
		}
		if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {
			requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
					"Storage write permission is needed to save the image.",
					REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
			return false;
		}
		return true;
	}

	private boolean checkCameraPermission(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    "Camera permission is needed to capture image.",
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
            return false;
        }
        return true;
    }

	/**
	 * Requests given permission.
	 * If the permission has been denied previously, a Dialog will prompt the user to grant the
	 * permission, otherwise it is requested directly.
	 */
	protected void requestPermission(final String permission, String rationale, final int requestCode) {
		if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
			showAlertDialog("Permission needed", rationale,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							ActivityCompat.requestPermissions(getActivity(),new String[]{permission}, requestCode);
						}
					}, "Ok", null, "Cancel");
		} else {
			ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
		}
	}


	/**
	 * This method shows dialog with given title & message.
	 * Also there is an option to pass onClickListener for positive & negative button.
	 *
	 * @param title                         - dialog title
	 * @param message                       - dialog message
	 * @param onPositiveButtonClickListener - listener for positive button
	 * @param positiveText                  - positive button text
	 * @param onNegativeButtonClickListener - listener for negative button
	 * @param negativeText                  - negative button text
	 */
	protected void showAlertDialog(@Nullable String title, @Nullable String message,
                                   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   @NonNull String positiveText,
                                   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   @NonNull String negativeText) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
		builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
		builder.show();
	}

    private void storeImage() {

	    File dir = new File(Constant.APP_DIR);
	    if(!dir.exists())
            dir.mkdirs();

	    File photo = new File(dir, Constant.TEMP_PHOTO);

        try {
            FileOutputStream fos = new FileOutputStream(photo);
            humanBitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (Exception e) {
            Log.d("", "Error accessing file: " + e.getMessage());
        }
    }


}
