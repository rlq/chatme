package com.he.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.he.data.login.AccRequestBean;
import com.he.config.KeyConfig;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class Utils {

    private static TelephonyManager telephonyManager = null;

    public static void showTips(Context context, String strText) {
		Toast toast = Toast.makeText(context, strText, Toast.LENGTH_LONG);
		toast.show();
	}

    
    public static String getIp(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            return null;
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return intToIp(ipAddress);
    }


    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
    }

	@SuppressWarnings("unused")
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress();
					}
                }
            }
        } catch (Exception ex) {
            Log.e(KeyConfig.TAG_NAME, "getLocalIpAddress error" +
                    "", ex);
        }
        return null;
    }

	@SuppressWarnings("unused")
    public static String getPhoneDev(Context context) {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static String getPhoneImei(Context context) {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimSerialNumber();
    }

    public static String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getMacAddress();
    }

	
	public static String getRandomAcc(int length){
		Random random = new Random();
		String text = "he";
		int num;
		for(int i=0; i<length; i++){
			num = Math.abs(random.nextInt()) % 10 + 48;//产生48到57的随机数(0-9的键位值)
			text += Character.toString((char) num);
		}
		return text;
	}

	@SuppressWarnings("unused")
	// 流水号
	public static String getSerialNo() {
		int intCount = (new Random()).nextInt(999999);//
		if (intCount < 100000) {
			intCount += 100000;
		}
		return String.valueOf(intCount);
	}

	@SuppressWarnings("unused")
	public static String getVersionName(Context context)
	{
		PackageManager manager = context.getPackageManager();
		try {
		  PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
		  return info.versionName;
		} catch (Exception e) {
		  e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unused")
	public static String getPackageName(Context context) {
		PackageManager manager = context.getPackageManager();
		try {
		  PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
		  return info.packageName;
		} catch (Exception e) {
		  e.printStackTrace();
		}
		return null;
	}

	public static String getCurTimestamp()
	{
		return String.valueOf(System.currentTimeMillis() / 1000L);
	}

	public static String  getPhoneNum(Context context)
	{
		telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getLine1Number();
	}

	@SuppressWarnings("unused")
	public static boolean isMobileNum(Context context, String mobiles, Boolean isAuto) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		if (m.matches()) {
		  return true;
		}
		else{
			showTips(context, "请输入正确的手机号");
			return false;
		}

	}

	@SuppressWarnings("unused")
	public static boolean isEmail(String email) {
		//String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		String str = "^[a-z_](\\w)+([\\.\\-]\\w+)*@(\\w)+(([\\.\\-]\\w+)+)$" ;
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static void showSoftInput(Context context, View view){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(
				Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public static void hideSoftInput(Context context, View view){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(
				Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
	}

	@SuppressWarnings("unused")
	public static boolean isShowSoftInput(Context context){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(
				Context.INPUT_METHOD_SERVICE);
		//获取状态信息
		return imm.isActive();//true 打开
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	@SuppressWarnings("unused")
	public static int px2dip(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int getScreenSizeWidth(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metric);
		return  metric.widthPixels;
	}

	public static int getScreenSizeHeight(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.heightPixels;
	}

	@SuppressWarnings("unused")
	/** 获取状态栏高度*/
	public static int getStatusBarHeight(View v) {
		if (v == null) {
			return 0;
		}
		Rect frame = new Rect();
		v.getWindowVisibleDisplayFrame(frame);
		return frame.top;
	}

	@SuppressWarnings("unused")
	/** 获取手机的密度*/
	public static float getDensity(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.density;
	}

	public static String getPassportIp() {
		return "http://lq.ren.cn/passport";
	}


	//motify pwd
	public static String getChangeSign(Context context, AccRequestBean bean)
	{
		 TreeMap<String, String> map = getAccMap(context, bean, false);
		 map.put("newPassword", bean.getNewPassword());
		 return setReqSign(map);
	}

	public static String getSign(Context context, AccRequestBean bean, Boolean isReg)
	{
		 TreeMap<String, String> map = getAccMap(context, bean, true);
		 if (isReg) {
			 map.put("phone", bean.getPhone()); //手机号码
			 map.put("email", KeyConfig.EMAIL);
		}
		 return setReqSign(map);
	}

	public static String getAccountJson(Context context,AccRequestBean bean, Boolean isOs)
	{
	  	return JSON.toJSONString(setAccJson(context, bean, isOs));
	}


	public static TreeMap<String, String> getAccMap(Context context,AccRequestBean bean, Boolean isOS) {
		 TreeMap<String, String> map = new TreeMap<>();
		 map.put("version", KeyConfig.VERSON);
		 map.put("lang", KeyConfig.LANG);
		 map.put("userName", bean.getUserName()); //手机号码
		 map.put("password", bean.getPassword());
		 if (isOS) {
			 map.put("ua", Build.MODEL);//手机型号
			 map.put("imei", getPhoneImei(context));//移动设备号
			 map.put("mac", getMacAddress(context));
			 map.put("os", "android");
			 map.put("channel", KeyConfig.CHANNEL);
		}
		 map.put("appId", KeyConfig.APP_ID);
		 map.put("timestamp", ""+bean.getTimestamp());
		 return map;
	}

	public static String setReqSign(TreeMap<String, String> map) {
		 StringBuilder buff = new StringBuilder();
		 String pound = "";
		 for(Entry<String, String> entry : map.entrySet()){
			 if(null == entry){
				 continue;
			 }
			 String val = entry.getValue();
			 if(null == val || 0 == val.length() || val.equals("0")){
				 continue;
			 }
			 buff.append(pound).append(val);
			 pound = "#";
		 }
		 buff.append(pound).append(KeyConfig.APP_KEY);
		return md5_string(buff.toString());
	}

	public static AccRequestBean setAccJson(Context context, AccRequestBean reg, Boolean isOS) {
		reg.setVersion(KeyConfig.VERSON);
		reg.setLang(KeyConfig.LANG);
		if (isOS) {
		  reg.setUa(Build.MODEL);
		  reg.setImei(getPhoneImei(context));
		  reg.setMac(getMacAddress(context));
		  reg.setOs("android");
		  if (KeyConfig.CHANNEL != null && !KeyConfig.CHANNEL.equals("0")) {
			  reg.setChannel(KeyConfig.CHANNEL);
			}
		}
		reg.setAppId(KeyConfig.APP_ID);

		return reg;
	}

	@SuppressWarnings("unused")
	public static HashMap<String, String> setMap(Context context,AccRequestBean bean, Boolean isOS) {
		 HashMap<String, String> map = new HashMap<>();
		 map.put("version", KeyConfig.VERSON);
		 map.put("lang", KeyConfig.LANG);
		 map.put("userName", bean.getUserName()); //手机号码
		 map.put("password", bean.getPassword());
		 if (isOS) {
			 map.put("ua", Build.MODEL);//手机型号
			 map.put("imei", getPhoneImei(context));//移动设备号
			 map.put("mac", getMacAddress(context));
			 map.put("os", "android");
		}
		 map.put("appId", KeyConfig.APP_ID);
		 map.put("timestamp", ""+bean.getTimestamp());
		 return map;
	}

	public static String md5_string(String s) {
		byte[] unEncodedPassword = s.getBytes();

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			return s;
		}

		md.reset();
		md.update(unEncodedPassword);
		byte[] encodedPassword = md.digest();
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xFF) < 16) {
				buf.append("0");
			}
			buf.append(Long.toString(encodedPassword[i] & 0xFF, 16));
		}

		return buf.toString();
	}

}
