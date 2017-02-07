package com.ima.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.example.myutil.MyWebView;
import com.example.myutil.R;

import net.tsz.afinal.FinalBitmap;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomButtonsController;

/**
 * SafeSupervision 图片展示对话框
 * 
 * @Copright: Copyright(安创) 2015
 * @Company:2015 河北安创 Inc. All rights reserved
 * @author 宋保衡
 * @date 2015-7-17
 */
public class ImaShowDialog extends Dialog {

	private MyWebView webView;
	private ImageView imaShowView;

	private int imaIndex;

	private ArrayList<Bitmap> bitmap_list;// 获取本地图片
	private int[] imaResList;// 本地图片路径列表
	private String[] imaUrlArray;// 网络图片路径数组
	private Context context;
	private ImageView closeView;// 关闭
	private ImageView leftArrowView;// 左箭头
	private ImageView rightArrowView;// 右箭头
	private int imaType;// 图片类型
	private static final int HTTPIMA = 0;// 网络图片
	private static final int RESIMA = 1;// 本地图片
	private static final int SDIMA = 2;// 本地（sdcard）图片
	private ProgressDialog progressDialog;

	/**
	 * 展示本地图片
	 * 
	 * @param context
	 * @param theme
	 * @param imaResList
	 *            本地图片资源列表
	 * @param imaIndex图片索引
	 */
	public ImaShowDialog(Context context, int theme, int[] imaResList,
			int imaIndex) {
		super(context, theme);
		this.context = context;
		this.setContentView(R.layout.imashow);
		this.imaResList = imaResList;
		this.imaIndex = imaIndex;
		this.imaType = RESIMA;
		initView();
		webView.setVisibility(View.GONE);
		showIma(imaIndex);
		initListener();
	}

	/**
	 * 展示本地(sdcard)图片
	 * 
	 * @param context
	 * @param theme
	 * @param imaResList
	 * 
	 * @param imaIndex图片索引
	 */
	public ImaShowDialog(Context context, int theme,
			ArrayList<Bitmap> bitmap_list, int imaIndex) {
		super(context, theme);
		this.context = context;
		this.setContentView(R.layout.imashow);
		this.bitmap_list = bitmap_list;
		this.imaIndex = imaIndex;
		this.imaType = SDIMA;
		initView();
		webView.setVisibility(View.GONE);
		showIma(imaIndex);
		initListener();
	}

	/**
	 * 展示网络图片
	 * 
	 * @param context
	 * @param theme
	 * @param imaUrlList
	 * @param imaIndex
	 */
	public ImaShowDialog(Context context, int theme, String[] imaUrlArray,
			int imaIndex) {
		super(context, theme);
		this.context = context;
		this.setContentView(R.layout.imashow);
		this.imaUrlArray = imaUrlArray;
		this.imaIndex = imaIndex;
		this.imaType = HTTPIMA;
		initView();
		webView.setVisibility(View.VISIBLE);
		showIma(imaIndex);
		initListener();
	}

	/**
	 * 设置网络图片资源
	 * 
	 * @param imaUrlList
	 * @param imaIndex
	 */
	public void setImaData(String[] imaUrlArray, int imaIndex) {
		this.imaUrlArray = imaUrlArray;
		this.imaIndex = imaIndex;
		this.imaType = HTTPIMA;
		showIma(imaIndex);
	}

	/**
	 * 设置本地图片资源
	 * 
	 * @param imaResList
	 * @param imaIndex
	 */
	public void setImaData(int[] imaResList, int imaIndex) {
		this.imaResList = imaResList;
		this.imaIndex = imaIndex;
		this.imaType = RESIMA;
		showIma(imaIndex);
	}

	/**
	 * 设置本地图片资源
	 * 
	 * @param imaResList
	 * @param imaIndex
	 */
	public void setImaData(ArrayList<Bitmap> bitmap_list, int imaIndex) {
		this.bitmap_list = bitmap_list;
		this.imaIndex = imaIndex;
		this.imaType = SDIMA;
		showIma(imaIndex);
	}

	private void initView() {
		imaShowView = (ImageView) findViewById(R.id.ima_show);
		webView = (MyWebView) findViewById(R.id.web_imashow);
		closeView = (ImageView) findViewById(R.id.ima_dialog_close);
		leftArrowView = (ImageView) findViewById(R.id.ima_leftArrow);
		rightArrowView = (ImageView) findViewById(R.id.ima_rightArrow);
		progressDialog = new ProgressDialog(context, R.style.dialog);
		setup(webView);

	}

