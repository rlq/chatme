package com.he.func.login;

import com.he.config.HeTask;
import com.he.data.User;
import com.lq.ren.chat.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
/** 切换帐号*/
public class SwitchActivity extends Activity {

	@OnClick({R.id.account_manager})
	void onClicked(){
		HeTask.getInstance().startLoginActivity(SwitchActivity.this);
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.he_switch);
		ButterKnife.bind(this);
		((TextView)findViewById(R.id.keylogin_name)).setText(new User().getUsername());

		int time = 3;
	    new Handler().postDelayed(new Runnable()
	    {
	      public void run() {
	    	  SwitchActivity.this.finish();
	      }
	    }
	    , time * 1000);
	}

}
