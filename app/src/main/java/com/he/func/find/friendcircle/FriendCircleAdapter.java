package com.he.func.find.friendcircle;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.he.base.HeView;
import com.he.config.HeTask;
import com.he.data.FriendCircleData;
import com.he.data.friendcircle.CircleItem;
import com.he.data.friendcircle.CommentConfig;
import com.he.data.friendcircle.CommentItem;
import com.he.func.find.friendcircle.itemadapter.FriendCircleViewHolder;
import com.he.func.find.friendcircle.itemview.CommentListView;
import com.he.func.find.friendcircle.itemview.LongClickDialog;
import com.he.widget.MultiImageView;
import com.lq.ren.chat.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendCircleAdapter extends RecyclerView.Adapter implements HeView,
        View.OnClickListener, View.OnTouchListener {


    private FriendCirclePresenter mPresenter;
    private Context context;
    private List<CircleItem> mDatas = new ArrayList<>();
    private FriendCircleViewHolder mViewHolder;
    CircleItem circleItem;

    private static FriendCircleAdapter instance;

    public static FriendCircleAdapter getInstance() {
        if (instance == null) {
            instance = new FriendCircleAdapter();
        }
        return instance;
    }

    public void setDatas(List<CircleItem> data) {
        this.mDatas = data;
    }

    public List<CircleItem> getDatas() {
        return this.mDatas;
    }

    public FriendCircleAdapter() {
    }

    public FriendCircleAdapter(Context context, FriendCirclePresenter presenter) {
        this.context = context;
        this.mPresenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;
        if (viewType == mPresenter.TYPE_HEAD) {
            View headView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.he_friendcircle_head, parent, false);
            holder = new FriendCircleHeaderHolder(headView);
            headView.setOnTouchListener(this);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.he_friendcircle_adapter, parent, false);

            holder = new FriendCircleViewHolder(view, viewType, mPresenter);
            view.setOnTouchListener(this);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        circleItem = mDatas.get(position);
        if (getItemViewType(position) == mPresenter.TYPE_HEAD) {
            FriendCircleHeaderHolder holder1 = (FriendCircleHeaderHolder) holder;
            holder1.nickName.setText(circleItem.getOwer().getNickname());
        } else {
            bindView(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return mPresenter.TYPE_HEAD;
        }
        int itemType = 0;
        CircleItem item = mDatas.get(position);
        if (CircleItem.TYPE_URL.equals(item.getType())) {//"1" TYPE_URL
            itemType = mPresenter.TYPE_URL;
        } else if (CircleItem.TYPE_IMG.equals(item.getType())) {//2 TYPE_IMG
            itemType = mPresenter.TYPE_IMAGE;
        }
        return itemType;
    }

    private void bindView(RecyclerView.ViewHolder vh, int position) {
        mViewHolder = (FriendCircleViewHolder) vh;
        mViewHolder.commonPosition = position;
        /** 朋友圈 */
        String name = circleItem.getUser().getNickname();
        String headImg = circleItem.getUser().getPortrait();
        final String content = circleItem.getContent();
        String createTime = circleItem.getCreateTime();

        ImageLoader.getInstance().displayImage(headImg, mViewHolder.headIv);
        mViewHolder.nameTv.setText(name);
        mViewHolder.timeTv.setText(createTime);
        mViewHolder.contentTv.setText(content);
        mViewHolder.contentTv.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);
        mPresenter.headImg = headImg;

        //自己发的朋友圈 可删除
        if (FriendCircleData.getInstance().curUser.getUserId().equals(circleItem.getUser().getUserId())) {
            mViewHolder.deleteBtn.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.deleteBtn.setVisibility(View.GONE);
        }
        mViewHolder.deleteBtn.setOnClickListener(this);

        //展开 赞和评论
        mViewHolder.disView.setVisibility(View.GONE);
        /** 点赞列表*/
        mViewHolder.myFavortUserId = circleItem.getMyFavortUserId();
        mViewHolder.setFavort(circleItem.getIsMyFavort());

        mViewHolder.setFavortItem(circleItem.hasFavort(), circleItem.getFavorters());
        if (mPresenter.isDis) {
            setOldDisView();
        }
        /** 评论列表*/
        setCommentItem(circleItem.hasComment(), circleItem.getComments(), position);
        mViewHolder.linDig.setVisibility(circleItem.hasFavort() && circleItem.hasComment() ? View.VISIBLE : View.GONE);
        mViewHolder.digCommentBody.setVisibility(circleItem.hasFavort() && circleItem.hasComment() ? View.VISIBLE : View.GONE);

        /** 处理链接动态的链接内容和和图片*/
        if (mViewHolder.viewType == mPresenter.TYPE_URL) {
            String linkImg = circleItem.getLinkImg();
            String linkTitle = circleItem.getLinkTitle();
            ImageLoader.getInstance().displayImage(linkImg, mViewHolder.urlImageIv);
            mViewHolder.urlContentTv.setText(linkTitle);
            mViewHolder.urlBody.setVisibility(View.VISIBLE);
        }

        if (mViewHolder.viewType == mPresenter.TYPE_IMAGE) {
            // 处理图片
            final List<String> photos = circleItem.getPhotos();
            if (photos != null && photos.size() > 0) {
                mViewHolder.multiImageView.setVisibility(View.VISIBLE);
                mViewHolder.multiImageView.setList(photos);
                mViewHolder.multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // 因为单张图片时，图片实际大小是自适应的，imageLoader缓存时是按测量尺寸缓存的
                        OpenImageActivity.imageSize = new ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                        HeTask.getInstance().startImagePagerActivity(context, photos, position);

                    }
                });
            } else {
                mViewHolder.multiImageView.setVisibility(View.GONE);
            }
        }
    }

    private void setCommentItem(boolean hasComment, final List<CommentItem> commentsDatas, final int position) {
        if (hasComment) {//处理评论列表
            mViewHolder.commentList.setOnItemClick(new CommentListView.OnItemClickListener() {
                @Override
                public void onItemClick(int commentPosition) {
                    if (mPresenter.isDis)
                        setOldDisView();
                    CommentItem commentItem = commentsDatas.get(commentPosition);
                    if (FriendCircleData.getInstance().curUser.getUserId().equals(commentItem.getUser().getUserId())) {
                        //复制或者删除自己的评论
                        LongClickDialog dialog = new LongClickDialog(context, mPresenter, commentItem, position);
                        dialog.show();
                    } else {//回复别人的评论
                        if (mPresenter != null) {
                            CommentConfig config = new CommentConfig();
                            config.circlePosition = position;
                            config.commentPosition = commentPosition;
                            config.commentType = CommentConfig.Type.REPLY;
                            config.replyUser = commentItem.getUser();
                            mPresenter.showEditTextBody(config);
                        }
                    }
                }
            });
            mViewHolder.commentList.setOnItemLongClick(new CommentListView.OnItemLongClickListener() {
                @Override
                public void onItemLongClick(int commentPosition) {
                    if (mPresenter.isDis)
                        setOldDisView();
                    //长按进行复制或者删除
                    CommentItem commentItem = commentsDatas.get(commentPosition);
                    LongClickDialog dialog = new LongClickDialog(context, mPresenter, commentItem, position);
                    dialog.show();
                }
            });
            mViewHolder.commentAdapter.setDatas(commentsDatas);
            mViewHolder.commentAdapter.notifyDataSetChanged();
            mViewHolder.commentList.setVisibility(View.VISIBLE);

        } else {
            mViewHolder.commentList.setVisibility(View.GONE);
        }
        mViewHolder.digCommentBody.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteBtn:
                if (mPresenter != null) {
                    mPresenter.deleteCircle(getDatas(), mViewHolder.commonPosition - 1);
                }
                break;
        }
    }

    //显示 点赞/取消  还是添加评论
    @Override
    public void setText(String[] arg0) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mPresenter.isDis) {
            setOldDisView();
        }
        return false;
    }

    public void setOldDisView() {
        mPresenter.isDis = false;
        mPresenter.oldHolder.disView.setVisibility(View.GONE);
    }


    static class FriendCircleHeaderHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nick)
        public TextView nickName;

        public FriendCircleHeaderHolder(View itemView) {
            super(itemView);
            try {
                ButterKnife.bind(this, itemView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
