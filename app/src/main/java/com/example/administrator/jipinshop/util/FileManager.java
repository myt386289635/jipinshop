package com.example.administrator.jipinshop.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.os.Environment.DIRECTORY_PICTURES;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe 文件管理
 */
public class FileManager {

    /**
     * /storage/emulated/0/Android/data/com.example.administrator.jipinshop/files/Pictures
     * 这类文件夹是用户可见，可删的，并且在APP卸载后也会自动卸载掉该文件夹
     * 一般放一些长时间保存的数据
     * @param context
     * @return
     */
    public static String externalFiles(Context context){
        return context.getExternalFilesDir(DIRECTORY_PICTURES).getPath();
    }

    public static String editPhone(String phone){
        if(!TextUtils.isEmpty(phone)){
            String start = phone.substring(0,3);
            String end = phone.substring(phone.length() - 4,phone.length());
            start = start + "****" + end;
            return start;
        }
        return phone;
    }


    /**
     * 保存图片
     * @param bm
     * @throws IOException
     */
    public static void saveFile(Bitmap bm ,Context mContext){
        String sdDir = FileManager.externalFiles(mContext);
        String str =  sdDir + "/"+ System.currentTimeMillis() + "bitmap.jpg";
        File myCaptureFile = new File(str);
        if (myCaptureFile.exists()) {
            myCaptureFile.delete();
        }
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            //刷新图库
            MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                    myCaptureFile.getAbsolutePath(), myCaptureFile.getName(), null);
            ToastUtil.show("已保存到相册");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //把图片保存后声明这个广播事件通知系统相册有新图片到来
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        mContext.sendBroadcast(intent);
    }
}
