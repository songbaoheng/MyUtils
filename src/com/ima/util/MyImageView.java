package com.ima.util;

import java.io.File;

import com.example.myutil.R;



import net.tsz.afinal.FinalBitmap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * SafeSupervision
 * 
 * @Copright: Copyright(安创) 2015
 * @Company:2015 河北安创 Inc. All rights reserved
 * @author 宋保衡
 * @date 2015-7-17
 */
public class MyImageView extends LinearLayout {

	private ImageButton closeBtn;
	private ProcessImageView iconView;
	private TextView pathView;
	private Activity context;
	private View convertView;
	private String imaPath;//图片路径
	private String returnName;// 服务器返回的图片名称
	private int imaIndex;// 图片索引
	private boolean canRemove = true;
	private int width;// 图片宽度
	private int height;// 图片高度
	private LinearLayout imaLin;// 存放图片的父容器
	private ImageView okView;
	private boolean showOkView=true;// 是否展示ok图片
	private IMyImaViewListener imalistener;
	public static final int IMASHOW = -1;
	public static final int REMOVIMA = 0;
	public static final int IMARESULT = 2;

	public MyImageView(Activity context, int imaIndex) {
		super(context);
		this.context = context;
		this.imaIndex = imaIndex;
		initView();
	}

	public void setImaListener(IMyImaViewListener imalistener) {
		this.imalistener = imalistener;
	}

	/**
	 * 是否支持长按删除
	 * 
	 * @param canRemove
	 */
	public void setRemove(boolean canRemove) {
		this.canRemove = canRemove;
		this.showOkView=canRemove;
		
	}

	/**
	 * 设置服务器返回的图片名称
	 * 
	 * @param returnName
	 */
	public void setReturnName(String returnName) {
		this.returnName = returnName;
	}

	/**
	 * 设置图片大小
	 * 
	 * @param width
	 * @param height
	 */
	public void setImaSize(int width, int height) {
		this.width = width;
		this.height = height;

	}

	private void initView() {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.imaviewline, null);
		iconView = (ProcessImageView) convertView.findViewById(R.id.ima_icon);
		pathView = (TextView) convertView.findViewById(R.id.tv_imaPath);
		closeBtn = (ImageButton) convertView
				.findViewById(R.id.ima_close_button);
		imaLin = (LinearLayout) convertView.findViewById(R.id.lin_ima);
		okView = (ImageView) convertView.findViewById(R.id.ima_ok_button);
		iconView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// if (!PublicTool.isNull(imaPath)) {
				imalistener.imaClick(imaIndex);
				// }
			}
		});
		iconView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (imalistener != null && canRemove) {
					if (closeBtn.isShown()) {
						closeBtn.setVisibility(View.GONE);
						int imaBg = Color.parseColor("#00000000");
						imaLin.setBackgroundColor(imaBg);
					} else {
						int imaBg = Color.parseColor("#D04444");
						imaLin.setBackgroundColor(imaBg);
						closeBtn.setVisibility(View.VISIBLE);
					}
				}
				return true;
			}
		});
		closeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (imalistener != null) {
					imalistener.imaRemove(MyImageView.this, imaPath, imaIndex);
				}
			}
		});
		this.addView(convertView);
	}

	
	/**
	 * 设置加载进度
	 * 
	 * @param progress
	 */
	public void setProgress(int progress) {
		if (progress <100) {
			iconView.setProgress(progress);
			

		}else{
			
			iconView.setProgress(progress);
				if (showOkView){
					okView.setVisibility(View.VISIBLE);
				}else{
					okView.setVisibility(View.GONE);
				}
			
		}
	}

	/**
	 * @param imaPath
	 */
	public void setImaPath(String imaPath) {
		this.imaPath = imaPath;
		pathView.setText(imaPath);

	}

	/**
	 * @return imapath
	 */
	public String getImapath() {
		return imaPath;
	}

	/**
	 * @param bitmap
	 */
	public void setIma(Bitmap bitmap) {
		iconView.setImageBitmap(bitmap);
	}

	/**
	 * @param resId
	 */
	public void setIma(int resId) {
		iconView.setImageResource(resId);

	}

	/**
	 * @param loadImaId
	 * @param url
	 */
	public void setIma(int loadImaId, int errorImaId, String url) {
		this.imaPath = url;

		FinalBitmap finalIma = FinalBitmap.create(context);
		finalIma.configLoadfailImage(errorImaId);
		finalIma.configLoadingImage(loadImaId);
	
		if (width != 0 && height != 0) {
			finalIma.display(iconView, url, width, height);
		} else {
			finalIma.display(iconView,url);
		}
	}

	public interface IMyImaViewListener {

		/**
		 * 图片移除接口
		 * 
		 * @param view
		 * @param imaPath
		 * @param index
		 */
		void imaRemove(View view, String imaPath, int index);

		/**
		 * 图片点击事件
		 * 
		 * @param index
		 */
		void imaClick(int index);
	}
}
