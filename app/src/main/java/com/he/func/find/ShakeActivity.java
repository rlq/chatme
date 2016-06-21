package com.he.func.find;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;

import com.he.config.KeyConfig;
import com.he.util.Utils;
import com.lq.ren.chat.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class ShakeActivity extends SwipeBackActivity {

    private Vibrator mVibrator;
    private ShakeListener mShakeListener;

    @BindView(R.id.shakeImgUp)
    RelativeLayout mImageUp;
    @BindView(R.id.shakeImgDown)
    RelativeLayout mImageDown;
    @BindView(R.id.shake_title_bar)
    RelativeLayout mTitle;

    /** Drawer*/
    @BindView(R.id.slidingDrawer)
    SlidingDrawer drawer;
    @BindView(R.id.handleBtn)
    Button drawerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.he_shake);
        ButterKnife.bind(this);
        initSwipeView();
        initShakeView();
        initDrawerView();
    }

    private void initSwipeView(){
        SwipeBackLayout layout = getSwipeBackLayout();
        layout.setEdgeSize(Utils.getScreenSizeWidth(this));
        layout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    private void initShakeView(){
        mVibrator = (Vibrator)getApplication().getSystemService(VIBRATOR_SERVICE);
        mShakeListener = new ShakeListener(this);
        mShakeListener.setOnShakeListener(new OnShakeListener() {
            @Override
            public void onShake() {
                Log.i(KeyConfig.TAG_NAME, "onshake");
                startAnim(mImageUp, -0.5f);
                startAnim(mImageDown, 0.5f);
                mShakeListener.onStop();
                mVibrator.vibrate(new long[]{500, 200, 500, 200}, -1);
                quest();
            }
        });
    }

    private void initDrawerView(){
        drawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                drawerBtn.setBackgroundResource(R.drawable.he_shake_dragger_down);
                startDrawerAnim(0,-1f,true);
            }
        });
        drawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                drawerBtn.setBackgroundResource(R.drawable.he_shake_dragger_up);
                startDrawerAnim(-1f,0,true);
            }
        });
    }


    private void quest(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               Utils.showTips(ShakeActivity.this, "");
                mVibrator.cancel();
                mShakeListener.onStart();
            }
        }, 2000);
    }

    private void startAnim(View view, float toYValue){
        float toYValue1;
        if(toYValue > 0){
            toYValue1 = toYValue - 1;
        }else{
            toYValue1 = toYValue + 1;
        }
        AnimationSet anim = new AnimationSet(true);
        TranslateAnimation trans = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,toYValue);
        trans.setDuration(300);
        TranslateAnimation transTo = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,toYValue1);
        transTo.setDuration(300);
        transTo.setStartOffset(1000);

        anim.addAnimation(trans);
        anim.addAnimation(transTo);
        view.startAnimation(anim);
    }

    private void startDrawerAnim(float f1, float f2, Boolean isUp){
        TranslateAnimation trans = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,f1,
                Animation.RELATIVE_TO_SELF,f2);
        trans.setDuration(200);
        trans.setFillAfter(isUp);
        mTitle.startAnimation(trans);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mShakeListener != null){
            mShakeListener.onStop();
        }
    }

    public void backTo(View v){
        finish();
    }

    class ShakeListener implements SensorEventListener{
        private static final int UPDATE_INTERVAL_TIME = 70;
        private SensorManager manager;
        private Sensor sensor;
        private OnShakeListener listener;
        private Context context;

        private long lastUpdataTime;
        public ShakeListener(Context context){
            this.context = context;
            onStart();
        }

        private void onStart(){
            manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
            if(manager != null){
                sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            }
            if(sensor != null){
                manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
            }
        }

        private void onStop(){
            manager.unregisterListener(this);
        }

        private void setOnShakeListener(OnShakeListener listener){
            this.listener = listener;
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            long curUpdateTime = System.currentTimeMillis();
            long timeInterval = curUpdateTime - lastUpdataTime;
            if(timeInterval < UPDATE_INTERVAL_TIME){
                return;
            }
            lastUpdataTime = curUpdateTime;
            float[] values = event.values;

            if((Math.abs(values[0])>14 || Math.abs(values[1])>14 ||Math.abs(values[2])>14)){
                listener.onShake();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    public interface OnShakeListener {
        void onShake();
    }
}
