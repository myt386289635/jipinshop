package com.example.administrator.jipinshop.activity.tryout.freedetail;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.CommenBannerAdapter;
import com.example.administrator.jipinshop.bean.FreeDetailBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.DistanceHelper;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/6/21
 * @Describe
 */
public class FreeDetailPresenter {

    private Repository mRepository;
    private FreeDetailView mView;

    public void setView(FreeDetailView view) {
        mView = view;
    }

    @Inject
    public FreeDetailPresenter(Repository repository) {
        mRepository = repository;
    }

    public void init(Context context ,RelativeLayout relativeLayout, LinearLayout statusBar,LinearLayout statusBar1,RelativeLayout titleContainer){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
            titleContainer.setMinimumHeight(statusBarHeight);
            ViewGroup.LayoutParams layoutParams1 = statusBar1.getLayoutParams();
            layoutParams1.height = statusBarHeight;
        }

        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        layoutParams.height = DistanceHelper.getAndroiodScreenwidthPixels(context);
    }

    public void setTitle(AppBarLayout appBarLayout,ImageView view,LinearLayout statusBar){
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if(verticalOffset == 0){
                //展开
                if (statusBar.getVisibility() != View.INVISIBLE){
                    statusBar.setVisibility(View.INVISIBLE);
                    view.setVisibility(View.GONE);
                }
            }else if(Math.abs(verticalOffset) >= appBarLayout1.getTotalScrollRange()){
                //折叠
                if (statusBar.getVisibility() != View.VISIBLE){
                    statusBar.setVisibility(View.VISIBLE);
                    view.setVisibility(View.VISIBLE);
                }
            }else {
                //过程
                if (statusBar.getVisibility() != View.INVISIBLE){
                    statusBar.setVisibility(View.INVISIBLE);
                    view.setVisibility(View.GONE);
                }
            }
        });
    }

    public void getDate(String freeId, LifecycleTransformer<FreeDetailBean> transformer){
        mRepository.freeDetail(freeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(freeDetailBean -> {
                    if (freeDetailBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess(freeDetailBean);
                        }
                    }else {
                        if (mView != null){
                            mView.onFile(freeDetailBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
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

    public void initTabLayout(Context context, List<Fragment> mFragments, TabLayout mTabLayout) {
        final List<Integer> textLether = new ArrayList<>();
        for (int i = 0; i < mFragments.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.tablayout_home, null);
            TextView textView = view.findViewById(R.id.tab_name);
            if (i == 0) {
                textView.setText("商品介绍");
            } else if (i == 1){
                textView.setText("参与名单");
            }else if (i == 2){
                textView.setText("规则说明");
            }
            mTabLayout.getTabAt(i).setCustomView(view);
            int a = (int) textView.getPaint().measureText(textView.getText().toString());
            textLether.add(a);
        }
        mTabLayout.setSelectedTabIndicatorColor(context.getResources().getColor(R.color.color_FF3939));
        mTabLayout.setTabRippleColor(ColorStateList.valueOf(context.getResources().getColor(R.color.transparent)));
        mTabLayout.post(() -> {
            //拿到tabLayout的mTabStrip属性
            LinearLayout mTabStrip = (LinearLayout) mTabLayout.getChildAt(0);
            int totle = textLether.get(0) + textLether.get(1) + textLether.get(2);
            int dp10 = (mTabLayout.getWidth() - totle) / 3;
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);
                tabView.setPadding(0, 0, 0, 0);
                int width = textLether.get(i) + dp10;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                        tabView.getLayoutParams();
                params.width = width;
                params.leftMargin = dp10  / 2;
                params.rightMargin = dp10  / 2;
                tabView.setLayoutParams(params);
                tabView.invalidate();
            }
        });
    }

    /**
     * 获取专属淘宝购买链接地址
     */
    public void goodsBuyLink(String goodsId, LifecycleTransformer<ImageBean> transformer){
        mRepository.goodsBuyLink(goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(imageBean -> {
                    if (imageBean.getCode() == 0){
                        if (mView != null){
                            mView.onBuyLinkSuccess(imageBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(imageBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    /**
     * 申请参加
     */
    public void freeApply(String freeId , LifecycleTransformer<FreeDetailBean> transformer){
        mRepository.freeApply(freeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode() == 0){
                        if (mView != null){
                            mView.onApplySuccess(successBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });

    }
}
