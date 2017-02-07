package com.example.myutil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 *MyUtil
 *TODO
 *@Copright: Copyright(地阳传奇) 2014
 *@Company:2014 地阳传奇 Inc. All rights reserved
 *@author 宋保衡
 *@Date:2015-1-14
 * 
 */
public class VolleyActivity extends Activity {
	private TextView netDataView;
	private Context context;
	private ImageView netImaView;
	String dataUrl = "http://192.168.0.107:8080/GifWord_web/giftdata";
	String imaUrl = "http://ww3.sinaimg.cn/mw600/c0788b86gw1ehp3gsjfupj20go0bpglx.jpg";
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Toast.makeText(context, msg.obj.toString(), Toast.LENGTH_SHORT)
					.show();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		setContentView(R.layout.volley);
		context = this;
		initView();
		initIma();
		initData();
		super.onCreate(savedInstanceState);
	}

	private void initView() {
		netDataView = (TextView) findViewById(R.id.tv_netData);
		netImaView = (ImageView) findViewById(R.id.ima_netIma);
	}

	private void initData() {

		RequestQueue queue = Volley.newRequestQueue(this);

		StringRequest dataRequest = new StringRequest(Method.POST,dataUrl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						netDataView.setText(response);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Toast.makeText(context, error.toString(),
								Toast.LENGTH_SHORT).show();
					}
				}){
			@Override
			protected Map<String, String> getParams()
					throws AuthFailureError {
				// TODO Auto-generated method stub
				 Map<String, String> map = new HashMap<String, String>();  
			        map.put("pageNum", "1");  
			        map.put("type", "1");  
				return map;
			}
		};
		queue.add(dataRequest);
	}

	private void volleyTest1() {
		RequestQueue requestQueue;
		Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
		Network network = new BasicNetwork(new HurlStack());
		requestQueue = new RequestQueue(cache, network);
		requestQueue.start();
		StringRequest dataRequest = new StringRequest(dataUrl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Message message = new Message();
						message.obj = response;
						handler.sendMessage(message);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Toast.makeText(context, error.toString(),
								Toast.LENGTH_SHORT).show();
					}
				}) 
			
		;
		requestQueue.add(dataRequest);
	}

	private void initIma() {
		ImageRequest imaReq = new ImageRequest(imaUrl,
				new Response.Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap response) {
						// TODO Auto-generated method stub
						netImaView.setImageBitmap(response);
					}
				}, 0, 0, null, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						netDataView.setText(error.toString());
					}
				});
		MyVolleyTool.getInstance(context).addToRequestQueue(imaReq);

	}
}
