package com.example.administrator.jipinshop.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.jipinshop.R;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @author 莫小婷
 * @create 2019/11/13
 * @Describe 生成海报的分享
 */
public class ShareBoardDialog2 extends BottomSheetDialogFragment {

    private LinearLayout share_wechat,share_pyq;
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
        View view = inflater.inflate(R.layout.dialog_share_board2, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        share_wechat = view.findViewById(R.id.share_wechat);
        share_pyq = view.findViewById(R.id.share_pic);

        share_wechat.setOnClickListener(v -> {
            if(mOnShareListener != null){
                mOnShareListener.share(SHARE_MEDIA.WEIXIN);
            }
            dismiss();
        });
        share_pyq.setOnClickListener(v -> {
            if(mOnShareListener != null){
                mOnShareListener.share(SHARE_MEDIA.WEIXIN_CIRCLE);
            }
            dismiss();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setDimAmount(0.5f);
    }

    public interface onShareListener{
        void share(SHARE_MEDIA share_media);
    }
}
