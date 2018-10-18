package com.example.administrator.jipinshop.util;

import android.content.Context;

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
}
