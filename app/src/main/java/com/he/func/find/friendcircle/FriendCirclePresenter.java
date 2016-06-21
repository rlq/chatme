package com.he.func.find.friendcircle;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.he.base.HePresenter;
import com.he.config.KeyConfig;
import com.he.data.FriendCircleData;
import com.he.data.friendcircle.CircleItem;
import com.he.data.friendcircle.CommentConfig;
import com.he.func.find.friendcircle.itemadapter.FriendCircleViewHolder;
import com.he.base.HeListener;

import java.util.List;
import java.util.Random;


public class FriendCirclePresenter extends HePresenter {

    public final int TYPE_HEAD = 0;
    public final int TYPE_URL = 1;
    public final int TYPE_IMAGE = 2;

    public  boolean isDis = false;
    public FriendCircleViewHolder oldHolder;

    private FriendCircleModel friModle;
    private Context context;
    private FriendCircleView friView;
    private FriendCircleAdapter adapter;
    public String headImg;

    public FriendCirclePresenter(Context context, FriendCircleView friView){
        this.context = context;
        this.friView = friView;
        this.friModle = new FriendCircleModel(context);
    }

    public void setFriendCircleAdapter(FriendCircleAdapter adapter){
        this.adapter = adapter;
    }

    public List<CircleItem> loadData() {
        List<CircleItem> datas = FriendCircleData.getInstance().createCircleDatas(new Random().nextInt(10)+20,
                new Random().nextInt(4)+5);
        return datas;
    }

    public Context getContext(){
        return context;
    }

    /**Favorite 赞*/
    public void addFavorite(final int circlePosition){
        CircleItem circleItem  = adapter.getDatas().get(circlePosition);
        friModle.addFavorite(circleItem, new HeListener() {
            @Override
            public void onSuccess() {
               adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure() {

            }
        });
    }

    public void deleteFavorite(final int circlePosition, final String myFavortUserId){
        CircleItem item =  adapter.getDatas().get(circlePosition);
        Log.d(KeyConfig.TAG_NAME, "dele " + item.getFavorters().size());
        friModle.deleteFavorite(item, myFavortUserId, new HeListener() {
            @Override
            public void onSuccess() {
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure() {

            }
        });
    }

    /**comment 评论*/
    public void addComment(final String content, final CommentConfig config){
        final CircleItem circleItem = adapter.getDatas().get(config.circlePosition);
        friModle.addComment(circleItem,config, content, new HeListener() {
            @Override
            public void onSuccess() {
                adapter.notifyDataSetChanged();
                //friView.addComment(config.circlePosition, null);
            }
            @Override
            public void onFailure() {

            }
        });
    }

    public void deleteComment(final int circlePosition, final String commentId){
        CircleItem item = adapter.getDatas().get(circlePosition);
        friModle.deleteComment(item, commentId,new HeListener() {
            @Override
            public void onSuccess() {
                adapter.notifyDataSetChanged();
               //friView.deleteComment(circlePosition, commentId);
            }
            @Override
            public void onFailure() {

            }
        });
    }

    /** 自己添加的参数*/
    public void addCircle(final int circlePosition,final String circleId){

    }
    /**朋友圈*/
    public void addCircle(final List<CircleItem> list){
        List<CircleItem>  items  = friModle.addCircle(list);
        adapter.setDatas(items);
        adapter.notifyDataSetChanged();
        friView.addCircle();
    }

    public void deleteCircle( List<CircleItem> list,int circlePosition){
        List<CircleItem>  items = friModle.deleteCircle(list,circlePosition);
        adapter.setDatas(items);
        adapter.notifyDataSetChanged();
    }

    //点赞 或者取消
    @Override
    public void setText(String arg0, String arg1) {
        //friendCircleAdapter 会使用到
    }

    public void showEditTextBody(CommentConfig commentConfig){
        friView.updateEditTextBodyVisible(View.VISIBLE, commentConfig);
    }

}
