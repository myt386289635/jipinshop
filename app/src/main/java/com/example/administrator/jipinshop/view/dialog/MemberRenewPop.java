package com.example.administrator.jipinshop.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.MemberMoreAdapter;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.databinding.PopMemberRenewBinding;
import com.example.administrator.jipinshop.util.DistanceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/8/27
 * @Describe 会员续费弹窗
 */
public class MemberRenewPop extends PopupWindow {

    private PopMemberRenewBinding mBinding;
    private Context mContext;
    private OnClick mOnClick;
    private String type = "1";//1是支付宝 2是微信
    private String level = "2";//1是月卡 2是年卡
    private List<MemberNewBean.DataBean.VipBoxListBean> mList;
    private MemberMoreAdapter mMoreAdapter;
    private List<MemberNewBean.DataBean.VipBoxListBean> yearList;
    private List<MemberNewBean.DataBean.VipBoxListBean> monthList;
    private String monthPrice = "";
    private String yearPrice = "";
    private String yearEndTime = "";
    private String monthEndTime = "";

    public MemberRenewPop(Context context, OnClick onClick) {
        mContext = context;
        mOnClick = onClick;
        createView();
    }

    private void createView() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.pop_member_renew,null,false);
        mList = new ArrayList<>();
        yearList = new ArrayList<>();
        monthList = new ArrayList<>();
        mMoreAdapter = new MemberMoreAdapter(mList,mContext,"2");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL,false);
        mBinding.popMore.setLayoutManager(linearLayoutManager);
        mBinding.popMore.setNestedScrollingEnabled(false);
        mBinding.popMore.setAdapter(mMoreAdapter);
        mBinding.popMore.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //划出去的宽度
                int isResult = getResult(linearLayoutManager);
                //可划出去的总宽度
                double totleWith = getTotleWith(linearLayoutManager);
                //线条可划出去的总宽度
                double lineWith = mContext.getResources().getDimension(R.dimen.x60);
                //结果
                double result  = (lineWith / totleWith) * isResult;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.popPoint.getLayoutParams();
                layoutParams.leftMargin = (int) result;
                mBinding.popPoint.setLayoutParams(layoutParams);
            }
        });
        mBinding.popAlipay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mBinding.popWxpay.setChecked(!isChecked);
        });
        mBinding.popWxpay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mBinding.popAlipay.setChecked(!isChecked);
        });
        mBinding.popYearCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mBinding.popMonthCheckBox.setChecked(!isChecked);
            if (isChecked){
                mBinding.popYearContainer.setBackgroundResource(R.drawable.bg_member_buy);
                mBinding.popPay.setText("立即以"+yearPrice+"元续费");
                mBinding.popRenew.setText("续费成功后会员延续至" + yearEndTime);
                mList.clear();
                mList.addAll(yearList);
                if (mList.size() > 3){
                    mBinding.popPointContainer.setVisibility(View.VISIBLE);
                }else{
                    mBinding.popPointContainer.setVisibility(View.GONE);
                }
                mMoreAdapter.notifyDataSetChanged();
            }else {
                mBinding.popYearContainer.setBackgroundResource(R.drawable.bg_member_nobuy);
            }
        });
        mBinding.popMonthCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mBinding.popYearCheckBox.setChecked(!isChecked);
            if (isChecked){
                mBinding.popMonthContainer.setBackgroundResource(R.drawable.bg_member_buy);
                mBinding.popPay.setText("立即以"+monthPrice+"元续费");
                mBinding.popRenew.setText("续费成功后会员延续至" + monthEndTime);
                mList.clear();
                mList.addAll(monthList);
                if (mList.size() > 3){
                    mBinding.popPointContainer.setVisibility(View.VISIBLE);
                }else{
                    mBinding.popPointContainer.setVisibility(View.GONE);
                }
                mMoreAdapter.notifyDataSetChanged();
            }else {
                mBinding.popMonthContainer.setBackgroundResource(R.drawable.bg_member_nobuy);
            }
        });
        mBinding.popPay.setOnClickListener(v -> {
            if (mBinding.popYearCheckBox.isChecked()){
                level = "2";
            }else if (mBinding.popMonthCheckBox.isChecked()){
                level = "1";
            }
            if (mBinding.popAlipay.isChecked()){
                type = "1";
            }else if (mBinding.popWxpay.isChecked()){
                type = "2";
            }
            mOnClick.onBuyMember(level,type);
            this.dismiss();
        });
        createPop(mBinding.getRoot());
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
            int height = mBinding.popupLayout.getBottom();
            int left = mBinding.popupLayout.getLeft();
            int right = mBinding.popupLayout.getRight();
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
        mBinding.popDismiss.setOnClickListener(v -> {
            mOnClick.onDismiss();
            this.dismiss();
        });
    }

    private int getResult(LinearLayoutManager linearLayoutManager){
        //找到即将移出屏幕Item的position,position是移出屏幕item的数量
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        //根据position找到这个Item
        View firstVisiableChildView = linearLayoutManager.findViewByPosition(position);
        //获取Item的宽
        int itemWidth = 0;
        //算出该Item还未移出屏幕的高度
        int itemRight = 0;
        if (firstVisiableChildView != null){
            itemWidth = firstVisiableChildView.getWidth();
            itemRight = firstVisiableChildView.getRight();
        }
        //position移出屏幕的数量*高度得出移动的距离
        int iposition = position * itemWidth;
        //因为横着的RecyclerV第一个取到的Item position为零所以计算时需要加一个宽
        int iResult = iposition - itemRight + itemWidth;
        return  iResult;
    }

    private double getTotleWith(LinearLayoutManager linearLayoutManager){
        //找到即将移出屏幕Item的position,position是移出屏幕item的数量
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        //根据position找到这个Item
        View firstVisiableChildView = linearLayoutManager.findViewByPosition(position);
        //获取Item的宽
        int itemWidth = 0;
        if (firstVisiableChildView != null){
            itemWidth = firstVisiableChildView.getWidth();
        }
        double zWidth = DistanceHelper.getAndroiodScreenwidthPixels(mContext);
        return ((itemWidth * mList.size()) - zWidth);
    }

    public void setDate(String monthPrice , String monthPriceBefore ,
                        String yearPrice , String yearPriceBefore ,
                        String yearEndTime, String monthEndTime,
                        List<MemberNewBean.DataBean.VipBoxListBean> yearList,
                        List<MemberNewBean.DataBean.VipBoxListBean> monthList) {
        mBinding.popYearPrice.setText(yearPrice);
        mBinding.popYearOtherPrice.setText("原价￥" + yearPriceBefore);
        mBinding.popMonthPrice.setText(monthPrice);
        mBinding.popMonthOtherPrice.setText("原价￥" + monthPriceBefore);
        this.yearList.clear();
        this.yearList.addAll(yearList);
        this.monthList.clear();
        this.monthList.addAll(monthList);
        type = "1";//1是支付宝 2是微信
        level = "2";//1是月卡 2是年卡
        this.monthPrice = monthPrice;
        this.yearPrice = yearPrice;
        this.yearEndTime = yearEndTime;
        this.monthEndTime = monthEndTime;
        mBinding.popAlipay.setChecked(true);
        mBinding.popYearCheckBox.setChecked(true);
    }

    public void show(View view, String monthPrice , String monthPriceBefore ,
                     String yearPrice , String yearPriceBefore ,
                     String yearEndTime, String monthEndTime,
                     List<MemberNewBean.DataBean.VipBoxListBean> yearList,
                     List<MemberNewBean.DataBean.VipBoxListBean> monthList){
        setDate(monthPrice, monthPriceBefore, yearPrice, yearPriceBefore, yearEndTime, monthEndTime, yearList, monthList);
        int y = (int) mContext.getResources().getDimension(R.dimen.y826);
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
