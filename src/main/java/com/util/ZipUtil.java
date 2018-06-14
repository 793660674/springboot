package com.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;


/**
 * @author zhaizhangyin
 * 
 */
public class ZipUtil {

	public static final String EXT = ".zip";
	private static final String BASE_DIR = "";
	public static final String passwd = "ae831eedf9094c94a019ef6b0e5d8aa1";
	private static final String PATH = File.separator;
	private static final int BUFFER = 1024;

	public static String compress(File srcFile) throws Exception {
		String name = srcFile.getName();
		String basePath = srcFile.getParent();
		String destPath = basePath + PATH + name + EXT;
		compress(srcFile, new File(destPath));
		return destPath;
	}

	public static void compress(File srcFile, File destFile) throws Exception {

		CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(destFile), new CRC32());
		ZipOutputStream zos = new ZipOutputStream(cos);
		compress(srcFile, zos, BASE_DIR);

		zos.flush();
		zos.close();
	}

	private static void compress(File srcFile, ZipOutputStream zos, String basePath) throws Exception {
		if (srcFile.isDirectory()) {
			compressDir(srcFile, zos, basePath);
		} else {
			compressFile(srcFile, zos, basePath);
		}
	}

	public static void compress(String srcPath) throws Exception {
		File srcFile = new File(srcPath);
		compress(srcFile);
	}

	private static void compressDir(File dir, ZipOutputStream zos, String basePath) throws Exception {
		File[] files = dir.listFiles();

		if (files.length < 1) {
			ZipEntry entry = new ZipEntry(basePath + dir.getName() + PATH);
			zos.putNextEntry(entry);
			zos.closeEntry();
		}

		for (File file : files) {
			compress(file, zos, basePath + dir.getName() + PATH);
		}
	}

	private static void compressFile(File file, ZipOutputStream zos, String dir) throws Exception {

		ZipEntry entry = new ZipEntry(dir + file.getName());
		zos.putNextEntry(entry);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = bis.read(data, 0, BUFFER)) != -1) {
			zos.write(data, 0, count);
		}
		bis.close();

		zos.closeEntry();
	}
	
	/**
	 * 将目录生成压缩包
	 * 
	 * @param srcFile	文件目录
	 * @param isEncrypt 是否需要加密
	 * @return
	 */
	public static String zip(File srcFile, boolean isEncrypt) {
		String dest = buildDestinationZipFilePath(srcFile);
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // 压缩方式
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 压缩级别
		
		if (isEncrypt) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式
			parameters.setPassword(passwd.toCharArray());
		}
		
		try {
			try{
				new File(dest).delete();
			}catch(Exception e){}
			
			ZipFile zipFile = new ZipFile(dest);
			if (srcFile.isDirectory()) {
				zipFile.addFolder(srcFile, parameters);
			} else {
				zipFile.addFile(srcFile, parameters);
			}
			return dest;
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 构建压缩文件存放路径,如果不存在将会创建 传入的可能是文件名或者目录,也可能不传,此方法用以转换最终压缩文件的存放路径
	 * 
	 * @param srcFile 源文件
	 * @param destParam  压缩目标路径
	 * @return 正确的压缩文件存放路径
	 */
	private static String buildDestinationZipFilePath(File srcFile) {
		String destParam = null;
			if (srcFile.isDirectory()) {
				destParam = srcFile.getParent() + File.separator + srcFile.getName() + EXT;
			} else {
				String fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
				destParam = srcFile.getParent() + File.separator + fileName +EXT;
			}
		
		return destParam;
	}



	public static void main(String[] args) {
		try {
			zip(new File("/Users/fuyue/Desktop/cert"),false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
