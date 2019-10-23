package com.example.administrator.jipinshop.activity.balance;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MyWalletBean;
import com.example.administrator.jipinshop.bean.ScoreStatusBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/3/7
 * @Describe
 */
public class MyWalletPresenter {

    private Repository mRepository;
    private MyWalletView mView;

    public void setView(MyWalletView view) {
        mView = view;
    }

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
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            if (i == 0) {
                textView.setText("收支明细");
            } else {
                textView.setText("提现明细");
            }
            mTabLayout.getTabAt(i).setCustomView(view);
            int a = (int) textView.getPaint().measureText(textView.getText().toString());
            textLether.add(a);
        }
        mTabLayout.setSelectedTabIndicatorColor(context.getResources().getColor(R.color.color_E31436));
        mTabLayout.setTabTextColors(context.getResources().getColor(R.color.color_B7B7B7),context.getResources().getColor(R.color.color_202020));
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

    public void myCommssionSummary(LifecycleTransformer<MyWalletBean> transformer){
        mRepository.myCommssionSummary()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(myWalletBean -> {
                    if (myWalletBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess(myWalletBean);
                        }
                    }else {
                        if (mView != null){
                            mView.onFile(myWalletBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    public void addScore(String content, int score,Boolean scoreFlag,LifecycleTransformer<SuccessBean> transformer){
        mRepository.addScore(content, score)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode() == 0){
                        if (scoreFlag){
                            ToastUtil.show("感谢您的反馈");
                        }
                    }
                }, throwable -> {

                });
    }

    public void getScoreStatus(LifecycleTransformer<ScoreStatusBean> transformer){
        mRepository.getScoreStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(scoreStatusBean -> {
                    if (scoreStatusBean.getCode() == 0){
                        if (mView != null){
                            mView.onScoreSuc(scoreStatusBean);
                        }
                    }
                }, throwable -> {

                });
    }
}
