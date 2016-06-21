package com.he.func.chat.FriendList;

import com.lq.ren.chat.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Window;
import android.widget.TextView;


public class ConversationActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.he_conversation);
        TextView mName = (TextView)findViewById(R.id.con_name);
        String sId = getIntent().getData().getQueryParameter("targetId");//群聊 或者私聊 不同 uID
        String sName = getIntent().getData().getQueryParameter("title");//获取昵称
        if(!TextUtils.isEmpty(sName)){
        	mName.setText(sName);
        }else {
			//TODO 拿到id 去自己服务器请求
        	
		}
    }

  }