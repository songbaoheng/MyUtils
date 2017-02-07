package com.hebg3.international.imagecache;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.example.myutil.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.util.DisplayMetrics;



/**
 * 获取系列图片
 * android.text.Html.ImageGetter
 * */
public class AsyncImageGetter implements ImageGetter {

	private ImageCacheManager manager;// 图像缓存管理对象
	private DisplayMetrics displayMetrics;// 获取分辨率对象

	/* 创建软引用缓存对象，存储图片 */
	private Map<String, SoftReference<Drawable>> cache = new HashMap<String, SoftReference<Drawable>>();

	/* 下载列表 */
	private Map<String, String> download = new HashMap<String, String>();
	private Context context;
	private boolean isInterruption;// 是否中断
	private int imageWidth;// 屏幕宽度

	public AsyncImageGetter(Context context) {
		this.context = context;

		this.displayMetrics = context.getResources().getDisplayMetrics();
		imageWidth = displayMetrics.widthPixels;
		manager = ImageCacheManager.getInstance(context);// 初始化ImageCacheManager
		isInterruption = false;
	}

	/**
	 * 设置图片获取中是否中断
	 * */
	public void setInterrpution(boolean flag) {
		isInterruption = flag;
		// Log.v("getter interruption:", isInterruption + "");
	}

	@Override
	public Drawable getDrawable(String source) {
		// boolean flag = true;
		synchronized (this) {
			
			Bitmap bitmap = null;
			Drawable drawable = manager.getDrawable(source, cache);// 获得对应的Drawable对象
			if (drawable != null) {

				/* 为drawable绑定一个矩形区域（int left, int top, int right, int bottom） */
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight());
				return drawable;
			}
			try {
				if (isInterruption) {

					/* 获取图片资源 */
					drawable = context.getResources().getDrawable(
							R.drawable.no_image);

					drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight());
					return drawable;
					// return null;
				}
				if (!downloadCheck(source)) {

					bitmap = getBitmapFromUrl(source);

				}
			} catch (Exception e) {
				// Log.v("download_error", source);
				download.remove(source);
				return null;
			}
			if (bitmap == null) {
				// Log.v("download_null", source);
				download.remove(source);
			} else {
				if (bitmap.getWidth() > imageWidth) {
					Bitmap tmp = resizeBitmap(bitmap, imageWidth);
					bitmap.recycle();
					drawable = new BitmapDrawable(tmp);
					try {
						Method m = BitmapDrawable.class.getMethod(
								"setTargetDensity",
								new Class[] { DisplayMetrics.class });
						m.invoke(drawable, displayMetrics);
					} catch (Exception e) {
						return null;
					}
					manager.addImageCache(source, tmp);
				} else {
					drawable = new BitmapDrawable(bitmap);
					manager.addImageCache(source, bitmap);
				}
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight());
			}
			return drawable;
		}
	}

	/**
	 * 判断资源图片是否已经下载
	 * 
	 * @param String
	 *            图片路径
	 * @eturn boolean
	 * */
	private boolean downloadCheck(String url) {
		synchronized (download) {
			if (download.containsKey(url)) {
				return true;
			} else {
				download.put(url, null);// 如果图片不存在，保存图片路径
				return false;
			}
		}
	}

	/**
	 * 通过Url获取已经下载的Bitmap对象
	 * 
	 * @param String
	 *            url
	 * @return Bitmap
	 * */
	private Bitmap getBitmapFromUrl(String url) {
		byte[] imageByte = getImageFromURL(url);// 获得对应url下的bitmap的字节数组
		if (imageByte == null)
			return null;
		BitmapFactory.Options opts = new BitmapFactory.Options();

		/*
		 * 主要用于防止内存溢出，当为true时，将不返回实际的Bitmap对象，
		 * 系统也不用给它分配内存空间，但是允许我们查询图片信息，包括图片的大小的信息
		 */
		opts.inJustDecodeBounds = true;

		/* 通过byte数组获得Bitmap对象 */
		Bitmap tmp = BitmapFactory.decodeByteArray(imageByte, 0,
				imageByte.length, opts);
		opts.inJustDecodeBounds = false;

		int be = (int) (opts.outWidth / (float) imageWidth);
		if (be <= 0)
			be = 1;
		opts.inSampleSize = be; // 计算得到图片缩小倍数

		tmp = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length,
				opts);
		return tmp;
	}

	/**
	 * 通过url获得对应的图片的byte字节数组
	 * */
	private byte[] getImageFromURL(String urlPath) {
		byte[] data = null;
		InputStream is = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlPath);// 创建url对象
			conn = (HttpURLConnection) url.openConnection();// 打开连接
			conn.setDoInput(true);// 设置此连接是否支持Input操作

			conn.setRequestMethod("GET");// 设置与服务器交互的方法
			conn.setConnectTimeout(6000);// 连接超时
			is = conn.getInputStream();// 获得输入流
			if (conn.getResponseCode() == 200) {

				data = readInputStream(is);// 连接成功。读取输入流
			} else
				System.out.println("发生异常！");

		} catch (Exception e) {
			return null;
		} finally {
			conn.disconnect();
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	/**
	 * 读取InuputStream
	 * 
	 * @param InputStream
	 * @return byte【】
	 * */
	private byte[] readInputStream(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = -1;
		try {
			/*阅读数据*/
			while ((length = is.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}
			baos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = baos.toByteArray();//转换为Byte【】
		try {
		
			/*关闭资源*/
			is.close();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	/**
	 * 设置Bitmap的大小
	 * 
	 * @param Bitmap
	 * @param int 图片的宽度
	 * @return Bitmap
	 * */
	public Bitmap resizeBitmap(Bitmap bitmap, int newWidth) {
		int width = bitmap.getWidth();// 获得宽度
		int height = bitmap.getHeight();// 获得高度
		float temp = ((float) height) / ((float) width);//计算原始图片的宽高比例
		int newHeight = (int) ((newWidth) * temp);//计算新图片的高度
		float scaleWidth = ((float) newWidth) / width;//计算宽度缩放比例
		float scaleHeight = ((float) newHeight) / height;//计算高度缩放比例
		Matrix matrix = new Matrix();

		matrix.postScale(scaleWidth, scaleHeight);// 缩放图片
		// matrix.postRotate(45);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		// bitmap.recycle();
		return resizedBitmap;
	}

	
}
