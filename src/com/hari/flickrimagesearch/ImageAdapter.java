package com.hari.flickrimagesearch;

import java.util.ArrayList;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;	
	ArrayList<ImageContener> imageContener;

	public ArrayList<ImageContener> getImageContener() {
		return imageContener;
	}

	public void setImageContener(ArrayList<ImageContener> imageContener) {
		this.imageContener = imageContener;
	}

	public ImageAdapter(Context c, ArrayList<ImageContener> imageContener) {
		mContext = c;
		this.imageContener = imageContener;
		
	}

	public int getCount() {
		return imageContener.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView image= new ImageView(mContext);
		if (imageContener.get(position).thumb != null) {
			Bitmap bitmap=imageContener.get(position).thumb;
			
			//image.setImageBitmap(imageContener.get(position).thumb);
			image.setImageBitmap(imageContener.get(position).thumb);
			image.setLayoutParams(new GridView.LayoutParams(
					LayoutParams.WRAP_CONTENT, 250));
			// i.setBackgroundResource(defaultItemBackground);
			image.setScaleType(ImageView.ScaleType.CENTER_CROP);
			image.setPadding(8, 8, 8, 8);
		} 
		else
			// i = (ImageView) convertView;
			image.setImageDrawable(mContext.getResources().getDrawable(
					android.R.color.black));
		return image;
	}

}