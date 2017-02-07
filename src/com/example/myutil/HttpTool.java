package com.example.myutil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpTool {

	private static final int CONNECTION_TIMEOUT = 1000 * 5; // Http连接超时时间

	private static HttpTool httpHelper = null;
	  
	  public static HttpTool getHttpHelper()
	  {
		  if (httpHelper == null){
			  
			  httpHelper = new HttpTool();
		  }
		  return httpHelper;
	  }
	  protected  HttpTool() {}
		
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
	public String httpPost(String url, List<Parameter> params) throws Exception {
		
		
		String response = null;
		HttpClient httpClient = new HttpClient();
		PostMethod httpPost = new PostMethod(url);
		// Post方式我们需要配置参数
		httpPost.addParameter("Connection", "Keep-Alive");
		httpPost.addParameter("Charset", "UTF-8");
		httpPost.addParameter("Content-Type",
				"application/x-www-form-urlencoded");
		httpPost.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
				new Integer(CONNECTION_TIMEOUT));
		if (null != params && params.size() != 0) {
			
			// 设置需要传递的参数，NameValuePair[]
			httpPost.setRequestBody(buildNameValuePair(params));
		}
		try {
			int statusCode = httpClient.executeMethod(httpPost);			
			if (statusCode == HttpStatus.SC_OK) {
				InputStream inputStream = httpPost.getResponseBodyAsStream();
				response = getData(inputStream);
			} else {
				
			

			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpPost.releaseConnection();
			httpClient = null;
		}
		return response;
	}

	/**
	 * 构建NameValuePair数组
	 * 
	 * @param params
	 *            List<Parameter>集合
	 * @return
	 */
	private NameValuePair[] buildNameValuePair(List<Parameter> params) {
		int size = params.size();
		NameValuePair[] pair = new NameValuePair[size];
		for (int i = 0; i < size; i++) {
			Parameter param = params.get(i);
			pair[i] = new NameValuePair(param.getName(), param.getValue());
		}
		return pair;
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
