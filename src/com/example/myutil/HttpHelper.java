package com.utildemo.util;




import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 网络请求封装 HttpURLConnection
 *
 * @author 宋保衡
 * @Copright: Copyright(安创) 2016
 * @Company:2016 河北安创 Inc. All rights reserved
 * @date 2016/11/16
 */
public class HttpHelper {

    private static final int CONNECTION_TIMEOUT = 1000 * 5; // Http连接超时时间
    private static final String ENCODING = "utf-8";//编码格式
    private static HttpHelper httpHelper = null;

    public static HttpHelper getHttpHelper() {
        if (httpHelper == null) {

            httpHelper = new HttpHelper();
        }

        return httpHelper;
    }



    /**
     * 通过POST方式发送请求
     *
     * @param urlPath URL地址
     * @param params  参数
     * @return
     * @throws Exception
     */
    public String httpPost(String urlPath, Map<String, String> params)
            throws Exception {

        StringBuilder sb = new StringBuilder();
        // 如果参数不为空
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                // Post方式提交参数的话，不能省略内容类型与长度
                sb.append(entry.getKey()).append('=').append(
                        URLEncoder.encode(entry.getValue(), ENCODING)).append(
                        '&');
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        // 得到实体的二进制数据
        byte[] entitydata = sb.toString().getBytes();
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(CONNECTION_TIMEOUT);
        // 如果通过post提交数据，必须设置允许对外输出数据
        conn.setDoOutput(true);
        // 这里只设置内容类型与内容长度的头字段
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        conn.setRequestProperty("Charset", ENCODING);
        conn.setRequestProperty("Content-Length", String
                .valueOf(entitydata.length));
        OutputStream outStream = conn.getOutputStream();
        // 把实体数据写入是输出流
        outStream.write(entitydata);
        // 内存中的数据刷入
        outStream.flush();
        outStream.close();
        // 如果请求响应码是200，则表示成功
        if (conn.getResponseCode() == 200) {
            // 获得服务器响应的数据
            BufferedReader in = new BufferedReader(new InputStreamReader(conn
                    .getInputStream(), ENCODING));
            // 数据
            String retData = null;
            String responseData = "";
            while ((retData = in.readLine()) != null) {
                responseData += retData;
            }
            in.close();
            return responseData;
        } else {
            return "连接异常,错误码" + conn.getResponseCode();
        }

    }


    /**
     * webservice访问数据
     *
     * @param nameSpace  命名空间
     * @param methodName 调用的方法名称
     * @param address    address
     * @param params
     */
    public String getData(String nameSpace, String methodName, String address, Map<String, String> params) {
        String result = "";
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名'
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的参数
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                rpc.addProperty(entry.getKey(), entry.getValue());
            }
        }

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = false;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        try {
            HttpTransportSE transport = new HttpTransportSE(address);
            // 调用WebService

            transport.call(soapAction, envelope);

            // 获取返回的数据
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 获取返回的结果
            result = object.getProperty(0).toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 通过webservice方式发送请求
     *
     * @param params     参数
     * @param methodName 调用的方法名
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    public String httpWebService(Map<String, String> params, String methodName) throws IOException, XmlPullParserException {
        // 指定WebService的命名空间和调用的方法名'
        String result = "";
        try {
            SoapObject rpc = new SoapObject(WebUtils.NAMESPACE, methodName);
            // 设置需调用WebService接口需要传入的参数
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    rpc.addProperty(entry.getKey(), entry.getValue());
                }

            }
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
            envelope.bodyOut = rpc;
            envelope.dotNet = false;
            envelope.encodingStyle = "UTF-8";
            envelope.setOutputSoapObject(rpc);
            HttpTransportSE transport = new HttpTransportSE(WebUtils.WEBADDRESS,5000);//address
            transport.getServiceConnection().connect();
            // 调用WebService
            transport.call(WebUtils.NAMESPACE + methodName, envelope);
            // 获取返回的数据
            Object object = (Object) envelope.getResponse();
            // 获取返回的结果

            result = object.toString();

        } catch (Exception e) {
            e.printStackTrace();

        }finally{

            return result;
        }

    }
}
