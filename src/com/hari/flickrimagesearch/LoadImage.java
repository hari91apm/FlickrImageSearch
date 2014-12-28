package com.hari.flickrimagesearch;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class LoadImage extends  AsyncTask<Object, Void, Bitmap> {
	  private ImageView image;
    private Bitmap bitmap;

	public LoadImage(ImageView image) {
		// TODO Auto-generated constructor stub
		
		bitmap=this.bitmap;
		image=this.image;
		   Log.i("hari","loadImages bitmap is"+bitmap);
	}

	@Override
	protected Bitmap doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return bitmap;
	}

	 @Override
	    protected void onPostExecute(Bitmap result) {
	      Log.i("hari","onPostExecute bitmap is"+result);

	        if(result != null && image != null){
	            image.setVisibility(View.VISIBLE);
	            image.setImageBitmap(result);
	        }
	    }

}
