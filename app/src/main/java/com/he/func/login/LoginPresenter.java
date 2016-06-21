package com.he.func.login;

import android.content.Context;

import com.he.base.HePresenter;
import com.he.data.User;
import com.he.base.HeListener;

import java.util.List;


public class LoginPresenter extends HePresenter implements HeListener {

    private LoginModel mLoginModle;
    private Context context;
    private LoginView mLoginView;

    public LoginPresenter(Context context, LoginView loginView){
        this.context = context;
        this.mLoginView = loginView;
        this.mLoginModle = new LoginModel(context);
    }

    public List<User> getUserDatas(){
        return mLoginModle.getUserDatas();
    }

    public String[] getUserNames(){
        return mLoginModle.getUserNames();
    }
    /**通过uName获得密码*/
    public String getPasswordByUsername(String userName){
        return mLoginModle.getPasswordByUsername(userName);
    }
    /** 验证帐号 密码等是否符合规范*/
    public Boolean checkPrams(String userName, String password, Boolean isReg) {
        return mLoginModle.checkPrams(userName,password,isReg);
    }
    /**验证新旧密码 */
    public Boolean checkPwdPrams(String userName, String newPwd, String newTPwd,Boolean isNew){
        return mLoginModle.checkPwdPrams(userName,newPwd,newTPwd,isNew);
    }
    /** 登录验证*/
    public void doLogin(String userName, String password,String newPassword, String str){
        mLoginModle.doLogin(userName,password,newPassword,str);
    }

    @Override
    public void setText(String arg0, String arg1) {
        if(mLoginView != null){
            mLoginView.setText(arg0, arg1);
        }
    }

    @Override
    public String[] getArg0() {
        return getUserNames();
    }

    @Override
    public String getArg1ByArg0(String arg0) {
        return getPasswordByUsername(arg0);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }
    /**检查是否存在用户*/
    public Boolean chackHasUser(Context context, String userName){
        return mLoginModle.chackHasUser(context, userName);
    }

}
