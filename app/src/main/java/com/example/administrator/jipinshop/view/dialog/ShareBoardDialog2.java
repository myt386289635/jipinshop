package com.example.administrator.jipinshop.view.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.databinding.DialogShareBoard2Binding;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @author 莫小婷
 * @create 2019/11/13
 * @Describe
 */
public class ShareBoardDialog2 extends BottomSheetDialogFragment implements View.OnClickListener {

    private DialogShareBoard2Binding mBinding;
    private onShareListener mOnShareListener;

    public void setOnShareListener(onShareListener onShareListener) {
        mOnShareListener = onShareListener;
    }

    public static ShareBoardDialog2 getInstance() {
        ShareBoardDialog2 fragment = new ShareBoardDialog2();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.dialog_share_board2, container, false);
        mBinding.setListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setDimAmount(0.35f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_wechat:
                //微信
                if(mOnShareListener != null){
                    mOnShareListener.share(SHARE_MEDIA.WEIXIN);
                }
                dismiss();
                break;
            case R.id.share_pyq:
                //朋友圈
                if(mOnShareListener != null){
                    mOnShareListener.share(SHARE_MEDIA.WEIXIN_CIRCLE);
                }
                dismiss();
                break;
            case R.id.share_link:
                //复制链接
                if(mOnShareListener != null){
                    mOnShareListener.onLink();
                }
                dismiss();
                break;
        }
    }

    public interface onShareListener{
        void share(SHARE_MEDIA share_media);
        void onLink();
    }
}
