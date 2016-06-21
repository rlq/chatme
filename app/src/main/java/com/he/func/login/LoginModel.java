package com.he.func.login;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.he.data.login.AccRequestBean;
import com.he.config.KeyConfig;
import com.he.data.db.DBCenter;
import com.he.data.User;
import com.he.base.HeListener;
import com.he.util.Utils;
import com.lq.ren.chat.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class LoginModel {

    private Context context;
    private List<User> userList = null;
    private String json ;
    private String url = Utils.getPassportIp();
    private AccRequestBean bean ;

    public LoginModel(Context context){
        this.context = context;
    }

    public List<User> getUserDatas(){
        try {
            userList = DBCenter.getInstance(this.context).selectAllUser();

            JSONArray jsonarray = new JSONArray();//json数组，里面包含的内容为pet的所有对象
            if (null != userList && userList.size() > 0) {
                for (User user : userList) {
                    JSONObject jsonObj = new JSONObject();//pet对象，json形式
                    jsonObj.put("password", user.getPassword());
                    jsonObj.put("userName", user.getUsername());
                    jsonarray.put(jsonObj);//向json数组里面添加user对象
                }
            }

            JSONObject json2 = new JSONObject();
            json2.put("phone", Utils.getPhoneNum(this.context));
            json2.put("ua", android.os.Build.MODEL);
            json2.put("imei", Utils.getPhoneImei(this.context.getApplicationContext()));
            json2.put("mac", Utils.getMacAddress(this.context.getApplicationContext()));
            json2.put("openudid", "");
            json2.put("idfa", "");
            json2.put("ip", Utils.getIp(this.context.getApplicationContext()));

            final JSONObject obj = new JSONObject();
            obj.put("appId", KeyConfig.APP_ID);
            obj.put("accounts", jsonarray);
            obj.put("device", json2);
            String json = obj.toString();
            Log.i(KeyConfig.TAG_NAME, "users:" + json);
            return userList;

        } catch (Exception e) {
            Log.e(KeyConfig.TAG_NAME, "loadSdkArgs error", e);
        }
        return null;
    }

    public String[] getUserNames(){
        return DBCenter.getInstance(this.context).queryAllUserName();
    }

    public Boolean chackHasUser(Context context,String username){
        Boolean isHas = false;
        String[] uNameList = getUserNames();
        for (int i = 0; i < uNameList.length; i++) {
            if(username.equals(uNameList[i])){
                isHas = true;
                break;
            }
        }
        return isHas;
    }

    public void insertUserDatas(String userName, String password){
        DBCenter.getInstance(this.context).insertOrUpdateLoginUserName(userName, password,System.currentTimeMillis());
    }

    public String getPasswordByUsername(String userName){

        return  DBCenter.getInstance(this.context).queryPasswordByName(userName);
    }

    public void doLogin(final String userName, final String password, final String newPassword, String str){
        int timeStamp = Integer.valueOf(Utils.getCurTimestamp());
        bean = new AccRequestBean();
        bean.setPassword( password);//Utils.setPwd(password, timeStamp));
        bean.setTimestamp(timeStamp);
        bean.setUserName(userName);
        String sign = "";
        boolean isLogin = true;
        if(newPassword != null && !newPassword.equals("")) {
            isLogin = false;
            bean.setNewPassword(newPassword);//Utils.setPwd(newPassword, timeStamp));
            sign = Utils.getChangeSign(this.context.getApplicationContext(),bean);
        }else {

            sign = Utils.getSign(this.context.getApplicationContext(), bean, false);
        }
        bean.setSign(sign);
        json = Utils.getAccountJson(this.context.getApplicationContext(), bean, isLogin);
        url += str;//"/account/login"
        //new LogActivity.LogThread().start();
        loginRequest(new HeListener(){
            @Override
            public void onSuccess() {
                insertUserDatas(userName, newPassword.equals("")?password:newPassword);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    public Boolean checkPrams(String userName, String password, Boolean isReg) {

        if (TextUtils.isEmpty(userName)) {
            Utils.showTips(this.context, context.getString(R.string.acc_notnull));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Utils.showTips(this.context, context.getString(R.string.pwd_notnull));
            return false;
        }
        if (isReg) {
            if (!userName.matches("^[a-z|A-Z]{1}.{0,}$")) {
                Utils.showTips(this.context, context.getString(R.string.acc_a_z));
                return false;
            }
            if (!userName.matches("^[a-z|A-Z|0-9|_|.|-]{1,}$")) {
                Utils.showTips(this.context, context.getString(R.string.acc_1_z));
                return false;
            }
            if (!userName.matches("^.{4,16}$")) {
                Utils.showTips(this.context, context.getString(R.string.acc_4_16size));
                return false;
            }
            if (!password.matches("^.{6,20}$")) {
                Utils.showTips(this.context, context.getString(R.string.pwd_6_20size));
                return false;
            }
        }
        return true;
    }

    public Boolean checkPwdPrams(String userName, String newPwd, String newTPwd,Boolean isNew){
        if (TextUtils.isEmpty(userName)) {
            Utils.showTips(context,  context.getString(R.string.acc_notnull));
            return false;
        }
        if(!chackHasUser(context, userName)){
            Utils.showTips(context,context.getString(R.string.acc_true));
            return false;
        }
        if (TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(newTPwd)) {
            Utils.showTips(this.context,  context.getString(R.string.pwd_notnull));
            return false;
        }
        if(!newPwd.equals(getPasswordByUsername(userName))){
            Utils.showTips(context,context.getString(R.string.pwd_error));
            return false;
        }
        if (!newTPwd.matches("^.{6,20}$")) {
            Utils.showTips(this.context, context.getString(R.string.pwd_6_20size));
            return false;
        }
        if (isNew) {
            if (!newPwd.equals(newTPwd)) {
                Utils.showTips(this.context, context.getString(R.string.pwd_same));
                return false;
            }
        }
        else if (newPwd.equals(newTPwd)) {
            Utils.showTips(this.context, context.getString(R.string.pwd_diff));
            return false;
        }
        return true;
    }


    public void loginRequest(final HeListener listener){

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {

                    listener.onSuccess();

            }
        }, 2000);
    }

    private Handler m_logHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
        if ( msg.what == 0) {

            //HeTask.getInstance().startSwitchActivity(LogActivity.this);
        }
        else if(msg.what == 1){
            //finish();
        }

        }
    };

}
