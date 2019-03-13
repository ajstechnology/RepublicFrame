package com.ajstechnosoft.republicframe.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;


import com.ajstechnosoft.republicframe.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProjectUtils {

	

	/* Share application play store link with application logo*/
	public static void shareLink(Context context) {

		String sAux = context.getString(R.string.ShareText)+context.getPackageName();

		Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);

		sharingIntent.setDataAndType(Uri.parse("file:///sdcard/republic.jpg"), "image/jpeg/text/plain");

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		largeIcon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		File f = new File(Environment.getExternalStorageDirectory() + File.separator + "republic.jpg");
		try {
			f.createNewFile();
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(bytes.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}

		sharingIntent.putExtra("sms_body", sAux);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			sharingIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Uri uri = FileProvider.getUriForFile(context, context.getPackageName()+".fileProvider", f);
			sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
		} else {
			sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/republic.jpg"));
		}

		sharingIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
		sharingIntent.putExtra(Intent.EXTRA_TEXT, sAux);
		sharingIntent.putExtra(Intent.ACTION_ATTACH_DATA, sAux);
		context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}

	/**
	 * show the list of application of publisher
	 * @param context
	 * @param
	 */
	public static void moreApp(Context context) {

		String PUBLISHER_NAME = "Skill Brighter Infotech";
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("market://search?q=pub:"+PUBLISHER_NAME));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	//Give Feedback
	public static void giveFeedBack(final Context context){

		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.rate_dialog);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.gravity = Gravity.BOTTOM;
		lp.windowAnimations = R.style.DialogAnimation;
		dialog.getWindow().setAttributes(lp);

		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

		RatingBar ratView = (RatingBar) dialog.findViewById(R.id.ratView);
		ImageView iv_close=(ImageView)dialog.findViewById(R.id.iv_close);
		Button btn_yes=(Button) dialog.findViewById(R.id.btn_yes);
		iv_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		});

		btn_yes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
				Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
				myAppLinkToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				try {
					((Activity)context).startActivity(myAppLinkToMarket);
				} catch (ActivityNotFoundException e) {
					Toast.makeText(context, " unable to find market app", Toast.LENGTH_LONG).show();
				}
				dialog.dismiss();
			}
		});
		dialog.show();
	}

}
