package com.ajstechnosoft.republicframe.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.ajstechnosoft.republicframe.R;
import com.ajstechnosoft.republicframe.utility.Constant;


public class FrameAdapter extends BaseAdapter {

	private Context context;
	private int itemBackground;

	public FrameAdapter(Context c) {
		context = c;
		// sets a grey background; wraps around the images
		TypedArray a = c.obtainStyledAttributes(R.styleable.MyGallery);
		itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
		a.recycle();
	}

	// returns the number of images
	public int getCount() {
		
		return Constant.FRAMED_ID.length;
	}

	// returns the ID of an item
	public Object getItem(int position) {
		
		return position;
	}

	// returns the ID of an item
	public long getItemId(int position) {
		
		return position;
	}

	// returns an ImageView view
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ImageView imageView = new ImageView(context);
		imageView.setImageResource(Constant.FRAMED_ID[position]);
		imageView.setLayoutParams(new Gallery.LayoutParams(150, 150));
		imageView.setBackgroundResource(itemBackground);
		//imageView.setScaleType(ScaleType.FIT_XY);
		return imageView;
	}
}
