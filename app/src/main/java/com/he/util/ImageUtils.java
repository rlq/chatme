package com.he.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ImageUtils {
    /**
     * 使用此加载框架的imageloader加载的图片，设置了缓存后，下次使用，手工从缓存取出来用，这时特别要注意，不能直接使用：
     * imageLoader.getMemoryCache().get(uri)来获取，因为在加载过程中，key是经过运算的，而不单单是uri,而是：
     * String memoryCacheKey = MemoryCacheUtil.generateKey(uri, targetSize);
     *
     * @return
     */
    public static Bitmap getBitmapFromCache(String uri,ImageLoader imageLoader){//这里的uri一般就是图片网址
        List<String> memCacheKeyNameList = MemoryCacheUtils.findCacheKeysForImageUri(uri, imageLoader.getMemoryCache());
        if(memCacheKeyNameList != null && memCacheKeyNameList.size() > 0){
            for(String each:memCacheKeyNameList){
            }
            return imageLoader.getMemoryCache().get(memCacheKeyNameList.get(0));
        }

        return null;
    }


    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "DownloadJianXi");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + appDir.getPath())));
    }
}
