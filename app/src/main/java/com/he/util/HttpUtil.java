package com.he.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeMap;

import com.he.config.KeyConfig;


public class HttpUtil {
    private static int TIMEOUT = 5000;
    private static HttpUtil httpUtil;
    public static HttpUtil getHttp(){
    	if(httpUtil == null){
    		httpUtil = new HttpUtil();
    	}
    	return httpUtil;
    }

    public String doHttpPost(String postData, String postUrl) {
        Log.i(KeyConfig.TAG_NAME, "doHttpPost: url=" + postUrl);
        Log.i(KeyConfig.TAG_NAME, "doHttpPost: data=" + postData);
        InputStream is = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(postUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            if(null != postData){
                out.write(postData.getBytes("utf-8"));
            }
            out.flush();
            out.close();
            is = connection.getInputStream();
            String ret = convertStreamToString(is);
            Log.i(KeyConfig.TAG_NAME, "doHttpPost result :" + ret);
            return ret;
        } catch (Exception e) {
            Log.e(KeyConfig.TAG_NAME,"doHttpPost error",e);
            return null;
        } finally {
            if (connection != null)
                connection.disconnect();
            if (is != null) {
                try {
                    is.close();
                }catch (Exception ex){}
            }
        }
    }

    public String doHttpGet(String urlData) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlData);
            Log.i(KeyConfig.TAG_NAME, "set url :" + urlData);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.connect();
            String str = convertStreamToString(connection.getInputStream());
            Log.v(KeyConfig.TAG_NAME, "doHttpGet result :" + str);
            return str;
        } catch (Exception e) {
            //throw e;
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                line = new String(line.getBytes(), "utf-8");
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                is.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public TreeMap<String,String> getParamsMapFromUrlArgs(String urlArgs){
        TreeMap<String,String> paramsMap = new TreeMap<String,String>();
        try{
            String[] args = urlArgs.split("&");
            for(String item : args){
                if(null == item || 0 == item.trim().length()){
                    continue;
                }
                String[] arrs = item.split("=",2);
                paramsMap.put(arrs[0],arrs[1]) ;
            }
        }catch (Exception ex){
        }
        return paramsMap ;
    }


}