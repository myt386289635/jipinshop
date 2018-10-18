package com.example.administrator.jipinshop.fragment.evaluation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.util.DistanceHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class EvaluationFragmentPresenter {

    @Inject
    public EvaluationFragmentPresenter() {
    }


    public void initTabLayout(Context context , final TabLayout tabLayout){
        float totle = 0f;
        final List<Integer> totleSingle = new ArrayList<>();
        final int screenWidth = DistanceHelper.getAndroiodScreenProperty(context);

        for (int i = 0; i < 5; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.tablayout_home,null);
            TextView textView = view.findViewById(R.id.tab_name);
            if(i == 0){
                textView.setText("精选榜");
            }else if(i == 1){
                textView.setText("个护健康");
            }else if(i == 2){
                textView.setText("厨卫电器");
            }else if(i == 3){
                textView.setText("生活家电");
            }else {
                textView.setText("家用大电");
            }
            tabLayout.getTabAt(i).setCustomView(view);
//            TextPaint textPaint = new TextPaint();
//            totle = totle + textPaint.measureText(textView.getText().toString());
//            totleSingle.add((int) textPaint.measureText(textView.getText().toString()));
            int a = (int) textView.getPaint().measureText(textView.getText().toString());
            totle = totle + a;
            totleSingle.add(a);
        }
        tabLayout.setSelectedTabIndicatorColor(context.getResources().getColor(R.color.color_FF3939));
        tabLayout.setTabRippleColor(ColorStateList.valueOf(context.getResources().getColor(R.color.transparent)));
        final float finalTotle = totle;
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
//                try {
//                    //拿到tabLayout的mTabStrip属性
//                    Field mTabStripField = tabLayout.getClass().getDeclaredField("mTabStrip");
//                    mTabStripField.setAccessible(true);
//                    LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabLayout);
//                    int dp10 = DistanceHelper.dip2px((int) (((screenWidth - finalTotle) / 5)));
//                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
//                        View tabView = mTabStrip.getChildAt(i);
//                        //拿到tabView的mTextView属性
//                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
//                        mTextViewField.setAccessible(true);
//                        tabView.setPadding(0, 0, 0, 0);
//                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
//                        int width = 0;
//                        width = totleSingle.get(i) + dp10;
//                        if(i == 0){
//                            //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
//                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
//                            params.width = width ;
//                            params.leftMargin = dp10 /2;
//                            params.rightMargin = dp10 /2- DistanceHelper.dip2px(4);
//                            tabView.setLayoutParams(params);
//                        }else {
//                            //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
//                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
//                            params.width = width ;
//                            params.rightMargin = dp10 /2;
//                            tabView.setLayoutParams(params);
//                        }
//                        tabView.invalidate();
//                    }
//
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
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

    public void setStatusBarHight(LinearLayout StatusBar , Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }
}
