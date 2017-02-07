package com.example.myutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;


/**
 *MyUtil
 *TODO
 *@Copright: Copyright(地阳传奇) 2014
 *@Company:2014 地阳传奇 Inc. All rights reserved
 *@author 宋保衡
 *@Date:2015-1-14
 * 
 */
public class MyVolleyTool {
	private RequestQueue requestQueue;
	private ImageLoader imageLoader;
	private Context context;
	private static MyVolleyTool myVolleyTool;

	public MyVolleyTool(Context context) {
		super();
		this.context = context;
		requestQueue = getRequestQueue();
		imageLoader = new ImageLoader(requestQueue, new ImageCache() {
			private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(
					20);

			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				// TODO Auto-generated method stub
				cache.put(url, bitmap);
			}

			@Override
			public Bitmap getBitmap(String url) {
				// TODO Auto-generated method stub
				return cache.get(url);
			}
		});
	}

	
	public static synchronized MyVolleyTool getInstance(Context context) {
		if (myVolleyTool == null) {
			myVolleyTool = new MyVolleyTool(context);
		}
		return myVolleyTool;
	}

	
	public RequestQueue getRequestQueue() {
		if (requestQueue == null) {
			requestQueue = Volley.newRequestQueue(context
					.getApplicationContext());
		}
		return requestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req) {
		getRequestQueue().add(req);
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}

}
