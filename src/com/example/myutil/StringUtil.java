package com.ac.api.util;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class StringUtil {
	private static final String BLANK = "";

	/**
	 * 把16进制串转为字符
	 * 
	 * @author
	 * @date Jun 26, 2009
	 * @param hexStr
	 *            16进制串
	 * @return 字符
	 */
	public static char hex2Char(String hexStr) {
		int code = Integer.parseInt(hexStr, 16);
		return (char) code;
	}

	/**
	 * 把字符转为16进制串
	 * 
	 * @author
	 * @date Jun 26, 2009
	 * @param value
	 * @return
	 */
	public static String char2Hex(char value) {
		int code = (int) value;
		return Integer.toHexString(code);
	}

	/**
	 * 把字符串编码为16进制串
	 * 
	 * @author
	 * @date Jun 26, 2009
	 * @param s
	 *            源串
	 * @return 16进制串
	 */
	public static String encodeHex(String s) {
		StringBuffer sb = new StringBuffer();
		if (s != null) {
			int n = 0, l = s.length();
			while (n < l) {
				String enc = char2Hex(s.charAt(n++));
				sb.append(fill(enc, 4, 0));
			}
		}
		return sb.toString();
	}
	/**
	 * 替换指定字符串包含的HTML代码,比如'&lt;' 和 '&gt;'
	 * 
	 * @param input
	 *            String 原字符串
	 * @return String 返回新的字符串
	 */
	public static final String escapeHTMLTags(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer(input.length());
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * 把16进制串解码为源串
	 * 
	 * @author
	 * @date Jun 26, 2009
	 * @param s
	 *            16进制串
	 * @return
	 */
	public static String decodeHex(String s) {
		StringBuffer sb = new StringBuffer();
		if (s != null) {
			while (true) {
				String str = s.substring(0, 4);
				sb.append(hex2Char(str));
				if (s.length() <= 4) {
					break;
				}
				s = s.substring(4);
			}
		}
		return sb.toString();
	}

	/**
	 * 判断字符是否为空或空串
	 * 
	 * @author
	 * @date Jun 26, 2009
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || BLANK.compareTo(str) == 0;
	}

	/**
	 * 判断对像是否为空或空串
	 * 
	 * @author 作者
	 * @date Jun 26, 2009
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Object str) {
		return str == null || BLANK.equals(str);
	}

	/**
	 * 判断字符串是否是整数
	 * 
	 * @author mao
	 * @date Nov 17, 2008
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (!isEmpty(str)) {
			return str.matches("\\d+");
		}
		return false;
	}

	/**
	 * 判断字符串是否是浮点数
	 * 
	 * @author Mao
	 * @date Jul 20, 2009
	 * @param str
	 * @return
	 */
	public static boolean isFloat(String str) {
		if (!isEmpty(str)) {
			return str.matches("\\d*(?:\\.\\d*)?");
		}
		return false;
	}

	/**
	 * 判断字符串是否是整数
	 * 
	 * @author mao
	 * @date Nov 17, 2008
	 * @param str
	 * @return
	 */
	public static boolean isNumbers(String str, String splitChar) {
		if (str != null) {
			return str.matches("\\d+(?:" + splitChar + "\\d+)*");
		}
		return false;
	}

	/**
	 * 判断日期格式
	 * @author 滕辉
	 * @date 2009-8-4
	 * @param str
	 * @return
	 */
	public static boolean isDate(String str){
		if(str != null){
			String regex = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)(\\s+(([01])?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$";
			return str.matches(regex);
		}
		return false;
	}
	
	/**
	 * 对源串进行填充返回长度为max的新串
	 * @author mao
	 * @date Feb 26, 2009
	 * @param str 源串
	 * @param max 长度
	 * @param character 填充字符
	 * @return
	 */
	public static String fill(String str, int max, int character) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < max - str.length(); i++) {
			s.append(character);
		}
		s.append(str);
		return s.toString();
	}

	/**
	 * 如果串为null返回空串，否则返回源串
	 * 
	 * @author Mao
	 * @date Jun 26, 2009
	 * @param str
	 * @return
	 */
	public static String dealNull(String str) {
		return str != null ? str : BLANK;
	}

	/**
	 * 把对像转成字符串
	 * 
	 * @author mao
	 * @date Nov 19, 2008
	 * @param obj
	 * @return
	 */
	public static String getString(Object obj) {
		String str = null;
		if (obj != null) {
			if (obj instanceof String) {
				str = (String) obj;
			} else {
				str = obj.toString();
			}
		}
		return str;
	}

	/**
	 * 如果串对像为null返回空串，否则返回对像转码的串
	 * 
	 * @author 作者
	 * @date Jun 26, 2009
	 * @param obj
	 * @return
	 */
	public static String dealNull(Object obj) {
		String str = getString(obj);
		return str == null ? BLANK : str;
	}
	/**
	 * 
	 * @return
	 */
	public static String getBlobString(Blob blob){
		int bolblen;
		String content = "";
		try {
			bolblen = (int) blob.length();
			byte[] data = blob.getBytes(1, bolblen);  
			content = new String(data); 
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		
		return content;
	}

	/**
	 * 如果串为空返回默认串，否则返回源串
	 * 
	 * @author
	 * @date Jun 26, 2009
	 * @param str
	 *            串参数
	 * @param defaultVal
	 *            默认值
	 * @return
	 */
	public static String dealNull(String str, String defaultVal) {
		return str != null ? str.trim() : defaultVal;
	}

	/**
	 * ISO转GBK
	 * @param str
	 * @return
	 */
	public static String iso88592gbk(String str) {
		if (str == null)
			return null;
		try {
			return new String(str.getBytes("8859_1"), "GBK").trim();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 字符串按字符集解码(默认转为utf-8)
	 * 
	 * @author
	 * @date Jun 26, 2009
	 * @param str
	 *            串
	 * @param encoding
	 *            字符集
	 * @return
	 */
	public static String decode(String str, String encoding) {
		try {
			if (str != null) {
				return new String(str.getBytes("ISO_8859_1"), dealNull(encoding, "utf-8")).trim();
			}
		} catch (Exception e) {

		}
		return str;
	}
	
	public static String urlDecode(Object str){
		String reslut = dealNull(str);
		String encoding = "UTF-8";
		try {
			String paramsTrans = new String(reslut.getBytes("ISO-8859-1"),encoding);
			reslut = java.net.URLDecoder.decode(paramsTrans , encoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reslut;
	}
	

	/**
	 * 对字符串进行url编码并指定字符集
	 * 
	 * @author
	 * @date Jun 26, 2009
	 * @param str
	 * @param encoding
	 * @return
	 */
	public static String urlEncode(String str, final String encoding) {
		try {
			if (!StringUtil.isEmpty(str)) {
				str = URLEncoder.encode(str, encoding);
			}
		} catch (Exception e) {
		}
		return str;
	}

	/**
	 * 判断两个对像是否相等
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static boolean equals(Object value1, Object value2) {
		boolean is = false;
		if (value1 == value2) { // is null or self
			return is = true;
		}
		if (value1 != null && value2 != null) { // is not null;
			return value1.equals(value2);
		}
		return is;
	}

	/**
	 * 给字符串加上指定的值
	 * @param obj 字符串对像 
	 * @param number 增量
	 * @return
	 */
	public static String plus(Object obj, int number) {
		String str = "";
		String sa = obj.toString();
		String sb = String.valueOf(number);
		int t = 0;
		int i = sa.length() - 1;
		int j = sb.length() - 1;
		while (i >= 0 && j >= 0) {
			char a = sa.charAt(i);
			char b = sb.charAt(j);
			int n = a - 48 + b - 48 + t;
			t = n / 10;
			str = String.valueOf(n % 10) + str;
			i--;
			j--;
		}
		if (i >= 0) {
			while (i >= 0) {
				str = String.valueOf(sa.charAt(i--) - 48 + t) + str;
				if (t > 0) {
					t = 0;
				}
			}
		}
		if (j >= 0) {
			while (j >= 0) {
				str = String.valueOf(sb.charAt(j--) - 48 + t) + str;
				if (t > 0) {
					t = 0;
				}
			}
		}
		if (t != 0) {
			str = String.valueOf(t) + str;
		}
		return str;
	}

	/**
	 * 给对像累加某个值并保持累加后的字符串长度
	 * 
	 * @author cui
	 * @date Feb 26, 2009
	 * @param obj
	 *            被累加对像
	 * @param number
	 *            累加数
	 * @param length
	 *            累加后的长度
	 * @return
	 */
	public static String plus(Object obj, int number, int length) {
		String string = plus(obj, number);
		if (length != 0) {
			return fill(string, length, 0);
		}
		return string;
	}

	/**
	 * @description 去掉空格
	 * @author mao
	 * @date Nov 17, 2008
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		if (str != null) {
			return str.trim();
		}
		return str;
	}

	/**
	 * 字符串中是否有中文字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasChineseCharacter(String str) {
		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]+");
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}
	
	/**
	 * 字符串是否是固定电话
	 * @param str
	 * @return
	 */
	public static boolean isPhone(String str){
		Pattern pattern = Pattern.compile("^\\d{8,11}$");
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}
	/**
	 * 字符串是否是手机号
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str){
		Pattern pattern = Pattern.compile("^1\\d{10}$");
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}
	
	
	/**
	 * 字符串是否是邮政编码
	 * @param str
	 * @return
	 */
	public static boolean isZipCode(String str){
		Pattern pattern = Pattern.compile("^[0-9]{6}$");
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}
	
	
	/**
	 * @desc 给文件名增加后缀
	 * @author mao
	 * @date Feb 26, 2009
	 * @param fileName
	 * @return
	 */
	public static String addFileNameSuffix(String fileName, String suffix) {
		return fileName != null ? fileName.replaceFirst("(\\.[^\\.]+)$", suffix + "$1") : fileName;
	}

	/**
	 * 判断源串(以分隔符分隔的串)是否包括目标串
	 * @author Mao
	 * @date Oct 16, 2009
	 * @param src 源串
	 * @param dest 目标串
	 * @param splitChar 分隔符
	 * @return
	 */
	public static boolean match(String src, String dest, String splitChar){
		if(src!=null){
			Pattern pattern = Pattern.compile("(^|"+splitChar+")+"+dest+"("+splitChar+"|$)");
			Matcher matcher = pattern.matcher(src);
			return  matcher.find();
		}
		return false;
	}
	
	
	/**
	 * 把数组合并成字符串并用加上指定分隔符
	 * @author Mao
	 * @date Jul 20, 2009
	 * @param array 数组对像
	 * @param split 分隔符
	 * @return 合并后的串
	 */
	public static String merge(Object[] array, String split) {
		if (array != null) {
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i< array.length; i++){
				if(i>0){
					sb.append(split);
				}
				sb.append(array[i]);
			}
			return sb.toString();
		}
		return null;
	}
	public static String getString(String[] arrs, String split) {
		StringBuilder str = new StringBuilder();
		for(int i = 0; i <(arrs==null?0:arrs.length); i++){
			if(arrs[i] == null || "".equals(arrs[i]) || "null".equals(arrs[i])) {
				continue;
			}
			if(i != 0) {
				str.append(split);
			}
			str.append(arrs[i]);
		}
		return str.toString();
	}
	
	public static Object[] replaceAll(Object[] objs,String indexof,Object shopid){
		if(objs!= null && objs.length>0){
			int i=0;
			for(Object obj :objs){
				int of = obj.toString().indexOf(indexof+shopid);
				if(of!=-1)
					obj = obj.toString().subSequence(0, of);
				objs[i] = obj;
				i++;
			}
			return objs;
		}
		return null;
	}
	
	public static String getClassName(Class cls){
		return cls.getName();
	}
	/**
	 * 判断是否是身份证号
	 * @param card
	 * @return
	 */
	public static boolean isCardNo(Object card){
		Boolean result = false;
		if(StringUtil.isEmpty(card))
			return result;
		Pattern p=Pattern.compile("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)");
		Matcher m=p.matcher((String)card);
		
		if(m.find()){
		   result = true;
		}
		return result;
	}
}