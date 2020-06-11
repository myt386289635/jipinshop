package com.example.administrator.jipinshop.activity.balance.team;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.graphics.ColorUtils;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.TeacherBean;
import com.example.administrator.jipinshop.bean.TeamBean;
import com.example.administrator.jipinshop.databinding.ActivityTeamBinding;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/6/5
 * @Describe
 */
public class TeamPresenter {

    private Repository mRepository;
    private TeamView mView;

    public void setView(TeamView view) {
        mView = view;
    }

    @Inject
    public TeamPresenter(Repository repository) {
        mRepository = repository;
    }

    public void setStatusBarHight(ActivityTeamBinding mBinding , Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = mBinding.statusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
            ViewGroup.LayoutParams lp = mBinding.toolBar.getLayoutParams();
            lp.height = (int) (context.getResources().getDimension(R.dimen.y88) + statusBarHeight);
        }
    }

    public void setTitleBlack(Context context,AppBarLayout appBarLayout, ActivityTeamBinding mBinding){
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if(verticalOffset == 0){
                //展开
                mBinding.titleBack.setColorFilter(Color.WHITE);
                mBinding.titleContainer.setBackgroundColor(Color.TRANSPARENT);
                mBinding.statusBar.setBackgroundColor(Color.TRANSPARENT);
                mBinding.titleTv.setTextColor(Color.TRANSPARENT);
            }else if(Math.abs(verticalOffset) >= appBarLayout1.getTotalScrollRange()){
                //折叠
                mBinding.titleBack.setColorFilter(Color.BLACK);
                mBinding.titleContainer.setBackgroundColor(Color.WHITE);
                mBinding.statusBar.setBackgroundColor(Color.WHITE);
                mBinding.titleTv.setTextColor(context.getResources().getColor(R.color.color_202020));
            }else {
                //过程
                float a = Math.abs(verticalOffset);
                float b = a / 1000;
                float max = (float) Math.min(1, b * 2);
                int blendCorlor = ColorUtils.blendARGB(Color.WHITE, Color.BLACK, max);
                mBinding.titleBack.setColorFilter(blendCorlor);
                //标题背景颜色设置
                int bgColor = ColorUtils.blendARGB(Color.TRANSPARENT, Color.WHITE, max);
                mBinding.titleContainer.setBackgroundColor(bgColor);
                mBinding.statusBar.setBackgroundColor(bgColor);
                //标题颜色设置
                int tvColor = ColorUtils.blendARGB(Color.TRANSPARENT, context.getResources().getColor(R.color.color_202020), max);
                mBinding.titleTv.setTextColor(tvColor);
            }
        });
    }

    public void getMyTeamInfo(LifecycleTransformer<TeamBean> transformer){
        mRepository.getMyTeamInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(teamBean -> {
                    if (teamBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess(teamBean);
                        }
                    }else {
                        if (mView != null){
                            mView.onFile(teamBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }

    public void getParentInfo(LifecycleTransformer<TeacherBean> transformer){
        mRepository.parentInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(teacherBean -> {
                    if (teacherBean.getCode() == 0) {
                        mView.onTeacher(teacherBean);
                    }else {
                        ToastUtil.show("导师信息获取失败，请重新尝试");
                    }
                }, throwable -> {
                    ToastUtil.show("导师信息获取失败，请重新尝试");
                });
    }
}
