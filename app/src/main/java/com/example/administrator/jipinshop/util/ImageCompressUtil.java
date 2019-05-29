package com.example.administrator.jipinshop.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 莫小婷
 * @create 2018/10/17
 * @Describe 图片压缩工具以及图片旋转工具
 */
public class ImageCompressUtil {

    /**
     * 图片压缩比例
     */
    public static String getimage(Context context,String srcPath) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = DistanceHelper.getAndroiodScreenheightPixels(context);// 这里设置高度为800f
        float ww = DistanceHelper.getAndroiodScreenwidthPixels(context);// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return saveBitmap(context,compressImage(bitmap));// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public  static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;

        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /** 保存方法 */
    public  static String saveBitmap(Context context,Bitmap bm) {
        Log.e("----", "保存图片");
        String sdDir = FileManager.externalFiles(context);
        String str =  sdDir + "/"+ System.currentTimeMillis() + "bitmap.jpg";
        File f = new File(str);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.i("----", "已经保存");
            return  str;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  str;
    }


    /**
     * 根据图片地址，把竖着照的图片(原来是横着放的)，竖着放
     * 旋转图片的代码
     * @param fileName
     * @return
     */
    public static String getPicture(Context context,String fileName) {
        String sdDir = FileManager.externalFiles(context);
        String filName = sdDir + "/"+ System.currentTimeMillis() + ".jpg";
        try {
            ExifInterface exifInterface = new ExifInterface(fileName);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                Matrix matrix = new Matrix();
                Bitmap bitmap = BitmapFactory.decodeFile(fileName);
                matrix.setRotate(90);
                Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                if (null != bitmap) {
                    bitmap.recycle();
                }
                // 保存图片
                File parent = new File(sdDir);
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                File file = new File(filName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();//输出
                    fos.close();//关闭
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                Matrix matrix = new Matrix();
                Bitmap bitmap = BitmapFactory.decodeFile(fileName);
                matrix.setRotate(180);
                Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                if (null != bitmap) {
                    bitmap.recycle();
                }
                // 保存图片
                File parent = new File(sdDir);
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                File file = new File(filName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();//输出
                    fos.close();//关闭
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                Matrix matrix = new Matrix();
                Bitmap bitmap = BitmapFactory.decodeFile(fileName);
                matrix.setRotate(270);
                Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                if (null != bitmap) {
                    bitmap.recycle();
                }
                // 保存图片
                File parent = new File(sdDir);
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                File file = new File(filName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();//输出
                    fos.close();//关闭
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                return fileName;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filName;
    }


    /**
     * 只进行图片压缩比例，不进行质量压缩
     */
    public static String getimage1(Context context,String srcPath) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = DistanceHelper.getAndroiodScreenheightPixels(context);// 这里设置高度为800f
        float ww = DistanceHelper.getAndroiodScreenwidthPixels(context);// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return saveBitmap(context,bitmap);// 压缩好比例大小后再进行质量压缩
    }
}
