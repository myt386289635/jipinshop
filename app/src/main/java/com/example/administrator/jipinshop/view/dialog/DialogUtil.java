package com.example.administrator.jipinshop.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe 弹框工具类
 */
public class DialogUtil{

    public static void LoginDialog(Context context, String titleStr, final View.OnClickListener sureListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_log,null);
        TextView title = view.findViewById(R.id.dialog_title);
        title.setText(titleStr);
        TextView sure =  view.findViewById(R.id.dialog_sure);
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.35f);
        sure.setOnClickListener(view12 -> {
            sureListener.onClick(view12);
            dialog.dismiss();
        });
        TextView cancle =  view.findViewById(R.id.dialog_cancle);
        cancle.setOnClickListener(view1 -> dialog.dismiss());
        dialog.show();
        dialog.setContentView(view);
    }


    public static void LoginDialog(Context context, String titleStr,String sureText ,String cancleText, final View.OnClickListener sureListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_log,null);
        TextView title = view.findViewById(R.id.dialog_title);
        title.setText(titleStr);
        TextView sure =  view.findViewById(R.id.dialog_sure);
        sure.setText(sureText);
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.35f);
        sure.setOnClickListener(view1 -> {
            sureListener.onClick(view1);
            dialog.dismiss();
        });
        TextView cancle =  view.findViewById(R.id.dialog_cancle);
        cancle.setText(cancleText);
        cancle.setOnClickListener(view12 -> dialog.dismiss());
        dialog.show();
        dialog.setContentView(view);
    }

    public static  void SignSuccess(Context context,String title ,String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_signsuccess,null);
        TextView mTitle = view.findViewById(R.id.sign_title);
        mTitle.setText(title);
        TextView sure =  view.findViewById(R.id.sign_code);
        sure.setText(content);
        ImageView diss = view.findViewById(R.id.sign_diss);
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.75f);
        diss.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
    }

    public static  void SignPrice(Context context,String image,String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_signprice,null);
        TextView sure =  view.findViewById(R.id.sign_code);
        sure.setText(content);
        ImageView diss = view.findViewById(R.id.sign_diss);
        ImageView mImage = view.findViewById(R.id.sign_price);
        if(!TextUtils.isEmpty(image)){
            ImageManager.displayImage(image,mImage,0,0);
        }
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.75f);
        diss.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
    }


}
