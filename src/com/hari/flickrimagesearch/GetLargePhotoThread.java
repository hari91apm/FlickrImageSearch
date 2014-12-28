package com.hari.flickrimagesearch;

import android.graphics.Bitmap;
import android.os.Message;

import com.hari.flickrimagesearch.ImageSearchActivity.UIHandler;

public class GetLargePhotoThread extends Thread {
	ImageContener ic;
	UIHandler uih;

	public GetLargePhotoThread(ImageContener ic, UIHandler uih) {
		this.ic = ic;
		this.uih = uih;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (ic.getPhoto() == null) {
		//	ic.setPhoto(FlickrManager.getImage(ic));
		}
		Bitmap bmp = ic.getPhoto();
		if (ic.getPhoto() != null) {
			Message msg = Message.obtain(uih, FlickrConstant.ID_SHOW_IMAGE);
			msg.obj = bmp;
			uih.sendMessage(msg);
		}
	}
}