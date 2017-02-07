package com.hebg3.international.imagecache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**图像缓存SQLiteOpenHelper对象*/
class ImageCacheHelper extends SQLiteOpenHelper {

//	private Handler handler = new Handler();
	private Context context;

	public ImageCacheHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists image_cache(_id integer primary key,"
				+ "url varchar(500),path varchar(200),length NUMERIC)");
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			new ClearCacheTask(context)
					.execute(CacheConstant.CACHE_DIR);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
