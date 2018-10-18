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
 * @create 2018/8/14
 * @Describe
 */
public class ShareBoardDialog extends BottomSheetDialogFragment {


    private LinearLayout share_wechat,share_pyq,share_weibo,share_qq_friend,share_qq_space;
    private onShareListener mOnShareListener;

    public void setOnShareListener(onShareListener onShareListener) {
        mOnShareListener = onShareListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_share_board, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setDimAmount(0.35f);
    }

    private void initView(View view) {
        share_wechat = view.findViewById(R.id.share_wechat);
        share_pyq = view.findViewById(R.id.share_pyq);
        share_weibo = view.findViewById(R.id.share_weibo);
        share_qq_friend = view.findViewById(R.id.share_qq_friend);
        share_qq_space = view.findViewById(R.id.share_qq_space);

        share_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnShareListener != null){
                    mOnShareListener.share(SHARE_MEDIA.WEIXIN);
                }
            }
        });
        share_pyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnShareListener != null){
                    mOnShareListener.share(SHARE_MEDIA.WEIXIN_CIRCLE);
                }
            }
        });
        share_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnShareListener != null){
                    mOnShareListener.share(SHARE_MEDIA.SINA);
                }
            }
        });
        share_qq_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnShareListener != null){
                    mOnShareListener.share(SHARE_MEDIA.QQ);
                }
            }
        });
        share_qq_space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnShareListener != null){
                    mOnShareListener.share(SHARE_MEDIA.QZONE);
                }
            }
        });
    }
    public interface onShareListener{
        void share(SHARE_MEDIA share_media);
    }
}
