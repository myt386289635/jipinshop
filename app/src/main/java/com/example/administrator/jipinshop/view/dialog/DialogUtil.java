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
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.cheapgoods.CheapBuyActivity;
import com.example.administrator.jipinshop.activity.home.home.HomeNewActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.activity.sreach.result.TBSreachResultActivity;
import com.example.administrator.jipinshop.adapter.DialogLuckAdapter;
import com.example.administrator.jipinshop.bean.FamilyBean;
import com.example.administrator.jipinshop.bean.GroupInfoBean;
import com.example.administrator.jipinshop.bean.NewPeopleBean;
import com.example.administrator.jipinshop.bean.PrizeLogBean;
import com.example.administrator.jipinshop.bean.SubUserBean;
import com.example.administrator.jipinshop.bean.TklBean;
import com.example.administrator.jipinshop.databinding.DialogBuyOutBinding;
import com.example.administrator.jipinshop.databinding.DialogCheapBuyBinding;
import com.example.administrator.jipinshop.databinding.DialogCheapOutBinding;
import com.example.administrator.jipinshop.databinding.DialogFamilyBinding;
import com.example.administrator.jipinshop.databinding.DialogFirstimgBinding;
import com.example.administrator.jipinshop.databinding.DialogGroupBinding;
import com.example.administrator.jipinshop.databinding.DialogHomeBuyBinding;
import com.example.administrator.jipinshop.databinding.DialogLuckBinding;
import com.example.administrator.jipinshop.databinding.DialogMemberBuyBinding;
import com.example.administrator.jipinshop.databinding.DialogMessageDeteleBinding;
import com.example.administrator.jipinshop.databinding.DialogNewpeople2Binding;
import com.example.administrator.jipinshop.databinding.DialogNewpeopleBuyBinding;
import com.example.administrator.jipinshop.databinding.DialogNonewBinding;
import com.example.administrator.jipinshop.databinding.DialogOutBinding;
import com.example.administrator.jipinshop.databinding.DialogOutGroupBinding;
import com.example.administrator.jipinshop.databinding.DialogOutH5Binding;
import com.example.administrator.jipinshop.databinding.DialogParityBinding;
import com.example.administrator.jipinshop.databinding.DialogPayFileBinding;
import com.example.administrator.jipinshop.databinding.DialogPaySuccessBinding;
import com.example.administrator.jipinshop.databinding.DialogShopGuideBinding;
import com.example.administrator.jipinshop.databinding.DialogSign1Binding;
import com.example.administrator.jipinshop.databinding.DialogSignBinding;
import com.example.administrator.jipinshop.databinding.DialogTbLoginBinding;
import com.example.administrator.jipinshop.databinding.DialogTklBinding;
import com.example.administrator.jipinshop.databinding.DialogUserBinding;
import com.example.administrator.jipinshop.databinding.DialogUserDetailBinding;
import com.example.administrator.jipinshop.databinding.DialogZeroBuyBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
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
        if (isCrude) {
            sure.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        } else {
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
     * 颜色都是202020
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
    public static void SingleDialog(Context context, String titleStr, String sureText,
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
                                     String sureText, String cancleText, Boolean isCrude,
                                     final View.OnClickListener sureListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_listing, null);
        TextView title = view.findViewById(R.id.dialog_title);
        title.setText(titleStr);
        TextView sure = view.findViewById(R.id.dialog_sure);
        sure.setText(sureText);
        if (isCrude) {
            sure.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        } else {
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
     * 带title的默认弹框
     */
    public static void listingDetele(Context context, String titleStr, String content, String sureText, String cancleText,
                                     int titleColor, int sureColor, int cancleColor, int contentColor,
                                     Boolean isSureCrude, Boolean isContentCrude,
                                     final View.OnClickListener sureListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_listing, null);

        TextView title = view.findViewById(R.id.dialog_titleLine);
        title.setText(titleStr);
        title.setTextColor(context.getResources().getColor(titleColor));
        title.setTextSize(17);

        TextView sure = view.findViewById(R.id.dialog_sure);
        sure.setText(sureText);
        sure.setTextColor(context.getResources().getColor(sureColor));
        if (isSureCrude) {
            sure.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        } else {
            sure.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//不加粗
        }

        TextView contentView = view.findViewById(R.id.dialog_title);
        contentView.setText(content);
        contentView.setTextColor(context.getResources().getColor(contentColor));
        if (isContentCrude) {
            contentView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        } else {
            contentView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//不加粗
        }
        contentView.setTextSize(15);

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


    //版本更新diaglog
    public static void UpDateDialog(Context context, String content, final View.OnClickListener updateListener, OnDismissLitener dismissLitener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_signsuccess, null);
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

    //强制版本更新diaglog
    public static void UpDateDialog1(Context context,  String content, final View.OnClickListener updateListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_signsuccess, null);
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

    //用户主页里的 点赞弹窗
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

    //比价弹窗
    public static void parityDialog(Context context, String source, final View.OnClickListener sureListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        DialogParityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_parity, null, false);
        final Dialog dialog = builder.create();
        if (source.equals("4")) {
            //拼多多
            binding.dialogTitle.setText("本提示与拼多多比价订单佣金调整政策相关，");
        } else {
            binding.dialogTitle.setText("本提示与淘宝比价订单佣金调整政策相关，");
        }
        binding.dialogBackground.setOnClickListener(v -> dialog.dismiss());
        binding.dialogCancle.setOnClickListener(v -> {
            dialog.dismiss();
        });
        binding.dialogParity.setOnClickListener(v -> {
            if (sureListener != null)
                sureListener.onClick(v);
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //新人系列4弹窗
    public static void newPeopleDialog(Context context, String url, final View.OnClickListener cancleListener,
                                       final View.OnClickListener sureListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        DialogNewpeople2Binding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_newpeople2, null, false);
        final Dialog dialog = builder.create();
        GlideApp.loderImage(context, url, binding.dialogImage, 0, 0);
        binding.dialogCancle.setOnClickListener(v -> {
            if (cancleListener != null)
                cancleListener.onClick(v);
            dialog.dismiss();
        });
        binding.dialogImage.setOnClickListener(v -> {
            if (sureListener != null) {
                sureListener.onClick(v);
            }
            dialog.dismiss();
        });
        dialog.setCancelable(false);
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //首次下载显示五重礼抽奖动画效果
    public static void fristDialog(Context context, final View.OnClickListener cancleListener,
                                   final View.OnClickListener sureListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        DialogFirstimgBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_firstimg, null, false);
        final Dialog dialog = builder.create();
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context)
                .asGif()
                .load(R.drawable.main_frist)
                .apply(options)
                .into(binding.dialogBg);
        Glide.with(context)
                .asGif()
                .load(R.drawable.main_pmd)
                .apply(options)
                .into(binding.dialogPmd);
        binding.dialogResult.setVisibility(View.GONE);
        CountDownTimer timer =  new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {}

            public void onFinish() {
                binding.dialogResult.setVisibility(View.VISIBLE);
            }
        }.start();
        dialog.setOnDismissListener(dialog1 -> {
            if (timer != null)
                timer.cancel();
        });
        binding.dialogDismiss.setOnClickListener(v -> {
            if (cancleListener != null)
                cancleListener.onClick(v);
            dialog.dismiss();
        });
        binding.dialogResult.setOnClickListener(v -> {
            if (sureListener != null) {
                sureListener.onClick(v);
            }
            dialog.dismiss();
        });
        dialog.setCancelable(false);
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
        GlideApp.loderImage(context, resource, dialog_img, 0, 0);
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

    //评分
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

    //公用非会员提示弹窗
    public static void memberGuideDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogShopGuideBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_shop_guide, null, false);
        binding.itemGoMember.setOnClickListener(v -> {
            context.startActivity(new Intent(context, HomeNewActivity.class)
                    .putExtra("type",HomeNewActivity.member)
            );
            dialog.dismiss();
        });
        binding.dialogDismiss.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.8f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //公用非会员提示弹窗
    public static void memberGuideDialog(Context context, View.OnClickListener go) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogShopGuideBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_shop_guide, null, false);
        binding.itemGoMember.setOnClickListener(v -> {
            dialog.dismiss();
            if (go != null) {
                go.onClick(v);
            }
        });
        binding.dialogDismiss.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.8f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //首页专属导师弹窗
    public static void teacherDialog(Context context, String Twechat, String Tavatar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogNewpeopleBuyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_newpeople_buy, null, false);
        binding.dialogWx.setText("微信：" + Twechat);
        GlideApp.loderCircleImage(context, Tavatar, binding.dialogImage, R.mipmap.rlogo, 0);
        binding.dialogCancle.setOnClickListener(v -> dialog.dismiss());
        binding.dialogSure.setOnClickListener(v -> {
            ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("jipinshop", binding.dialogWx.getText().toString().replace("微信：", ""));
            clip.setPrimaryClip(clipData);
            ToastUtil.show("复制成功");
            SPUtils.getInstance().put(CommonDate.CLIP, binding.dialogWx.getText().toString().replace("微信：", ""));
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //邀请码dialog
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
                    .putExtra("source", bean.getData().getData().getSource())
                    .putExtra("parity", bean.getData().getData().getCommissionRate())
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

    //H5签到活动页面 离开时弹框
    public static void outH5Dialog(Context context, View.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogOutH5Binding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_out_h5, null, false);
        binding.dialogCancle.setOnClickListener(v -> {
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

    //特惠购离开时弹窗
    public static void cheapOutDialog(Context context, List<NewPeopleBean.DataBean.AllowanceGoodsListBean> strings, String allowance, View.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogCheapOutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_cheap_out, null, false);
        binding.dialogMoney.setText(allowance + "元");
        GlideApp.loderTopRoundImage(context, strings.get(0).getImg(), binding.dialogImg1, (int) context.getResources().getDimension(R.dimen.x10));
        GlideApp.loderTopRoundImage(context, strings.get(1).getImg(), binding.dialogImg2, (int) context.getResources().getDimension(R.dimen.x10));
        GlideApp.loderTopRoundImage(context, strings.get(2).getImg(), binding.dialogImg3, (int) context.getResources().getDimension(R.dimen.x10));
        binding.dialogText1.setText("补贴" + strings.get(0).getUseAllowancePrice() + "元");
        binding.dialogText2.setText("补贴" + strings.get(1).getUseAllowancePrice() + "元");
        binding.dialogText3.setText("补贴" + strings.get(2).getUseAllowancePrice() + "元");
        binding.dialogSure.setOnClickListener(v -> {
            if (listener != null)
                listener.onClick(v);
            dialog.dismiss();
        });
        binding.dialogDismiss.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //关联上下级用户弹窗
    public static void userDialog(Context context, TklBean bean, final OnInvitationListener sureListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        DialogUserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_user, null, false);
        final Dialog dialog = builder.create();
        String html = "关联成功奖励<font color='#E25838'>" + bean.getAddPoint() + "极币</font>";
        binding.dialogContent2.setText(Html.fromHtml(html));
        binding.setData(bean.getData().getData());
        binding.executePendingBindings();
        binding.dialogCancle.setOnClickListener(v -> {
            dialog.dismiss();
        });
        binding.dialogSure.setOnClickListener(v -> {
            if (sureListener != null)
                sureListener.invitation(bean.getData().getData().getInvitationCode(), dialog, null);
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //特惠购购买时弹窗
    public static void cheapBuyDialog(Context context, String useAllowancePrice, View.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogCheapBuyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_cheap_buy, null, false);
        binding.dialogMoney.setText(useAllowancePrice + "元");
        binding.dialogDismiss.setOnClickListener(v -> {
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

    //打开朋友圈
    public static void sharePYQDialog(Context context, View.OnClickListener listener) {
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
    public static void servceDialog(Context context, View.OnClickListener listener, View.OnClickListener cancleListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_servce, null);
        TextView dialog_cancle = view.findViewById(R.id.dialog_cancle);
        TextView dialog_sure = view.findViewById(R.id.dialog_sure);
        TextView servce_content = view.findViewById(R.id.servce_content);
        TextView servce_content2 = view.findViewById(R.id.servce_content2);
        String content = "依据最新法律要求，我们更新了《隐私政策》，特向您说明，在使用我们的服务时，" +
                "我们如何收集、使用、储存和分享这些信息，以及我们为您提供的访问、更新、控制和保护这些信息的方式。";
        SpannableString string = new SpannableString(content);
        //设置点击效果 设置多个Span时，需要为每个span创建新的对象，否者不起作用
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                context.startActivity(new Intent(context, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL + "privacy.html")
                        .putExtra(WebActivity.title, "隐私政策")
                );
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //去掉可点击文字的下划线
                ds.setColor(context.getResources().getColor(R.color.color_E25838));
                ds.setUnderlineText(false);
            }
        };
        string.setSpan(clickableSpan1, 14, 20, Spanned.SPAN_INCLUSIVE_INCLUSIVE);//隐私协议
        // 设置此方法后，点击事件才能生效
        servce_content.setMovementMethod(LinkMovementMethod.getInstance());
        //去掉点击效果
        servce_content.setHighlightColor(Color.TRANSPARENT);
        servce_content.setText(string);

        String content2 = "请您在使用前仔细阅读《用户服务协议》及《隐私政策》，充分理解后选择“同意并继续”。";
        SpannableString string2 = new SpannableString(content2);
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                context.startActivity(new Intent(context, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL + "agreement.html")
                        .putExtra(WebActivity.title, "用户协议")
                );
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //去掉可点击文字的下划线
                ds.setColor(context.getResources().getColor(R.color.color_E25838));
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan3 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                context.startActivity(new Intent(context, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL + "privacy.html")
                        .putExtra(WebActivity.title, "隐私政策")
                );
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //去掉可点击文字的下划线
                ds.setColor(context.getResources().getColor(R.color.color_E25838));
                ds.setUnderlineText(false);
            }
        };
        string2.setSpan(clickableSpan2, 10, 18, Spanned.SPAN_INCLUSIVE_INCLUSIVE);//用户协议
        string2.setSpan(clickableSpan3, 19, 25, Spanned.SPAN_INCLUSIVE_INCLUSIVE);//隐私协议
        servce_content2.setMovementMethod(LinkMovementMethod.getInstance());
        servce_content2.setHighlightColor(Color.TRANSPARENT);
        servce_content2.setText(string2);
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
    public static void TBLoginDialog(Context context, View.OnClickListener listener ,View.OnClickListener cancleListener ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogTbLoginBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_tb_login, null , false);
        binding.dialogCancle.setOnClickListener(v -> {
            cancleListener.onClick(v);
            dialog.dismiss();
        });
        binding.dialogSure.setOnClickListener(v -> {
            listener.onClick(v);
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //红包活动时的弹窗
    public static void hbWebDialog(Context context, View.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        DialogNewpeople2Binding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_newpeople2, null, false);
        final Dialog dialog = builder.create();
        binding.dialogCancle.setVisibility(View.GONE);
        binding.dialogImage.setImageResource(R.mipmap.action_hb);
        binding.dialogImage.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(v);
            }
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //下级用户详情
    public static void userDetailDialog(Context context, SubUserBean bean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        DialogUserDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_user_detail, null, false);
        final Dialog dialog = builder.create();
        binding.dialogClose.setOnClickListener(v -> dialog.dismiss());
        binding.dialogTitle.setText(bean.getData().getNickname());
        if (TextUtils.isEmpty(bean.getData().getMobile())) {
            binding.dialogPhone.setText("暂未填写");
        } else {
            binding.dialogPhone.setText(bean.getData().getMobile());
        }
        if (TextUtils.isEmpty(bean.getData().getWechat())) {
            binding.dialogWxCode.setText("暂未填写");
        } else {
            binding.dialogWxCode.setText(bean.getData().getWechat());
        }
        binding.dialogMonthMoney.setText("￥" + bean.getData().getPreMonthFee());
        binding.dialogTodayMoney.setText("￥" + bean.getData().getTodayFee());
        if (bean.getData().getLevel() == 0) {
            //普通人员
            binding.dialogProgressTitle.setText("VIP进度");
        } else {
            binding.dialogProgressTitle.setText("合伙人进度");
        }
        binding.dialogProgress.setText(bean.getData().getSubTotal() + "/" + bean.getData().getLevelInvitedUser());
        binding.dialogFans.setText(bean.getData().getSubTotal());
        if (TextUtils.isEmpty(bean.getData().getLastOrderTime())) {
            binding.dialogTime.setText("暂未下单");
        } else {
            binding.dialogTime.setText(bean.getData().getLastOrderTime());
        }
        binding.dialogPhoneCopy.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(bean.getData().getMobile())) {
                ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("jipinshop", bean.getData().getMobile());
                clip.setPrimaryClip(clipData);
                ToastUtil.show("复制成功");
                SPUtils.getInstance().put(CommonDate.CLIP, bean.getData().getMobile());
            } else {
                ToastUtil.show("暂未填写");
            }
        });
        binding.dialogWxCopy.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(bean.getData().getWechat())) {
                ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("jipinshop", bean.getData().getWechat());
                clip.setPrimaryClip(clipData);
                ToastUtil.show("复制成功");
                SPUtils.getInstance().put(CommonDate.CLIP, bean.getData().getWechat());
            } else {
                ToastUtil.show("暂未填写");
            }
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //购买时会员提示弹窗
    public static void buyMemberDialog(Context context, String buyFree, String upFree,
                                       View.OnClickListener listener , View.OnClickListener buyListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        DialogMemberBuyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_member_buy, null, false);
        final Dialog dialog = builder.create();
        binding.dialogUpFree.setText("加入会员（本单可返￥" + upFree + "）");
        binding.dialogBuy.setText("直接购买（本单可返￥" + buyFree + "）");
        binding.dialogUpFree.setOnClickListener(v -> {
            buyListener.onClick(v);
            dialog.dismiss();
        });
        binding.dialogBuy.setOnClickListener(v -> {
            listener.onClick(v);
            dialog.dismiss();
        });
        binding.dialogDismiss.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    public static void onLuckDialog(Context context, List<PrizeLogBean.DataBean> list) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        DialogLuckBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_luck, null, false);
        final Dialog dialog = builder.create();
        binding.dialogRv.setLayoutManager(new LinearLayoutManager(context));
        DialogLuckAdapter adapter = new DialogLuckAdapter(list, context);
        binding.dialogRv.setAdapter(adapter);
        binding.dialogRv.setNestedScrollingEnabled(false);
        binding.dialogDismiss.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    public static void wxDialog(Context context, String title, String wxCode, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogNewpeopleBuyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_newpeople_buy, null, false);
        binding.dialogTitle.setText(title);
        GlideApp.loderCircleImage(context, R.mipmap.logo, binding.dialogImage, 0, 0);
        binding.dialogWx.setText(wxCode + "极品城");
        binding.dialogContent.setText(content);
        binding.dialogCancle.setOnClickListener(v -> dialog.dismiss());
        binding.dialogSure.setOnClickListener(v -> {
            ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("jipinshop", "极品城");
            clip.setPrimaryClip(clipData);
            ToastUtil.show("复制成功");
            SPUtils.getInstance().put(CommonDate.CLIP, "极品城");
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //0元购详情失去免单资格
    public static void noBuyDialog(Context context, View.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        DialogNonewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_nonew, null, false);
        final Dialog dialog = builder.create();
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.free_scale);
        binding.dialogSure.startAnimation(animation);
        binding.dialogCancle.setOnClickListener(v -> {
            dialog.dismiss();
        });
        binding.dialogSure.setOnClickListener(v -> {
            if (listener != null)
                listener.onClick(v);
            dialog.dismiss();
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.horizontalMargin = 0;
            window.setAttributes(layoutParams);
            window.setDimAmount(0.35f);
        }
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //支付成功弹窗
    public static void paySucDialog(Context context, String level , View.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        DialogPaySuccessBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_pay_success, null, false);
        final Dialog dialog = builder.create();
        if (level.equals("1") || level.equals("4")) {
            binding.dialogImage.setImageResource(R.mipmap.pay_success2);
        } else if (level.equals("2")|| level.equals("5")){
            binding.dialogImage.setImageResource(R.mipmap.pay_success);
        } else {
            binding.dialogImage.setImageResource(R.mipmap.pay_success3);
        }
        binding.dialogDismiss.setOnClickListener(v -> {
            dialog.dismiss();
        });
        binding.dialogImage.setOnClickListener(v -> {
            listener.onClick(v);
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //支付失败弹窗 //type 1是支付宝 2是微信
    public static void payFileDialog(Context context, String isBuy , String type, OnPayListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        DialogPayFileBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_pay_file, null, false);
        final Dialog dialog = builder.create();
        binding.dialogDismiss.setOnClickListener(v -> {
            listener.onFinish();
            dialog.dismiss();
        });
        binding.dialogSure.setOnClickListener(v -> {
            listener.onPay(type);
            dialog.dismiss();
        });
        if (isBuy.equals("1")) {// 1是购买  2续费
            binding.dialogTitle.setText("确定放弃购买极品VIP会员吗？");
        } else {
            binding.dialogTitle.setText("确定放弃续费极品VIP会员吗？");
        }
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    public interface OnPayListener {
        void onPay(String type);
        void onFinish();
    }

    //家庭dialog
    public static void familyDialog(Context context, FamilyBean.DataBean bean, String totle, View.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        DialogFamilyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_family, null, false);
        final Dialog dialog = builder.create();
        String html;
        if (bean.getStatus().equals("1")) {
            //已加入
            binding.dialogBottomContainer.setVisibility(View.GONE);
            binding.dialogName.setText(bean.getNickename());
            html = bean.getCreateTime() + "共享特权<br>累计节省<font color='#E25838'>" + bean.getTotalFee() + "</font>元";
        } else {
            //待加入
            binding.dialogBottomContainer.setVisibility(View.VISIBLE);
            binding.dialogName.setText(bean.getNickename() + "请求共享特权");
            html = "成员加入后无法移除，<br>您最多可以再邀请<font color='#E25838'>" + totle + "</font>位成员共享特权";
        }
        binding.dialogContent.setText(Html.fromHtml(html));
        binding.dialogCancle.setOnClickListener(v -> {
            dialog.dismiss();
        });
        binding.dialogClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        binding.dialogSure.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) {
                listener.onClick(v);
            }
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //0元购购买时弹窗
    public static void zeroBuyDialog(Context context, View.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogZeroBuyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_zero_buy, null, false);
        binding.dialogDismiss.setOnClickListener(v -> {
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

    //拼团退出弹框
    public static void groupOutDialog(Context context, GroupInfoBean.DataBean bean, View.OnClickListener listener) {
        if (bean == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogOutGroupBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_out_group, null, false);
        GlideApp.loderRoundImage(context, bean.getImg(), binding.dialogImage);
        binding.dialogName.setText(bean.getGoodsName());
        binding.dialogPrice.setText("￥" + bean.getUpFee());
        BigDecimal bigDecimal = new BigDecimal(bean.getUpFee());//拼成返
        BigDecimal bigDecimal1 = new BigDecimal(bean.getFee());//未拼成返
        String html = "未拼成少返<b><font color='#E25838'>￥" +
                bigDecimal.subtract(bigDecimal1).stripTrailingZeros().toPlainString() + "</font></b>";
        binding.dialogFee.setText(Html.fromHtml(html));
        binding.dialogSure.setOnClickListener(v -> {
            dialog.dismiss();
        });
        binding.dialogCancle.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null)
                listener.onClick(v);
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //首页拼团信息弹框
    public static void groupDialog(Context context, GroupInfoBean.DataBean bean,
                                   View.OnClickListener cancleListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogGroupBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_group, null, false);
        GlideApp.loderRoundImage(context, bean.getImg(), binding.dialogImage);
        binding.dialogName.setText(bean.getGoodsName());
        binding.dialogPrice.setText("￥" + bean.getUpFee());
        String html = "未邀请成功返<b><font color='#E25838'>￥"+ bean.getFee() + "</font></b>";
        binding.dialogTime.setText(Html.fromHtml(html));
        if (bean.getAvatarList().size() >= 1) {
            GlideApp.loderCircleImage(context, bean.getAvatarList().get(0), binding.groupGrouper, 0, 0);
        }
        binding.dialogCancle.setOnClickListener(v -> {
            dialog.dismiss();
            if (cancleListener != null)
                cancleListener.onClick(v);
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //提现成功时弹窗
    public static void withdrawDialog(Context context , String price , String cost , String rate, View.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogBuyOutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_buy_out, null, false);
        binding.dialogPrice.setText("¥" + price);
        binding.dialogNotice.setText(rate);
        binding.dialogCostContainer.setOnClickListener(v -> {
            dialog.dismiss();
            context.startActivity(new Intent(context, SignActivity.class));
        });
        binding.dialogDismiss.setOnClickListener(v -> {
            dialog.dismiss();
        });
        binding.dialogSure.setOnClickListener(v -> {
            dialog.dismiss();
            listener.onClick(v);
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //每次下单后都需要弹出该弹窗
    public static void buyNoticeDialog(Context context , String fee, String cost,
                                      View.OnClickListener shareListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogHomeBuyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_home_buy, null, false);
        binding.dialogFee.setText("¥" + fee);
        binding.dialogCost.setText("+"+ cost +"极币");
        binding.dialogSure.setOnClickListener(v -> {
            context.startActivity(new Intent(context, CheapBuyActivity.class));
            dialog.dismiss();
        });
        binding.dialogDismiss.setOnClickListener(v -> {
            dialog.dismiss();
        });
        binding.dialogShare.setOnClickListener(v -> {
            dialog.dismiss();
            shareListener.onClick(v);
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //会员兑换dialog
    public static void memberExchange(Context context , OnInvitationListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogMessageDeteleBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.dialog_message_detele, null, false);
        InputMethodManager inputManager = (InputMethodManager) binding.dialogEdit
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        binding.dialogDismiss.setOnClickListener(v -> {
            if (dialog.getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(dialog.getCurrentFocus().getWindowToken(), 0);
            dialog.dismiss();
        });
        binding.dialogSure.setOnClickListener(v -> {
            listener.invitation(binding.dialogEdit.getText().toString().trim(), dialog, inputManager);
        });
        dialog.getWindow().setDimAmount(0.35f);
        showKeyboard(binding.dialogEdit, inputManager);
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.setContentView(binding.getRoot());
    }

    //会员兑换失败dialog
    public static void memberExchangeFile(Context context, String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogOutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_out, null, false);
        binding.dialogContent.setText(error);
        binding.dialogSure.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //签到1-6天
    public static void signDialog(Context context , String bottonName, String content ,
                                  int backImage, View.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogSignBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.dialog_sign, null, false);
        binding.dialogSure.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null)
                listener.onClick(v);
        });
        binding.dialogDismiss.setOnClickListener(v -> {
            dialog.dismiss();
        });
        binding.dialogContent.setText(content);
        binding.dialogSure.setText(bottonName);
        binding.dialogBg.setImageResource(backImage);
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }

    //签到第7天
    public static void signSvn(Context context , int addPoint , View.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog dialog = builder.create();
        DialogSign1Binding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.dialog_sign1, null, false);
        binding.dialogBg.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null)
                listener.onClick(v);
        });
        binding.dialogDismiss.setOnClickListener(v -> {
            dialog.dismiss();
        });
        binding.dialogContent.setText("恭喜你，获得"+ addPoint +"极币");
        dialog.getWindow().setDimAmount(0.35f);
        dialog.show();
        dialog.setContentView(binding.getRoot());
    }
}