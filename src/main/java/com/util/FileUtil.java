package com.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;


public class FileUtil {
	static String key = "fEJ9/o528Ciusbcgn5mPuw==";

	public static void copyFile(String oldPath, String newPath) {

		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				new File(new File(newPath).getParent()).mkdirs();
				InputStream inStream = new FileInputStream(oldfile); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[2048];
				while ((byteread = inStream.read(buffer)) != -1) {

					fs.write(buffer, 0, byteread);
				}
				fs.close();
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
	}

	public static void encrypt(String oldPath, String newPath) {
		try {
			// AES加密
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(key.getBytes()));
			SecretKey skey = kgen.generateKey();
			byte[] raw = skey.getEncoded();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

			// 写文件
			File file = new File(oldPath);
			if (!file.isAbsolute()) {
				return;
			}
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			byte[] bytIn = new byte[(int) file.length()];

			File file1 = new File(newPath);
			new File(file1.getParent()).mkdirs();
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file1));

			bis.read(bytIn);

			byte[] bytOut = cipher.doFinal(bytIn);
			byte[] tt4 = new Base64().encode(bytOut);
			bos.write(tt4, 0, tt4.length);

			bis.close();
			bos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}

	public static void Base64Decode(String oldPath, String newPath) {
		try {

			// 读文件
			File file = new File(oldPath);
			if (!file.isAbsolute()) {
				return;
			}
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			byte[] bytIn = new byte[(int) file.length()];
			bis.read(bytIn);

			// 编码
			byte[] tt4 = new Base64().encode(bytIn);

			// 写文件
			File file1 = new File(newPath);
			new File(file1.getParent()).mkdirs();
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file1));
			bos.write(tt4, 0, tt4.length);

			bis.close();
			bos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void decrypt(String oldPath, String newPath) {
		try {
			// AES加密
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(key.getBytes()));
			SecretKey skey = kgen.generateKey();
			byte[] raw = skey.getEncoded();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);

			// 写文件

			File file = new File(oldPath);
			if (!file.isAbsolute()) {
				return;
			}
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			byte[] bytIn = new byte[(int) file.length()];

			File file1 = new File(newPath);
			new File(file1.getParent()).mkdirs();
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file1));

			bis.read(bytIn);

			byte[] tt4 = new Base64().decode(bytIn);
			byte[] bytOut = cipher.doFinal(tt4);

			bos.write(bytOut, 0, bytOut.length);

			bis.close();
			bos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void Base64Encode(String oldPath, String newPath) {
		try {

			// 读文件
			File file = new File(oldPath);
			if (!file.isAbsolute()) {
				return;
			}
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			byte[] bytIn = new byte[(int) file.length()];
			bis.read(bytIn);

			// 编码
			byte[] tt4 = new Base64().decode(bytIn);

			// 写文件
			File file1 = new File(newPath);
			new File(file1.getParent()).mkdirs();
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file1));
			bos.write(tt4, 0, tt4.length);

			bis.close();
			bos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	 /** 
     * 根据网络地址保存图片 
     * @param destUrl 网络地址 
     * @param localPath 图片存储路径 
     */  
    public static String downloadNet(String destUrl,String localPath) {    
        FileOutputStream fos = null;    
        BufferedInputStream bis = null;    
        HttpURLConnection httpUrl = null;    
        URL url = null;    
        int BUFFER_SIZE = 1024;    
        byte[] buf = new byte[BUFFER_SIZE];    
        int size = 0;    
        String filePath = "";
        try {    
            url = new URL(destUrl);    
            httpUrl = (HttpURLConnection) url.openConnection();    
            httpUrl.connect();    
            bis = new BufferedInputStream(httpUrl.getInputStream());    

			String fileName = destUrl.substring(destUrl.lastIndexOf("/") + 1);
			File file = new File(localPath, fileName);
			new File(file.getParent()).mkdirs();
			
            fos = new FileOutputStream(file);    
            while ((size = bis.read(buf)) != -1) {     
                fos.write(buf, 0, size);    
            }    
            fos.flush();   
            filePath = file.getName();
        } catch (IOException e) {    
        } catch (ClassCastException e) {    
        } finally {    
            try {    
                fos.close();    
                bis.close();    
                httpUrl.disconnect();    
            } catch (IOException e) {    
            } catch (NullPointerException e) {    
            }    
        }    
        return filePath;
    }    

    /**
	 * 获取网络地址保存到本地磁盘
	 *  图片加密
	 * 
	 * @param urlPath
	 * @param localPath 图片存储路径 
	 */
    public static String downloadNetDecode(String urlPath, String local){
        try {
    		String fileName = urlPath.substring(urlPath.lastIndexOf("/")+1);
    		
    		URL url = new URL(urlPath);
    		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    		conn.setRequestMethod("GET");
    		conn.setConnectTimeout(100 * 1000);
    		InputStream inStream = conn.getInputStream();
    		byte[] data = readInputStream(inStream);
    		conn.disconnect();
    		inStream.close();
    		
    		File file = new File(local,fileName);
    		new File(file.getParent()).mkdirs(); 
    		//编码
    		if(!fileName.endsWith(".mp4")&&!fileName.endsWith(".mp3")&&!fileName.endsWith(".MP4")&&!fileName.endsWith(".MP3")){
		   		byte[] tt4 = new Base64().encode(data);	
		   		FileOutputStream outStream = new FileOutputStream(file);
		   		outStream.write(tt4);
	    		
	    		outStream.close(); 		       
    		}else{
	    		FileOutputStream outStream = new FileOutputStream(file);
	    		outStream.write(data);
	    		
	    		outStream.close();
    		}
    		return file.getName();
        }  catch (Exception e) {
            e.printStackTrace();
        }
		return "";
    }
    
    /**
     * 删除指定目录下N天之前的文件
     * 
     * @param dir	目录地址
     * @param day	天数
     */
    public static void deleteDayBefore(String dir, int day) {
	    	Date date = new Date(System.currentTimeMillis() - 3600000 * day);  
	    	File folder = new File(dir);  
	    	File[] files = folder.listFiles();  
	    	if (files != null) {
		    	for (int i=0;i<files.length;i++){  
		    	    File file = files[i];  
		    	    if (new Date(file.lastModified()).before(date)){  
		    	        file.delete();  
		    	    }  
		    	}  
	    	}
    }
    
	public static void main(String[] args) {
		try {
//			Base64Encode("C:\\Users\\zz\\Desktop\\examination2\\examination.txt",
//					"C:\\Users\\zz\\Desktop\\examination2\\examination_1.txt");
			
			deleteDayBefore("/Users/fuyue/logs/rocketmqlogs", 1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
