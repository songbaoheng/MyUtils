/**
 *GifWord
 *TODO
 *@Copright: Copyright(地阳传奇) 2014
 *@Company:2014 地阳传奇 Inc. All rights reserved
 *@author 宋保衡
 *@Date:2014-5-19
 * 
 */
package com.example.myutil;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * GifWord TODO
 * 
 * @Copright: Copyright(地阳传奇) 2014
 * @Company:2014 地阳传奇 Inc. All rights reserved
 * @author 宋保衡
 * @Date:2014-5-19
 * 
 */
public class MyWebView extends WebView implements OnTouchListener,
		OnGestureListener {
	GestureDetector gesture;// 手势管理器
	Context context;
	PointF mTouch = new PointF();
	PointF mUp = new PointF();
	

	/**
	 * @param context
	 * @param attrs
	 */
	public MyWebView(Context context, AttributeSet attrs) {

		super(context, attrs);
		this.context = context;
		gesture = new GestureDetector(context, this);

		this.setOnTouchListener(this);
	}

	
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		/* 下一页 */
	
	

		return false;
	}

	
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:
			mUp.x = event.getX();
			mUp.y = event.getY();
			break;
		case MotionEvent.ACTION_DOWN:
			mTouch.x = event.getX();
			mTouch.y = event.getY();
			break;
		default:
			break;
		}
		return gesture.onTouchEvent(event);
	}

}
