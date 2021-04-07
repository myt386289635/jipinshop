package com.example.administrator.jipinshop.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.databinding.LoadingDialogPlatformGroupBinding;

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

    /**
     * 商品详情正在打开淘宝、京东、拼多多
     */
    public Dialog createPlatformDialog(Context context, String money , int imageId){
        Dialog dialog = new Dialog(context, R.style.dialog);// 创建自定义样式dialog
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog_platform, null);
        ImageView dialog_platform = view.findViewById(R.id.dialog_platform);
        dialog_platform.setImageResource(imageId);
        TextView dialog_title = view.findViewById(R.id.dialog_title);
        String str = "买完<font color='#E25838'>回来拿佣金哦~</font>";
        dialog_title.setText(Html.fromHtml(str));
        TextView dialog_money = view.findViewById(R.id.dialog_money);
        dialog_money.setText("可省￥" + money);
        dialog.getWindow().setDimAmount(0.35f);
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return dialog;
    }


    /**
     * 其他页面正在打开淘宝、京东、拼多多
     */
    public Dialog createOtherDialog(Context context, String platformName , int imageId){
        Dialog dialog = new Dialog(context, R.style.dialog);// 创建自定义样式dialog
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog_platform2, null);

        ImageView dialog_platform = view.findViewById(R.id.dialog_platform);
        dialog_platform.setImageResource(imageId);
        TextView dialog_title = view.findViewById(R.id.dialog_title);
        dialog_title.setText("正在打开"+platformName+"~");

        dialog.getWindow().setDimAmount(0.35f);
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return dialog;
    }

    //商品详情拼团正在打开 1京东,2淘宝，4拼多多
    public Dialog createPlatformGroupDialog(Context context, String source , String UpFee , String fee){
        Dialog dialog = new Dialog(context, R.style.dialog);// 创建自定义样式dialog
        LoadingDialogPlatformGroupBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.loading_dialog_platform_group, null,false);
        if (source.equals("2")){
            binding.dialogPlatform.setImageResource(R.mipmap.dialog_tb);
        }else if (source.equals("1")){
            binding.dialogPlatform.setImageResource(R.mipmap.dialog_jd);
        }else {
            binding.dialogPlatform.setImageResource(R.mipmap.dialog_pdd);
        }
        binding.dialogPrice.setText("￥" + UpFee);
        String html = "未邀请成功返<b><font color='#E25838'>￥"+ fee + "</font></b>";
        binding.dialogFee.setText(Html.fromHtml(html));
        dialog.getWindow().setDimAmount(0.35f);
        dialog.setContentView(binding.getRoot(), new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return dialog;
    }
}
