package com.he.func.find.friendcircle;

import com.he.data.friendcircle.CommentConfig;
import com.he.data.friendcircle.CommentItem;
import com.he.data.friendcircle.FavortItem;


public interface FriendCircleView {

    void addCircle();

    void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);
}
