package com.example.administrator.jipinshop.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.CircleTitleBean;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/3/17
 * @Describe popWindows
 */
public class PopView extends PopupWindow{

    private Context mContext;
    private RelativeLayout popupLL;
    private ImageView pop_dismiss;
    private FlexboxLayout pop_content;
    private OnClick mOnClick;

    public PopView(Context context , OnClick click) {
        mContext = context;
        mOnClick = click;
        createView();
    }

    public void createView(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_circle,null);
        popupLL = view.findViewById(R.id.popup_layout);
        pop_dismiss = view.findViewById(R.id.pop_dismiss);
        pop_content = view.findViewById(R.id.pop_content);
        createPop(view);
    }

    public void setDate(List<CircleTitleBean.DataBean.ChildrenBean> list , int position){
        pop_content.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            final View itemTypeView = LayoutInflater.from(mContext).inflate(R.layout.item_pop, null);
            final TextView textView = itemTypeView.findViewById(R.id.item_text);
            textView.setText(list.get(i).getTitle());
            if (i == position){
                textView.setTextColor(mContext.getResources().getColor(R.color.color_E25838));
            }else {
                textView.setTextColor(mContext.getResources().getColor(R.color.color_565252));
            }
            int finalI = i;
            textView.setOnClickListener(v -> {
                mOnClick.onPopItemOnClick(finalI);
                this.dismiss();
            });
            pop_content.addView(itemTypeView);
        }
    }

    public void createPop(View view){
        // 设置AccessoryPopup的view
        this.setContentView(view);
        // 设置AccessoryPopup弹出窗体的宽度
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置AccessoryPopup弹出窗体的高度
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置AccessoryPopup弹出窗体可点击
        this.setFocusable(true);
        // 设置AccessoryPopup弹出窗体的动画效果
        this.setAnimationStyle(R.style.AnimTopMiddle);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
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
                    this.dismiss();
                }
            }
            return true;
        });
        pop_dismiss.setOnClickListener(v -> {
            this.dismiss();
        });
    }

    public void show(View view, List<CircleTitleBean.DataBean.ChildrenBean> list , int pos){
        setDate(list , pos);
        this.showAsDropDown( this ,view, 0, 0);
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
        void onPopItemOnClick(int pos);
    }
}