	/**
	 * 展示图片
	 * 
	 * @param imaIndex
	 *            图片索引
	 */
	private void showIma(int imaIndex) {
		switch (imaType) {
		case HTTPIMA:
			showIma2WebView(imaUrlArray[imaIndex]);
			break;
		case RESIMA:
			// 显示本地图片
			imaShowView.setImageResource(imaResList[imaIndex]);

			break;
		case SDIMA:
			// 显示本地图片
			imaShowView.setImageBitmap(bitmap_list.get(imaIndex));
			break;
		default:
			break;
		}

	}

	private void initListener() {

		webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				progressDialog.dismiss();
				super.onPageFinished(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				progressDialog.show();
				super.onPageStarted(view, url, favicon);
			}

		});

		/* 关闭 */
		closeView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ImaShowDialog.this.dismiss();
			}
		});

		/* 上一个图片 */
		leftArrowView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				switch (imaType) {
				case HTTPIMA:
					if (imaIndex > 0) {
						imaIndex--;
					} else {
						imaIndex = imaUrlArray.length - 1;
					}
					break;
				case RESIMA:
					if (imaIndex > 0) {
						imaIndex--;
					} else {
						imaIndex = imaResList.length - 1;
					}
					break;
				case SDIMA:
					// 显示本地图片
					if (imaIndex > 0) {
						imaIndex--;
					} else {
						imaIndex = bitmap_list.size() - 1;
					}
					break;
				}

				showIma(imaIndex);
			}
		});

		/* 下一个图片 */
		rightArrowView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				switch (imaType) {
				case HTTPIMA:
					if (imaIndex < imaUrlArray.length - 1)
						imaIndex++;
					break;
				case RESIMA:
					if (imaIndex < imaResList.length - 1)
						imaIndex++;
					break;
				case SDIMA:
					if (imaIndex < bitmap_list.size() - 1) {
						imaIndex++;
					} else {
						imaIndex = 0;
					}

					break;
				}

				showIma(imaIndex);
			}
		});

	}

	/* 设置webview组件 */
	@SuppressLint("NewApi")
	private void setup(WebView web) {
		web.setInitialScale(0);
		web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);// 去除滚动白边

		web.setVerticalScrollBarEnabled(false);
		// Enable JavaScript
		WebSettings settings = web.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);

		// Set the nav dump for HTC 2.x devices (disabling for ICS, deprecated
		// entirely for Jellybean 4.2)
		try {
			Method gingerbread_getMethod = WebSettings.class.getMethod(
					"setNavDump", new Class[] { boolean.class });

			String manufacturer = android.os.Build.MANUFACTURER;
			if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB
					&& android.os.Build.MANUFACTURER.contains("HTC")) {
				gingerbread_getMethod.invoke(settings, true);
			}
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		// We don't save any form data in the application
		settings.setSaveFormData(true);
		settings.setSavePassword(true);
		// Enable database
		// We keep this disabled because we use or shim to get around
		// DOM_EXCEPTION_ERROR_16
		settings.setDatabaseEnabled(true);
		// Enable DOM storage
		settings.setDomStorageEnabled(true);
		// Enable built-in geolocation
		settings.setGeolocationEnabled(true);
		// Enable AppCache
		// Fix for CB-2282
		settings.setAppCacheMaxSize(5 * 1048576);
		settings.setAppCacheEnabled(true);
		// settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		int version = 0;
		try {
			version = Integer.valueOf(android.os.Build.VERSION.SDK);
			if (version > 3) {
				settings.setDisplayZoomControls(false);
			} else {
				setZoomControlGone(web);
			}
		} catch (NumberFormatException e) {
		}

		// settings.setBuiltInZoomControls(true);
		// Fix for CB-1405
		// Google issue 4641

	}

	public void setZoomControlGone(View view) {
		Class classType;
		Field field;
		try {
			classType = WebView.class;
			field = classType.getDeclaredField("mZoomButtonsController");
			field.setAccessible(true);
			ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(
					view);
			mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
			try {
				field.set(view, mZoomButtonsController);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过webview显示图片
	 * 
	 * @param path
	 */
	private void showIma2WebView(String path) {

		StringBuffer data = new StringBuffer();

		String style = "style=\"width:320px\"";

		String meta = "<meta content=\"width=device-width ,initial-scale=1.0, minimum-scale=1.0, user-scalable=yes\"  name=\"viewport\">";

		data.append("<html style=\"width:100%;height:100%\">")
				.append("<body  style=\"padding:0px;margin:0px;background-color:#00000000; width:100%; height:100%;display: -webkit-box;-webkit-box-align: center;-webkit-box-pack: center;  \"><img  src=\"")
				.append(path).append("\" ").append(style)
				.append("></body></html>");

		webView.loadDataWithBaseURL(path, data.toString(), "text/html",
				"UTF-8", "");
		webView.setBackgroundColor(0);
	}

}
