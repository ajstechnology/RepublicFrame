package com.ajstechnosoft.republicframe.utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class BitmapUtils {
	
	public static java.text.DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_hhmmss_aa");
	//public static final String HAIR_PHOTO_PATH  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/HairMustacheGallery/";
	
	/**
	 * Retrive the store photo
	 * @param path
	 * @return
	 */
	public static List<String> RetriveCapturedImagePath(String path) {
		List<String> tFileList = new ArrayList<String>();
		File f = new File(path);
		if (f.exists()) {
			File[] files = f.listFiles();
			Arrays.sort(files);

			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isDirectory())
					continue;
				tFileList.add(file.getPath());
			}
		}
		return tFileList;
	}

	/**
	 * Store edit photo
	 * @param bitmap
	 * @param dirPath
	 */
	public static String storePhoto(Context context, Bitmap bitmap,String dirPath) {
		
		String imgcurTime = dateFormat.format(new Date());
		File imageDirectory = new File(dirPath);
		if(!imageDirectory.exists())
			imageDirectory.mkdirs();
		
		String _path = dirPath + imgcurTime + ".jpeg";
		
		try {
			FileOutputStream out = new FileOutputStream(_path);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.close();
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}

		instantallySetImageInDeviceGallery(_path, context);
		return _path;
	}
	
	
	/**
	 * This methods isused to show photo into gallery
	 */
	private static void instantallySetImageInDeviceGallery(String imagePath, Context context) {

		// Save Image To Gallery
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(imagePath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);
	}
}
