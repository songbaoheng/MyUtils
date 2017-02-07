package com.example.myutil;

import android.os.AsyncTask;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.widget.TextView;

/**
 * html的文本转换为html界面
 * */
public class HtmlTask extends AsyncTask<Void, Void, Spanned> {

	private TextView view;// 显示html的组件	
	private String content;// html内容
	private ImageGetter imageGetter;// 获取图片

	public HtmlTask(TextView view, String content, ImageGetter imageGetter) {
		this.view = view;
		this.content = content;
		this.imageGetter = imageGetter;
	}

	@Override
	protected void onPostExecute(Spanned result) {
		super.onPostExecute(result);
		view.setText(result);

	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected Spanned doInBackground(Void... params) {
		/* Returns displayable styled text from the provided HTML string */

		return Html.fromHtml(content, imageGetter, null);
	}

}
