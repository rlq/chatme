package com.he.func.find.friendcircle.itemadapter;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.he.base.HeApplication;
import com.he.config.HeTask;
import com.he.data.friendcircle.CommentConfig;
import com.he.data.friendcircle.FavortItem;
import com.he.func.find.friendcircle.FriendCirclePresenter;
import com.he.func.find.friendcircle.OpenImageActivity;
import com.he.func.find.friendcircle.itemview.CommentListView;
import com.he.func.find.friendcircle.itemview.FavortListView;
import com.he.widget.CircleImage;
import com.he.widget.MultiImageView;
import com.he.widget.spannable.NameClickListener;
import com.lq.ren.chat.R;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendCircleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public int viewType;
    @BindView(R.id.headIv)
    public CircleImage headIv;//头像
    @BindView(R.id.nameTv)
    public TextView nameTv;//昵称
    @BindView(R.id.urlTipTv)
    public TextView urlTipTv;
    /**
     * 动态的内容
     */
    @BindView(R.id.contentTv)
    public TextView contentTv;
    @BindView(R.id.viewStub)
    public ViewStub viewStub;
    @BindView(R.id.timeTv)
    public TextView timeTv;
    @BindView(R.id.deleteBtn)//自己才会删除
    public TextView deleteBtn;
    /**
     * 展开 点赞和评论
     */
    @BindView(R.id.favBtn)
    public TextView favBtn;
    @BindView(R.id.commentBtn)
    public TextView commentBtn;
    @BindView(R.id.disView)
    public LinearLayout disView;
    @BindView(R.id.snsBtn)
    public ImageView snsBtn;
    /**
     * 点赞列表
     */
    @BindView(R.id.favortListTv)
    public FavortListView favortListTv;
    @BindView(R.id.lin_dig)
    public View linDig;
    /**
     * 评论列表
     */
    @BindView(R.id.commentList)
    public CommentListView commentList;
    @BindView(R.id.digCommentBody)
    public LinearLayout digCommentBody;

    /**
     * 链接
     */
    public ImageView urlImageIv;
    public TextView urlContentTv;
    public LinearLayout urlBody;
    /**
     * 图片
     */
    public MultiImageView multiImageView;

    public FavortListAdapter favortListAdapter;
    public CommentListAdapter commentAdapter;
    FriendCirclePresenter presenter;
    public int commonPosition;
    public String myFavortUserId;


    public FriendCircleViewHolder(View itemView, int viewType, FriendCirclePresenter presenter) {
        super(itemView);
        try {
            ButterKnife.bind(this, itemView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.viewType = viewType;
        this.presenter = presenter;

        commentAdapter = new CommentListAdapter(itemView.getContext());
        favortListAdapter = new FavortListAdapter();

        favortListTv.setAdapter(favortListAdapter);
        commentList.setAdapter(commentAdapter);
        snsBtn.setOnClickListener(this);
        favBtn.setOnClickListener(this);
        commentBtn.setOnClickListener(this);
        headIv.setOnClickListener(this);

        ViewStub viewStub = (ViewStub) itemView.findViewById(R.id.viewStub);
        if (viewType == presenter.TYPE_URL) {// 链接view
            viewStub.setLayoutResource(R.layout.he_friendcircle_url);
            viewStub.inflate();
            urlBody = (LinearLayout) itemView.findViewById(R.id.urlBody);
            if (urlBody != null) {
                urlImageIv = (ImageView) itemView.findViewById(R.id.urlImageIv);
                urlContentTv = (TextView) itemView.findViewById(R.id.urlContentTv);
                urlBody.setOnClickListener(this);
            }
        }
        if (viewType == presenter.TYPE_IMAGE) {  // 图片view
            viewStub.setLayoutResource(R.layout.he_friendcircle_image);
            viewStub.inflate();
            MultiImageView multiImageView = (MultiImageView) itemView.findViewById(R.id.multiImagView);
            if (multiImageView != null) {
                this.multiImageView = multiImageView;
            }
        }

    }

    @OnClick({R.id.favBtn, R.id.commentBtn, R.id.snsBtn, R.id.urlBody, R.id.headIv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.favBtn:
                //点赞、取消点赞
//                if(System.currentTimeMillis()-lastClickTime<700)//防止快速点击操作
//                    return;
//                lastClickTime = System.currentTimeMillis();
                if (presenter != null) {
                    if (presenter.getContext().getResources().getString(R.string.favort).equals(favBtn.getText())) {
                        favBtn.setText(presenter.getContext().getResources().getString(R.string.cancel));
                        presenter.addFavorite(commonPosition);
                    } else {//取消点赞
                        favBtn.setText(presenter.getContext().getResources().getString(R.string.favort));
                        presenter.deleteFavorite(commonPosition, myFavortUserId);
                    }
                }
                presenter.isDis = false;
                disView.setVisibility(View.GONE);
                break;
            case R.id.commentBtn:
                CommentConfig config = new CommentConfig();
                config.circlePosition = commonPosition;
                config.commentType = CommentConfig.Type.PUBLIC;
                presenter.showEditTextBody(config);
                presenter.isDis = false;
                disView.setVisibility(View.GONE);
                break;
            case R.id.snsBtn:
                //显示  分为两组
                if (presenter.oldHolder == null || presenter.oldHolder == this) {
                    if (!presenter.isDis) {
                        disView.setVisibility(View.VISIBLE);
                        presenter.oldHolder = this;
                    } else {
                        disView.setVisibility(View.GONE);
                    }
                } else {
                    if (presenter.oldHolder.disView.getVisibility() == View.VISIBLE) {//前一个隐藏 当前显示
                        presenter.oldHolder.disView.setVisibility(View.GONE);
                    }
                    presenter.oldHolder = this;
                    disView.setVisibility(View.VISIBLE);
                }
                presenter.isDis = !presenter.isDis;
                break;
            case R.id.urlBody:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
                presenter.getContext().startActivity(intent);
                break;
            case R.id.headIv:
                setIconClick(view, presenter.headImg);
                break;
            default:
                break;
        }
    }

    public void setFavort(boolean isMyFavort) {
        if (!isMyFavort)
            favBtn.setText(presenter.getContext().getResources().getString(R.string.favort));
        else
            favBtn.setText(presenter.getContext().getResources().getString(R.string.cancel));
    }

    public void setFavortItem(boolean hasFavort, final List<FavortItem> favortDatas) {

        if (hasFavort) {//处理点赞列表
            favortListTv.setSpanClickListener(new NameClickListener() {
                @Override
                public void onClick(int position) {
                    String userName = favortDatas.get(position).getUser().getNickname();
                    String userId = favortDatas.get(position).getUser().getUserId();
                    Toast.makeText(HeApplication.getContext, userName + " &id = " + userId, Toast.LENGTH_SHORT).show();
                }
            });
            favortListAdapter.setDatas(favortDatas);
            favortListAdapter.notifyDataSetChanged();
            favortListTv.setVisibility(View.VISIBLE);
        } else {
            favortListTv.setVisibility(View.GONE);
        }
    }


    private void setIconClick(View view, String picUrl) {
        List<String> url = new ArrayList<>();
        url.add(picUrl);
        OpenImageActivity.imageSize = new ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
        HeTask.getInstance().startImagePagerActivity(presenter.getContext(), url, 0);
    }

}
