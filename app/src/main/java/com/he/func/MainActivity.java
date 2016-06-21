package com.he.func;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import com.he.func.chat.FriendListFragmet;
import com.he.func.find.FriendCircleFragment;
import com.he.func.setting.SettingFragment;
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
		RongIM.UserInfoProvider, ViewPager.OnPageChangeListener{

	@BindView(R.id.viewpager)
	ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragment = new ArrayList<>();
	private Fragment mConversationFragment = null;


	@BindView(R.id.chat_text_one)
	TextView mChatText0;
	@BindView(R.id.chat_text_two)
	TextView mChatText1;
	@BindView(R.id.chat_text_three)
	TextView mChatText2;
	@BindView(R.id.chat_text_four)
	TextView mChatText3;
	//@OnClick({R.id.chat_main_one,R.id.chat_main_two,R.id.chat_main_three,R.id.chat_main_four})
	@OnClick({R.id.chat_text_one,R.id.chat_text_two,R.id.chat_text_three,R.id.chat_text_four})
	void onClicked(View view){
		onClick(view.getId());
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

	List<InfoList> list = new ArrayList<>();
	private void initUserInfo(){
		
		list.add(new InfoList("ren1234", "小蓝","http://img.7160.com/uploads/allimg/140607/9-14060G040290-L.jpg"));
		list.add(new InfoList("ren123", "小河", "http://img.7160.com/uploads/allimg/140619/9-140619121T70-L.jpg"));
		list.add(new InfoList("ren1243", "小王", "http://www.ilegance.com/sj/UploadFiles_9645/201111/2011111223572737.jpg"));
		list.add(new InfoList("ren1235", "小马", "http://www.zhlzw.com/sj/UploadFiles_9645/201111/2011111002080725.jpg"));
		RongIM.setUserInfoProvider(this, true);
	}

	@Override
	public UserInfo getUserInfo(String uId) {
//		UserInfo info = new UserInfo("ren1234", "小蓝", 
//				Uri.parse("http://img.7160.com/uploads/allimg/140607/9-14060G040290-L.jpg"));//http://img.7160.com/uploads/allimg/140619/9-140619121T70-L.jpg
		for (InfoList info: list) {
			if(info.getuId().equals(uId)){
				return new UserInfo(info.getuId(), info.getName(), Uri.parse(info.getUrl()));
			}
		}
		return null;
		
	}

	class InfoList{
		String uId;
		String name;
		String url;
		InfoList(String uId, String name, String url) {
			this.uId = uId;
			this.name = name;
			this.url = url;
		}
		
		public String getuId() {
			return uId;
		}
		public void setuId(String uId) {
			this.uId = uId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
	}

	
}
