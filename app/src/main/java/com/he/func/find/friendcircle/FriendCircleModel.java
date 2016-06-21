package com.he.func.find.friendcircle;

import android.content.Context;
import android.os.AsyncTask;

import com.he.base.HeListener;
import com.he.data.FriendCircleData;
import com.he.data.friendcircle.CircleItem;
import com.he.data.friendcircle.CommentConfig;
import com.he.data.friendcircle.CommentItem;
import com.he.data.friendcircle.FavortItem;

import java.util.List;

public class FriendCircleModel {

    private Context context;

    public FriendCircleModel(Context context){
        this.context = context;
    }

    public void addFavorite(CircleItem circleItem, final HeListener listener){
        FavortItem item = FriendCircleData.getInstance().createCurUserFavortItem();
        if(!circleItem.getIsMyFavort())
            circleItem.getFavorters().add(item);
        request(listener);
    }

    public void deleteFavorite(CircleItem item, String myFavortUserId, final HeListener listener){
        List<FavortItem> items = item.getFavorters();
        for(int i=0; i<items.size(); i++){
            if(myFavortUserId.equals(items.get(i).getUser().getUserId())){
                items.remove(i);
                request(listener);
                return;
            }
        }
    }

    public void addComment(CircleItem circleItem, CommentConfig config, String content, final HeListener listener){
        CommentItem newItem ;
        if(config.commentType == CommentConfig.Type.PUBLIC){
            newItem = FriendCircleData.getInstance().createPublicComment(content);
        }
        else{
            newItem = FriendCircleData.getInstance().createReplyComment(config.replyUser,content);
        }
        circleItem.getComments().add(newItem);
        request(listener);
    }

    public void deleteComment(CircleItem item, String commentId, final HeListener listener){
        List<CommentItem> items = item.getComments();
        for (int i = 0; i < items.size(); i++){
            if(commentId.equals(items.get(i).getId())){
                items.remove(i);
                request(listener);
                return;
            }
        }
    }

    public List<CircleItem> addCircle(List<CircleItem> curCircleDatas){
        //request(listener);
        return addCircleDatas(curCircleDatas);
    }
    public List<CircleItem> deleteCircle(List<CircleItem> items, int pos){
        for (int i = 0; i < items.size(); i++){
            if(pos == i){
                items.remove(i);
                break;
            }
        }
        return items;
    }


    public List<CircleItem> addCircleDatas(List<CircleItem> curCircleDatas) {
        return FriendCircleData.getInstance().addCircleDatas(curCircleDatas);
    }


    public void request(final HeListener listener){
       /* new Handler().postDelayed(new Runnable() {
            @Override public void run() {

                    listener.onSuccess();

            }
        }, 2000);*/
        //or
        new AsyncTask<Object, Integer, Object>(){
            @Override
            protected Object doInBackground(Object... params) {
                //和后台交互
                return null;
            }

            protected void onPostExecute(Object result) {
                listener.onSuccess();
            };
        }.execute();
    }

}
