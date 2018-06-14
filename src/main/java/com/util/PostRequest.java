package com.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class PostRequest {
	 
    
    private static final Logger log = LoggerFactory  
            .getLogger(PostRequest.class);  
    public static String GET_URL = "";  
  
    public static String POST_URL = "";  
  
    public static String readContentFromGet() throws IOException {  
        String getURL = GET_URL;  
        URL getUrl = new URL(getURL);  
        // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，  
        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection  
        HttpURLConnection connection = (HttpURLConnection) getUrl  
                .openConnection();  
        // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到  
        // 服务器  
        connection.connect();  
        // 取得输入流，并使用Reader读取  
        BufferedReader reader = new BufferedReader(new InputStreamReader(  
                connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码  
        String line = "";  
        String lines = "";  
        while ((line = reader.readLine()) != null) {  
            // line = new String(line.getBytes(), "utf-8");  
            lines += line.trim();  
        }  
        reader.close();  
        // 断开连接  
        connection.disconnect();  
        return lines;  
    }  
      
    public static void contentFromGet() throws IOException {  
        String getURL = GET_URL;  
        URL getUrl = new URL(getURL);  
        // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，  
        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection  
        HttpURLConnection connection = (HttpURLConnection) getUrl  
                .openConnection();  
        // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到  
        // 服务器  
        connection.connect();  
    }  
  
    public static String readContentFromPost() throws IOException {  
        // Post请求的url，与get不同的是不需要带参数  
        URL postUrl = new URL(POST_URL);  
        // 打开连接  
        HttpURLConnection connection = (HttpURLConnection) postUrl  
                .openConnection();  
        // Output to the connection. Default is  
        // false, set to true because post  
        // method must write something to the  
        // connection  
        // 设置是否向connection输出，因为这个是post请求，参数要放在  
        // http正文内，因此需要设为true  
        connection.setDoOutput(true);  
        // Read from the connection. Default is true.  
        connection.setDoInput(true);  
        // Set the post method. Default is GET  
        connection.setRequestMethod("POST");  
        // Post cannot use caches  
        // Post 请求不能使用缓存  
        connection.setUseCaches(false);  
        // This method takes effects to  
        // every instances of this class.  
        // URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。  
        // connection.setFollowRedirects(true);  
  
        // This methods only  
        // takes effacts to this  
        // instance.  
        // URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数  
        connection.setInstanceFollowRedirects(true);  
        // Set the content type to urlencoded,  
        // because we will write  
        // some URL-encoded content to the  
        // connection. Settings above must be set before connect!  
        // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的  
        // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode  
        // 进行编码  
        connection.setRequestProperty("Content-Type",  
                "application/x-www-form-urlencoded");  
        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，  
        // 要注意的是connection.getOutputStream会隐含的进行connect。  
        connection.connect();  
        DataOutputStream out = new DataOutputStream(  
                connection.getOutputStream());  
        // The URL-encoded contend  
        // 正文，正文内容其实跟get的URL中'?'后的参数字符串一致  
        // String content =  
        // "key=j0r53nmbbd78x7m1pqml06u2&type=1&toemail=jiucool@gmail.com" +  
        // "&activatecode=" + URLEncoder.encode("久酷博客", "utf-8");  
        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面  
        // out.writeBytes(content);  
        out.flush();  
        out.close(); // flush and close  
        BufferedReader reader = new BufferedReader(new InputStreamReader(  
                connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码  
        String line = "";  
        String lines = "";  
        while ((line = reader.readLine()) != null) {  
            // line = new String(line.getBytes(), "utf-8");  
            lines += line.trim();  
        }  
        reader.close();  
        connection.disconnect();  
        return lines;  
    }  
  
    /** 
     * 经纬度 
     *  
     * @param lng 
     * @param lat 
     * @return 
     */  
    public static Map<String, String> getAddressByLatlat(String lng, String lat) {  
        String str = "http://api.map.baidu.com/geocoder?output=json&location="  
                + lat + "," + lng + "&key=37492c0ee6f924cb5e934fa08c6b167";  
        Map<String, String> map = new HashMap<String, String>();  
        try {  
            PostRequest.POST_URL = str;  
            String line = PostRequest.readContentFromPost();  
            JSONObject jsonObj = JSONObject.parseObject(line);  
            String result = jsonObj.getString("result");  
            JSONObject jsonObj2 = JSONObject.parseObject(result);  
            String cityCode = jsonObj2.getString("cityCode");  
            String formattedAddress = jsonObj2.getString("formatted_address");  
            JSONObject jsonObj3 = JSONObject.parseObject(jsonObj2  
                    .getString("addressComponent"));  
            String city = jsonObj3.getString("city");  
            map.put("cityCode", cityCode);  
            map.put("formattedAddress", formattedAddress);  
            map.put("city", city);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return map;  
    }  
  
    /** 
     * 城市 
     *  
     * @param city 
     * @return 
     */  
    public static Map<String, String> getAddressByCity(String city) {  
        String str = "http://api.map.baidu.com/geocoder/v2/?ak=E4805d16520de693a3fe707cdc962045&callback=renderOption&output=json&address="  
                + city + "&city=" + city;  
        Map<String, String> map = new HashMap<String, String>();  
        try {  
            PostRequest.POST_URL = str;  
            String line = PostRequest.readContentFromPost();  
            JSONObject jsonObj = JSONObject.parseObject(line);  
            String result = jsonObj.getString("result");  
            JSONObject jsonObj2 = JSONObject.parseObject(result);  
            String cityCode = jsonObj2.getString("cityCode");  
            String formattedAddress = jsonObj2.getString("formatted_address");  
            map.put("cityCode", cityCode);  
            map.put("formattedAddress", formattedAddress);  
            map.put("city", city);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return map;  
    }  
  
    /** 
     * 城市 
     *  
     * @param city 
     * @return 
     */  
    public static Map<String, String> getQQMessages(String access_token,  
            String openid) {  
        String qqUrl = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=100330589&access_token="  
                + access_token + "&openid=" + openid + "&format=json";  
        Map<String, String> map = new HashMap<String, String>();  
        try {  
            PostRequest.GET_URL = qqUrl;  
            String line = PostRequest.readContentFromGet();  
            JSONObject jsonObj = JSONObject.parseObject(line);  
            String nickname = jsonObj.getString("nickname");  
            String gender = jsonObj.getString("gender");  
            String url = jsonObj.getString("figureurl_qq_1");  
            map.put("nickname", nickname);  
            map.put("gender", gender);  
            map.put("url", url);  
            map.put("openid", openid);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return map;  
    }  
  
    public static void main(String[] args) {  
        PostRequest.GET_URL="http://mc-storage.b0.upaiyun.com/song_lib/lrc/20140702/Y2014060133.txt";  
        String line;  
        try {  
            line = PostRequest.readContentFromGet();  
            System.out.println(line);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
      
      
    /**  
     * 用传统的URI类进行请求  
     * @param urlStr  
     */    
    public static String post(String urlStr,String xmlInfo) {    
        String line = "";    
        try {    
            URL url = new URL(urlStr);    
            URLConnection con = url.openConnection();    
            con.setDoOutput(true);    
            con.setRequestProperty("Pragma:", "no-cache");    
            con.setRequestProperty("Cache-Control", "no-cache");    
            con.setRequestProperty("Content-Type", "text/xml");    
    
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());      
//            System.out.println("xmlInfo=" + xmlInfo);     
            out.write(new String(xmlInfo.getBytes("UTF-8")));    
            out.flush();    
            out.close();    
            BufferedReader br = new BufferedReader(new InputStreamReader(con    
                    .getInputStream()));    
            for (line = br.readLine(); line != null; line = br.readLine()) {    
                line+=line;    
            }    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
        return line;  
    }    
      
    /** 
     * 发送https请求 
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return 返回微信服务器响应的信息 
     */  
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
            URL url = new URL(requestUrl);  
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();  
            conn.setSSLSocketFactory(ssf);  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            conn.setRequestMethod(requestMethod);  
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");  
            // 当outputStr不为null时向输出流写数据  
            if (null != outputStr) {  
                OutputStream outputStream = conn.getOutputStream();  
                // 注意编码格式  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
            // 从输入流读取返回内容  
            InputStream inputStream = conn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
            String str = null;  
            StringBuffer buffer = new StringBuffer();  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            // 释放资源  
            bufferedReader.close();  
            inputStreamReader.close();  
            inputStream.close();  
            inputStream = null;  
            conn.disconnect();  
            return buffer.toString();  
        } catch (ConnectException ce) {  
            log.error("连接超时：{}", ce);  
        } catch (Exception e) {  
            log.error("https请求异常：{}", e);  
        }  
        return null;  
    }  
      
    /**  
     * 发送HttpPost请求  
     *   
     * @param strURL  
     *            服务地址  
     * @param params  
     *            json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/>  
     * @return 成功:返回json字符串<br/>  
     */    
    public static String postByJson(String strURL, String params) {    
        System.out.println(strURL);    
        System.out.println(params);    
        try {    
            URL url = new URL(strURL);// 创建连接    
            HttpURLConnection connection = (HttpURLConnection) url    
                    .openConnection();    
            connection.setDoOutput(true);    
            connection.setDoInput(true);    
            connection.setUseCaches(false);    
            connection.setInstanceFollowRedirects(true);    
            connection.setRequestMethod("POST"); // 设置请求方式    
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式    
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式    
            connection.connect();    
            OutputStreamWriter out = new OutputStreamWriter(    
                    connection.getOutputStream(), "UTF-8"); // utf-8编码    
            out.append(params);    
            out.flush();    
            out.close();    
            // 读取响应    
            int length = (int) connection.getContentLength();// 获取长度    
            InputStream is = connection.getInputStream();    
            if (length != -1) {    
                byte[] data = new byte[length];    
                byte[] temp = new byte[512];    
                int readLen = 0;    
                int destPos = 0;    
                while ((readLen = is.read(temp)) > 0) {    
                    System.arraycopy(temp, 0, data, destPos, readLen);    
                    destPos += readLen;    
                }    
                String result = new String(data, "UTF-8"); // utf-8编码    
                System.out.println(result);    
                return result;    
            }    
        } catch (IOException e) {    
            // TODO Auto-generated catch block    
            e.printStackTrace();    
        }    
        return "error"; // 自定义错误信息    
    } 
}
