package com.example.administrator.jipinshop.activity.balance;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/3/7
 * @Describe
 */
public class MyWalletPresenter {

    private Repository mRepository;

    @Inject
    public MyWalletPresenter(Repository repository) {
        mRepository = repository;
    }

    public void setStatusBarHight(LinearLayout StatusBar , Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }

    public void setStatusBarHight(LinearLayout StatusBar , RelativeLayout relativeLayout, Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;

            relativeLayout.setMinimumHeight(statusBarHeight + (int) context.getResources().getDimension(R.dimen.y80));
        }
    }

    //title透明度的设计
    public void initTitleLayout(LinearLayout StatusBar , AppBarLayout appBarLayout, RelativeLayout relativeLayout , TextView textView){
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if(verticalOffset == 0){
                //展开
                textView.setVisibility(View.INVISIBLE);
                relativeLayout.getBackground().setAlpha(0);
                StatusBar.getBackground().setAlpha(0);
            }else if(Math.abs(verticalOffset) >= appBarLayout1.getTotalScrollRange()){
                //折叠
                textView.setVisibility(View.VISIBLE);
                relativeLayout.getBackground().setAlpha(255);
                StatusBar.getBackground().setAlpha(255);
            }else {
                //过程
                textView.setVisibility(View.VISIBLE);
                float a = Math.abs(verticalOffset);
                float max = (float) Math.min(255, a);
                relativeLayout.getBackground().setAlpha((int) max);
                StatusBar.getBackground().setAlpha((int) max);
                int c = appBarLayout1.getTotalScrollRange();
                float min = (float) Math.min(1, a/c);
                textView.setAlpha(min);
            }
        });
    }

    public void initTabLayout(Context context ,TabLayout mTabLayout) {
        final List<Integer> textLether = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.tablayout_home, null);
            TextView textView = view.findViewById(R.id.tab_name);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            if (i == 0) {
                textView.setText("收支明细");
            } else {
                textView.setText("提现明细");
            }
            mTabLayout.getTabAt(i).setCustomView(view);
            int a = (int) textView.getPaint().measureText(textView.getText().toString());
            textLether.add(a);
        }
        mTabLayout.setSelectedTabIndicatorColor(context.getResources().getColor(R.color.color_FF3939));
        mTabLayout.setTabTextColors(context.getResources().getColor(R.color.color_ACACAC),context.getResources().getColor(R.color.color_DE151515));
        mTabLayout.setTabRippleColor(ColorStateList.valueOf(context.getResources().getColor(R.color.transparent)));
        mTabLayout.post(() -> {
            //拿到tabLayout的mTabStrip属性
            LinearLayout mTabStrip = (LinearLayout) mTabLayout.getChildAt(0);
            int totle = textLether.get(0) + textLether.get(1);
            int dp10 = (mTabLayout.getWidth() - totle) / 2;
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
}
