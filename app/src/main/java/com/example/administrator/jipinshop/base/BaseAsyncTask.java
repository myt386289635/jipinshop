package com.example.administrator.jipinshop.base;

import android.content.Context;
import android.os.AsyncTask;

import com.example.administrator.jipinshop.base.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe
 */
public abstract class BaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    protected WeakReference<Context> mReference;
    protected Throwable mError = null;
    protected String mContextName;
    protected boolean mShowLoadingDialog;
//    protected LoadingProgressDialog mDialog;

    public BaseAsyncTask(Context context) {
        this(context, false);
    }

    public BaseAsyncTask(Context context, boolean showLoadingDialog) {
        this.mReference = new WeakReference<Context>(context);
        mContextName = context.getClass().getName();
        this.mShowLoadingDialog = showLoadingDialog;
        if (mShowLoadingDialog) {
//            mDialog = new LoadingProgressDialog(context);
        }
    }

    protected abstract Result realDoInBackground(Params... params);

    protected abstract void realOnPostExecute(Result result);

    protected void realOnCancelled() {}

    protected void realOnProgressUpdate(Progress... values) {}

    protected void onError(Throwable error) {}

    protected Context getContext() {
        if (mReference == null) {
            return null;
        }
        return mReference.get();
    }

    private boolean isActivityFinishing() {
        if (mReference == null || mReference.get() == null) {
            return true;
        } else {
            Context ctx = mReference.get();
            if (ctx instanceof BaseActivity && ((BaseActivity) ctx).isFinishing()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Result doInBackground(Params... params) {
        Thread.currentThread().setName(this.getClass().getName() + "#" + mContextName);
        try {
            return realDoInBackground(params);
        } catch (Throwable e) {
            e.printStackTrace();
            mError = e;
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (isActivityFinishing()) {
            cancel(true);
        }
//        if (mShowLoadingDialog) {
//            mDialog.show();
//        }
    }

    @Deprecated
    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (isActivityFinishing()) {
            return;
        }

        if (mError != null) {
            onError(mError);
        } else {
            realOnPostExecute(result);
        }
//        if (mShowLoadingDialog) {
//            mDialog.dismiss();
//        }
    }

    @Deprecated
    @Override
    protected void onProgressUpdate(Progress... values) {
        super.onProgressUpdate(values);
        if (isActivityFinishing()) {
            return;
        }
        realOnProgressUpdate(values);
    }

    @Deprecated
    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (isActivityFinishing()) {
            return;
        }

        realOnCancelled();
    }
}

