package com.hari.flickrimagesearch;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.hari.flickrimagesearch.ImageSearchActivity.UIHandler;

public class FlickrManager {

	public int numberofphotos,incr;
	public static UIHandler uihandler;
	ArrayList<ImageContener> tmp = new ArrayList<ImageContener>();

	public void setNumberofPhotos(int numberofphotos) {
		this.numberofphotos = numberofphotos;
	}
  
	
	public FlickrManager() {
		// TODO Auto-generated constructor stub
		
	}

	private String createURL(int methodId, String parameter) {
		
		String method_type = "";
		String url = null;
		switch (methodId) {
		case FlickrConstant.FLICKR_PHOTOS_SEARCH_ID:
			method_type = FlickrConstant.FLICKR_PHOTOS_SEARCH_STRING;
			url = FlickrConstant.FLICKR_BASE_URL + method_type + FlickrConstant.APIKEY_SEARCH_STRING
					+ FlickrConstant.TAGS_STRING + parameter + FlickrConstant.FORMAT_STRING
					+ "&safe_search=1" + "&per_page=" + numberofphotos
					+ "&page=1" + "&media=photos+" + "&content_type=1"
					+ "&sort=relevance";
			break;

		}
		return url;
	}

	// http://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
	public void getImageURLS(ImageContener imgCon) {
		String url = createURL(FlickrConstant.FLICKR_GET_SIZES_ID, imgCon.id);
		ByteArrayOutputStream baos = URLConnector.readBytes(url);
		String json = baos.toString();
		try {
			JSONObject root = new JSONObject(json.replace("jsonFlickrApi(", "")
					.replace(")", ""));
			JSONObject sizes = root.getJSONObject("sizes");
			JSONArray size = sizes.getJSONArray("size");
			for (int i = 0; i < size.length(); i++) {
				JSONObject image = size.getJSONObject(i);
				if (image.getString("label").equals("Square")) {
					imgCon.setThumbURL(image.getString("source"));
				} else if (image.getString("label").equals("Medium")) {
					imgCon.setLargeURL(image.getString("source"));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}



	public ArrayList<ImageContener> searchImagesByTag(UIHandler uih,
			Context ctx, String tag) {
		uihandler = uih;
		String url = createURL(FlickrConstant.FLICKR_PHOTOS_SEARCH_ID, tag);

		String jsonString = null;
		try {
			if (URLConnector.isOnline(ctx)) {
				ByteArrayOutputStream baos = URLConnector.readBytes(url);
				jsonString = baos.toString();
			}
			try {
				JSONObject root = new JSONObject(jsonString.replace(
						"jsonFlickrApi(", "").replace(")", ""));
				JSONObject photos = root.getJSONObject("photos");
				JSONArray imageJSONArray = photos.getJSONArray("photo");
				for (int i = incr; i < imageJSONArray.length(); i++) {
					JSONObject item = imageJSONArray.getJSONObject(i);
					ImageContener imgCon = new ImageContener(
							item.getString("id"), item.getString("owner"),
							item.getString("secret"), item.getString("server"),
							item.getString("farm"));
					imgCon.position = i;
					Log.i("hari", "photo id is" + item.getString("id"));

					tmp.add(imgCon);

				}				
				incr+=10;
				Message msg = Message.obtain(uih,
						FlickrConstant.ID_METADATA_DOWNLOADED);
				msg.obj = tmp;
				uih.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NullPointerException nue) {
			nue.printStackTrace();
		}

		return tmp;
	}


	public void clearArray() {
		// TODO Auto-generated method stub
		tmp.clear();
	}

}
