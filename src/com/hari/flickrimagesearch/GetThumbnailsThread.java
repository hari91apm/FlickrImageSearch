package com.hari.flickrimagesearch;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.util.Log;

import com.hari.flickrimagesearch.ImageSearchActivity.UIHandler;

public class GetThumbnailsThread extends Thread {
	UIHandler uih;
	ImageContener imgContener;

	public GetThumbnailsThread(UIHandler uih, ImageContener imgCon) {
		this.uih = uih;
		this.imgContener = imgCon;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		imgContener.thumb = getThumbnail(imgContener);
		if (imgContener.thumb != null) {
			Message msg = Message.obtain(uih, FlickrConstant.ID_UPDATE_ADAPTER);
			uih.sendMessage(msg);

		}
	}

	public static Bitmap getThumbnail(ImageContener imgCon) {
		Bitmap bm = null;
		try {
			URL aURL = new URL(imgCon.thumbURL);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (Exception e) {
			bm.recycle();
			Log.e("FlickrManager", e.getMessage());
		}
		return bm;
	}

}