package com.example.administrator.jipinshop.activity.sreach.result;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/13
 * @Describe
 */
public class SreachResultPresenter {

    private Repository mRepository;

    @Inject
    public SreachResultPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initTabLayout(Context context , final TabLayout tabLayout, List<String> tabTitle){
        final List<Integer> textLether = new ArrayList<>();
        for (int i = 0; i < tabTitle.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.tablayout_home, null);
            TextView textView = view.findViewById(R.id.tab_name);
            textView.setText(tabTitle.get(i));
            tabLayout.getTabAt(i).setCustomView(view);
            int a = (int) textView.getPaint().measureText(textView.getText().toString());
            textLether.add(a);
        }
        tabLayout.setSelectedTabIndicatorColor(context.getResources().getColor(R.color.color_FF3939));
        tabLayout.setTabRippleColor(ColorStateList.valueOf(context.getResources().getColor(R.color.transparent)));
        tabLayout.post(() -> {
            //拿到tabLayout的mTabStrip属性
            LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
            int totle = textLether.get(0) + textLether.get(1) + textLether.get(2) + textLether.get(3)+ textLether.get(4);
            int dp10 = (tabLayout.getWidth() - totle) / tabTitle.size();
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
