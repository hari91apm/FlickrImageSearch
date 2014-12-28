package com.hari.flickrimagesearch;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImageSearchActivity extends Activity implements OnClickListener {

	public UIHandler uihandler;
	public ImageAdapter imgAdapter;
	private ArrayList<ImageContener> imageList;
	Boolean isFirst = true;
	// UI
	private Button downloadPhotos;
	private GridView gridView;
	private EditText editText;
	FlickrManager flickrmanager;
	public int numberofphotos;
	ProgressDialog progressDialog;
	TextView txtLoadmore;
	ImageView images;

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liv_image_search);
		// Init UI Handler

		uihandler = new UIHandler();
		flickrmanager = new FlickrManager();
		downloadPhotos = (Button) findViewById(R.id.button1);
		editText = (EditText) findViewById(R.id.editText1);

		images = (ImageView) findViewById(R.id.images);
		gridView = (GridView) findViewById(R.id.gridview);
		txtLoadmore = (TextView) findViewById(R.id.txtLoadmore);
		txtLoadmore.setOnClickListener(this);
		// txtLoadmore.setVisibility(View.GONE);

		// Click on search
		downloadPhotos.setOnClickListener(onSearchButtonListener);

		progressDialog = ProgressDialog.show(ImageSearchActivity.this,
				"ProgressDialog", "Wait!");

		flickrmanager.setNumberofPhotos(numberofphotos += 10);

		txtLoadmore.setVisibility(View.GONE);
		new Thread(getMetadata).start();

		gridView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				Log.i("hari", "gridview onScrollStateChanged" + scrollState);
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem + visibleItemCount >= totalItemCount) {
					txtLoadmore.setVisibility(View.VISIBLE);
					Log.i("hari", "gridview reachead bottom" + totalItemCount
							+ "firstVisibleItem" + firstVisibleItem
							+ "visibleItemCount" + visibleItemCount);
				} else {
					txtLoadmore.setVisibility(View.GONE);
				}
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ImageView im = (ImageView) view;
				Drawable d = im.getDrawable();
				Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
//				Intent i = new Intent(ImageSearchActivity.this,
//						LIVComposeQuestionActivity.class);
//				i.putExtra("bitmapimage", bitmap);
//				startActivity(i);

				// images.setImageBitmap(bitmap);
				// gridView.setVisibility(View.GONE);
				// images.setVisibility(View.VISIBLE);
				Toast.makeText(getApplicationContext(),
						"Item Clicked: " + position, Toast.LENGTH_SHORT).show();

			}
		});

	}

	@Override
	public void onBackPressed() {
		// do something here and don't write super.onBackPressed()
//		Intent i = new Intent(ImageSearchActivity.this,
//				LIVComposeQuestionActivity.class);
//		startActivity(i);
//		finish();
	}

	Runnable getMetadata = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String tag = editText.getText().toString().trim();
			if (tag != null && tag.length() >= 3)
				flickrmanager.searchImagesByTag(uihandler,
						getApplicationContext(), tag);
		}
	};

	@SuppressLint("HandlerLeak")
	class UIHandler extends Handler {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case FlickrConstant.ID_METADATA_DOWNLOADED:
				// Set of information required to download thumbnails is
				// available now
				if (msg.obj != null) {
					imageList = (ArrayList<ImageContener>) msg.obj;
					imgAdapter = new ImageAdapter(getApplicationContext(),
							imageList);
					// gridview.setAdapter(imgAdapter);
					for (int i = 0; i < imgAdapter.getCount(); i++) {
						new GetThumbnailsThread(uihandler, imgAdapter
								.getImageContener().get(i)).start();
					}
				}
				break;

			case FlickrConstant.ID_UPDATE_ADAPTER:
				// Update adapter with thumnails
				if (isFirst == true) {
					gridView.setAdapter(imgAdapter);
					Log.i("hari", "setadapter first time");
					isFirst = false;
				} else if (isFirst == false) {
					gridView.invalidateViews();

				}
				progressDialog.dismiss();
				break;
			}
			super.handleMessage(msg);

		}

	}

	OnClickListener onSearchButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.i("hari", "number of photos is" + numberofphotos);
			progressDialog = ProgressDialog.show(ImageSearchActivity.this,
					"ProgressDialog", "Wait!");
			isFirst = true;
			flickrmanager.clearArray();
			flickrmanager.setNumberofPhotos(numberofphotos += 10);
			new Thread(getMetadata).start();
		}
	};

	@Override
	public void onClick(View v) {
		if (v == txtLoadmore) {
			Log.i("hari",
					"gridview.getFirstVisiblePosition"
							+ gridView.getFirstVisiblePosition());
			progressDialog = ProgressDialog.show(ImageSearchActivity.this,
					"ProgressDialog", "Wait!");
			flickrmanager.setNumberofPhotos(numberofphotos += 10);

			new Thread(getMetadata).start();

		}
	}

}
