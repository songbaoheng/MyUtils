package com.example.myutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.tsz.afinal.FinalBitmap;



import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioButton;

/**
 * ShopCenter  商城首页 banner幻灯切换
 * 
 * @Copright: Copyright(汉佳) 2015
 * @Company:2015 汉佳科技 Inc. All rights reserved
 * @author 宋保衡
 * @date 2015-5-6
 */
public class BannerView extends LinearLayout {

	private View convertView;
	private ViewPager adViewPager;

	private LinearLayout radioLin;
	private Context context;
	private List<ImageView> bannerViewList = new ArrayList<ImageView>();

	private View radioView;
	private List<String> imaList;
	private int[] imaArray;
	private int pageCount;// 图片个数
	private Timer bannerTimer;
	private TimerTask bannerTimerTask;
	private LinearLayout.LayoutParams radioParam;
	private TranslateAnimation animation;
	float fromXDelta = 0;
	float toXDelta = 0;

	private int lastPageNum = 0;
	private Handler handler;

	/**
	 * @param context
	 */
	public BannerView(Context context) {
		super(context);
		this.context = context;
		initView();

	}

	public BannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	private void initView() {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.bannerimaview, null);
		adViewPager = (ViewPager) convertView
				.findViewById(R.id.viewpaper_bannerIma);

		radioLin = (LinearLayout) convertView
				.findViewById(R.id.lin_banner_radio);
		bannerTimer = new Timer();
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				adViewPager.setCurrentItem(msg.what);
			};
		};

		this.addView(convertView);

	}

	public void startTimer() {
		if (bannerTimer != null) {
			if (bannerTimerTask != null)
				bannerTimerTask.cancel();
			bannerTimerTask = new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					lastPageNum += 1;
					if (lastPageNum > pageCount) {
						lastPageNum = 0;
					}
					handler.sendEmptyMessage(lastPageNum);
				}
			};
			bannerTimer.schedule(bannerTimerTask, 0, 5000);// 5秒后执行，每隔5秒执行一次
		}

	}

	public void bannerStopPlay() {
		if (bannerTimerTask != null)
			bannerTimerTask.cancel();
	}

	/**
	 * 设置图片资源
	 * 
	 * @param imaArray
	 */
	public void setBannerRes(int[] imaArray, String colorStr) {
		this.imaArray = imaArray;
		pageCount = imaArray.length;
		radioParam = new LayoutParams(PublicTool.width / pageCount,
				LayoutParams.MATCH_PARENT);
		
		radioView = new View(context);
		int color = Color.parseColor(colorStr);
		radioView.setBackgroundColor(color);
		radioLin.addView(radioView, radioParam);
		initImaView(imaArray);
	}

	/**
	 * 设置图片资源 图片路径为网络路径 y
	 * 
	 * @param imaList
	 */
	public void setBannerRes(List<String> imaList, String radioColor) {
		this.imaList = imaList;
		pageCount = imaList.size();
		radioParam = new LayoutParams(PublicTool.width / pageCount,
				LayoutParams.MATCH_PARENT);

		radioView = new View(context);
		int color = Color.parseColor(radioColor);
		radioView.setBackgroundColor(color);
		radioLin.addView(radioView, radioParam);
		initImaView(imaList);
	}

	/**
	 * 导入banner网络图片
	 * 
	 * @param imaList
	 */
	private void initImaView(int[] imaArray) {

		for (int i = 0; i < imaArray.length; i++) {
			ImageView bannerIma = new ImageView(context);
			bannerIma.setScaleType(ScaleType.FIT_XY);
			bannerIma.setBackgroundResource(imaArray[i]);
			bannerViewList.add(bannerIma);

		}

		initPaper();
	}

	/**
	 * 导入banner
	 * 
	 * @param imaList
	 */
	private void initImaView(List<String> imaList) {

		for (int i = 0; i < imaList.size(); i++) {
			ImageView bannerIma = new ImageView(context);
			FinalBitmap finalBitmap = FinalBitmap.create(context);
			finalBitmap.configLoadfailImage(R.drawable.gallery1);
			finalBitmap.configLoadingImage(R.drawable.gallery1);
			finalBitmap.display(bannerIma, imaList.get(i));
			bannerIma.setScaleType(ScaleType.FIT_XY);
			bannerViewList.add(bannerIma);
		}

		initPaper();
	}

	private void initPaper() {

		BannerViewAdapter adapter = new BannerViewAdapter(context,
				bannerViewList);
		adViewPager.setAdapter(adapter);
		adViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				fromXDelta = lastPageNum * (PublicTool.width / pageCount);
				toXDelta = arg0 * (PublicTool.width / pageCount);
				initAnimation(radioView, arg0);
				fromXDelta = toXDelta;
				lastPageNum = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void initAnimation(View view, int position) {

		float fromYDelta = view.getPivotY();
		float toYDelta = view.getPivotY();

		if (toXDelta > PublicTool.width) {
			toXDelta = 0f;
		}

		animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta,
				toYDelta);

		animation.setDuration(1000);
		animation.setFillAfter(true);
		radioView.startAnimation(animation);

	}
}
