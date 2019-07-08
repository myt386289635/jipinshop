package com.example.administrator.jipinshop.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe 弹框工具类
 */
public class DialogUtil{

    public static void LoginDialog(Context context, String titleStr, final View.OnClickListener sureListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
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

    /**
     * 版本更新diaglog
     */
    public static  void UpDateDialog(Context context,String varsonNum ,String content,final View.OnClickListener updateListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_signsuccess,null);
        TextView update_varsonNum = view.findViewById(R.id.update_varsonNum);
        update_varsonNum.setText(varsonNum);
        TextView update_content =  view.findViewById(R.id.update_content);
        update_content.setText(content);
        ImageView update_close = view.findViewById(R.id.update_close);
        TextView update = view.findViewById(R.id.update);
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.75f);
        update.setOnClickListener(v -> {
            updateListener.onClick(v);
            dialog.dismiss();
        });
        update_close.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
    }

    /**
     * 强制版本更新diaglog
     */
    public static  void UpDateDialog1(Context context,String varsonNum ,String content,final View.OnClickListener updateListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_signsuccess,null);
        TextView update_varsonNum = view.findViewById(R.id.update_varsonNum);
        update_varsonNum.setText(varsonNum);
        TextView update_content =  view.findViewById(R.id.update_content);
        update_content.setText(content);
        ImageView update_close = view.findViewById(R.id.update_close);
        TextView update = view.findViewById(R.id.update);
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.75f);
        update.setOnClickListener(v -> {
            updateListener.onClick(v);
        });
        update_close.setVisibility(View.GONE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setContentView(view);
    }

    public static  void buleDialog(Context context,String titleStr,String sureText , final View.OnClickListener sureListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
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
        cancle.setTextColor(context.getResources().getColor(R.color.color_4A90E2));
        cancle.setOnClickListener(view12 -> dialog.dismiss());
        dialog.show();
        dialog.setContentView(view);
    }

    public static  void SingleDialog(Context context,String titleStr,String sureText , final View.OnClickListener sureListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_signfaile,null);
        TextView title = view.findViewById(R.id.dialog_title);
        title.setText(titleStr);
        TextView sure =  view.findViewById(R.id.dialog_sure);
        sure.setText(sureText);
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.35f);
        sure.setOnClickListener(v -> {
            sureListener.onClick(v);
            dialog.dismiss();
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
    }

    public static void MyGoods(Context context,String title ,String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_goods,null);
        TextView mTitle = view.findViewById(R.id.dialog_title);
        mTitle.setText(title);
        TextView dialog_content =  view.findViewById(R.id.dialog_content);
        dialog_content.setText(content);
        TextView diss = view.findViewById(R.id.dialog_cancle);
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.35f);
        diss.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
        dialog.setContentView(view);
    }

    public static void NewPeopleDialog(Context context,String code , final View.OnClickListener sureListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_newpeople,null);
        TextView dialog_code = view.findViewById(R.id.dialog_code);
        dialog_code.setText(code);
        TextView dialog_sure =  view.findViewById(R.id.dialog_sure);
        ImageView dialog_cancle = view.findViewById(R.id.dialog_cancle);
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.35f);
        dialog_cancle.setOnClickListener(v -> dialog.dismiss());
        dialog_sure.setOnClickListener(v -> {
            sureListener.onClick(v);
            dialog.dismiss();
        });
        dialog.show();
        dialog.setContentView(view);
    }

    public static  void QuestionDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_question,null);
        TextView sure =  view.findViewById(R.id.dialog_sure);
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.35f);
        sure.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
    }
}
