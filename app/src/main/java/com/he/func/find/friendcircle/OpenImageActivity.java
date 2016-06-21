package com.he.func.find.friendcircle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.he.base.HeApplication;
import com.he.config.HeTask;
import com.he.data.friendcircle.OpenImageData;
import com.he.util.Utils;
import com.lq.ren.chat.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class OpenImageActivity extends Activity {

    private List<OpenImageData.CommentPicBean> picDataList;
    private List<View> guideViewList = new ArrayList<View>();
    private LinearLayout guideGroup;
    public static ImageSize imageSize;
    private ViewPager viewPager;
    private ImageAdapter mAdapter;
    public static String PICDATALIST = "PICDATALIST";
    int startPos;
    ArrayList<String> imgUrls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.he_friendcricle_image);

        viewPager = (ViewPager) findViewById(R.id.pager);
        guideGroup = (LinearLayout) findViewById(R.id.guideGroup);

        startPos = getIntent().getIntExtra(HeTask.getInstance().INTENT_POSITION, 0);
        imgUrls = getIntent().getStringArrayListExtra(HeTask.getInstance().INTENT_IMGURLS);
        picDataList = (List<OpenImageData.CommentPicBean>) getIntent().getSerializableExtra(PICDATALIST);

        mAdapter = new ImageAdapter(this);
        mAdapter.setDatas(imgUrls);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0; i<guideViewList.size(); i++){
                    guideViewList.get(i).setSelected(i==position ? true : false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(startPos);

        addGuideView(guideGroup, startPos, imgUrls);
    }

    private void addGuideView(LinearLayout guideGroup, int startPos, ArrayList<String> imgUrls) {
        if(imgUrls!=null && imgUrls.size()>0){
            guideViewList.clear();
            for (int i=0; i<imgUrls.size(); i++){
                View view = new View(this);
                view.setBackgroundResource(R.drawable.selector_guide_bg);
                view.setSelected(i==startPos ? true : false);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.gudieview_width),
                        getResources().getDimensionPixelSize(R.dimen.gudieview_heigh));
                layoutParams.setMargins(10, 0, 0, 0);
                guideGroup.addView(view, layoutParams);
                guideViewList.add(view);
            }
        }
    }

    int height,width;
    private void computeImageWidthAndHeight(PhotoView imageView) {
//      获取真实大小
        Drawable drawable = imageView.getDrawable();
        int imageHeight = drawable.getIntrinsicHeight();
        int imageWidth = drawable.getIntrinsicWidth();
//        计算出与屏幕的比例，用于比较以宽的比例为准还是高的比例为准，因为很多时候不是高度没充满，就是宽度没充满
        float h = Utils.getScreenSizeHeight(this) * 1.0f / imageHeight;
        float w = Utils.getScreenSizeWidth(this) * 1.0f / imageWidth;
        if (h > w) {
            h = w;
        } else {
            w = h;
        }
//      得出当宽高至少有一个充满的时候图片对应的宽高
        height = (int) (imageHeight * h);
        width = (int) (imageWidth * w);
    }

    private static class ImageAdapter extends PagerAdapter implements
            PhotoViewAttacher.OnPhotoTapListener {

        private List<String> mDatas = new ArrayList<>();
        private LayoutInflater mInflater;
        private Context context;
        private View mCurrentView;

        public void setDatas(List<String> datas) {
            if(datas != null )
                this.mDatas = datas;
        }

        public ImageAdapter(Context context){
            this.context = context;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if(mDatas == null) return 0;
            return mDatas.size();
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            mCurrentView = (View) object;
        }

        public View getPrimaryItem() {
            return mCurrentView;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = mInflater.inflate(R.layout.he_friendcircle_imageitem, container, false);
            if(view != null){
                final PhotoView imageView = (PhotoView) view.findViewById(R.id.image);

                //预览imageView
                final ImageView smallImageView = new ImageView(context);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(imageSize.getWidth(), imageSize.getHeight());
                layoutParams.gravity = Gravity.CENTER;
                smallImageView.setLayoutParams(layoutParams);
                smallImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ((FrameLayout)view).addView(smallImageView);
                //loading
                final ProgressBar loading = new ProgressBar(context);
                FrameLayout.LayoutParams loadingLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                loadingLayoutParams.gravity = Gravity.CENTER;
                loading.setLayoutParams(loadingLayoutParams);
                ((FrameLayout)view).addView(loading);

                final String imgurl = mDatas.get(position);
                ImageLoader.getInstance().displayImage(imgurl, imageView, HeApplication.options, new SimpleImageLoadingListener(){
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        //获取内存中的缩略图
                        String memoryCacheKey = MemoryCacheUtils.generateKey(imageUri, imageSize);
                        Bitmap bmp = ImageLoader.getInstance().getMemoryCache().get(memoryCacheKey);
                        if(bmp != null && !bmp.isRecycled()){
                            smallImageView.setVisibility(View.VISIBLE);
                            smallImageView.setImageBitmap(bmp);
                        }
                        loading.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        loading.setVisibility(View.GONE);
                        smallImageView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        super.onLoadingFailed(imageUri, view, failReason);
                    }
                });

                container.addView(view, 0);
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }


        @Override
        public void onPhotoTap(View view, float v, float v1) {

        }
    }
}
