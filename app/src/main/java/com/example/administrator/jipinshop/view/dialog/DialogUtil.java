package com.example.administrator.jipinshop.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.sreach.TBSreachActivity;
import com.example.administrator.jipinshop.activity.sreach.result.TBSreachResultActivity;
import com.example.administrator.jipinshop.bean.PopInfoBean;
import com.example.administrator.jipinshop.bean.TklBean;
import com.example.administrator.jipinshop.databinding.DialogTklBinding;
import com.example.administrator.jipinshop.util.NotificationUtil;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.math.BigDecimal;

import static com.example.administrator.jipinshop.util.TimeUtil.dateAddOneDay;

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
    public static  void UpDateDialog(Context context,String varsonNum ,String content,final View.OnClickListener updateListener,OnDismissLitener dismissLitener){
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
        update_close.setOnClickListener(v ->{
            dialog.dismiss();
            dismissLitener.onDismiss();
        });
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

    public static void listingDetele(Context context, String titleStr, final View.OnClickListener sureListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_listing,null);
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

    public static void imgDialog(Context context, String resource , final View.OnClickListener sureListener , View.OnClickListener dissListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_img,null);
        ImageView dialog_img = view.findViewById(R.id.dialog_img);
        ImageView dialog_cancle = view.findViewById(R.id.dialog_cancle);
        Glide.with(context)
                .asBitmap()
                .load(resource)
                .into(dialog_img);
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.35f);
        dialog_cancle.setOnClickListener(v -> {
            dialog.dismiss();
            dissListener.onClick(v);
        });
        dialog_img.setOnClickListener(v -> {
            sureListener.onClick(v);
            dialog.dismiss();
        });
        dialog.show();
        dialog.setContentView(view);
    }


    public static void freeDialog(Context context, PopInfoBean bean , View.OnClickListener sureListener , View.OnClickListener dissListener){
        long timer = dateAddOneDay(bean.getData().getData().getDendlineTime()) - System.currentTimeMillis();
        if (timer > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
            final Dialog dialog = builder.create();
            dialog.getWindow().setDimAmount(0.35f);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_free, null);
            ImageView dialog_image = view.findViewById(R.id.dialog_image);
            TextView dialog_price = view.findViewById(R.id.dialog_price);
            TextView dialog_name = view.findViewById(R.id.dialog_name);
            TextView dialog_reminder = view.findViewById(R.id.dialog_reminder);
            TextView dialog_hour = view.findViewById(R.id.dialog_hour);
            TextView dialog_minute = view.findViewById(R.id.dialog_minute);
            TextView dialog_second = view.findViewById(R.id.dialog_second);
            TextView dialog_sure = view.findViewById(R.id.dialog_sure);
            ImageView dialog_cancle = view.findViewById(R.id.dialog_cancle);
            GlideApp.loderImage(context, bean.getData().getData().getImg(), dialog_image, 0, 0);
            BigDecimal actualBd = new BigDecimal(bean.getData().getData().getActualPrice());
            BigDecimal freeBd = new BigDecimal(bean.getData().getData().getFreePrice());
            double price = actualBd.doubleValue() - freeBd.doubleValue();
            BigDecimal priceBd = new BigDecimal(price + "");
            dialog_price.setText(priceBd.setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
            dialog_name.setText(bean.getData().getData().getName());
            dialog_reminder.setText("全额售价¥" + actualBd.stripTrailingZeros().toPlainString()
                    + "  额外补贴¥" + freeBd.stripTrailingZeros().toPlainString());
            dialog_sure.setOnClickListener(v -> {
                sureListener.onClick(v);
                dialog.dismiss();
            });
            dialog_cancle.setOnClickListener(v -> {
                dialog.dismiss();
                dissListener.onClick(v);
            });
            final CountDownTimer[] countDownTimer = {new CountDownTimer(timer, 1000) {
                public void onTick(long millisUntilFinished) {

                    int ss = 1000;
                    int mi = ss * 60;
                    int hh = mi * 60;
                    int dd = hh * 24;

                    long day = millisUntilFinished / dd;
                    long hour = ((millisUntilFinished - day * dd) / hh);
                    long minute = (millisUntilFinished - hour * hh - day * dd) / mi;
                    long second = (millisUntilFinished - hour * hh - minute * mi - day * dd) / ss;

                    long re = (day * 24) + hour;

                    dialog_hour.setText(re + "");
                    dialog_minute.setText(minute + "");
                    dialog_second.setText(second + "");
                }

                public void onFinish() {
                    dialog.dismiss();
                }
            }.start()};
            dialog.show();
            dialog.setCanceledOnTouchOutside(true);
            dialog.setContentView(view);
            dialog.setOnDismissListener(dialog1 -> {
                if (countDownTimer[0] != null){
                    countDownTimer[0].cancel();
                    countDownTimer[0] = null;
                }
            });
        }
    }

    /**
     * 评分
     */
    public static void scoreDialog(Context context, OnScoreListener badListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_score,null);

        ImageView dialog_titleImg = view.findViewById(R.id.dialog_titleImg);
        TextView dialog_titleText = view.findViewById(R.id.dialog_titleText);
        TextView dialog_cancle = view.findViewById(R.id.dialog_cancle);
        ImageView dialog_score1 = view.findViewById(R.id.dialog_score1);
        ImageView dialog_score2 = view.findViewById(R.id.dialog_score2);
        ImageView dialog_score3 = view.findViewById(R.id.dialog_score3);
        ImageView dialog_score4 = view.findViewById(R.id.dialog_score4);
        ImageView dialog_score5 = view.findViewById(R.id.dialog_score5);
        TextView dialog_content = view.findViewById(R.id.dialog_content);
        EditText dialog_edit = view.findViewById(R.id.dialog_edit);
        View dialog_line = view.findViewById(R.id.dialog_line);
        TextView dialog_sure = view.findViewById(R.id.dialog_sure);
        ImageView[] textViews = {dialog_score1,dialog_score2,dialog_score3,dialog_score4,dialog_score5};
        final int[] score = {0};

        InputMethodManager inputManager = (InputMethodManager) dialog_edit
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        dialog_score1.setOnClickListener(v -> {
            score[0] = 1;
            setSex(textViews,1);
            dialog_titleImg.setImageResource(R.mipmap.dialog_score4);
            dialog_titleText.setText("很抱歉对您造成不便，能给我们一些意见么？");
            dialog_content.setVisibility(View.GONE);
            dialog_edit.setVisibility(View.VISIBLE);
            dialog_line.setVisibility(View.VISIBLE);
            dialog_sure.setVisibility(View.VISIBLE);
            showKeyboard(dialog_edit,inputManager);
            if (badListener != null){
                badListener.onScore(1,"",false);
            }
        });
        dialog_score2.setOnClickListener(v -> {
            score[0] = 2;
            setSex(textViews,2);
            dialog_titleImg.setImageResource(R.mipmap.dialog_score4);
            dialog_titleText.setText("很抱歉对您造成不便，能给我们一些意见么？");
            dialog_content.setVisibility(View.GONE);
            dialog_edit.setVisibility(View.VISIBLE);
            dialog_line.setVisibility(View.VISIBLE);
            dialog_sure.setVisibility(View.VISIBLE);
            showKeyboard(dialog_edit,inputManager);
            if (badListener != null){
                badListener.onScore(2,"",false);
            }
        });
        dialog_score3.setOnClickListener(v -> {
            score[0] = 3;
            setSex(textViews,3);
            dialog_titleImg.setImageResource(R.mipmap.dialog_score4);
            dialog_titleText.setText("很抱歉对您造成不便，能给我们一些意见么？");
            dialog_content.setVisibility(View.GONE);
            dialog_edit.setVisibility(View.VISIBLE);
            dialog_line.setVisibility(View.VISIBLE);
            dialog_sure.setVisibility(View.VISIBLE);
            showKeyboard(dialog_edit,inputManager);
            if (badListener != null){
                badListener.onScore(3,"",false);
            }
        });
        dialog_score4.setOnClickListener(v -> {
            setSex(textViews,4);
            if (badListener != null){
                badListener.onScore(4,"",false);
            }
            if (!ShopJumpUtil.toQQDownload(context, "com.example.administrator.jipinshop")) {
                if (!ShopJumpUtil.toMarket(context, "com.example.administrator.jipinshop", null)) {
                    ToastUtil.show("没有找到您手机里的应用商店，请确认");
                }
            }
            dialog.dismiss();
        });
        dialog_score5.setOnClickListener(v -> {
            setSex(textViews,5);
            if (badListener != null){
                badListener.onScore(5,"",false);
            }
            if (!ShopJumpUtil.toQQDownload(context, "com.example.administrator.jipinshop")) {
                if (!ShopJumpUtil.toMarket(context, "com.example.administrator.jipinshop", null)) {
                    ToastUtil.show("没有找到您手机里的应用商店，请确认");
                }
            }
            dialog.dismiss();
        });
        dialog_sure.setOnClickListener(v -> {
            inputManager.hideSoftInputFromWindow( dialog.getCurrentFocus().getWindowToken(), 0);
            if (badListener != null){
                badListener.onScore(score[0],dialog_edit.getText().toString(),true);
            }
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog_cancle.setOnClickListener(v -> {
            //在dismiss的时候,getWindowToken()为空指针,所以要在dialog.dismiss()之前关闭软键盘
            if (dialog.getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(dialog.getCurrentFocus().getWindowToken(), 0);
            dialog.dismiss();
        });
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);//解决无法弹出输入法问题，写在show()方法之后才有效。
        dialog.setContentView(view);
    }

    private static void showKeyboard(EditText editText,InputMethodManager inputManager) {
        if(editText!=null){
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            inputManager.showSoftInput(editText, 0);
        }
    }

    private static void setSex(ImageView[] imageViews, int sexNum){
        for (int i = 0; i < imageViews.length; i++) {
            if (i < sexNum){
                imageViews[i].setImageResource(R.mipmap.dialog_score3);
            }else {
                imageViews[i].setImageResource(R.mipmap.dialog_score2);
            }
        }
    }

    public interface OnDismissLitener {
        void onDismiss();
    }

    public interface OnScoreListener{
        void onScore(int score,String content,Boolean scoreFlag);
    }

    /**
     * 新人首次进入商品详情时弹出新手引导
     */
    public static void shopGuideDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_shop_guide,null);
        ImageView dialog_cancle = view.findViewById(R.id.dialog_cancle);
        dialog_cancle.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.8f);
        dialog.show();
        dialog.setContentView(view);
    }

    /**
     * 新免单详情购买弹框
     */
    public static void freeBuyDialog(Context context , String actualPrice, String freePrice , View.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
        final Dialog dialog = builder.create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_free_buy,null);
        TextView dialog_time = view.findViewById(R.id.dialog_time);
        String html = "您需在<b>一小时内</b>前往淘宝APP购买";
        dialog_time.setText(Html.fromHtml(html));
        TextView dialog_actualPrice = view.findViewById(R.id.dialog_actualPrice);
        String html2 = "购买价格<font color='#E25838'><b>¥"+actualPrice+"</b></font>";
        dialog_actualPrice.setText(Html.fromHtml(html2));
        TextView dialog_feePrice = view.findViewById(R.id.dialog_feePrice);
        String html3 = "补贴<font color='#E25838'><b>¥"+freePrice+"</b></font>";
        dialog_feePrice.setText(Html.fromHtml(html3));
        TextView dialog_cancle = view.findViewById(R.id.dialog_cancle);
        TextView dialog_sure = view.findViewById(R.id.dialog_sure);
        dialog.getWindow().setDimAmount(0.35f);
        dialog_cancle.setOnClickListener(v -> dialog.dismiss());
        dialog_sure.setOnClickListener(v -> {
            listener.onClick(v);
            dialog.dismiss();
        });
        dialog.show();
        dialog.setContentView(view);
    }

    /**
     * 新手专区详情购买弹框
     */
    public static void NewPeopleBuyDialog(Context context , String actualPrice, String freePrice , View.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
        final Dialog dialog = builder.create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_newpeople_buy,null);
        TextView dialog_time = view.findViewById(R.id.dialog_time);
        String html = "您需在<b>一小时内</b>前往淘宝APP购买";
        dialog_time.setText(Html.fromHtml(html));
        TextView dialog_actualPrice = view.findViewById(R.id.dialog_actualPrice);
        String html2 = "购买价格<font color='#E25838'><b>¥"+actualPrice+"</b></font>";
        dialog_actualPrice.setText(Html.fromHtml(html2));
        TextView dialog_feePrice = view.findViewById(R.id.dialog_feePrice);
        String html3 = "补贴<font color='#E25838'><b>¥"+freePrice+"</b></font>";
        dialog_feePrice.setText(Html.fromHtml(html3));
        ImageView dialog_cancle = view.findViewById(R.id.dialog_cancle);
        TextView dialog_sure = view.findViewById(R.id.dialog_sure);
        dialog.getWindow().setDimAmount(0.35f);
        dialog_cancle.setOnClickListener(v -> dialog.dismiss());
        dialog_sure.setOnClickListener(v -> {
            listener.onClick(v);
            dialog.dismiss();
        });
        dialog.show();
        dialog.setContentView(view);
    }

    /**
     * 邀请码dialog
     */
    public static void invitationDialog(Context context, OnInvitationListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
        final Dialog dialog = builder.create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_invitation,null);
        ImageView dialog_cancle = view.findViewById(R.id.dialog_cancle);
        EditText dialog_edit = view.findViewById(R.id.dialog_edit);
        TextView dialog_sure = view.findViewById(R.id.dialog_sure);
        dialog.getWindow().setDimAmount(0.35f);
        InputMethodManager inputManager = (InputMethodManager) dialog_edit
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        dialog_cancle.setOnClickListener(v -> {
            if (dialog.getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(dialog.getCurrentFocus().getWindowToken(), 0);
            dialog.dismiss();
        });
        dialog_sure.setOnClickListener(v -> {
            listener.invitation(dialog_edit.getText().toString().trim(),dialog,inputManager);
        });
        showKeyboard(dialog_edit,inputManager);
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.setContentView(view);
    }

    public interface OnInvitationListener {
        void invitation(String invitationCode, Dialog dialog, InputMethodManager inputManager);
    }

    //淘口令弹框
    public static void tklDialog(Context context, TklBean bean,String tkl){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.dialog);
        final Dialog dialog = builder.create();
        DialogTklBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_tkl,null,false);
        binding.setData(bean.getData());
        binding.executePendingBindings();
        if (bean.getData() == null){
            binding.dialogContent.setText(tkl);
        }else {
            binding.detailOtherPrice.setTv(true);
            binding.detailOtherPrice.setColor(R.color.color_9D9D9D);
            double coupon = new BigDecimal(bean.getData().getCouponPrice()).doubleValue();
            if (coupon == 0){//没有优惠券
                binding.detailCoupon.setVisibility(View.GONE);
            }else {
                binding.detailCoupon.setVisibility(View.VISIBLE);
            }
            double free = new BigDecimal(bean.getData().getFee()).doubleValue();
            if (free == 0){//没有补贴
                binding.detailFee.setVisibility(View.GONE);
            }else {
                binding.detailFee.setVisibility(View.VISIBLE);
            }
            if (coupon == 0 && free == 0){
                binding.detailOtherPrice.setVisibility(View.GONE);
            }else {
                binding.detailOtherPrice.setVisibility(View.VISIBLE);
            }
        }
        binding.dialogSure1.setOnClickListener(v -> {
            context.startActivity(new Intent(context, TBSreachResultActivity.class)
                    .putExtra("content", tkl)
                    .putExtra("type","1")
            );
            dialog.dismiss();
        });
        binding.dialogSure2.setOnClickListener(v -> {
            context.startActivity(new Intent(context, TBShoppingDetailActivity.class)
                    .putExtra("otherGoodsId", bean.getData().getOtherGoodsId())
            );
            dialog.dismiss();
        });
        binding.dialogCancle.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }
}
