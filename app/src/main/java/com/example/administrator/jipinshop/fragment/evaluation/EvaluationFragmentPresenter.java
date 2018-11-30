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
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EvaluationFragmentPresenter {

    private Repository mRepository;
    private EvaluationView mView;

    public void setView(EvaluationView view) {
        mView = view;
    }

    @Inject
    public EvaluationFragmentPresenter(Repository repository) {
        this.mRepository = repository;
    }


    public void initTabLayout(Context context , final TabLayout tabLayout,List<EvaluationTabBean.ListBean> tabTitle,List<TextView> tabTextView){
        float totle = 0f;
        final List<Integer> totleSingle = new ArrayList<>();

        if(tabTextView.size() != 0){
            boolean isResher = false;
            for (int i = 0; i < tabTitle.size(); i++) {
                if(!tabTextView.get(i).getText().toString().equals(tabTitle.get(i).getCategoryName())){
                    tabTextView.get(i).setText(tabTitle.get(i).getCategoryName());
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
        }else {
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                View view = LayoutInflater.from(context).inflate(R.layout.tablayout_home,null);
                TextView textView = view.findViewById(R.id.tab_name);
                textView.setText(tabTitle.get(i).getCategoryName());
                tabLayout.getTabAt(i).setCustomView(view);
                tabTextView.add(textView);
                int a = (int) textView.getPaint().measureText(textView.getText().toString());
                totle = totle + a;
                totleSingle.add(a);
            }
        }

        tabLayout.setSelectedTabIndicatorColor(context.getResources().getColor(R.color.color_FF3939));
        tabLayout.setTabRippleColor(ColorStateList.valueOf(context.getResources().getColor(R.color.transparent)));
        final float finalTotle = totle;
        tabLayout.post(() -> {
            int dp10 = (int) (((tabLayout.getWidth() - finalTotle) /tabLayout.getTabCount()));
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

    public void initTab(LifecycleTransformer<EvaluationTabBean> transformer){
        mRepository.evaTab()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(evaluationTabBean -> {
                    if(evaluationTabBean.getCode() == 200){
                        if(mView != null){
                            mView.onSucTab(evaluationTabBean);
                        }
                    }else {
                        if(mView != null){
                            mView.onFaile(evaluationTabBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.onFaile(throwable.getMessage());
                    }
                });
    }
}
