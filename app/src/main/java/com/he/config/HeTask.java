package com.he.config;

import com.he.func.find.friendcircle.OpenImageActivity;
import com.he.func.find.friendcircle.FriendCircleActivity;
import com.he.func.find.ShakeActivity;
import com.he.func.login.MotifyPwdActivity;
import com.he.func.setting.SettingVideoActivity;
import com.he.func.login.LogActivity;
import com.he.func.login.RegActivity;
import com.he.func.login.SwitchActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class HeTask {

	public final String INTENT_IMGURLS = "imgurls";
	public final String INTENT_POSITION = "position";

	private static HeTask task;
	
	public static HeTask getInstance(){
		if(task == null){
			task = new HeTask();
		}
		return task;
	}

	public void startSwitchActivity(Context context) {
		context.startActivity(new Intent(context, SwitchActivity.class));
	}
	
	//   跳转帐号注册页
	public void startRegisterActivity(Context context) {
		context.startActivity(new Intent(context, RegActivity.class));
	}

	// 跳转登录页
	public void startLoginActivity(Context context) {
		KeyConfig.APP_ID = "YHebi99eilUN09";
		KeyConfig.APP_KEY = "yRbd9pD0O8T3AEk1Ao8DXUMlqfDzRU4otdug";
		Intent intent = new Intent(context, LogActivity.class);
		context.startActivity(intent);
	}
	
	//修改密码
	public void startMotifyActivity(Context context, String uName, String pwd ) {
		Intent intent = new Intent(context, MotifyPwdActivity.class);
		intent.putExtra("UserName", uName);
		intent.putExtra("Password", pwd);
		context.startActivity(intent);
	}

	public void startFriendCircle(Context context){
		context.startActivity(new Intent(context, FriendCircleActivity.class));
	}

	public void startShake(Context context){
		context.startActivity(new Intent(context, ShakeActivity.class));
	}

	public void startImagePagerActivity(Context context, List<String> imgUrls, int position){
		Intent intent = new Intent(context, OpenImageActivity.class);
		intent.putStringArrayListExtra(INTENT_IMGURLS, new ArrayList<>(imgUrls));
		intent.putExtra(INTENT_POSITION, position);
		context.startActivity(intent);
	}

	/**
	 * 开始跳转动画
	 */
	public void startActivityAnim(Context context) {
		((Activity)context).overridePendingTransition(0, 0);
	}

	public void startVideoActivity(Context context){
		context.startActivity(new Intent(context, SettingVideoActivity.class));
	}
	
	public HeDialog showProgress(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable) {
		HeDialog dialog = new HeDialog(context);
	
		//dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setIndeterminate(indeterminate);
		dialog.setCancelable(cancelable);
		dialog.show();
		return dialog;
	}

	public final String sdCardRoot() {
		boolean sdExist = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
		if (!sdExist) {
			Log.e(KeyConfig.TAG_NAME, "sd card not exist");
			return null;
		}
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public String sdkRoot() {
	        return sdCardRoot() + "/Moo/";
	    }

}