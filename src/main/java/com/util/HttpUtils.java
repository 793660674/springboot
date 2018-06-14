package com.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;


public class HttpUtils {

	public static enum HttpConnectionTimeOut{
		fastest,faster,fast,normal,slow,slower,slowest,dead,endless,
	}
	
	public static enum HttpReadTimeOut{
		fastest,faster, fast,normal,slow,slower,slowest,dead,endless,
	}
	
	//设置读取网页内容时间
	public static int getReadTimeOut(HttpReadTimeOut s){
		EnumMap<HttpReadTimeOut,Integer> em = new EnumMap<HttpReadTimeOut,Integer>(HttpReadTimeOut.class);
		em.put(HttpReadTimeOut.fastest, 100);
		em.put(HttpReadTimeOut.faster, 300);
		em.put(HttpReadTimeOut.fast, 500);
		em.put(HttpReadTimeOut.normal, 1000);
		em.put(HttpReadTimeOut.slow, 1500);
		em.put(HttpReadTimeOut.slower, 2000);
		em.put(HttpReadTimeOut.slowest, 3000);
		em.put(HttpReadTimeOut.dead, 20000);
		em.put(HttpReadTimeOut.endless, 120000);
		return ((Integer)em.get(s)).intValue();
	}
	
	//设置http延时时间
	public static int getConnectionTimeOut(HttpConnectionTimeOut s){
		EnumMap<HttpConnectionTimeOut,Integer> em = new EnumMap<HttpConnectionTimeOut,Integer>(HttpConnectionTimeOut.class);
		em.put(HttpConnectionTimeOut.fastest, 100);
		em.put(HttpConnectionTimeOut.faster, 300);
		em.put(HttpConnectionTimeOut.fast, 500);
		em.put(HttpConnectionTimeOut.normal, 1000);
		em.put(HttpConnectionTimeOut.slow, 1500);
		em.put(HttpConnectionTimeOut.slower, 2000);
		em.put(HttpConnectionTimeOut.slowest, 3000);
		em.put(HttpConnectionTimeOut.dead, 20000);
		em.put(HttpConnectionTimeOut.endless, 120000);
		return ((Integer)em.get(s)).intValue();
	}
	/** 
	 * http访问数据，默认返回的数据为UTF-8编码 
	 *  
	 * @param url 
	 *            url地址 
	 * @param method 
	 *            提交方式 
	 * @return 
	 * @throws IOException 
	 */
	public static String doGet(String url, HttpConnectionTimeOut o, HttpReadTimeOut r ,String charset){
		String result = null;
		HttpClient httpClient = null;
		try{
			httpClient = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);
			HttpParams params = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, HttpUtils.getConnectionTimeOut(o));
			HttpConnectionParams.setSoTimeout(params, HttpUtils.getReadTimeOut(r));
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(),charset);
			} 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}

	public static String doGet(String url) throws Exception {
		return doGet(url, HttpConnectionTimeOut.endless, HttpReadTimeOut.endless, "UTF-8");
	}
	
	/** 
	 * post提交数据 
	 *  
	 * @param url 
	 *            提交地址 
	 * @param params 
	 *            参数 
	 * @return 
	 * @throws ClientProtocolException 
	 * @throws IOException 
	 */
	public static String doPost(String url, Map<String, String> params )
		throws ClientProtocolException, IOException {
		return doPost(url, params, HttpConnectionTimeOut.endless, HttpReadTimeOut.endless, "UTF-8");
	}

	/** 
	 * post提交数据 
	 *  
	 * @param url 
	 *            提交地址 
	 * @param params 
	 *            参数 
	 * @param encoding 
	 *            参数编码 
	 * @return 
	 * @throws ClientProtocolException 
	 * @throws IOException 
	 */
	public static String doPost(String url, Map<String, String> params,
			HttpConnectionTimeOut o, HttpReadTimeOut r, String charset)
			throws ClientProtocolException, IOException {
		List<NameValuePair> param = new ArrayList<NameValuePair>(); //参数
		String result = null;
		HttpClient client = null;
		try {
			//param.add(new BasicNameValuePair("par", "request-post"));
			//添加参数
			Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				param.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			HttpPost request = new HttpPost(url);
			HttpEntity entity = new UrlEncodedFormEntity(param, charset);
			request.setEntity(entity);
			client = new DefaultHttpClient();
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT,
					HttpUtils.getConnectionTimeOut(o)); // 设置请求超时时间
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					HttpUtils.getReadTimeOut(r)); // 读取超时
			request.setHeader("Content-Type","application/x-www-form-urlencoded;charset="+ charset + "");
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(), charset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
		return result;
	}
	
    public static String doPostStream(String url , byte out[] )
			throws ClientProtocolException, IOException {
    	
		String result = null;
		HttpClient client = null;
		try {
			
			HttpPost request = new HttpPost(url);
			ByteArrayEntity entity = new ByteArrayEntity(out);
			request.setEntity(entity);
			client = new DefaultHttpClient();
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT,
					HttpUtils.getConnectionTimeOut(HttpConnectionTimeOut.endless)); // 设置请求超时时间
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					HttpUtils.getReadTimeOut(HttpReadTimeOut.endless)); // 读取超时
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(),  "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
		return result;
	}
    

	/**
	 * get
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doGet(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpGet request = new HttpGet(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }
        
        return httpClient.execute(request);
    }
	
	/**
	 * post form
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param bodys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			Map<String, String> bodys)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (bodys != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

            for (String key : bodys.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
            formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            request.setEntity(formEntity);
        }

        return httpClient.execute(request);
    }	
	
	/**
	 * Post String
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			String body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (StringUtil.isNotEmpty(body)) {
        	request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }
	
	/**
	 * Post stream
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			byte[] body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (body != null) {
        	request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }
	
	/**
	 * Put String
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPut(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			String body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPut request = new HttpPut(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (StringUtil.isNotEmpty(body)) {
        	request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }
	
	/**
	 * Put stream
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPut(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			byte[] body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPut request = new HttpPut(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (body != null) {
        	request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }
	
	/**
	 * Delete
	 *  
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doDelete(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }
        
        return httpClient.execute(request);
    }
	
	private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
    	StringBuilder sbUrl = new StringBuilder();
    	sbUrl.append(host);
    	if (StringUtil.isNotEmpty(path)) {
    		sbUrl.append(path);
        }
    	if (null != querys) {
    		StringBuilder sbQuery = new StringBuilder();
        	for (Map.Entry<String, String> query : querys.entrySet()) {
        		if (0 < sbQuery.length()) {
        			sbQuery.append("&");
        		}
        		if (StringUtil.isEmpty(query.getKey()) && StringUtil.isNotEmpty(query.getValue())) {
        			sbQuery.append(query.getValue());
                }
        		if (StringUtil.isNotEmpty(query.getKey())) {
        			sbQuery.append(query.getKey());
        			if (StringUtil.isNotEmpty(query.getValue())) {
        				sbQuery.append("=");
        				sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
        			}        			
                }
        	}
        	if (0 < sbQuery.length()) {
        		sbUrl.append("?").append(sbQuery);
        	}
        }
    	
    	return sbUrl.toString();
    }
	
	private static HttpClient wrapClient(String host) {
		HttpClient httpClient = new DefaultHttpClient();
		if (host.startsWith("https://")) {
			sslClient(httpClient);
		}
		
		return httpClient;
	}
	
	@SuppressWarnings("deprecation")
	private static void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                	
                }
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                	
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
        	throw new RuntimeException(ex);
        }
    }
}