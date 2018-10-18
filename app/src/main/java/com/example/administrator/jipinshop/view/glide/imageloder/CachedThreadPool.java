package com.example.administrator.jipinshop.view.glide.imageloder;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 莫小婷
 * @create 2018/8/18
 * @Describe
 */
public class CachedThreadPool {
    static CachedThreadPool mInstance;
    ExecutorService mThreadPool;

    private CachedThreadPool() {
        mThreadPool = Executors.newCachedThreadPool();
    }

    public static CachedThreadPool getInstance() {
        return mInstance == null ? (mInstance = new CachedThreadPool()) : mInstance;
    }

    /**
     * 异步执行一个命令
     *
     * @param command
     *            要执行的命令
     */
    public void execute(Runnable command) {
        mThreadPool.execute(command);
    }

    /**
     * 在线程池中执行AsyncTask
     */
    @SuppressLint("NewApi")
    public <Params> void execute(AsyncTask<Params, ?, ?> task, Params... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(mThreadPool, params);
        } else {
            try {
                Method m = AsyncTask.class.getDeclaredMethod("setDefaultExecutor", Executor.class);
                m.invoke(null, mThreadPool);
            } catch (Exception e) {
                e.printStackTrace();
            }
            task.execute(params);
        }
    }

    public Executor getExecutor() {
        return mThreadPool;
    }
}
