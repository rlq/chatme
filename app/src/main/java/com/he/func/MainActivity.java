package com.he.func;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

import java.util.ArrayList;
import java.util.List;

import com.he.func.chat.FriendListFragmet;
import com.he.func.find.FriendCircleFragment;
import com.he.func.setting.SettingFragment;
import com.he.util.Utils;
import com.lq.ren.chat.R;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


public class MainActivity extends FragmentActivity  implements
		ViewPager.OnPageChangeListener{

	@BindView(R.id.viewpager)
	ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragment = new ArrayList<>();
	private Fragment mConversationFragment = null;
	private long mLastTime;

	@BindView(R.id.chat_text_one)
	TextView mChatText0;
	@BindView(R.id.chat_text_two)
	TextView mChatText1;
	@BindView(R.id.chat_text_three)
	TextView mChatText2;
	@BindView(R.id.chat_text_four)
	TextView mChatText3;

	@OnClick({R.id.chat_text_one,R.id.chat_text_two,R.id.chat_text_three,R.id.chat_text_four})
	void onClicked(View view){
		onClick(view.getId());
	}

	private Fragment initConversationList() {
		if(mConversationFragment == null){
			ConversationListFragment listFragment = ConversationListFragment.getInstance();
			Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
					.appendPath("conversationlist")
					.appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
					.appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
					.appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//讨论组
					.appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
					.appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
					.appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//系统
					.build();
			listFragment.setUri(uri);
			return listFragment;
		}else {
			return mConversationFragment;
		}
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.he_chatmian);
		ButterKnife.bind(this);
		setIsSelect(mChatText0, R.color.chat_main_bg);
		mFragment.add(initConversationList());
		mFragment.add(FriendListFragmet.getInstance());
		mFragment.add(FriendCircleFragment.getInstance());
		mFragment.add(SettingFragment.getInstance());
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mFragment.size();
			}
			
			@Override
			public Fragment getItem(int pos) {
				// TODO Auto-generated method stub
				return mFragment.get(pos);
			}
		};
		
		mViewPager.setAdapter(mAdapter);
		mViewPager.addOnPageChangeListener(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	private void onClick(int id){
		switch (id) {
			case R.id.chat_text_one:
				mViewPager.setCurrentItem(0);
				break;
			case R.id.chat_text_two:
				mViewPager.setCurrentItem(1);
				break;
			case R.id.chat_text_three:
				mViewPager.setCurrentItem(2);
				break;
			case R.id.chat_text_four:
				mViewPager.setCurrentItem(3);
				break;
			default:
				break;
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int i) {
		unSelected();
		switch (i) {
			case 0:
				setIsSelect(mChatText0, R.color.chat_main_bg);
				break;
			case 1:
				setIsSelect(mChatText1, R.color.chat_main_bg);
				break;
			case 2:
				setIsSelect(mChatText2, R.color.chat_main_bg);
				break;
			case 3:
				setIsSelect(mChatText3, R.color.chat_main_bg);
				break;
			default:
				break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	private void setIsSelect(TextView text, int color ){
		text.setTextColor(getResources().getColor(color));
	}

	private void unSelected() {
		setIsSelect(mChatText0, R.color.chat_text_bg);
		setIsSelect(mChatText1, R.color.chat_text_bg);
		setIsSelect(mChatText2, R.color.chat_text_bg);
		setIsSelect(mChatText3, R.color.chat_text_bg);
	}

	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - mLastTime < 2000) {
			super.onBackPressed();
		} else {
			mLastTime = System.currentTimeMillis();
			Utils.showTips(this, "再按一次退出应用");
		}
	}
}
