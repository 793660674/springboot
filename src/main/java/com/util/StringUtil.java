package com.util;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class StringUtil {

	/**
	 * @see 转换null字符串
	 * @param o
	 *            原字符串
	 * @return 字符串
	 * 
	 * */
	public static String getString(Object o) {
		if (o == null) {
			return "";
		} else {
			return o + "";
		}
	}

	/**
	 * @see 替换[']为['']
	 * @param o
	 *            原字符串
	 * @return 新字符串
	 * 
	 * */
	public static String replace(Object o) {
		if (o != null && !"".equals(o)) {
			String tmp = o.toString().replace("'", "\'");
			tmp = tmp.replace("   ", "");
			tmp = tmp.replace("　　　", "");
			tmp = tmp.replace("(null);", "");
			tmp = tmp.replace("(null)", "");
			return tmp;
		} else {
			return "";
		}
	}

	/**
	 * @see 将字符串转换为整型
	 * @param o
	 *            原字符串
	 * @return 整型
	 * 
	 * */
	public static Integer getInt(Object o) {
		try {
			if (o == null || "".equals(o)) {
				return null;
			} else {
				if (o.toString().indexOf(".") > 0) {
					Double d = Double.parseDouble(o + "");
					return d.intValue();
				}
				return Integer.parseInt(o + "");
			}
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * @see 将字符串转换为整型
	 * @param o
	 *            原字符串
	 * @return 整型
	 * 
	 * */
	public static Long getLong(Object o) {
		try {
			if (o == null || "".equals(o)) {
				return null;
			} else {
				if (o.toString().indexOf(".") > 0) {
					Double d = Double.parseDouble(o + "");
					return d.longValue();
				}
				return Long.parseLong(o + "");
			}
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * @see 将字符串转换为整型
	 * @param o
	 *            原字符串
	 * @return 整型
	 * 
	 * */
	public static Double getDouble(Object o) {
		if (o == null || "".equals(o)) {
			return null;
		} else {
			return Double.parseDouble(o + "");
		}
	}

	public static Integer getInt(HashMap<String, Object> parameters, String key) {
		if (parameters == null) {
			return null;
		} else {
			return Integer.parseInt(parameters.get(key) + "");
		}
	}

	public static String getString(HashMap<String, Object> parameters, String key) {
		if (parameters != null) {
			return parameters.get(key) + "";
		}
		return null;
	}

	/**
	 * @see 获取解密字符串
	 * @param str1
	 *            原字符串
	 * @param str2
	 *            原字符串
	 * @return 字符串
	 * 
	 * */
	// public static String getDecryptString(String str1, String str2){
	// String[] tmp = new String[]{str1, str2};
	// String upd = StringUtil.makeSha1(tmp,true);
	// String key = Const.SOURCE_DOWNLOAD_KEY.substring(8, 15) +
	// Const.SOURCE_DOWNLOAD_KEY.substring(20, 28);
	//
	// return upd.substring(19,25) + key.substring(5,10) + str2.substring(5,10);
	// }

	/**
	 * 获取客户端的IP地址
	 * 
	 * */
	// public static String getIpAddr(HttpServletRequest request) {
	public static String getIpAddr(Map<String, String> request) {
		// String ip = request.getHeader("x-forwarded-for");
		String ip = request.get("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			// ip = request.getHeader("Proxy-Client-IP");
			ip = request.get("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			// ip = request.getHeader("WL-Proxy-Client-IP");
			ip = request.get("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			// ip = request.getRemoteAddr();
			ip = request.get("remoteAddr");
		}
		return ip;
	}

	/**
	 * 是否不为NULL
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if (str == null)
			return false;
		if ("".equals(str.trim()))
			return false;
		return true;
	}

	public static boolean isNotEmpty(Object obj) {
		if (obj == null)
			return false;
		if ("".equals(String.valueOf(obj)))
			return false;
		return true;
	}

	public static boolean isEmpty(Object o) {
		if (o == null || "".equals(o))
			return true;
		return false;
	}

	/**
	 * 半角转全角
	 * 
	 * @param input
	 *            String.
	 * @return 全角字符串.
	 */
	public static String b2qChange(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + 65248);

			}
		}
		return new String(c);
	}

	/**
	 * 全角转半角
	 * 
	 * @param input
	 *            String.
	 * @return 半角字符串
	 */
	public static String q2bChange(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);

			}
		}
		String returnString = new String(c);

		return returnString;
	}

	/**
	 * md5加密
	 * 
	 * @param source
	 * @return
	 */
	public static String getMD5String(String source) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			byte[] byteArray = messageDigest.digest(source.getBytes("UTF-8"));
			StringBuffer md5StrBuff = new StringBuffer();
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
					md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
				} else {
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
				}
			}
			return md5StrBuff.toString();
		} catch (Exception e) {
			throw new RuntimeException("fail to get MD5 String", e);
		}
	}

	/**
	 * 右补位，左对齐
	 * 
	 * @param oriStr
	 *            原字符串
	 * @param len
	 *            目标字符串长度
	 * @param alexin
	 *            补位字符
	 * @return 目标字符串
	 */
	public static String rpad(String oriStr, int len, char alexin) {
		int strlen = oriStr.length();
		String str = "";
		if (strlen < len) {
			for (int i = 0; i < len - strlen; i++) {
				str = str + alexin;
			}
		}
		return oriStr + str;
	}

	/**
	 * 左补位，右对齐
	 * 
	 * @param oriStr
	 *            原字符串
	 * @param len
	 *            目标字符串长度
	 * @param alexin
	 *            补位字符
	 * @return 目标字符串
	 */
	public static String lpad(String oriStr, int len, char alexin) {
		int strlen = oriStr.length();
		String str = "";
		if (strlen < len) {
			for (int i = 0; i < len - strlen; i++) {
				str = str + alexin;
			}
		}
		return str + oriStr;
	}

	/**
	 * 计算年龄
	 * 
	 * @param birthDay
	 * @return
	 * @throws Exception
	 */
	public static String getAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}

		return age + "";
	}

	/**
	 * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0 如果版本相同，有子版本的为大，如：1.0.0 大于 1.0
	 * 
	 * @param version1
	 * @param version2
	 * @return
	 */
	public static int compareVersion(String version1, String version2) {
		if (version1 == null || version2 == null) {
			return 0;
		}
		String[] versionArray1 = version1.split("\\.");// 注意此处为正则匹配，不能用.；
		String[] versionArray2 = version2.split("\\.");
		int idx = 0;
		int minLength = Math.min(versionArray1.length, versionArray2.length);// 取最小长度值
		int diff = 0;
		while (idx < minLength && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0// 先比较长度
		        && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {// 再比较字符
			++idx;
		}
		// 如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
		diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
		return diff;
	}

	public static String getBASE64(String s) {
		if (s == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
	}

	// 将 BASE64 编码的字符串 s 进行解码 解密
	public static String getFromBASE64(String s) {
		if (s == null)
			return null;
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 加密
	 * 
	 * @param ming
	 * @return
	 */
	public static String mTOa(Object ming) {
		return StringUtil.getBASE64(StringUtil.getBASE64(StringUtil.getBASE64((String) ming)));
	}

	/**
	 * 解密
	 * 
	 * @param ming
	 * @return
	 */
	public static String aTOm(String an) {
		return StringUtil.getFromBASE64(StringUtil.getFromBASE64(StringUtil.getFromBASE64(an)));
	}

	/**
	 * Main unit test method.
	 * 
	 * @param args
	 *            Command line arguments.
	 * 
	 */
	public static void main(String args[]) {
		try {

			// String str = "updir/abc/2014/08/09/a.jpg;updir/abc/2014/08/09/b.jpg;updir/abc/2014/08/09/c.jpg;";
			//
			// String tmp = getSmallImagePath(str);

			System.out.println(rpad("fuyue", 20, '0'));
			System.out.println(q2bChange("ｘīｎ jí rú fén"));

		} catch (Exception e) {
			System.err.println("Error:" + e.toString());
		}
	}
}
