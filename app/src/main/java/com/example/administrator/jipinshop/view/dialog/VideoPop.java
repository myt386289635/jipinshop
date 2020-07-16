package com.example.administrator.jipinshop.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.VideoPopAdapter;
import com.example.administrator.jipinshop.bean.SchoolHomeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/7/16
 * @Describe
 */
public class VideoPop extends PopupWindow {

    private Context mContext;
    private ScrollView popupLL;
    private RecyclerView item_rv;
    private OnClick mOnClick;
    private VideoPopAdapter mAdapter;
    private List<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean> mList;

    public VideoPop(Context context , OnClick click) {
        mContext = context;
        mOnClick = click;
        createView();
    }

    public void createView(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_video,null);
        popupLL = view.findViewById(R.id.popup_layout);
        item_rv = view.findViewById(R.id.item_rv);
        item_rv.setLayoutManager(new LinearLayoutManager(mContext));
        item_rv.setNestedScrollingEnabled(false);
        mList = new ArrayList<>();
        mAdapter = new VideoPopAdapter(mContext,mList);
        mAdapter.setOnClick(pos -> {
            mOnClick.onPopItemOnClick(pos);
            this.dismiss();
        });
        item_rv.setAdapter(mAdapter);
        createPop(view);
    }

    public void setDate(List<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean> list , int position){
        mList.clear();
        mList.addAll(list);
        mAdapter.setPosition(position);
        mAdapter.notifyDataSetChanged();
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
                    this.dismiss();
                }
            }
            return true;
        });
    }

    public void show(View view, List<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean> list , int pos){
        setDate(list , pos);
        int y = (int) mContext.getResources().getDimension(R.dimen.y501);
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
        void onPopItemOnClick(int pos);
    }
}
