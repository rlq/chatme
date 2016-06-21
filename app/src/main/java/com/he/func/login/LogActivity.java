package com.he.func.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.he.config.HeTask;
import com.he.data.db.DBCenter;
import com.he.data.User;
import com.he.func.chat.ChatPresenter;
import com.he.util.Utils;
import com.lq.ren.chat.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  ({@link LoginPresenter  } {@link ChatPresenter  })
 */
public class LogActivity extends Activity implements LoginView{

	@BindView(R.id.login_account_et)
	EditText uName;
	@BindView(R.id.login_password_et)
	EditText pwd;
	private PopupWindow mPopView;
	@BindView(R.id.login_display_btn)
	CheckBox mCheckBox =null;

	private String userName;
	private String password;
	private String psdDB;
	private List<User> mUserList = null;
	private LoginPresenter mLoginPresenter;
	private ChatPresenter mChatPresenter;

	@OnClick({R.id.login_login_btn,R.id.login_changepwd_btn,R.id.login_register_btn,
			R.id.login_display_btn,R.id.login_arrow_btn,R.id.inner_layout})
	void onClicked(View view){
		onClick(view.getId());
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.he_login);
		ButterKnife.bind(this);
		mLoginPresenter = new LoginPresenter(this, this);
		mChatPresenter = new ChatPresenter(this);
		initLoginPage();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mUserList = mLoginPresenter.getUserDatas();
		if(mUserList.size() > 0) {
			uName.setText(mUserList.get(0).getUsername());
			pwd.setText(mUserList.get(0).getPassword());
		}
	}

	private void initPopView(String[] usernames) {
		List<HashMap<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < usernames.length; i++) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("name", usernames[i]);
			//map.put("drawable", R.drawable.he_del_acount);
			list.add(map);
		}

		PopAdapter dropDownAdapter = new PopAdapter(this, list, mLoginPresenter,
				R.layout.he_dropdown_item,
				new String[] { "name", "drawable" },
				new int[] { R.id.droptv,
						R.id.droptv});
		//MyAdapter dropDownAdapter = new MyAdapter(this, list, R.layout.he_dropdown_item, new String[] { "name", "drawable" }, new int[] {R.id.textview, R.id.delete });
		ListView listView = new ListView(this);
		listView.setAdapter(dropDownAdapter);
		listView.setDivider(getResources().getDrawable(R.color.bgcolor));
		listView.setDividerHeight(1);

		mPopView = new PopupWindow(listView, uName.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
		mPopView.setFocusable(true);
		mPopView.setOutsideTouchable(true);
		mPopView.setBackgroundDrawable(getResources().getDrawable(R.drawable.he_popview_bg));
	}


	private void initLoginPage() {
		//dis pwd
		pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
		mCheckBox.setChecked(false);
	}

	public void doLoginAccount() {
		if(mLoginPresenter.chackHasUser(this,userName)) {
			mChatPresenter.success(LogActivity.this, userName, password);
			finish();
		}
		else {
			Utils.showTips(this, getString(R.string.unReg));
		}
	}

	private void onClick(int id){
		Utils.hideSoftInput(this,pwd);
		switch (id){
			case R.id.login_register_btn:
				HeTask.getInstance().startRegisterActivity(LogActivity.this);
				break;
			case R.id.login_login_btn:
				userName = uName.getText().toString();
				psdDB = mLoginPresenter.getPasswordByUsername(userName);
				password = pwd.getText().toString();
				Boolean result = mLoginPresenter.checkPrams(userName, password, false);
				if (result ) {
					if (password.equals(psdDB) || psdDB =="" || psdDB == null) {
						//progress = HeTask.getInstance().showProgress(LogActivity.this, "hegame", "请输入正确密码" , false, true);
						doLoginAccount();
					}
					else{
						Utils.showTips(LogActivity.this, getString(R.string.inputPassword));
						pwd.setText("");
					}
				}
				break;
			case R.id.login_changepwd_btn:
				HeTask.getInstance().startMotifyActivity(this, uName.getText().toString(), pwd.getText().toString());
				break;
			case R.id.login_display_btn:
				if (mCheckBox.isChecked()) {
					pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				}
				else
					pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
				break;
			case R.id.login_arrow_btn:
				if (mPopView != null) {
					if (!mPopView.isShowing()) {
						mPopView.showAsDropDown(uName);
					} else {
						mPopView.dismiss();
					}
				} else {
					if (mUserList.size() > 0) {
						initPopView(DBCenter.getInstance(LogActivity.this).queryAllUserName());
						if (!mPopView.isShowing()) {
							mPopView.showAsDropDown(uName);
						} else {
							mPopView.dismiss();
						}
					}
				}
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
	
	protected void onDestroy()
	  {
		super.onDestroy();
	  }

	@Override
	public void setText(String userName, String password) {
		uName.setText(userName);
		pwd.setText(password);
		mPopView.dismiss();
	}
}