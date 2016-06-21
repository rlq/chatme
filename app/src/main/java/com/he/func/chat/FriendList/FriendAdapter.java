package com.he.func.chat.FriendList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.he.data.friendcircle.Friend;
import com.lq.ren.chat.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public  class FriendAdapter extends BaseAdapter {

    private final Context context;
    private List<Friend> friList = new ArrayList<>();
    public FriendAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Friend> list){
        this.friList = list;
    }

    @Override
    public String getItem(int position) {
        return friList.get(position).getUserId();
    }

    @Override
    public int getCount() {
        return friList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FriendViewHolder mViewHolder = new FriendViewHolder();
        if(convertView == null){
            convertView = LayoutInflater.from(this.context)
                    .inflate(R.layout.he_item_friend, parent, false);
            mViewHolder.name = (TextView) convertView.findViewById(R.id.item_friend_username);
            mViewHolder.icon = (ImageView) convertView.findViewById(R.id.item_friend_portrait);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (FriendViewHolder) convertView.getTag();
        }
        if(convertView != null){
            Friend friend = friList.get(position);
            if(!mViewHolder.name.getText().toString().equals(friend.getNickname()))
                mViewHolder.name.setText(friend.getNickname());
            ImageLoader.getInstance().displayImage(friend.getPortrait(), mViewHolder.icon);
        }
        return convertView;
    }

    class FriendViewHolder {
       // @BindView(R.id.item_friend_username)
        public TextView name;
       // @BindView(R.id.item_friend_portrait)
        public ImageView icon;

//        FriendViewHolder(Context context){
//            ButterKnife.bind((Activity) context);
//        }
    }
}


