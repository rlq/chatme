package com.he.func.find.friendcircle.itemview;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.content.ClipboardManager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.he.data.FriendCircleData;
import com.he.data.friendcircle.CommentItem;
import com.he.func.find.friendcircle.FriendCirclePresenter;
import com.he.util.Utils;
import com.lq.ren.chat.R;

public class LongClickDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Context context;
	private FriendCirclePresenter mPresenter;
	private CommentItem commentItem;
	private int mCirclePosition;

	public LongClickDialog(Context context, FriendCirclePresenter presenter,
						   CommentItem commentItem, int circlePosition) {
		super(context, R.style.comment_dialog);
		this.context = context;
		this.mPresenter = presenter;
		this.commentItem = commentItem;
		this.mCirclePosition = circlePosition;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.he_dialog_comment);
		initWindowParams();
		initView();
	}

	private void initWindowParams() {
		Window dialogWindow = getWindow();
		// 获取屏幕宽、高用
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = (int) (Utils.getScreenSizeWidth(context) * 0.65); // 宽度设置为屏幕的0.65

		dialogWindow.setGravity(Gravity.CENTER);
		dialogWindow.setAttributes(lp);
	}

	private void initView() {
		TextView copyTv = (TextView) findViewById(R.id.copyTv);
		copyTv.setOnClickListener(this);
		TextView deleteTv = (TextView) findViewById(R.id.deleteTv);
		if (commentItem != null
				&& FriendCircleData.getInstance().curUser.getUserId().equals(
						commentItem.getUser().getUserId())) {
			deleteTv.setVisibility(View.VISIBLE);
		} else {
			deleteTv.setVisibility(View.GONE);
		}
		deleteTv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.copyTv:
			if (commentItem != null) {
				ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboard.setPrimaryClip(ClipData.newPlainText(null,commentItem.getContent()));
				//clipboard.setText(commentItem.getContent());
			}
			dismiss();
			break;
		case R.id.deleteTv:
			if (mPresenter != null && commentItem != null) {
				mPresenter.deleteComment(mCirclePosition, commentItem.getId());
			}
			dismiss();
			break;
		default:
			break;
		}
	}

}
