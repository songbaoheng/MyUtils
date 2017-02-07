package com.hebg3.international.imagecache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.UUID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

/**图像缓存管理类*/
public class ImageCacheManager {

	private static ImageCacheManager manager;
	private ImageCacheHelper helper;// SQLiteOpenHelper对象
	private SQLiteDatabase db;
	private String cache_dir;// 缓存地址

	/*
	 * BitmapFactory.Options 类,允许我们定义图片以何种方式如何读到内存 防止内存溢出
	 * http://blog.sina.com.cn/s/blog_7139b0e30100xklb.html
	 */
	private BitmapFactory.Options options;
	private boolean flag_sdcard;// sd卡是否存在
	
	/**
	 * 获取ImageCacheManager对象
	 * 
	 * @param context
	 * */
	public static ImageCacheManager getInstance(Context context) {
		if (manager == null) {
			manager = new ImageCacheManager(context);
		}
	
		return manager;
	}

	/**
	 * 关闭数据库
	 * */
	public static void close() {
		if (manager != null && manager.db != null) {
			if (manager.db.isOpen())
				manager.db.close();
		}
	}

	private ImageCacheManager(Context context) {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
			flag_sdcard = true;
		if (flag_sdcard) {
			helper = new ImageCacheHelper(context, "image_cache_db", null, 1);// 创建数据库
			db = helper.getReadableDatabase();
			cache_dir = CacheConstant.CACHE_DIR;// 指定图像缓存的地址
			File f = new File(cache_dir);
			if (!f.exists())
				f.mkdirs();
			options = new BitmapFactory.Options();
			options.inPurgeable = true;// 设置图片可以被回收
			options.inInputShareable = true;// 设置此BiteMap可共享
		}
	}

	/**
	 * 向数据库中添加数据
	 * 
	 * @param String
	 *            图片的地址
	 * @param String
	 *            存储地址
	 * @long long 长度
	 * */
	private void add(String url, String path, long length) {
		ContentValues values = new ContentValues();
		values.put("url", url);
		values.put("path", path);
		values.put("length", length);
		if (!db.isReadOnly())
			db.insert("image_cache", "_id", values);
	}

	/**
	 * 删除数据库中对应url下的图片资源
	 * 
	 * @param String
	 *            url
	 * */
	private void remove(String url) {
		db.delete("image_cache", "url=?", new String[] { url });
	}

	/**
	 * 清除图片缓存，及清空数据库表
	 * */
	public void clearCache() {
		db.execSQL("delete from image_cache");
	}

	/**
	 *从数据库中 获取uil图片的路径
	 * 
	 * @param String
	 *            url
	 * @param String
	 *            path
	 * */
	private String getPath(String url) {
		String path = null;
		Cursor c = db.rawQuery("select path from image_cache where url=?",
				new String[] { url });
		if (c.moveToFirst()) {
			path = c.getString(0);
		}
		c.close();
		return path;
	}

	/**
	 * 存取圖片
	 * 
	 * @param BitMap
	 *            需要存储的对象
	 * @return File 存储的file对象
	 * */
	private File writeBitmap(Bitmap bitmap) {
		if (bitmap == null)
			return null;
		try {
			/*
			 * UUID.randomUUID()自动生成由十六进制组成的主键
			 * http://blog.csdn.net/csdn_balance/article/details/7258833
			 */
			File f = new File(cache_dir + "/" + UUID.randomUUID().toString());
			FileOutputStream fos = new FileOutputStream(f);
			if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos))
				return f;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加图片缓存
	 * 
	 * @param String
	 *            url
	 * @param Bitmap
	 * */
	public void addImageCache(String url, Bitmap bitmap) {
		if (flag_sdcard) {
			synchronized (this) {
				if ("".equals(url) || bitmap == null) {
					return;
				}
				if (getPath(url) != null)
					return;
				File f = manager.writeBitmap(bitmap);// 存取图片
				if (f != null) {
					manager.add(url, f.getAbsolutePath(), f.length());// 添加数据库
				}
			}
		}
	}

	/**
	 * 获取对应url下的Drawable对象
	 * 
	 * @param String
	 *            url
	 * @param Map
	 *            <String, SoftReference<Drawable>> 对应key下的软引用
	 * @return Drawable
	 * */
	public Drawable getDrawable(String url,
			Map<String, SoftReference<Drawable>> cache) {
		Drawable drawable = null;
		if (flag_sdcard) {
			
			/*缓存中是否存在key值为url的对象，存在的话就对drawable赋值*/
			if (cache.containsKey(url)) {
			
				drawable = cache.get(url).get();
			}
			if (drawable != null) {
				// Log.d("memory_cache:", url);
				return drawable;
			}
			String path = manager.getPath(url);//获取图片路径
			if (path != null) {
				File f = new File(path);
				if (f.exists()) {
					try {
                        
						/*获取对应file下的Bitmap对象*/
						drawable = new BitmapDrawable(
								BitmapFactory.decodeStream(new FileInputStream(
										f), null, options));
						
						/*将获取到的bitmap对象存储到弱引用的缓存中*/
						cache.put(url, new SoftReference<Drawable>(drawable));
						// Log.d("sdcard_cache:", url);
						return drawable;
					} catch (Exception e) {
						return drawable;
					}
				} else {
					
					/*File的缓存文件中不存在此对象，删除数据库中的记录*/
					manager.remove(url);
					return drawable;
				}
			}
		}
		return drawable;
	}
}
