package com.example.administrator.jipinshop.fragment.home.commen;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.HomeCommenBean;
import com.example.administrator.jipinshop.bean.OrderbyTypeBean;
import com.example.administrator.jipinshop.databinding.FragmentHomeCommenBinding;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/1/5
 * @Describe
 */
public class HomeCommenPresenter {

    private Repository mRepository;
    private HomeCommenView mView;

    public void setView(HomeCommenView view) {
        mView = view;
    }

    @Inject
    public HomeCommenPresenter(Repository repository) {
        mRepository = repository;
    }

    //解决冲突问题
    public void solveScoll(Context context ,RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad,
                           AppBarLayout appBarLayout,Boolean[] once,FragmentHomeCommenBinding mBinding,List<View> mTabLine,
                           int[] set){
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if(once[0]){
                mSwipeToLoad.setRefreshEnabled(true);
            }else {
                if(verticalOffset == 0){
                    //展开
                    mSwipeToLoad.setRefreshEnabled(true);
                }else if(Math.abs(verticalOffset) >= appBarLayout1.getTotalScrollRange()){
                    //折叠
                    mSwipeToLoad.setRefreshEnabled(false);
                    mBinding.didver.setVisibility(View.GONE);
                    mBinding.tabLayout.setBackgroundColor(context.getResources().getColor(R.color.color_F5F5F5));
                    mTabLine.get(set[0]).setVisibility(View.GONE);
                }else {
                    //过程
                    mSwipeToLoad.setRefreshEnabled(false);
                    mBinding.didver.setVisibility(View.VISIBLE);
                    mBinding.tabLayout.setBackgroundColor(context.getResources().getColor(R.color.color_white));
                    mTabLine.get(set[0]).setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                mSwipeToLoad.setRefreshEnabled(linearManager.findFirstCompletelyVisibleItemPosition() == 0);
                mSwipeToLoad.setLoadMoreEnabled(isSlideToBottom(mRecyclerView));
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    /**
     * 判断RecyclerView是否滑动到底部
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    public void goodRank(String category2Id,String orderbyType,LifecycleTransformer<HomeCommenBean> transformer){
        Map<String,String> param = new HashMap<>();
        param.put("category2Id",category2Id);
        param.put("orderbyType",orderbyType);
        param.put("client","1");
        mRepository.goodRank(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(commenBean -> {
                    if(commenBean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccess(commenBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFile(commenBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    public void initBanner(List<Fragment> mBannerList , Context context , List<ImageView> point, LinearLayout mDetailPoint){
        for (int i = 0; i < mBannerList.size(); i++) {
            ImageView imageView = new ImageView(context);

            point.add(imageView);

            if (i == 0) {
                imageView.setImageResource(R.drawable.banner_down2);

            } else {
                imageView.setImageResource(R.drawable.banner_up2);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            layoutParams.leftMargin =context.getResources().getDimensionPixelSize(R.dimen.x4);
            layoutParams.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.x4);

            mDetailPoint.addView(imageView, layoutParams);
        }
    }

    public void initTabLayout(Context context , FragmentHomeCommenBinding mBinding) {
        mBinding.tab1.tabText.setText("热卖榜");
        mBinding.tab1.tabText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mBinding.tab1.tabText.setTextColor(context.getResources().getColor(R.color.color_E31436));
        mBinding.tab1.tabLine.setVisibility(View.VISIBLE);
        mBinding.tab2.tabText.setText("轻奢榜");
        mBinding.tab2.tabText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        mBinding.tab2.tabText.setTextColor(context.getResources().getColor(R.color.color_ACACAC));
        mBinding.tab2.tabLine.setVisibility(View.GONE);
        mBinding.tab3.tabText.setText("新品榜");
        mBinding.tab3.tabText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        mBinding.tab3.tabText.setTextColor(context.getResources().getColor(R.color.color_ACACAC));
        mBinding.tab3.tabLine.setVisibility(View.GONE);
        mBinding.tab4.tabText.setText("性价比榜");
        mBinding.tab4.tabText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        mBinding.tab4.tabText.setTextColor(context.getResources().getColor(R.color.color_ACACAC));
        mBinding.tab4.tabLine.setVisibility(View.GONE);
    }

    public void seleteTab(Context context , int pos ,List<TextView> mTabTextView,List<View> mTabLine){
        for (int i = 0; i < mTabTextView.size(); i++) {
            if( i == pos){
                mTabTextView.get(i).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                mTabTextView.get(i).setTextColor(context.getResources().getColor(R.color.color_E31436));
                mTabLine.get(i).setVisibility(View.VISIBLE);
            }else {
                mTabTextView.get(i).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                mTabTextView.get(i).setTextColor(context.getResources().getColor(R.color.color_ACACAC));
                mTabLine.get(i).setVisibility(View.GONE);
            }
        }
    }

    public void orderbyTypeList(LifecycleTransformer<OrderbyTypeBean> transformer){
        mRepository.orderbyTypeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(orderbyTypeBean -> {
                    if(orderbyTypeBean.getCode() == 0){
                        if(mView != null){
                            mView.onSuccessTab(orderbyTypeBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFileTab("网络请求错误：" + orderbyTypeBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFileTab("网络请求错误：" + throwable.getMessage());
                    }
                });
    }
}
