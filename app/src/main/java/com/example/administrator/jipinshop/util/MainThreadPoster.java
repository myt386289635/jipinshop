package com.example.administrator.jipinshop.util;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

public class MainThreadPoster {
    private static Handler mHandler;

    static {
        init();
    }

    public static void init() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static void postRunnable(Runnable runnable, Object token) {
        postRunnableDelay(runnable, token, 0);
    }

    public static void postRunnableDelay(Runnable runnable, Object token, long delay) {
        if (mHandler != null && runnable != null) {
            mHandler.postAtTime(runnable, token, SystemClock.uptimeMillis() + delay);
        }
    }

    public static void postRunnableAtFixRate(final Runnable runnable, final Object token, long delay, final long period) {
        if (mHandler != null && runnable != null) {
            mHandler.postAtTime(new Runnable() {
                @Override
                public void run() {
                    runnable.run();
                    mHandler.postAtTime(this, token, SystemClock.uptimeMillis() + period);
                }
            }, token, SystemClock.uptimeMillis() + delay);
        }
    }

    public static void clear(Object token) {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(token);
        }
    }
}
