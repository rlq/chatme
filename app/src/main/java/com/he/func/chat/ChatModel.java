package com.he.func.chat;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.he.config.KeyConfig;
import com.he.data.db.DBCenter;
import com.he.data.friendcircle.Friend;
import com.he.func.MainActivity;
import com.he.base.HeApplication;
import com.he.util.Utils;
import com.lq.ren.chat.R;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class ChatModel implements RongIM.UserInfoProvider{

    public List<Friend> friendList;
    public ChatModel(){

    }

    public String[] tokenStr = {
            "UuC2FNMBEjizvz/K69Q4T1u0a6FiDAXkqZVFq869ZR6i5ii6n7L+xgxHDmtdTlZb+zGb5uBhqrAVvYLEFFq5EQ==",
            "yzwAEF6NDbKhZcMOdgWbJwCKVeyBeCTy9gF4nyF5RjQsy7kDx1P2fwkvIPJZnqG7UgpxtmPu98VJYLe9URpqFGtfnliM5tMR",
            "zjaaqYRpIa/5uTT7w+H3plu0a6FiDAXkqZVFq869ZR5b+8q648N/feNVvmsZUBkk+zGb5uBhqrCz3W7pun1H6w==",
            "PWzFmv7HKh/Ntq2tvmADKwCKVeyBeCTy9gF4nyF5RjQsy7kDx1P2f7C+YCuDeDHY06P5SQGo0cjW2aqRzBtjD2tfnliM5tMR",
    "PWzFmv7HKh/Ntq2tvmADKwCKVeyBeCTy9gF4nyF5RjQsy7kDx1P2f7C+YCuDeDHY06P5SQGo0cjW2aqRzBtjD2tfnliM5tMR"};

    public String[] names = {"小河","小蓝","小王","小马"};

    public String[] uId = {"ren123","ren1234","ren1243","ren1235","ren0000"};

    public String[] url = {"http://img.7160.com/uploads/allimg/140619/9-140619121T70-L.jpg",
            "http://img.7160.com/uploads/allimg/140607/9-14060G040290-L.jpg",
            //"http%3A%2F%2Fimg.7160.com%2Fuploads%2Fallimg%2F140607%2F9-14060G040290-L.jpg"
            "http://www.ilegance.com/sj/UploadFiles_9645/201111/2011111223572737.jpg",
            "http://www.zhlzw.com/sj/UploadFiles_9645/201111/2011111002080725.jpg",
             "https://pic.pimg.tw/alx570601/1434006818-3220185377_n.jpg?v=1434006823"};

    //新注册帐号 i=4 此时是没有好友的 friendList.size()=0;
    public List<Friend> initChatUserData(int index){
        friendList = new ArrayList<>();
        if(index != 4) {
            for (int i = 0; i < 4; i++) {
                if(index != i){
                    friendList.add(new Friend(uId[i],names[i],url[i]));
                }
            }
        }
        RongIM.setUserInfoProvider(this,true);
        return friendList;
    }

    //添加聊天对象
    public void addChatUser(String uName){
        Boolean isNew = true;
        for (int i = 0; i < uId.length; i++) {
            if(uName.equals(uId[i])){
                friendList.add(new Friend(uId[i],names[i],url[i]));
                isNew = false;
            }
        }
        if(isNew){
            friendList.add(new Friend(uId[4],uName,url[4]));
        }
    }

    public List<Friend> getChatUser(){
        return friendList;
    }
    //获取用户
    @Override
    public UserInfo getUserInfo(String s) {
        for (Friend i : friendList){
            if(i.getUserId().equals(s)){
                return new UserInfo(i.getUserId(),i.getNickname(),Uri.parse(i.getPortrait()));
            }
        }
        return null;
    }

    //检查这个帐号是否注册
    public Boolean chackHasUser(Context context,String username){
        Boolean isHas = false;
        for (int i = 0; i < friendList.size(); i++) {
            if(username.equals(friendList.get(i).getUserId())){
                Utils.ShowTips(context, context.getString(R.string.reged_nick)+friendList.get(i).getNickname());
                return false;
            }
        }
       String[] uNameList = DBCenter.getInstance(context).queryAllUserName();
        for (int i = 0; i < uNameList.length; i++) {
            if(username.equals(uNameList[i])){
                isHas = true;
                break;
            }
        }
        if(isHas){
            addChatUser(username);
        }else {
            Utils.ShowTips(context,  context.getString(R.string.unReg));
        }
        return isHas;
    }
    /**
     * 建立与融云服务器的连接
     */
    public void connect(final Context context, int index) {
        String token = tokenStr[index];
        if (context.getApplicationInfo().packageName.equals(HeApplication.getCurProcessName(context.getApplicationContext()))) {
            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("LoginActivity", "--onTokenIncorrect");
                }
                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
                }
                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }

}
