package com.example.administrator.jipinshop.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.sreach.result.TBSreachResultActivity;
import com.example.administrator.jipinshop.bean.NewFreeBean;
import com.example.administrator.jipinshop.bean.PopBean;
import com.example.administrator.jipinshop.bean.TklBean;
import com.example.administrator.jipinshop.databinding.DialogCheapBinding;
import com.example.administrator.jipinshop.databinding.DialogNewpeople2Binding;
import com.example.administrator.jipinshop.databinding.DialogNewpeopleBinding;
import com.example.administrator.jipinshop.databinding.DialogNewpeopleBuyBinding;
import com.example.administrator.jipinshop.databinding.DialogOutBinding;
import com.example.administrator.jipinshop.databinding.DialogTklBinding;
import com.example.administrator.jipinshop.databinding.DialogUserBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.TimeUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe 弹框工具类
 */
public class DialogUtil {

    /**
     * 动态的默认弹框
     */
    public static void LoginDialog(Context context, String titleStr,
                                   String sureText, String cancleText,
                                   int sureColor, int cancleColor, Boolean isCrude,
                                   final View.OnClickListener sureListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_log, null);
        TextView title = view.findViewById(R.id.dialog_title);
        title.setText(titleStr);
        TextView sure = view.findViewById(R.id.dialog_sure);
        sure.setText(sureText);
        sure.setTextColor(context.getResources().getColor(sureColor));
        if (isCrude){
            sure.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        }else {
            sure.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//不加粗
        }
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.35f);
        sure.setOnClickListener(view12 -> {
            sureListener.onClick(view12);
            dialog.dismiss();
        });
        TextView cancle = view.findViewById(R.id.dialog_cancle);
        cancle.setText(cancleText);
        cancle.setTextColor(context.getResources().getColor(cancleColor));
        cancle.setOnClickListener(view1 -> dialog.dismiss());
        dialog.show();
        dialog.setContentView(view);
    }

    /**
     * 右边需要加粗的(默认弹框)
     *  颜色都是202020
     */
    public static void LoginDialog(Context context, String titleStr,
                                   String sureText, String cancleText,
                                   final View.OnClickListener sureListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_log, null);
        TextView title = view.findViewById(R.id.dialog_title);
        title.setText(titleStr);
        TextView sure = view.findViewById(R.id.dialog_sure);
        sure.setText(sureText);
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.35f);
        sure.setOnClickListener(view1 -> {
            sureListener.onClick(view1);
            dialog.dismiss();
        });
        TextView cancle = view.findViewById(R.id.dialog_cancle);
        cancle.setText(cancleText);
        cancle.setOnClickListener(view12 -> dialog.dismiss());
        dialog.show();
        dialog.setContentView(view);
    }

    /**
     * 单个确认框
     */
    public static void SingleDialog(Context context,String titleStr, String sureText,
                                      final View.OnClickListener sureListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_question, null);
        TextView sure = view.findViewById(R.id.dialog_sure);
        sure.setText(sureText);
        TextView dialog_title = view.findViewById(R.id.dialog_title);
        dialog_title.setText(titleStr);
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.35f);
        sure.setOnClickListener(v -> {
            if (sureListener != null)
                sureListener.onClick(v);
            dialog.dismiss();
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
    }

    /**
     * 带有温馨提示的默认弹框
     */
    public static void listingDetele(Context context, String titleStr,
                                     String sureText, String cancleText,Boolean isCrude,
                                     final View.OnClickListener sureListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_listing, null);
        TextView title = view.findViewById(R.id.dialog_title);
        title.setText(titleStr);
        TextView sure = view.findViewById(R.id.dialog_sure);
        sure.setText(sureText);
        if (isCrude){
            sure.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        }else {
            sure.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//不加粗
        }
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.35f);
        sure.setOnClickListener(view12 -> {
            sureListener.onClick(view12);
            dialog.dismiss();
        });
        TextView cancle = view.findViewById(R.id.dialog_cancle);
        cancle.setText(cancleText);
        cancle.setOnClickListener(view1 -> dialog.dismiss());
        dialog.show();
        dialog.setContentView(view);
    }

    /**
     * 版本更新diaglog
     */
    public static void UpDateDialog(Context context, String varsonNum, String content, final View.OnClickListener updateListener, OnDismissLitener dismissLitener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_signsuccess, null);
        TextView update_varsonNum = view.findViewById(R.id.update_varsonNum);
        update_varsonNum.setText(varsonNum);
        TextView update_content = view.findViewById(R.id.update_content);
        update_content.setText(content);
        ImageView update_close = view.findViewById(R.id.update_close);
        TextView update = view.findViewById(R.id.update);
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.75f);
        update.setOnClickListener(v -> {
            updateListener.onClick(v);
            dialog.dismiss();
        });
        update_close.setOnClickListener(v -> {
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
    public static void UpDateDialog1(Context context, String varsonNum, String content, final View.OnClickListener updateListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_signsuccess, null);
        TextView update_varsonNum = view.findViewById(R.id.update_varsonNum);
        update_varsonNum.setText(varsonNum);
        TextView update_content = view.findViewById(R.id.update_content);
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

    /**
     * 用户主页里的 点赞弹窗
     */
    public static void MyGoods(Context context, String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_goods, null);
        TextView mTitle = view.findViewById(R.id.dialog_title);
        mTitle.setText(title);
        TextView dialog_content = view.findViewById(R.id.dialog_content);
        dialog_content.setText(content);
        TextView diss = view.findViewById(R.id.dialog_cancle);
        final Dialog dialog = builder.create();
        dialog.getWindow().setDimAmount(0.35f);
        diss.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
        dialog.setContentView(view);
    }

    /**
     * 用户首次下载 20元津贴弹窗
     */
    public static void NewPeopleDialog(Context context, final View.OnClickListener cancleListener ,
                                       final View.OnClickListener sureListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        DialogNewpeopleBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_newpeople, null,false);
        final Dialog dialog = builder.create();
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.free_scale);
        binding.dialogSure.startAnimation(animation);
        binding.dialogCancle.setOnClickListener(v -> {
            if (cancleListener != null)
                cancleListener.onClick(v);
            dialog.dismiss();
        });
        binding.dialogSure.setOnClickListener(v -> {
            binding.dialogBackground.setVisibility(View.GONE);
            binding.dialogSure.setVisibility(View.GONE);
            binding.dialogBackgroundResult.setVisibility(View.VISIBLE);
            binding.dialogSureResult.setVisibility(View.VISIBLE);
        });
        binding.dialogSureResult.setOnClickListener(v -> {
            if (sureListener != null){
                sureListener.onClick(v);
            }
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    /**
     * 用户首次下载 30元购物津贴
     */
    public static void newPeopleDialog(Context context, final View.OnClickListener cancleListener ,
                                       final View.OnClickListener sureListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        DialogNewpeople2Binding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_newpeople2, null,false);
        final Dialog dialog = builder.create();
        binding.dialogCancle.setOnClickListener(v -> {
            if (cancleListener != null)
                cancleListener.onClick(v);
            dialog.dismiss();
        });
        binding.dialogImage.setOnClickListener(v -> {
            if (sureListener != null){
                sureListener.onClick(v);
            }
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }


    //活动弹窗  后台设置的活动
    public static void imgDialog(Context context, String resource, final View.OnClickListener sureListener, View.OnClickListener dissListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_img, null);
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

    /**
     * 评分
     */
    public static void scoreDialog(Context context, OnScoreListener badListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_score, null);

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
        ImageView[] textViews = {dialog_score1, dialog_score2, dialog_score3, dialog_score4, dialog_score5};
        final int[] score = {0};

        InputMethodManager inputManager = (InputMethodManager) dialog_edit
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        dialog_score1.setOnClickListener(v -> {
            score[0] = 1;
            setSex(textViews, 1);
            dialog_titleImg.setImageResource(R.mipmap.dialog_score4);
            dialog_titleText.setText("很抱歉对您造成不便，能给我们一些意见么？");
            dialog_content.setVisibility(View.GONE);
            dialog_edit.setVisibility(View.VISIBLE);
            dialog_line.setVisibility(View.VISIBLE);
            dialog_sure.setVisibility(View.VISIBLE);
            showKeyboard(dialog_edit, inputManager);
            if (badListener != null) {
                badListener.onScore(1, "", false);
            }
        });
        dialog_score2.setOnClickListener(v -> {
            score[0] = 2;
            setSex(textViews, 2);
            dialog_titleImg.setImageResource(R.mipmap.dialog_score4);
            dialog_titleText.setText("很抱歉对您造成不便，能给我们一些意见么？");
            dialog_content.setVisibility(View.GONE);
            dialog_edit.setVisibility(View.VISIBLE);
            dialog_line.setVisibility(View.VISIBLE);
            dialog_sure.setVisibility(View.VISIBLE);
            showKeyboard(dialog_edit, inputManager);
            if (badListener != null) {
                badListener.onScore(2, "", false);
            }
        });
        dialog_score3.setOnClickListener(v -> {
            score[0] = 3;
            setSex(textViews, 3);
            dialog_titleImg.setImageResource(R.mipmap.dialog_score4);
            dialog_titleText.setText("很抱歉对您造成不便，能给我们一些意见么？");
            dialog_content.setVisibility(View.GONE);
            dialog_edit.setVisibility(View.VISIBLE);
            dialog_line.setVisibility(View.VISIBLE);
            dialog_sure.setVisibility(View.VISIBLE);
            showKeyboard(dialog_edit, inputManager);
            if (badListener != null) {
                badListener.onScore(3, "", false);
            }
        });
        dialog_score4.setOnClickListener(v -> {
            setSex(textViews, 4);
            if (badListener != null) {
                badListener.onScore(4, "", false);
            }
            if (!ShopJumpUtil.toQQDownload(context, "com.example.administrator.jipinshop")) {
                if (!ShopJumpUtil.toMarket(context, "com.example.administrator.jipinshop", null)) {
                    ToastUtil.show("没有找到您手机里的应用商店，请确认");
                }
            }
            dialog.dismiss();
        });
        dialog_score5.setOnClickListener(v -> {
            setSex(textViews, 5);
            if (badListener != null) {
                badListener.onScore(5, "", false);
            }
            if (!ShopJumpUtil.toQQDownload(context, "com.example.administrator.jipinshop")) {
                if (!ShopJumpUtil.toMarket(context, "com.example.administrator.jipinshop", null)) {
                    ToastUtil.show("没有找到您手机里的应用商店，请确认");
                }
            }
            dialog.dismiss();
        });
        dialog_sure.setOnClickListener(v -> {
            inputManager.hideSoftInputFromWindow(dialog.getCurrentFocus().getWindowToken(), 0);
            if (badListener != null) {
                badListener.onScore(score[0], dialog_edit.getText().toString(), true);
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

    private static void showKeyboard(EditText editText, InputMethodManager inputManager) {
        if (editText != null) {
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            inputManager.showSoftInput(editText, 0);
        }
    }

    private static void setSex(ImageView[] imageViews, int sexNum) {
        for (int i = 0; i < imageViews.length; i++) {
            if (i < sexNum) {
                imageViews[i].setImageResource(R.mipmap.dialog_score3);
            } else {
                imageViews[i].setImageResource(R.mipmap.dialog_score2);
            }
        }
    }

    public interface OnDismissLitener {
        void onDismiss();
    }

    public interface OnScoreListener {
        void onScore(int score, String content, Boolean scoreFlag);
    }

    /**
     * 新人首次进入商品详情时弹出新手引导
     */
    public static void shopGuideDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_shop_guide, null);
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
    public static void freeBuyDialog(Context context, String actualPrice, String freePrice, View.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_free_buy, null);
        TextView dialog_time = view.findViewById(R.id.dialog_time);
        String html = "您需在<b>一小时内</b>前往淘宝APP购买";
        dialog_time.setText(Html.fromHtml(html));
        TextView dialog_actualPrice = view.findViewById(R.id.dialog_actualPrice);
        String html2 = "购买价格<font color='#E25838'><b>¥" + actualPrice + "</b></font>";
        dialog_actualPrice.setText(Html.fromHtml(html2));
        TextView dialog_feePrice = view.findViewById(R.id.dialog_feePrice);
        String html3 = "补贴<font color='#E25838'><b>¥" + freePrice + "</b></font>";
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
     * 首页专属导师弹窗
     */
    public static void teacherDialog(Context context,String Twechat , String Tavatar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogNewpeopleBuyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_newpeople_buy, null,false);
        binding.dialogWx.setText("微信：" + Twechat);
        GlideApp.loderCircleImage(context,Tavatar,binding.dialogImage,R.mipmap.rlogo,0);
        binding.dialogCancle.setOnClickListener(v -> dialog.dismiss());
        binding.dialogSure.setOnClickListener(v -> {
            ClipboardManager clip = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("jipinshop", binding.dialogWx.getText().toString().replace("微信：",""));
            clip.setPrimaryClip(clipData);
            ToastUtil.show("复制成功");
            SPUtils.getInstance().put(CommonDate.CLIP,binding.dialogWx.getText().toString().replace("微信：",""));
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    /**
     * 邀请码dialog
     */
    public static void invitationDialog(Context context, OnInvitationListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_invitation, null);
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
            listener.invitation(dialog_edit.getText().toString().trim(), dialog, inputManager);
        });
        showKeyboard(dialog_edit, inputManager);
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.setContentView(view);
    }

    public interface OnInvitationListener {
        void invitation(String invitationCode, Dialog dialog, InputMethodManager inputManager);
    }

    //淘口令弹框
    public static void tklDialog(Context context, TklBean bean, String tkl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogTklBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_tkl, null, false);
        if (bean.getData() == null) {
            binding.dialogContent.setText(tkl);
        } else {
            binding.setData(bean.getData().getData());
            binding.executePendingBindings();
            binding.detailOtherPrice.setTv(true);
            binding.detailOtherPrice.setColor(R.color.color_9D9D9D);
            double coupon = new BigDecimal(bean.getData().getData().getCouponPrice()).doubleValue();
            if (coupon == 0) {//没有优惠券
                binding.detailCoupon.setVisibility(View.GONE);
            } else {
                binding.detailCoupon.setVisibility(View.VISIBLE);
            }
            double free = new BigDecimal(bean.getData().getData().getFee()).doubleValue();
            if (free == 0) {//没有补贴
                binding.detailFee.setVisibility(View.GONE);
            } else {
                binding.detailFee.setVisibility(View.VISIBLE);
            }
            if (coupon == 0 && free == 0) {
                binding.detailOtherPrice.setVisibility(View.GONE);
            } else {
                binding.detailOtherPrice.setVisibility(View.VISIBLE);
            }
        }
        binding.dialogSure1.setOnClickListener(v -> {
            context.startActivity(new Intent(context, TBSreachResultActivity.class)
                    .putExtra("content", tkl)
                    .putExtra("type", "2")//默认淘宝搜索
            );
            dialog.dismiss();
        });
        binding.dialogSure2.setOnClickListener(v -> {
            context.startActivity(new Intent(context, TBShoppingDetailActivity.class)
                    .putExtra("otherGoodsId", bean.getData().getData().getOtherGoodsId())
                    .putExtra("source",bean.getData().getData().getSource())
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

    //新人0元购专区 离开时弹框
    public static void outDialog(Context context, List<String> strings, String allowance, View.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogOutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_out, null, false);
        binding.dialogTime.setText("还有" + allowance + "元津贴仍未使用下单立减当钱花");
        GlideApp.loderTopRoundImage(context, strings.get(0), binding.dialogImg1, (int) context.getResources().getDimension(R.dimen.x10));
        GlideApp.loderTopRoundImage(context, strings.get(1), binding.dialogImg2, (int) context.getResources().getDimension(R.dimen.x10));
        GlideApp.loderTopRoundImage(context, strings.get(2), binding.dialogImg3, (int) context.getResources().getDimension(R.dimen.x10));
        binding.dialogSure.setOnClickListener(v -> {
            dialog.dismiss();
        });
        binding.dialogDismiss.setOnClickListener(v -> {
            if (listener != null)
                listener.onClick(v);
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //新人0元购免单专区 离开时弹窗
    public static void outDialog2(Context context, NewFreeBean bean , View.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogOutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_out, null, false);
        if (bean.getData().size() >= 5){
            GlideApp.loderTopRoundImage(context, bean.getData().get(2).getImg(), binding.dialogImg1, (int) context.getResources().getDimension(R.dimen.x10));
            GlideApp.loderTopRoundImage(context, bean.getData().get(3).getImg(), binding.dialogImg2, (int) context.getResources().getDimension(R.dimen.x10));
            GlideApp.loderTopRoundImage(context, bean.getData().get(4).getImg(), binding.dialogImg3, (int) context.getResources().getDimension(R.dimen.x10));
            binding.dialogText1.setText("¥"+ bean.getData().get(2).getBuyPrice());
            binding.dialogText2.setText("¥"+ bean.getData().get(3).getBuyPrice());
            binding.dialogText3.setText("¥"+ bean.getData().get(4).getBuyPrice());
        }else {
            GlideApp.loderTopRoundImage(context, bean.getData().get(0).getImg(), binding.dialogImg1, (int) context.getResources().getDimension(R.dimen.x10));
            GlideApp.loderTopRoundImage(context, bean.getData().get(1).getImg(), binding.dialogImg2, (int) context.getResources().getDimension(R.dimen.x10));
            GlideApp.loderTopRoundImage(context, bean.getData().get(2).getImg(), binding.dialogImg3, (int) context.getResources().getDimension(R.dimen.x10));
            binding.dialogText1.setText("¥"+ bean.getData().get(0).getBuyPrice());
            binding.dialogText2.setText("¥"+ bean.getData().get(1).getBuyPrice());
            binding.dialogText3.setText("¥"+ bean.getData().get(2).getBuyPrice());
        }
        long endTime = bean.getEndTime();
        long time = (endTime * 1000) - System.currentTimeMillis();
        if (time > 0){
            binding.dialogTime.setVisibility(View.VISIBLE);
             new CountDownTimer(time, 1000) {
                public void onTick(long millisUntilFinished) {
                    binding.dialogTime.setText(TimeUtil.getCountTimeByLong2(millisUntilFinished) + "后将失效");
                }
                public void onFinish() {
                    binding.dialogTime.setVisibility(View.GONE);
                }
            }.start();
        }else {
            binding.dialogTime.setVisibility(View.GONE);
        }
        binding.dialogSure.setOnClickListener(v -> {
            dialog.dismiss();
        });
        binding.dialogDismiss.setOnClickListener(v -> {
            if (listener != null)
                listener.onClick(v);
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //特惠购首次下单奖励弹框
    public static void cheapDialog(Context context, String addAllowancePrice ,
                                   List<PopBean.DataBean.AllowanceGoodsListBean> list,
                                   View.OnClickListener listener , View.OnClickListener cancleListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogCheapBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_cheap, null, false);
        binding.dialogTitle.setText("新人专享"+addAllowancePrice+"元补贴大礼包");
        for (int i = 0; i < list.size(); i++) {
            if (i==0){
                binding.setDate1(list.get(i));
            }else if (i == 1){
                binding.setDate2(list.get(i));
            }else if (i == 2){
                binding.setDate3(list.get(i));
            }
        }
        binding.executePendingBindings();
        binding.dialogCancle.setOnClickListener(v -> {
            if (cancleListener != null)
                cancleListener.onClick(v);
            dialog.dismiss();
        });
        binding.dialogSure.setOnClickListener(v -> {
            if (listener != null)
                listener.onClick(v);
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //关联上下级用户弹窗
    public static void userDialog(Context context,TklBean bean, final OnInvitationListener sureListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        DialogUserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_user, null,false);
        final Dialog dialog = builder.create();
        String html = "关联成功奖励<font color='#E25838'>"+bean.getAddPoint()+"极币</font>";
        binding.dialogContent2.setText(Html.fromHtml(html));
        binding.setData(bean.getData().getData());
        binding.executePendingBindings();
        binding.dialogCancle.setOnClickListener(v -> {
            dialog.dismiss();
        });
        binding.dialogSure.setOnClickListener(v -> {
            if (sureListener != null)
                sureListener.invitation(bean.getData().getData().getInvitationCode(),dialog,null);
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //赚钱红包页面弹框
    public static void bedDialog(Context context, String money , View.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_money, null);
        final Dialog dialog = builder.create();
        ImageView dialog_cancle = view.findViewById(R.id.dialog_cancle);
        TextView dialog_money = view.findViewById(R.id.dialog_money);
        TextView dialog_sure = view.findViewById(R.id.dialog_sure);
        dialog_cancle.setOnClickListener(view1 -> {
            dialog.dismiss();
        });
        dialog_sure.setOnClickListener(view12 -> {
            if (listener != null){
                listener.onClick(view12);
            }
            dialog.dismiss();
        });
        dialog_money.setText(money);
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(view);
    }

    //特惠购页面新手指导
    public static void cheapGuideDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_cheap_guide, null);
        ImageView dialog_cancle = view.findViewById(R.id.dialog_cancle);
        dialog_cancle.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.8f);
        dialog.show();
        dialog.setContentView(view);
    }

    //打开朋友圈
    public static void sharePYQDialog(Context context, View.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_share_pyq, null);
        TextView dialog_cancle = view.findViewById(R.id.dialog_cancle);
        TextView dialog_sure = view.findViewById(R.id.dialog_sure);
        dialog_cancle.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog_sure.setOnClickListener(v -> {
            listener.onClick(v);
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(view);
    }

    //隐私协议dialog
    public static void servceDialog(Context context,View.OnClickListener listener , View.OnClickListener cancleListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_servce, null);
        TextView dialog_cancle = view.findViewById(R.id.dialog_cancle);
        TextView dialog_sure = view.findViewById(R.id.dialog_sure);
        TextView servce_content = view.findViewById(R.id.servce_content);
        String content="依据最新法律要求，我们更新了《隐私政策》，特向您说明，在使用我们的服务时，" +
                "我们如何收集、使用、储存和分享这些信息，以及我们为您提供的访问、更新、控制和保护这些信息的方式。\n" +
                "请您在使用前仔细阅读《用户服务协议》及《隐私政策》，充分理解后选择“同意并继续”。";
        SpannableString string = new SpannableString(content);
        //设置点击效果 设置多个Span时，需要为每个span创建新的对象，否者不起作用
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                context.startActivity(new Intent(context, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL+"privacy.html")
                        .putExtra(WebActivity.title,"隐私政策")
                );
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                //去掉可点击文字的下划线
                ds.setColor(context.getResources().getColor(R.color.color_4E89FF));
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                context.startActivity(new Intent(context, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL+"agreement.html")
                        .putExtra(WebActivity.title,"用户协议")
                );
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                //去掉可点击文字的下划线
                ds.setColor(context.getResources().getColor(R.color.color_4E89FF));
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan3 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                context.startActivity(new Intent(context, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL+"privacy.html")
                        .putExtra(WebActivity.title,"隐私政策")
                );
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                //去掉可点击文字的下划线
                ds.setColor(context.getResources().getColor(R.color.color_4E89FF));
                ds.setUnderlineText(false);
            }
        };
        string.setSpan(clickableSpan1,14,20, Spanned.SPAN_INCLUSIVE_INCLUSIVE);//隐私协议
        string.setSpan(clickableSpan2,96,104, Spanned.SPAN_INCLUSIVE_INCLUSIVE);//用户协议
        string.setSpan(clickableSpan3,105,111, Spanned.SPAN_INCLUSIVE_INCLUSIVE);//隐私协议
        // 设置此方法后，点击事件才能生效
        servce_content.setMovementMethod(LinkMovementMethod.getInstance());
        //去掉点击效果
        servce_content.setHighlightColor(Color.TRANSPARENT);
        servce_content.setText(string);
        dialog_cancle.setOnClickListener(v -> {
            cancleListener.onClick(v);
            dialog.dismiss();
        });
        dialog_sure.setOnClickListener(v -> {
            listener.onClick(v);
            dialog.dismiss();
        });
        //设置点击屏幕不消失
        dialog.setCanceledOnTouchOutside(false);
        //设置点击返回键不消失
        dialog.setCancelable(false);
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(view);
    }

    //淘宝授权dialog
    public static void TBLoginDialog(Context context,View.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_tb_login, null);
        ImageView dialog_cancle = view.findViewById(R.id.dialog_cancle);
        TextView dialog_sure = view.findViewById(R.id.dialog_sure);
        TextView dialog_title = view.findViewById(R.id.dialog_title);
        String str = "授权<font color='#202020'>极品城</font>后即可<font color='#202020'>购买特价好物</font>";
        dialog_title.setText(Html.fromHtml(str));
        dialog_cancle.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog_sure.setOnClickListener(v -> {
            listener.onClick(v);
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(view);
    }

    //红包活动时的弹窗
    public static void hbWebDialog(Context context,View.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        DialogNewpeople2Binding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_newpeople2, null,false);
        final Dialog dialog = builder.create();
        binding.dialogCancle.setVisibility(View.GONE);
        binding.dialogImage.setImageResource(R.mipmap.action_hb);
        binding.dialogImage.setOnClickListener(v -> {
            if (listener != null){
                listener.onClick(v);
            }
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }
}