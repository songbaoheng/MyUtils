package com.example.myutil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/*
 * 读取流中的数据
 * */
public class IOTool {

	public static byte[] read(InputStream input) throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();// 定义ByteArrayOutputStream
		byte[] data = new byte[1024];// 定义用来控制读取的byte数组
		int length;// 每次读取的长度
		while ((length = input.read(data)) != -1) {

			out.write(data, 0, length);// 将读取的内容写入输出流中
		}
		input.close();// 关闭流
		return out.toByteArray();// 将流转换为ByteArray

	}
}
