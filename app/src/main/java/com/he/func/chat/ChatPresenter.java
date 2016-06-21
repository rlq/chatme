package com.he.func.chat;


import android.content.Context;

import com.he.data.db.DBCenter;
import com.he.data.User;
import com.he.data.friendcircle.Friend;

import java.util.List;

public class ChatPresenter {
    private ChatModel chatModle;
    private Context context;
    private static int index = 4;
    public  ChatPresenter(Context context){
        this.context = context;
        chatModle = new ChatModel();
    }

    public void connectRongyun(){
        chatModle.connect(this.context, index);
    }

    public List<Friend> getChatUserData(){
         //需要重置friendList列表 size=0
        return  chatModle.initChatUserData(index);
    }

    public List<Friend> getAddChatUser(){
        return  chatModle.getChatUser();
    }

    //此函数已将friendList初始化
    public void success(Context context, String userName, String password){
        User info = new User();
        info.setUsername(userName);
        DBCenter.getInstance(context).insertOrUpdateLoginUserName(userName,password ,System.currentTimeMillis());
        switch (userName){
            case "ren123":
                index = 0;
                break;
            case "ren1234":
                index = 1;
                break;
            case "ren1243":
                index = 2;
                break;
            case "ren1235":
                index = 3;
                break;
            default:
                index = 4;
                //需要移除会话列表,每次退出时移除吧
                break;
        }
        connectRongyun();
    }

    public Boolean chackHasUser(Context context,String username){
        return chatModle.chackHasUser(context, username);
    }
}
