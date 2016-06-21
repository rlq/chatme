package com.he.func.find.friendcircle.itemview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.he.func.find.friendcircle.itemadapter.FavortListAdapter;
import com.he.widget.spannable.NameClickListener;


public class FavortListView extends TextView {

    private NameClickListener mSpanClickListener;

    public void setSpanClickListener(NameClickListener listener){
        mSpanClickListener = listener;
    }

    public NameClickListener getSpanClickListener(){ return  mSpanClickListener; }

    public FavortListView(Context context) {
        super(context);
    }

    public FavortListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FavortListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(FavortListAdapter adapter){
        adapter.bindListView(this);
    }

}
