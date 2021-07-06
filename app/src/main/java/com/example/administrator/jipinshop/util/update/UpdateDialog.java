package com.example.administrator.jipinshop.util.update;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.databinding.DialogUpdateBinding;

/**
 * @author 莫小婷
 * @create 2021/7/1
 * @Describe 更新的dialog
 */
public class UpdateDialog extends Dialog {

    private DialogUpdateBinding mBinding;
    private Context mContext;

    public UpdateDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    public UpdateDialog(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_update, null, false);
        getWindow().setDimAmount(0.75f);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    public void showDialog(){
        show();
        setContentView(mBinding.getRoot());
    }

    public void setUpDateContent(String content){
        mBinding.updateContent.setText(content);
    }

    public void setProgress(int progress){
        mBinding.updateProgress.setProgress(progress);
    }
}
