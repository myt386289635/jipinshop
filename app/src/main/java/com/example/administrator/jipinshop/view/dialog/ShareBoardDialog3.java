package com.example.administrator.jipinshop.view.dialog;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.databinding.DialogShareBoard3Binding;
import com.example.administrator.jipinshop.util.permission.HasPermissionsUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @author 莫小婷
 * @create 2020/2/21
 * @Describe
 */
public class ShareBoardDialog3 extends BottomSheetDialogFragment implements View.OnClickListener {

    private DialogShareBoard3Binding mBinding;
    private onShareListener mOnShareListener;

    public void setOnShareListener(onShareListener onShareListener) {
        mOnShareListener = onShareListener;
    }

    public static ShareBoardDialog3 getInstance() {
        ShareBoardDialog3 fragment = new ShareBoardDialog3();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.dialog_share_board3, container, false);
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
            case R.id.share_qq:
                HasPermissionsUtil.permission(getContext(),new HasPermissionsUtil(){
                    @Override
                    public void hasPermissionsSuccess() {
                        super.hasPermissionsSuccess();
                        if(mOnShareListener != null){
                            mOnShareListener.share(SHARE_MEDIA.QQ);
                        }
                        dismiss();
                    }
                }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.share_wechat:
            case R.id.share_wechat_group:
                if(mOnShareListener != null){
                    mOnShareListener.share(SHARE_MEDIA.WEIXIN);
                }
                dismiss();
                break;
            case R.id.share_pyq:
                if(mOnShareListener != null){
                    mOnShareListener.share(SHARE_MEDIA.WEIXIN_CIRCLE);
                }
                dismiss();
                break;
            case R.id.share_pic:
                HasPermissionsUtil.permission(getContext(),new HasPermissionsUtil(){
                    @Override
                    public void hasPermissionsSuccess() {
                        super.hasPermissionsSuccess();
                        if(mOnShareListener != null){
                            mOnShareListener.download();
                        }
                        dismiss();
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
        }
    }

    public interface onShareListener{
        void share(SHARE_MEDIA share_media);
        void download();//下载图片
    }
}
