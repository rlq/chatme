package com.he.func.setting;


import android.app.Activity;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;

import com.he.config.KeyConfig;
import com.lq.ren.chat.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingVideoActivity extends Activity {

    @OnClick({R.id.play,R.id.pause,R.id.reset,R.id.stop})
    void buttonClick(View view){
        buttonClick(view.getId());
    }
    private String curFilePath = "";//当前mp3地址 url
    private String curTempFilePath = "";
    private String strVideoURL = "";

    @BindView(R.id.textView_sound)
    TextView tvSound;

    MediaPlayer mediaPlayer;
    Boolean isRlease = false;
    Boolean isPause = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.he_setting_sound);
        ButterKnife.bind(this);

        strVideoURL = "http://play.baidu.com/?__methodName=mboxCtrl.playSong&fm=altg&__argsValue=490468#";
        getWindow().setFormat(PixelFormat.TRANSPARENT);
    }

    private void buttonClick(int id){
        switch (id){
            case R.id.play:
                playVideo(strVideoURL);
                tvSound.setText("播放");
                break;
            case R.id.pause:
                tvSound.setText("暂停");
                if(mediaPlayer != null && !isRlease ){
                    if(isPause){
                        mediaPlayer.start();
                        tvSound.setText("播放");
                    }else{
                        mediaPlayer.pause();
                        tvSound.setText("暂停");
                    }

                    isPause = !isPause;
                }
                break;
            case R.id.reset:
                tvSound.setText("重播");
                if(mediaPlayer != null && !isRlease){
                    mediaPlayer.seekTo(0);
                    tvSound.setText("播放");
                }
                break;
            case R.id.stop:
                tvSound.setText("已停止");
                if(mediaPlayer != null && !isRlease ){
                    mediaPlayer.seekTo(0);
                    mediaPlayer.pause();
                    tvSound.setText("已停止");
                }
                break;
            default:
                break;
        }
    }

    /**播放的是存储卡中暂时保存的mp3文件*/
    private void playVideo(final String strPath){
        if(strPath.equals(curFilePath) && mediaPlayer != null){
            mediaPlayer.start();
            return;
        }
        curFilePath = strPath;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(2);//???

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        /**捕捉缓冲区更新事件*/
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                Log.i(KeyConfig.TAG_NAME, "Upadate buffer: " + percent + "%");//Integer.toString(percent)
            }
        });
        /**播放完毕所触发的事件*/
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i(KeyConfig.TAG_NAME, "mediaPlayer Completed");
            }
        });
        /**开始阶段的事件*/
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.i(KeyConfig.TAG_NAME, "Prepared ");
            }
        });

        /** 将文件存储到sd卡后,通过start()播放MP3*/
        try {
            setDataSource(strPath);
            /**线程顺利,在setDataSource之后运行prepare() */
            mediaPlayer.prepare();
            Log.e(KeyConfig.TAG_NAME, "duration: "+ mediaPlayer.getDuration());
            //播放mp3
            mediaPlayer.start();
            isRlease = false;

        }catch (Exception e){

        }
        /*Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    setDataSource(strPath);
                    /**线程顺利,在setDataSource之后运行prepare() */
        /* mediaPlayer.prepare();
                    Log.e(KeyConfig.TAG_NAME, "duration: "+ mediaPlayer.getDuration());
                    //播放mp3
                    mediaPlayer.start();
                    isRlease = false;

                }catch (Exception e){

                }
            }

        };
        new Thread(r).start();*/
    }

    /**用于存储URL的Mp3文件到sd卡*/
    private void setDataSource(String urlPath) throws IOException {
        if(!URLUtil.isNetworkUrl(urlPath)){
            mediaPlayer.setDataSource(urlPath);
        }else{
            if(!isRlease){
                URL url = new URL(urlPath);
                URLConnection conn = url.openConnection();
                conn.connect();

                InputStream is = conn.getInputStream();
                if(is == null){
                    throw new RuntimeException("connection stream is null");
                }

                File tempFile = File.createTempFile("sound", "."+getFileExtension(urlPath));
                curTempFilePath = tempFile.getAbsolutePath();
                FileOutputStream fos = new FileOutputStream(tempFile);
                byte[] buf = new byte[128];
                do{
                    int numread = is.read(buf);
                    if(numread <= 0){
                        break;
                    }
                    fos.write(buf,0,numread);
                }while(true);

                /**fos存储完毕后,*/
                mediaPlayer.setDataSource(curTempFilePath);
                try{
                    is.close();
                }catch(Exception e){

                }
            }
        }

    }

    /** 获取音乐文件的扩展名,若无法获取则默认为".dat"*/
    private String getFileExtension(String strFileName){
        File file = new File(strFileName);
        String fileExtension = ".dat";
        if(file.exists()){
            fileExtension = file.getName();
            fileExtension = (fileExtension.substring(fileExtension.lastIndexOf(".")+1)).toLowerCase();
        }
        return fileExtension;
    }

    /** 退出后删除临时音乐文件*/
    private void deleFile(String strFileName){
        try {
            File file = new File(strFileName);
            if (file.exists()) {
                file.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        deleFile(curTempFilePath);
    }
}
