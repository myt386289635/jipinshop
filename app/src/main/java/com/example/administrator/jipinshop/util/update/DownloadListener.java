package com.example.administrator.jipinshop.util.update;

/**
 * @author 莫小婷
 * @create 2021/7/1
 * @Describe
 */
public interface DownloadListener {

    void onProgress(int currentLength);

    void onFinish();
}
