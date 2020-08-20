package com.example.administrator.jipinshop.util;

import android.content.Context;
import android.widget.TextView;

import com.example.administrator.jipinshop.base.BaseAsyncTask;

import java.lang.ref.WeakReference;

/**
 * @author 莫小婷
 * @create 2020/8/20
 * @Describe
 */
public class ClearTask extends BaseAsyncTask<Void, Void, Void> {

    private WeakReference<Context> mContext;
    private WeakReference<TextView> mTextView;

    public ClearTask(Context context , TextView textView) {
        super(context);
        mContext = new WeakReference<Context>(context);
        mTextView =  new WeakReference<TextView>(textView);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //开始清除时候
    }

    @Override
    protected Void realDoInBackground(Void... voids) {
        MyDataCleanManager.clearAllCache(mContext.get());
        return null;
    }

    @Override
    protected void realOnPostExecute(Void aVoid) {
        mTextView.get().setText(MyDataCleanManager.getFormatSize(0L));
        ToastUtil.show("内存已清空");
    }
}
