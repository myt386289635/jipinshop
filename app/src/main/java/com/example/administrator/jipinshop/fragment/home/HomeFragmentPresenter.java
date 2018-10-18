package com.example.administrator.jipinshop.fragment.home;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.TabLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class HomeFragmentPresenter{

    private Repository mRepository;
    private HomeFragmentView mView;

    public void setView(HomeFragmentView view) {
        mView = view;
    }

    @Inject
    public HomeFragmentPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initTabLayout(Context context , final TabLayout tabLayout, List<String> tabList, List<TextView> tabTextView){
        float totle = 0f;
        final List<Integer> totleSingle = new ArrayList<>();

        //刷新
        if(tabTextView.size() != 0){
            boolean isResher = false;
            for (int i = 0; i < tabList.size(); i++) {
                if(!tabTextView.get(i).getText().toString().equals(tabList.get(i))){
                    tabTextView.get(i).setText(tabList.get(i));
                    isResher = true;
                }
                int a = (int) tabTextView.get(i).getPaint().measureText(tabTextView.get(i).getText().toString());
                totle = totle + a;
                totleSingle.add(a);
            }
            //不刷新
            if(!isResher){
                return;
            }
            final float finalTotle = totle;
            tabLayout.post(new Runnable() {
                @Override
                public void run() {
                    int dp10 = (int) (((tabLayout.getWidth() - finalTotle) /5));
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        tabView.setPadding(0, 0, 0, 0);
                        int width = totleSingle.get(i) + dp10;
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                                tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp10 / 2;
                        params.rightMargin = dp10 / 2 ;
                        tabView.setLayoutParams(params);
                        tabView.invalidate();
                    }
                }
            });
            return;
        }

        //第一次添加
        for (int i = 0; i < tabList.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.tablayout_home,null);
            TextView textView = view.findViewById(R.id.tab_name);
            textView.setText(tabList.get(i));
            tabLayout.getTabAt(i).setCustomView(view);
            tabTextView.add(textView);
            int a = (int) textView.getPaint().measureText(textView.getText().toString());
            totle = totle + a;
            totleSingle.add(a);
        }
        tabLayout.setSelectedTabIndicatorColor(context.getResources().getColor(R.color.color_FF3939));
//        tabLayout.setTabTextColors(context.getResources().getColor(R.color.color_ACACAC),context.getResources().getColor(R.color.color_050505));
        //水波纹颜色
        tabLayout.setTabRippleColor(ColorStateList.valueOf(context.getResources().getColor(R.color.transparent)));
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        final float finalTotle = totle;
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                    int dp10 = (int) (((tabLayout.getWidth() - finalTotle) /5));
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        tabView.setPadding(0, 0, 0, 0);
                        int width = totleSingle.get(i) + dp10;
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                                tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp10 / 2;
                        params.rightMargin = dp10 / 2 ;
                        tabView.setLayoutParams(params);
                        tabView.invalidate();
                    }
            }
        });

    }

    public void initBadgeView(QBadgeView mQBadgeView, ImageView imageView, Badge.OnDragStateChangedListener onDragStateChangedListener){
        mQBadgeView.bindTarget(imageView)
                .setBadgeTextSize(8,true)
                .setBadgeGravity( Gravity.END | Gravity.TOP)
                .setBadgePadding(2,true)
                .setGravityOffset(11,11,true)
                .setOnDragStateChangedListener(onDragStateChangedListener);
    }

    public void setStatusBarHight(LinearLayout StatusBar ,Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }

    /**
     * 榜单tab的字段
     */
    public void goodsCategory(LifecycleTransformer<TabBean> ransformer){
       mRepository.goodsCategory()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .compose(ransformer)
               .subscribe(tabBean -> {
                   if (tabBean.getCode() == 200){
                       if(mView != null){
                           mView.Success(tabBean);
                       }
                   }else {
                       if(mView != null){
                           mView.Faile(tabBean.getMsg());
                       }
                   }
               }, throwable -> {
                   if(mView != null){
                       mView.Faile(throwable.getMessage());
                   }
               });
    }
}
