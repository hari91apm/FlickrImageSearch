package com.hari.flickrimagesearch;

public class FlickrConstant {

	public static final String FLICKR_BASE_URL = "https://api.flickr.com/services/rest/?method=";
	public static final String FLICKR_PHOTOS_SEARCH_STRING = "flickr.photos.search";
	public static final String FLICKR_GET_SIZES_STRING = "flickr.photos.getSizes";
	static final int FLICKR_PHOTOS_SEARCH_ID = 1;
	public static final int FLICKR_GET_SIZES_ID = 2;
	public static final String APIKEY_SEARCH_STRING = "&api_key=64c0f179f8aec0444033c8b2c57a7db0";
    
	public static final String TAGS_STRING = "&tags=";
	public static final String PHOTO_ID_STRING = "&photo_id=";
	public static final String FORMAT_STRING = "&format=json";
	public static final int PHOTO_THUMB = 220;
	public static final int PHOTO_LARGE = 222;
	
	
	public static final int ID_METADATA_DOWNLOADED = 0;
	public static final int ID_SHOW_IMAGE = 1;
	public static final int ID_UPDATE_ADAPTER = 2;
	
}
