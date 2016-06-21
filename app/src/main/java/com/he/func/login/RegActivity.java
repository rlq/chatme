package com.he.func.login;

import com.he.func.chat.ChatPresenter;
import com.he.util.Utils;
import com.lq.ren.chat.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** 注册*/
public class RegActivity extends Activity {

	@BindView(R.id.login_username_et)
	EditText userNameTxt;
	@BindView(R.id.login_password_et)
	EditText loginPwdTxt;

	@OnClick({R.id.register_login_btn,R.id.register_cancel_btn,R.id.inner_layout})
	void onClicked(View view){
		onClick(view.getId());
	}

	private LoginPresenter mLoginPresenter;
	private ChatPresenter mChatPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.he_register);
		ButterKnife.bind(this);
		mLoginPresenter = new LoginPresenter(this, null);
		mChatPresenter = new ChatPresenter(this);
		initRegisterPage();
	}

	private void initRegisterPage() {
		userNameTxt.setText(Utils.getRandomAcc(4));
		loginPwdTxt.setText("111111");
		loginPwdTxt.setOnTouchListener(new EditText.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				loginPwdTxt.setCursorVisible(true);
				return false;
			}
		});
	}

	private void onClick(int id){
		Utils.hideSoftInput(this,loginPwdTxt);
		switch (id){
			case R.id.register_login_btn:
				// 隐藏虚拟键盘
				String userName = userNameTxt.getText().toString();
				String password = loginPwdTxt.getText().toString();
				Boolean result = mLoginPresenter.checkPrams(userName, password, true);
				if (result) {
					if(!mLoginPresenter.chackHasUser(this,userName)) {
						mChatPresenter.success(RegActivity.this, userName, password);
						finish();
					}else {
						Utils.showTips(this, getString(R.string.reged));
					}
				}
				break;
			case R.id.register_cancel_btn:
				finish();
				break;
			default:
				break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
