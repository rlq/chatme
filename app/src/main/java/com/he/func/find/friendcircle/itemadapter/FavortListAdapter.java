package com.he.func.find.friendcircle.itemadapter;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;

import com.he.data.friendcircle.FavortItem;
import com.he.base.HeApplication;
import com.he.func.find.friendcircle.itemview.FavortListView;
import com.he.widget.spannable.CircleMovementMethod;
import com.he.widget.spannable.NameClickable;
import com.lq.ren.chat.R;

import java.util.List;

import androidx.annotation.NonNull;

public class FavortListAdapter {

    private FavortListView mListView;
    private List<FavortItem> mDatas;

    public List<FavortItem> getDatas() { return mDatas; }

    public void setDatas(List<FavortItem> datas) {
        this.mDatas = datas;
    }

    private boolean notNull(){
        return mDatas !=null && mDatas.size()!=0;
    }

    @NonNull
    public void bindListView(FavortListView listview){
        if(listview != null){
            mListView = listview;
        }
    }

    public int getCount() {
        return notNull() ? mDatas.size() : 0;
    }

    public Object getItem(int position) {
        return notNull()? mDatas.get(position):null;
    }

    public long getItemId(int position) {
        return position;
    }

    public void notifyDataSetChanged(){
        if(mListView == null){
            return;
        }
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if(mDatas != null && mDatas.size() > 0){
            //添加点赞图标
            builder.append(setImageSpan());
            //builder.append("  ");
            for (int i = 0; i< mDatas.size(); i++){
                FavortItem item = mDatas.get(i);
                if(item != null){
                    builder.append(setClickableSpan(item.getUser().getNickname(), i));
                    if(i != mDatas.size()-1){
                        builder.append(", ");
                    }
                }
            }
        }
        mListView.setText(builder);
        mListView.setMovementMethod(new CircleMovementMethod(R.color.name_color));
    }

    @NonNull
    private SpannableString setClickableSpan(String textStr, int position) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new NameClickable(mListView.getSpanClickListener(), position), 0,
                subjectSpanText.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

    private SpannableString setImageSpan(){
        String text = "  ";
        SpannableString imgSpanText = new SpannableString(text);
        imgSpanText.setSpan(new ImageSpan(HeApplication.getContext, R.drawable.he_favort1,
                DynamicDrawableSpan.ALIGN_BASELINE),0 , 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return imgSpanText;
    }

}
