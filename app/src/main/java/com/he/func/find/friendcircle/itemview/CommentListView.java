package com.he.func.find.friendcircle.itemview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.he.func.find.friendcircle.itemadapter.CommentListAdapter;
import com.he.widget.spannable.NameClickListener;

public class CommentListView extends LinearLayout {

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public CommentListView(Context context) {
        super(context);
    }

    public CommentListView(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    public CommentListView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    public void setAdapter(CommentListAdapter adapter){
        adapter.bindListView(this);
    }

    public void setOnItemClick(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }
    public void setOnItemLongClick(OnItemLongClickListener listener){
        mOnItemLongClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener(){
        return mOnItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener(){
        return mOnItemLongClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(int position);
    }
}
