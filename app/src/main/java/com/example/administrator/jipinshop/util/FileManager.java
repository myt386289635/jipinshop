package com.example.administrator.jipinshop.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.example.administrator.jipinshop.util.update.DownloadListener;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

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

    //下载图片保存相册时用这个地址，因为微信获取图片可能获取不到。
    public static String externalFiles2(){
        return Environment.getExternalStorageDirectory().getPath();
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
    public static File saveFile(Bitmap bm ,Context mContext){
        String sdDir = FileManager.externalFiles2();
        String str =  sdDir + "/DCIM/Camera/";
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        String pathName = file.getAbsolutePath() + "/"+  System.currentTimeMillis() + "bitmap.jpg";
        File myCaptureFile = new File(pathName);
        if (myCaptureFile.exists()){
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
        return myCaptureFile;
    }


    /**
     * 保存视频
     */
    public static void saveVideo(InputStream inputStream, Context context){
        try {
            String sdDir = FileManager.externalFiles2();
            File file = new File(sdDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            String pathName = file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".mp4";
            File myCaptureFile = new File(pathName);
            if (myCaptureFile.exists()) {
                myCaptureFile.delete();
            }
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                outputStream = new FileOutputStream(myCaptureFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                addCamera(context,myCaptureFile);
                outputStream.flush();
            } catch (IOException  e) {
                e.printStackTrace();
            }finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        }catch (IOException e) {
        }
    }

    //保存apk
    public static File saveAPK(ResponseBody responseBody , DownloadListener listener){
        long currentLength = 0;//当前进度
        long totalLength = responseBody.contentLength();
        InputStream inputStream = responseBody.byteStream();
        String sdDir = FileManager.externalFiles2();
        File file = new File(sdDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String pathName = file.getAbsolutePath() + "/jipincheng.apk";
        File myCaptureFile = new File(pathName);
        if (myCaptureFile.exists()) {
            myCaptureFile.delete();
        }
        OutputStream outputStream = null;
        try {
            byte[] fileReader = new byte[4096];
            outputStream = new FileOutputStream(myCaptureFile);
            while (true) {
                int read = inputStream.read(fileReader);
                if (read == -1) {
                    break;
                }
                outputStream.write(fileReader, 0, read);
                //计算当前下载百分比，并经由回调传出
                currentLength += read;
                listener.onProgress((int) (100 * currentLength / totalLength));
                //当百分比为100时下载结束
                if ((int) (100 * currentLength / totalLength) == 100) {
                    listener.onFinish(); //下载完成
                }

            }
            outputStream.flush();
            return myCaptureFile;
        } catch (IOException e) {
            if (myCaptureFile != null && myCaptureFile.exists()) {
                myCaptureFile.deleteOnExit();
            }
            e.printStackTrace();
        } finally {
            closeStream(outputStream);
            closeStream(inputStream);
        }
        return null;
    }

    //关闭流
    public static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //视频文件添加到相册
    public static void addCamera(Context context,File file){
        ContentResolver localContentResolver = context.getContentResolver();
        ContentValues localContentValues = getVideoContentValues(context, file, System.currentTimeMillis());
        Uri localUri = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues);

        Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        localIntent.setData(localUri);
        context.sendBroadcast(localIntent);
    }

    public static ContentValues getVideoContentValues(Context paramContext, File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
        localContentValues.put("mime_type", "video/3gp");
        localContentValues.put("datetaken", Long.valueOf(paramLong));
        localContentValues.put("date_modified", Long.valueOf(paramLong));
        localContentValues.put("date_added", Long.valueOf(paramLong));
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", Long.valueOf(paramFile.length()));
        return localContentValues;
    }
}
