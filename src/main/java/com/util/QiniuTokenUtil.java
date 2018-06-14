package com.util;

import java.util.HashMap;
import java.util.Map;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public class QiniuTokenUtil {
	
	/**
	 * 获取七牛服务器的文件上传的token信息
	 * 
	 * @param accessKey		访问key
	 * @param secretKey		加密key
	 * @param bucket			对象空间名称
	 * @param bucketUrl		对象空间地址
	 * @return
	 */
	public static Map<String,Object> getQiNiuAccessToken(String accessKey,String secretKey, 
			String bucket, String bucketUrl){
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		Auth auth = Auth.create(accessKey, secretKey);
		StringMap putPolicy = new StringMap();
		putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
		long expireSeconds = 3600;//1小时
		String accessToken = null;
		
		try {
			accessToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
		} catch (Exception e) {
			e.printStackTrace();
			accessToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
		}
				
		//处理返回结果
		if (StringUtil.isEmpty(accessToken)){
			retMap = new HashMap<String, Object>();
			retMap.put("resultCode", "-1");
			retMap.put("message", "accessToken 无效");
		}else{
			retMap.put("resultCode", "0");
			retMap.put("accessToken", accessToken);
			retMap.put("domainUrl", bucketUrl);
		}
		
		return retMap;
	}
}
