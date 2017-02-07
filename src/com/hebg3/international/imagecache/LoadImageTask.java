package com.hebg3.international.imagecache;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Message;
import android.text.Html.ImageGetter;
import android.widget.ImageView;

/**
 * 在后台运行，从服务器端获取图片的异步处理 
 * http://blog.csdn.net/liuhe688/article/details/6532519
 * */
public class LoadImageTask extends AsyncTask<Void, Void, Drawable> {

	// private Map<String, SoftReference<Drawable>> imageCache = new
	// HashMap<String, SoftReference<Drawable>>();

	private String url;//url路径
	private ImageView image;//绑定的Imageview
	private Message msg;//
	private ImageGetter getter;//ImageGetter对象

	public LoadImageTask(ImageView image, String url, ImageGetter getter) {
		this.url = url;
		this.image = image;
		this.getter = getter;
	}

	public LoadImageTask(ImageView image, String url, Message msg,
			ImageGetter getter) {
		this(image, url, getter);
		this.msg = msg;
	}

	@Override
	protected void onPostExecute(Drawable result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (result != null) {
			image.setImageDrawable(result);
			if (msg != null)
				msg.sendToTarget();
		}
	}

	@Override
	protected Drawable doInBackground(Void... params) {
		// TODO Auto-generated method stub
		// if (imageCache.containsKey(url)) {
		// SoftReference<Drawable> softReference = imageCache.get(url);
		// Drawable drawable = softReference.get();
		// if (drawable != null) {
		// return drawable;
		// }
		// }
		// Drawable temp = downImage(url);
		// if (temp != null) {
		// imageCache.put(url, new SoftReference<Drawable>(temp));//
		// 如何客户点击了，退出又点击，就不要再用url搜索了，直接在map中找就可以了
		// }
		return getter.getDrawable(url);//获取Bitmap资源
	}

	// private Drawable downImage(String url) {
	// Drawable temp = null;
	// try {
	// temp = Drawable
	// .createFromStream(new URL(url).openStream(), "image");
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return temp;
	// }
}
