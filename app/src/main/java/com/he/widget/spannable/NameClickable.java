package com.he.widget.spannable;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.he.base.HeApplication;
import com.lq.ren.chat.R;

public class NameClickable extends ClickableSpan implements View.OnClickListener {
    private final NameClickListener mListener;
    private int mPosition;

    public NameClickable(NameClickListener l, int position) {
        mListener = l;
        mPosition = position;
    }

    @Override
    public void onClick(View widget) {
        mListener.onClick(mPosition);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        int colorValue = HeApplication.getContext.getResources().getColor(
                R.color.color_ublue);
        ds.setColor(colorValue);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
