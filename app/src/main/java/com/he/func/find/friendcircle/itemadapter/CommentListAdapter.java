package com.he.func.find.friendcircle.itemadapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.he.data.friendcircle.CommentItem;
import com.he.func.find.friendcircle.itemview.CommentListView;
import com.he.widget.spannable.CircleMovementMethod;
import com.he.widget.spannable.NameClickListener;
import com.he.widget.spannable.NameClickable;
import com.lq.ren.chat.R;

import java.util.List;

import androidx.annotation.NonNull;

public class CommentListAdapter {

    private Context context;
    private CommentListView mCommentListView;
    private List<CommentItem> mDatas;

    public List<CommentItem> getDatas() {
        return mDatas;
    }

    public void setDatas(List<CommentItem> datas) {
        this.mDatas = datas;
    }

    private boolean notNull(){
        return mDatas !=null && mDatas.size()!=0;
    }

    public CommentListAdapter(Context context){
        this.context = context;
    }

    public void bindListView(CommentListView listview){
        if(listview != null){
            mCommentListView = listview;
        }
    }

    public int getCount() {
        return notNull() ? mDatas.size():0;
    }

    public Object getItem(int position) {
        return notNull()? mDatas.get(position):null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int i){
        View view = View.inflate(context, R.layout.he_commentlist_item,null);
        TextView commTv = (TextView)view.findViewById(R.id.commentTv);

        CommentItem item = mDatas.get(i);
        String replyName = "";
        String userName = item.getUser().getNickname();

        final CircleMovementMethod circleMovementMethod =  new CircleMovementMethod(R.color.name_color,
                R.color.name_color);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(setClickableSpan(userName, 0));
        if(item.getToReplyUser() != null){
            replyName = item.getToReplyUser().getNickname();
            builder.append(context.getString(R.string.reply));
            builder.append(setClickableSpan(replyName, 1));
        }
        builder.append(" : ");
        builder.append(item.getContent());

        commTv.setText(builder);
        commTv.setMovementMethod(circleMovementMethod);
        commTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (circleMovementMethod.isPassToTv()) {
                mCommentListView.getOnItemClickListener().onItemClick(i);
            }
            }
        });
        commTv.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
            if (circleMovementMethod.isPassToTv()) {
                mCommentListView.getOnItemLongClickListener().onItemLongClick(i);
            }
            return true;
            }
        });
        return view;
    }

    public void notifyDataSetChanged(){
        if(mCommentListView == null || mDatas.size() == 0)
            return;
        mCommentListView.removeAllViews();
        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mDatas.size(); i++) {
            View view = getView(i);
            if(view != null){
                mCommentListView.addView(view, i, layoutparams);
            }
        }
    }

    @NonNull
    private SpannableString setClickableSpan(String textStr, int position) {
        final SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new NameClickable(new NameClickListener() {
                    @Override
                    public void onClick(int position) {
                        Toast.makeText(context, subjectSpanText + "的朋友圈敬请期待", Toast.LENGTH_SHORT).show();
                    }
                },position),
                0, subjectSpanText.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

}
