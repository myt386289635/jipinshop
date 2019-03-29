package com.example.administrator.jipinshop.activity.tryout.detail;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.CommenBannerAdapter;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.TaskFinishBean;
import com.example.administrator.jipinshop.bean.TryApplyBean;
import com.example.administrator.jipinshop.bean.TryDetailBean;
import com.example.administrator.jipinshop.databinding.ActivityTryDetailBinding;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/3/22
 * @Describe
 */
public class TryDetailPresenter {

    private Repository mRepository;
    private TryDetailView mView;

    public void setView(TryDetailView view) {
        mView = view;
    }

    @Inject
    public TryDetailPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initText(Context context , ActivityTryDetailBinding mBinding) {
        mBinding.detailTimeContainer.post(() -> {
            int width = mBinding.detailTimeContainer.getWidth();
            int height = mBinding.detailTimeContainer.getHeight();
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.detailApplyLayout.getLayoutParams();
            layoutParams.width = width / 2;
            layoutParams.height = height / 2 ;
            mBinding.detailApplyLayout.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) mBinding.detailApplying.getLayoutParams();
            layoutParams1.height = height / 2 ;
            layoutParams1.topMargin = height / 2;
            mBinding.detailApplying.setLayoutParams(layoutParams1);
        });
    }

    public void setStatusBarHight(LinearLayout StatusBar,LinearLayout StatusBar2 , LinearLayout StatusBar3 , RelativeLayout relativeLayout, Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;

            ViewGroup.LayoutParams layoutParams2 = StatusBar2.getLayoutParams();
            layoutParams2.height = statusBarHeight;

            ViewGroup.LayoutParams layoutParams3 = StatusBar3.getLayoutParams();
            layoutParams3.height = statusBarHeight;

            relativeLayout.setMinimumHeight(statusBarHeight + (int) context.getResources().getDimension(R.dimen.y80));
        }
    }

    public void initTabLayout(Context context , ActivityTryDetailBinding mBinding) {
        mBinding.detailTab1.tabText.setText("商品详情");
        mBinding.detailTab1.tabText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mBinding.detailTab1.tabText.setTextColor(context.getResources().getColor(R.color.color_DE050505));
        mBinding.detailTab1.tabText.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        mBinding.detailTab1.tabLine.setVisibility(View.VISIBLE);
        mBinding.detailTab2.tabText.setText("申请流程");
        mBinding.detailTab2.tabText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mBinding.detailTab2.tabText.setTextColor(context.getResources().getColor(R.color.color_ACACAC));
        mBinding.detailTab2.tabText.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        mBinding.detailTab2.tabLine.setVisibility(View.GONE);
        mBinding.detailTab3.tabText.setText("试用名单");
        mBinding.detailTab3.tabText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mBinding.detailTab3.tabText.setTextColor(context.getResources().getColor(R.color.color_ACACAC));
        mBinding.detailTab3.tabText.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        mBinding.detailTab3.tabLine.setVisibility(View.GONE);
        mBinding.detailTab4.tabText.setText("试用报告");
        mBinding.detailTab4.tabText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mBinding.detailTab4.tabText.setTextColor(context.getResources().getColor(R.color.color_ACACAC));
        mBinding.detailTab4.tabText.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        mBinding.detailTab4.tabLine.setVisibility(View.GONE);
    }

    public void seleteTab(Context context , int pos , List<TextView> mTabTextView, List<View> mTabLine){
        for (int i = 0; i < mTabTextView.size(); i++) {
            if( i == pos){
                mTabTextView.get(i).setTextColor(context.getResources().getColor(R.color.color_DE050505));
                mTabTextView.get(i).setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                mTabLine.get(i).setVisibility(View.VISIBLE);
            }else {
                mTabTextView.get(i).setTextColor(context.getResources().getColor(R.color.color_ACACAC));
                mTabTextView.get(i).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                mTabLine.get(i).setVisibility(View.GONE);
            }
        }
    }

    public void tryDetail(String trialId,LifecycleTransformer<TryDetailBean> transformer){
        mRepository.tryDetail(trialId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(tryDetailBean -> {
                    if(tryDetailBean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccess(tryDetailBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(tryDetailBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    public void initBanner(List<String> mBannerList , Context context , List<ImageView> point, LinearLayout mDetailPoint, CommenBannerAdapter mBannerAdapter){
        for (int i = 0; i < mBannerList.size(); i++) {
            ImageView imageView = new ImageView(context);

            point.add(imageView);

            if (i == 0) {
                imageView.setImageResource(R.drawable.banner_down);

            } else {
                imageView.setImageResource(R.drawable.banner_up);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            layoutParams.leftMargin =context.getResources().getDimensionPixelSize(R.dimen.x4);
            layoutParams.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.x4);

            mDetailPoint.addView(imageView, layoutParams);
        }
        mBannerAdapter.notifyDataSetChanged();
    }

    public void tryApply(String trialId,LifecycleTransformer<TryApplyBean> transformer){
        mRepository.tryApply(trialId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(tryApplyBean -> {
                    if(tryApplyBean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccessApply(tryApplyBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileApply(tryApplyBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileApply(throwable.getMessage());
                    }
                });
    }

    /**
     * 分享获取极币
     */
    public void taskshareFinish(LifecycleTransformer<TaskFinishBean> transformer){
        mRepository.taskFinish("5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(taskFinishBean -> {

                }, throwable ->{

                });
    }

}
