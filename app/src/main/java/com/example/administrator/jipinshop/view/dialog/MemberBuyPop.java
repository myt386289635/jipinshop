package com.example.administrator.jipinshop.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;

import java.math.BigDecimal;

/**
 * @author 莫小婷
 * @create 2020/8/27
 * @Describe 会员购买弹窗
 */
public class MemberBuyPop extends PopupWindow {

    private Context mContext;
    private OnClick mOnClick;
    private RelativeLayout popupLL;
    private ImageView pop_dismiss;
    private TextView pop_buyNotice,pop_otherPrice,pop_price1,pop_price2,pop_discount,pop_pay;
    private CheckBox pop_alipay, pop_wxpay;
    private String type = "1";//1是支付宝 2是微信
    private String level = "";//1是月卡 2是年卡

    public MemberBuyPop(Context context,OnClick onClick) {
        mContext = context;
        mOnClick = onClick;
        createView();
    }

    private void createView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_member_buy,null);
        popupLL = view.findViewById(R.id.popup_layout);
        pop_dismiss = view.findViewById(R.id.pop_dismiss);
        pop_buyNotice = view.findViewById(R.id.pop_buyNotice);
        pop_otherPrice = view.findViewById(R.id.pop_otherPrice);
        pop_price1 = view.findViewById(R.id.pop_price1);
        pop_price2 = view.findViewById(R.id.pop_price2);
        pop_discount = view.findViewById(R.id.pop_discount);
        pop_alipay = view.findViewById(R.id.pop_alipay);
        pop_wxpay = view.findViewById(R.id.pop_wxpay);
        pop_pay = view.findViewById(R.id.pop_pay);
        pop_pay.setOnClickListener(v -> {
            if (pop_alipay.isChecked()){
                type = "1";
            }else if (pop_wxpay.isChecked()){
                type = "2";
            }
            mOnClick.onBuyMember(level,type);
            this.dismiss();
        });
        pop_alipay.setChecked(true);
        pop_alipay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pop_wxpay.setChecked(!isChecked);
        });
        pop_wxpay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pop_alipay.setChecked(!isChecked);
        });
        createPop(view);
    }

    public void createPop(View view){
        // 设置AccessoryPopup的view
        this.setContentView(view);
        // 设置AccessoryPopup弹出窗体的宽度
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置AccessoryPopup弹出窗体的高度
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置AccessoryPopup弹出窗体可点击
        this.setFocusable(false);
        // 设置AccessoryPopup弹出窗体的动画效果
        this.setAnimationStyle(R.style.AnimPop);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        view.setOnTouchListener((v, event) -> {
            int height = popupLL.getBottom();
            int left = popupLL.getLeft();
            int right = popupLL.getRight();
            int y = (int) event.getY();
            int x = (int) event.getX();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (y > height || x < left || x > right) {
                    mOnClick.onDismiss();
                    this.dismiss();
                }
            }
            return true;
        });
        pop_dismiss.setOnClickListener(v -> {
            mOnClick.onDismiss();
            this.dismiss();
        });
    }

    //1是月卡 2是年卡
    public void setDate(String level, String price ,String priceBefore){
        this.level = level;
        pop_otherPrice.setText("原价￥" + priceBefore);
        pop_price1.setText(price);
        pop_price2.setText(price);
        if (level.equals("1")){
            pop_buyNotice.setText("月卡会员");
        }else {
            pop_buyNotice.setText("年卡会员");
        }
        BigDecimal bigDecimal = new BigDecimal(priceBefore);
        BigDecimal bigDecimal1 = new BigDecimal(price);
        pop_discount.setText("已优惠￥" + bigDecimal.subtract(bigDecimal1).setScale(2,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
    }

    public void show(View view,String level, String price ,String priceBefore){
        setDate(level, price, priceBefore);
        int y = (int) mContext.getResources().getDimension(R.dimen.y580);
        this.showAsDropDown( this ,view, 0, -y);
    }

    //Android7.0 及以上 popupwindow showAsDropDown 无效解决方法
    //同时解决，popWindow部分手机底部留白问题
    public  void showAsDropDown(final PopupWindow pw, final View anchor, final int xoff, final int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getWindowVisibleDisplayFrame(rect);
            Activity activity = (Activity) anchor.getContext();
            Rect outRect1 = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
            int h = outRect1.height() - rect.bottom;
            setHeight(h);
            pw.showAsDropDown(anchor, xoff, yoff);
        } else {
            pw.showAsDropDown(anchor, xoff, yoff);
        }
    }

    public interface OnClick{
        void onBuyMember(String level,String type);
        void onDismiss();
    }
}
