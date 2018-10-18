package com.example.administrator.jipinshop.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;

/**
 * @author 莫小婷
 * @create 2018/8/14
 * @Describe 加载框
 */
public class ProgressDialogView {
    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImgView = (ImageView) v.findViewById(R.id.imgView_loading_dialog_img);
        TextView tipTv = (TextView) v.findViewById(R.id.tv_loading_dialog_tip);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImgView.startAnimation(hyperspaceJumpAnimation);
        if (msg != null && !TextUtils.isEmpty(msg)) {
            tipTv.setVisibility(View.VISIBLE);
            tipTv.setText(msg);// 设置加载信息
        } else {
            tipTv.setVisibility(View.GONE);
        }
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.getWindow().setDimAmount(0.35f);
        loadingDialog.setCancelable(true);// 可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
        return loadingDialog;
    }
}
