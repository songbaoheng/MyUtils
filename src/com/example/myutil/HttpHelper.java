package com.example.myutil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpHelper {

	private static final int CONNECTION_TIMEOUT = 1000 * 5; // Http连接超时时间

	private static HttpHelper httpHelper = null;

	public static HttpHelper getHttpHelper() {
		if (httpHelper == null) {

			httpHelper = new HttpHelper();
		}
		return httpHelper;
	}

	protected HttpHelper() {
	}

	/**
	 * 通过GET方式发送请求
	 * 
	 * @param url
	 *            URL地址
	 * @param params
	 *            参数
	 * @return
	 * @throws Exception
	 */
	public String httpGet(String url, String params) throws Exception {
		String response = null; // 返回信息
		if (null != params && !params.equals("")) {
			url += "?" + params;
		}
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		GetMethod httpGet = new GetMethod(url);
		// 设置超时时间
		httpGet.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
				new Integer(CONNECTION_TIMEOUT));
		try {
			int statusCode = httpClient.executeMethod(httpGet);
			if (statusCode == HttpStatus.SC_OK) // SC_OK = 200
			{
				InputStream inputStream = httpGet.getResponseBodyAsStream(); // 获取输出流，流中包含服务器返回信息
				response = getData(inputStream);// 获取返回信息
			} else {

				throw new HttpClientError("网络连不上");

			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpGet.releaseConnection();
			httpClient = null;
		}
		return response;
	}

	/**
	 * 通过POST方式发送请求
	 * 
	 * @param url
	 *            URL地址
	 * @param params
	 *            参数
	 * @return
	 * @throws Exception
	 */
	public String httpPost(String url, List<NameValuePair> data)
			throws Exception {

		String result = "";
		HttpPost post;
		post = new HttpPost(url);
		UrlEncodedFormEntity entity;
		
			if (data != null) {
				entity = new UrlEncodedFormEntity(data, HTTP.UTF_8);
				post.setEntity(entity);
			}
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
			httpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 30000);
			httpClient.getParams().setParameter("s", Math.random());
			HttpResponse response = httpClient.execute(post);
			result = EntityUtils.toString(response.getEntity());
			return result;
		
	}

	/**
	 * 从输入流获取信息
	 * 
	 * @param inputStream
	 *            输入流
	 * @return
	 * @throws Exception
	 */
	private String getData(InputStream inputStream) throws Exception {
		String data = "";
		// 内存缓冲区
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len = -1;
		byte[] buff = new byte[1024];
		try {
			while ((len = inputStream.read(buff)) != -1) {
				outputStream.write(buff, 0, len);
			}
			byte[] bytes = outputStream.toByteArray();
			data = new String(bytes);
		} catch (IOException e) {
			throw new Exception(e.getMessage(), e);
		} finally {
			outputStream.close();
		}
		return data;
	}

}
