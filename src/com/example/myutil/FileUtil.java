package com.example.myutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

public class FileUtil {

	/**
	 * 保存文件至手机内存
	 * 
	 * @param mContext
	 * @param fileName
	 * @param content
	 * @param append
	 * */
	public boolean saveFileToROM(Context mContext, String filename,
			String content, boolean append) {
		try {
			File file = new File(mContext.getFilesDir(), filename);
			FileOutputStream fos = new FileOutputStream(file, append);
			fos.write(content.getBytes());
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * * 读取文件内容来自手机内存
	 * 
	 * @param mContext
	 * @param filename
	 *            * @return
	 */
	public String readFileFromROM(Context mContext, String filename) {
		File file = new File(mContext.getFilesDir(), filename);
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			String line;
			StringBuffer output = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				output.append(line);
			}
			reader.close();
			return output.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 判断是否存在内存卡
	 * 
	 * @return true
	 * */
	public static boolean hasSdcard() {

		String str = Environment.getExternalStorageState();
		if (str.equals(Environment.MEDIA_MOUNTED)) {
			return true;

		} else {
			return false;
		}
	}

	/**
	 *获取sd卡路径	
         * @return
	 */
	public static String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}
	/**
	 * 保存文本至存储卡
	 * 
	 * @param data
	 * @param fileName
	 *            以.txt结尾
	 * */
	public static void saveDataInSd(String data, String fileName) {
		File sdCardDir = Environment.getExternalStorageDirectory();// 获取SDCard目录

		File saveFile = new File(sdCardDir, fileName);

		FileOutputStream outStream;
		try {
			outStream = new FileOutputStream(saveFile, true);
			outStream.write(data.getBytes());

			outStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 创建文件路径
	 * 
	 * @param file
	 * */
	public static void creatPath(String file) {
		StringBuffer fileSb = new StringBuffer();
		String[] fileary = file.split("/");
		for (int i = 0; i < fileary.length - 1; i++) {
			fileSb.append(fileary[i] + "/");

			File saveFile = new File(fileSb.toString());
			if (saveFile.exists()) {
				continue;
			} else {
				saveFile.mkdir();

			}

		}

	}

	/**
	 * 删除file目录下的文件
	 * 
	 * @param path
	 * */
	public static void removeFile(String path) {

		File dataFile = new File(path);
		if (dataFile.exists()) {
			dataFile.delete();
		}
	}

	/** 创建文件
	 * @param file
	 *  */
	public static void creatFile(String file) {

		File imaSavePath = new File(file);
		if (!imaSavePath.exists()) {
			try {
				imaSavePath.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除指定目录下文件及目录
	 * 
	 * @param deleteThisPath 是否删除当前路径
	 * @param filepath
	 */
	public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
		if (!TextUtils.isEmpty(filePath)) {
			try {
				File file = new File(filePath);
				if (file.isDirectory()) {// 处理目录
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						deleteFolderFile(files[i].getAbsolutePath(), true);
					}
				}
				if (deleteThisPath) {
					if (!file.isDirectory()) {// 如果是文件，删除
						file.delete();
					} else {// 目录
						if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
							file.delete();
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除单个文件或清空文件夹
	 * @param fileName
	 */
	public static void deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists()) {
			if (file.isFile()) {
				if (file.delete()) {
					// System.out.println("删除" + fileName + "成功！");
				} else {
					// System.out.println("删除" + fileName + "失败！");
				}
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i].getPath()); // 把每个文件 用这个方法进行迭代
				}
			}

		} else {
			// System.out.println(fileName + "不存在！");
		}
	}

	/**
	 * 
	 * 取得文件大小（KB）.<br />
	 * 
	 * @param fileurl
	 * @return
	 * @throws Exception
	 */
	public static double getFileSize(String filepath) throws Exception {
		int size = 0;
		byte[] buf = new byte[1024];
		int num = 0;
		FileInputStream fis = new FileInputStream(filepath);
		while ((num = fis.read(buf)) != -1) {
			size += num;
		}
		fis.close();
		return (double) size / 1024;
	}
	
	/**
	 * 转换文件大小单位
	 * 
	 * @param l
	 * @return String
	 */
	public static String convertFileSize(long l) {
		DecimalFormat df = new DecimalFormat("0.00");
		long KB = 1024;
		long MB = 1048576;
		long GB = 1073741824;
		String size = "0 字节";
		double fs = l;
		if (fs > GB) {
			size = df.format(fs / GB) + " GB";
		} else if (fs > MB) {
			size = df.format(fs / MB) + " MB";
		} else if (fs > KB) {
			size = df.format(fs / KB) + " KB";
		} else {
			size = fs + " 字节";
		}
		return size;
	}

	/**
	 * 
	 * 获得文件编码.<br />
	 * 
	 * 
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public static String getFileCoding(String filepath) throws IOException {
		byte[] head = new byte[3];
		FileInputStream inputStream = new FileInputStream(filepath);
		inputStream.read(head);
		String code = "gb2312";
		if (head[0] == -1 && head[1] == -2) {
			code = "UTF-16";
		}
		if (head[0] == -2 && head[1] == -1) {
			code = "Unicode";
		}
		if (head[0] == -17 && head[1] == -69 && head[2] == -65) {
			code = "UTF-8";
		}
		// code = EncodingDetect.getJavaEncode(filepath);
		// log.info("code=" + code);
		return code;
	}
	
	/**
	 * 
	 * 读取文件返回字符串.<br />
	 * 
	 * @param fileName
	 * @param charset
	 * @return
	 */
	public static String readFileFromByte(String fileName, String charset) {
		if (charset == null || charset.trim().length() == 0) {
			charset = "GBK";
		}
		String fileString = "";
		try {
			fileString = new String(readByteStream(fileName), charset);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return fileString;
	}

	/**
	 * 
	 * 读取文件返回byte[]数组.<br />
	 * 
	 * 
	 * @param filepath
	 * @return
	 */
	public static byte[] readByteStream(String filepath) {
		byte[] buff = null;
		if (filepath != null && filepath.trim().length() > 0) {
			File fp = new File(filepath);
			buff = new byte[(int) fp.length()];
			FileInputStream in = null;
			try {
				in = new FileInputStream(filepath);
			} catch (FileNotFoundException e) {
				log.error(e.getMessage(), e);
			}
			try {
				in.read(buff);
				in.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return buff;
	}

	/**
	 * 
	 * 分割文件名和文件扩展名.<br />
	 * 
	 * @param fileName
	 * @return
	 */
	public static String[] getFileNameAndSuffix(String fileName) {
		String str[] = new String[2];
		int i = fileName.lastIndexOf(".");
		if (i != -1) {
			str[0] = fileName.substring(0, i);
			str[1] = fileName.substring(i).toLowerCase();
		}
		return str;
	}
	/**
	 * 复制文件
	 * @param oldpath
	 * @param newPath
	 */
	public static void copyFile(String oldpath ,String newPath) {
		FileChannel fc = null, out = null;
		if (new java.io.File(newPath).isFile()) {
			deleteFile(newPath);
		}

		try {
			fc = new FileInputStream(oldpath).getChannel();
			out = new FileOutputStream(newPath).getChannel();
			out.transferFrom(fc, 0, fc.size());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * 文件续写.<br />
	 * 
	 * @param filepath
	 *            文件物理路径
	 * @param list
	 *            写入文本集合按行
	 * @param wrap
	 *            true自动换行，fasle不自动换行。
	 * @param code
	 *            编码格式
	 */
	public static void FileWriting(String filepath, List<String> list,
			boolean wrap, String code) {
		BufferedWriter writer = null;
		try {
			File f = new File(filepath);
			if (!f.exists()) {
				f.createNewFile();
			}

			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(f, true), code);
			writer = new BufferedWriter(write);
			for (int i = 0; i < list.size(); i++) {
				if (wrap) {
					writer.write(list.get(i) + "\r\n");
				} else {
					writer.write(list.get(i));
				}
			}
			writer.close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 下载对应url下图片
	 * 
	 * @param urlString
	 * @param filename
	 * @param savePath
	 */
	public static boolean downloadPicture(String urlString, String filename,
			String savePath) {
		// log.info(urlString +"==="+filename+"==="+savePath);
		try {
			// 构造URL
			URL url = new URL(urlString);
			// 打开连接
			URLConnection con = url.openConnection();
			// 设置请求超时为5s
			con.setConnectTimeout(5 * 1000);
			// 输入流
			InputStream is = con.getInputStream();

			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			File sf = new File(savePath);
			if (!sf.exists()) {
				sf.mkdirs();
			}
			OutputStream os = new FileOutputStream(sf.getPath() + "\\"
					+ filename);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();
		} catch (Exception ex) {

			return false;
		}
		return true;
	}
}
