package com.he.func.login;

import com.he.data.db.DBCenter;
import com.he.util.Utils;
import com.lq.ren.chat.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MotifyPwdActivity extends Activity {

	@BindView(R.id.username_et)
	EditText userNameEdit;
	@BindView(R.id.old_pwd_et)
	EditText oldPwdEdit;
	@BindView(R.id.new_pwd_et)
	EditText newPwdEdit;

	private String userName ;
	private String oldPwd;
	private String newPwd;
	private LoginPresenter presenter;

	@OnClick({R.id.ok_btn,R.id.cancel_btn,R.id.inner_layout})
	void onClicked(View view){
		onClick(view.getId());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.he_change2_pwd);
		ButterKnife.bind(this);
		presenter = new LoginPresenter(MotifyPwdActivity.this, null);
		initChangePwdView();
	}

	private void initChangePwdView() {
		userName =  getIntent().getStringExtra("UserName");
		oldPwd = getIntent().getStringExtra("Password");
		userNameEdit.setText(userName);
		oldPwdEdit.setText(oldPwd);
	}

	private void onClick(int id){
		Utils.hideSoftInput(this,newPwdEdit);
		switch (id){
			case R.id.ok_btn:
				userName = userNameEdit.getText().toString();
				newPwd = newPwdEdit.getText().toString();
				oldPwd = oldPwdEdit.getText().toString();
				Boolean result = presenter.checkPwdPrams(userName, oldPwd, newPwd, false);
				if (result) {
					DBCenter.getInstance(MotifyPwdActivity.this).insertOrUpdateLoginUserName(userName, newPwd, System.currentTimeMillis());
					finish();
				}
				break;
			case R.id.cancel_btn:
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
