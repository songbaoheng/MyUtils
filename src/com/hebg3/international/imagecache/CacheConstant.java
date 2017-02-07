package com.hebg3.international.imagecache;

import android.os.Environment;

/*图像缓存标示*/
public class CacheConstant {

	public final static String CACHE_DIR = Environment.getExternalStorageDirectory() + "/international_city/cache";
	public final static String CLEAR_SUC = "清理缓存成功";
	public final static String CLEAR_FAIL = "清理缓存失败";
}
