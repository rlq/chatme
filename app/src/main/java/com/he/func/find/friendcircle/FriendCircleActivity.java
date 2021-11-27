package com.he.func.find.friendcircle;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.he.data.friendcircle.CommentConfig;
import com.he.func.find.friendcircle.itemview.CommentListView;
import com.he.util.Utils;
import com.lq.ren.chat.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * 朋友圈
 */
public class FriendCircleActivity extends SwipeBackActivity implements View.OnClickListener,
        FriendCircleView, SwipeRefreshLayout.OnRefreshListener{

    private View mBackBtn;
    private View mSendBtn;

    private RecyclerView mRecyclerView;
    private FriendCircleAdapter mAdapter;

    private FriendCirclePresenter mPresenter;
    private LinearLayout mCommentEditLayout;
    private EditText mCommentEditText;
    private CommentConfig mCommentConfig;
    private int mTitleBarHeight;
    /**refresh*/
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.he_friendcircle_main);
        mLayoutManager = new LinearLayoutManager(this);
        initFriendCircleTitle();
        initFriendCircleView();
        initFriendCircleConmment();
        initRefreshView();
    }

    private void initFriendCircleTitle(){
        mTitleBarHeight = (findViewById(R.id.main_title_bar)).getHeight();
        mBackBtn = findViewById(R.id.backto);
        mSendBtn = findViewById(R.id.send_circle);
        mBackBtn.setOnClickListener(this);
        mSendBtn.setOnClickListener(this);

        SwipeBackLayout layout = getSwipeBackLayout();
        layout.setEdgeSize(Utils.getScreenSizeWidth(this));
        layout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    private void initFriendCircleView(){
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mCommentEditLayout.getVisibility() == View.VISIBLE) {
                    updateEditTextBodyVisible(View.GONE, null);
                    return true;
                }
                return false;
            }
        });

        mPresenter = new FriendCirclePresenter(this, this);
        mAdapter = new FriendCircleAdapter(this, mPresenter);
        mPresenter.setFriendCircleAdapter(mAdapter);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDatas(mPresenter.loadData());
        mAdapter.notifyDataSetChanged();
    }

    private void initFriendCircleConmment(){
        mCommentEditLayout = (LinearLayout) findViewById(R.id.editTextBodyLl);
        mCommentEditText = (EditText) findViewById(R.id.circleEt);
        ImageView sendIv = (ImageView) findViewById(R.id.sendIv);
        sendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter != null) {
                    //发布评论
                    String content = mCommentEditText.getText().toString().trim();
                    if(TextUtils.isEmpty(content)){
                        Toast.makeText(FriendCircleActivity.this, "评论内容不能为空...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mPresenter.addComment(content, mCommentConfig);
                }
                updateEditTextBodyVisible(View.GONE, null);
            }
        });
        setViewTreeObserver();
    }

    private void initRefreshView(){
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mRefreshLayout.setColorSchemeColors(R.color.color_gray, R.color.color_yellow,
                R.color.color_black, R.color.color_blue);
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(true);
        mPresenter.addCircle(mAdapter.getDatas());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backto:
                finish();
                break;
            case R.id.send_circle:
                Utils.showTips(this,getString(R.string.wait));
                break;
        }
    }

    @Override
    public void addCircle() {
        mRefreshLayout.setRefreshing(false);
    }


    @Override
    public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {
        if(commentConfig == null || commentConfig.equals(this.mCommentConfig) || mCommentEditLayout.getVisibility() == visibility){
            visibility = View.GONE;
        }else{
            mCommentEditText.setText("");
            this.mCommentConfig = commentConfig;
            measureCircleItemHighAndCommentItemOffset(commentConfig);
        }
        mCommentEditLayout.setVisibility(visibility);
        if(View.VISIBLE==visibility){
            mCommentEditText.requestFocus();
            //弹出键盘
            Utils.showSoftInput(mCommentEditLayout.getContext(), mCommentEditText);
        }else if(View.GONE==visibility){
            //隐藏键盘
            Utils.hideSoftInput(mCommentEditLayout.getContext(), mCommentEditText);
        }
    }

    private int mScreenHeight;
    private int mCurrentKeyboardH;
    private int mSelectCircleItemH;
    private int mSelectCommentItemOffset;
    private LinearLayoutManager mLayoutManager;
    private RelativeLayout mBodyLayout;
    /** */
    private void setViewTreeObserver() {
        mBodyLayout = (RelativeLayout) findViewById(R.id.bodyLayout);
        final ViewTreeObserver swipeRefreshLayoutVTO = mBodyLayout.getViewTreeObserver();
        swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                mBodyLayout.getWindowVisibleDisplayFrame(r);
                int statusBarH =  getStatusBarHeight();//状态栏高度
                int screenH = mBodyLayout.getRootView().getHeight();
                if(r.top != statusBarH ){
                    /**在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
                    */
                     r.top = statusBarH;
                }
                int keyboardH = screenH - (r.bottom - r.top);
                //Log.d(KeyConfig.TAG_NAME, "screenH＝ "+ screenH +" &keyboardH = " + keyboardH + " &r.bottom=" + r.bottom + " &top=" + r.top + " &statusBarH=" + statusBarH);

                if(keyboardH == mCurrentKeyboardH){//有变化时才处理，否则会陷入死循环
                    return;
                }

                mCurrentKeyboardH = keyboardH;
                mScreenHeight = screenH;//应用屏幕的高度

                /**偏移listview*/
                if(mRecyclerView !=null && mCommentConfig != null){
                    mLayoutManager.scrollToPositionWithOffset(mCommentConfig.circlePosition, getListviewOffset(mCommentConfig));
                }
            }
        });
    }

    /**获取状态栏高度*/
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    /**测量偏移量 */
    private int getListviewOffset(CommentConfig commentConfig) {
        if(commentConfig == null)
            return 0;
        //这里如果你的listview上面还有其它占高度的控件，则需要减去该控件高度，listview的headview除外。
        //int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight;
        int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mCommentEditText.getHeight() - 100 ;
        if(commentConfig.commentType == CommentConfig.Type.REPLY){
            //回复评论的情况
            listviewOffset = listviewOffset + mSelectCommentItemOffset - 20;
        }
        return listviewOffset;
    }

    private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig){
        if(commentConfig == null)
            return;
        int firstPosition = mLayoutManager.findFirstVisibleItemPosition();
        //只能返回当前可见区域（列表可滚动）的子项
        View selectCircleItem =  mLayoutManager.getChildAt(commentConfig.circlePosition - firstPosition);
        if(selectCircleItem != null){
            mSelectCircleItemH = selectCircleItem.getHeight();

            if(commentConfig.commentType == CommentConfig.Type.REPLY) {
                //回复评论的情况
                CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.commentList);
                if (commentLv != null) {
                    //找到要回复的评论view,计算出该view距离所属动态底部的距离
                    View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
                    if (selectCommentItem != null) {
                        //选择的commentItem距选择的CircleItem底部的距离
                        mSelectCommentItemOffset = 0;
                        View parentView = selectCommentItem;
                        do {
                            int subItemBottom = parentView.getBottom();
                            parentView = (View) parentView.getParent();
                            if (parentView != null) {
                                mSelectCommentItemOffset += (parentView.getHeight() - subItemBottom);
                            }
                        } while (parentView != null && parentView != selectCircleItem);
                    }
                }
            }
        }
    }
}
