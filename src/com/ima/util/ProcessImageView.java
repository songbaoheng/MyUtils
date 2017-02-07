package com.ima.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * AndroidTest 带进度条的imageview
 * 
 * @Copright: Copyright(安创) 2015
 * @Company:2015 河北安创 Inc. All rights reserved
 * @author 宋保衡
 * @date 2015-7-17
 */
public class ProcessImageView extends ImageView {

	private Paint mPaint;// 画笔
	Context context = null;
	int progress = 0;
	public static final int LANDSCAPE = 1;// 横向
	public static final int PORTRAIT = 2;// 纵向
	private int direction = 1;// 缓冲框方向默认为横向

	/**
	 * @param context
	 * @param direction 1横向缓冲框 2纵向缓冲框
	 */
	public ProcessImageView(Context context, int direction) {
		super(context);
		this.context = context;
		this.direction = direction;
		mPaint = new Paint();
	}

	public ProcessImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.context = context;
		mPaint = new Paint();
	}

	public ProcessImageView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		mPaint = new Paint();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setAntiAlias(true); // 消除锯齿
		mPaint.setStyle(Paint.Style.FILL);
		if (direction == 1) {
			// 横向
			drawHorizontalRect(canvas);
		} else {
			// 纵向
			drawVerticalRect(canvas);
		}

	}

	/**
	 * 绘制纵向的缓冲框
	 */
	private void drawVerticalRect(Canvas canvas) {
		mPaint.setColor(Color.parseColor("#70000000"));// 半透明
		canvas.drawRect(0, 0, getWidth(), getHeight() - getHeight() * progress
				/ 100, mPaint);

		mPaint.setColor(Color.parseColor("#00000000"));// 全透明
		canvas.drawRect(0, getHeight() - getHeight() * progress / 100,
				getWidth(), getHeight(), mPaint);

		mPaint.setTextSize(30);
		mPaint.setColor(Color.parseColor("#FFFFFF"));
		mPaint.setStrokeWidth(2);
		Rect rect = new Rect();
		mPaint.getTextBounds("100%", 0, "100%".length(), rect);// 确定文字的宽度
		canvas.drawText(progress + "%", getWidth() / 2 - rect.width() / 2,
				getHeight() / 2, mPaint);
	}

	/**
	 * 绘制横向的缓冲框
	 * */
	private void drawHorizontalRect(Canvas canvas) {

		mPaint.setColor(Color.parseColor("#7091e591"));// 半透明
		canvas.drawRect(0, 0, getWidth() - getWidth() * progress / 100,
				getHeight(), mPaint);

		mPaint.setColor(Color.parseColor("#00000000"));// 全透明
		canvas.drawRect(getWidth(), getHeight(), getWidth() - getWidth()
				* progress / 100, 0, mPaint);

		mPaint.setTextSize(30);
		mPaint.setColor(Color.parseColor("#FFFFFF"));
		mPaint.setStrokeWidth(2);
		Rect rect = new Rect();
		mPaint.getTextBounds("100%", 0, "100%".length(), rect);// 确定文字的宽度
		if (progress < 100) {
			canvas.drawText(progress + "%", getWidth() / 2 - rect.width() / 2,
					getHeight() / 2, mPaint);
		} else {
			canvas.drawText("", getWidth() / 2 - rect.width() / 2,
					getHeight() / 2, mPaint);
		}
	}

	public void setProgress(int progress) {
		this.progress = progress;
		postInvalidate();
	};

}
