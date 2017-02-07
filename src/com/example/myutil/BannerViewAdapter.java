package com.example.myutil;

import java.util.List;



import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * ShopCenter 导航栏
 * 
 * @Copright: Copyright(汉佳) 2015
 * @Company:2015 汉佳科技 Inc. All rights reserved
 * @author 宋保衡
 * @date 2015-5-7
 */
public class BannerViewAdapter extends PagerAdapter {
	private List<ImageView> list;
	private Context context;

	public BannerViewAdapter(Context context, List<ImageView> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	public ImageView instantiateItem(View arg0, final int arg1) {
		((ViewPager) arg0).addView(list.get(arg1));
		list.get(arg1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "imaonclick" + arg1, 1000).show();
			}

		});
		return list.get(arg1);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
	}
}
