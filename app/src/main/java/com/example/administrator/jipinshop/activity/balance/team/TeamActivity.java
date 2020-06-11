package com.example.administrator.jipinshop.activity.balance.team;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.adapter.KTTabAdapter4;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.TeacherBean;
import com.example.administrator.jipinshop.bean.TeamBean;
import com.example.administrator.jipinshop.databinding.ActivityTeamBinding;
import com.example.administrator.jipinshop.fragment.mine.team.TeamOneFragment;
import com.example.administrator.jipinshop.fragment.mine.team.three.TeamThreeFragment;
import com.example.administrator.jipinshop.fragment.mine.team.two.TeamTwoFragment;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/6/5
 * @Describe 团队收入
 */
public class TeamActivity extends BaseActivity implements View.OnClickListener, TeamView, ViewPager.OnPageChangeListener, KTTabAdapter4.OnItem {

    @Inject
    TeamPresenter mPresenter;
    private ActivityTeamBinding mBinding;
    //tab
    private List<String> mTitle;
    private KTTabAdapter4 mTabAdapter;
    private List<Fragment> mFragments;
    private HomeFragmentAdapter mAdapter;
    //解决手势冲突
    float startX = 0;
    float startY = 0;
    float xDistance = 0;
    float yDistance = 0;
    //fragment
    private TeamTwoFragment mTwoFragment;
    private TeamThreeFragment mThreeFragment;
    //选择
    private Integer[] upOrDown = {1,1}; //1是向上  2是向下
    private Integer[] select = {0,0};   //选择的位置
    private String[] orderByType ={"1","1"};
    private List<TextView> mTextViews;

    private TeacherBean mTeacherBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_team);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mPresenter.setStatusBarHight(mBinding,this);
        mPresenter.setTitleBlack(this,mBinding.appbar,mBinding);

        //tab
        mTitle = new ArrayList<>();
        mTitle.add("潜在粉丝");
        mTitle.add("直邀粉丝");
        mTitle.add("其他粉丝");
        CommonNavigator commonNavigator = new CommonNavigator(this);
        mTabAdapter = new KTTabAdapter4(mTitle);
        mTabAdapter.setColor(getResources().getColor(R.color.color_DE151515),
                getResources().getColor(R.color.color_E25838));
        mTabAdapter.setTextSize(16f);
        mTabAdapter.setOnClick(this);
        commonNavigator.setAdapter(mTabAdapter);
        mBinding.viewIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer();
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable(){
            @Override
            public int getIntrinsicWidth() {
                return (int) getResources().getDimension(R.dimen.x72);
            }
        });
        ViewPagerHelper.bind(mBinding.viewIndicator, mBinding.viewPager);
        mBinding.teamBottomContainer.setVisibility(View.GONE);
        mBinding.teamNotice.setVisibility(View.GONE);
        mBinding.teamTitleNotice.setVisibility(View.GONE);

        //初始化选择title
        mTextViews = new ArrayList<>();
        mTextViews.add(mBinding.titleTime);
        mTextViews.add(mBinding.titlePeople);
        mTextViews.add(mBinding.titleMoney);
        initOnePage();

        mFragments = new ArrayList<>();
        mFragments.add(TeamOneFragment.getInstance());
        mTwoFragment = TeamTwoFragment.getInstance();
        mFragments.add(mTwoFragment);
        mThreeFragment = TeamThreeFragment.getInstance();
        mFragments.add(mThreeFragment);
        mAdapter = new HomeFragmentAdapter(getSupportFragmentManager());
        mAdapter.setFragments(mFragments);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.viewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mBinding.viewPager.addOnPageChangeListener(this);

        mPresenter.getMyTeamInfo(this.bindToLifecycle());
        mPresenter.getParentInfo(this.bindToLifecycle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.title_time:
                if (select[mBinding.viewPager.getCurrentItem() - 1] == 0){
                    if (upOrDown[mBinding.viewPager.getCurrentItem() - 1] == 1){
                        upOrDown[mBinding.viewPager.getCurrentItem() - 1] = 2;
                        orderByType[mBinding.viewPager.getCurrentItem() - 1] = "2";
                    }else {
                        upOrDown[mBinding.viewPager.getCurrentItem() - 1] = 1;
                        orderByType[mBinding.viewPager.getCurrentItem() - 1] = "1";
                    }
                }else {
                    upOrDown[mBinding.viewPager.getCurrentItem() - 1] = 1;
                    orderByType[mBinding.viewPager.getCurrentItem() - 1] = "1";
                }
                select[mBinding.viewPager.getCurrentItem() - 1] = 0;
                initTitle(select[mBinding.viewPager.getCurrentItem() - 1],true);
                break;
            case R.id.title_people:
                if (select[mBinding.viewPager.getCurrentItem() - 1] == 1){
                    if (upOrDown[mBinding.viewPager.getCurrentItem() - 1] == 1){
                        upOrDown[mBinding.viewPager.getCurrentItem() - 1] = 2;
                        orderByType[mBinding.viewPager.getCurrentItem() - 1] = "4";
                    }else {
                        upOrDown[mBinding.viewPager.getCurrentItem() - 1] = 1;
                        orderByType[mBinding.viewPager.getCurrentItem() - 1] = "3";
                    }
                }else {
                    upOrDown[mBinding.viewPager.getCurrentItem() - 1] = 1;
                    orderByType[mBinding.viewPager.getCurrentItem() - 1] = "3";
                }
                select[mBinding.viewPager.getCurrentItem() - 1] = 1;
                initTitle(select[mBinding.viewPager.getCurrentItem() - 1],true);
                break;
            case R.id.title_money:
                if (select[mBinding.viewPager.getCurrentItem() - 1] == 2){
                    if (upOrDown[mBinding.viewPager.getCurrentItem() - 1] == 1){
                        upOrDown[mBinding.viewPager.getCurrentItem() - 1] = 2;
                        orderByType[mBinding.viewPager.getCurrentItem() - 1] = "6";
                    }else {
                        upOrDown[mBinding.viewPager.getCurrentItem() - 1] = 1;
                        orderByType[mBinding.viewPager.getCurrentItem() - 1] = "5";
                    }
                }else {
                    upOrDown[mBinding.viewPager.getCurrentItem() - 1] = 1;
                    orderByType[mBinding.viewPager.getCurrentItem() - 1] = "5";
                }
                select[mBinding.viewPager.getCurrentItem() - 1] = 2;
                initTitle(select[mBinding.viewPager.getCurrentItem() - 1],true);
                break;
            case R.id.team_bottomContainer:
                if (mBinding.teamRule.getVisibility() == View.VISIBLE){
                    //todo h5
                }else {
                    if (mTeacherBean != null){
                        DialogUtil.userDetailDialog(this,mTeacherBean);
                    }else {
                        ToastUtil.show("导师信息获取失败，请重新尝试");
                    }
                }
                break;
        }
    }

    public AppBarLayout getBar() {
        return mBinding.appbar;
    }

    //有数据（潜在粉丝）
    public void initOnePage(){
        mBinding.teamBottomContainer.setVisibility(View.VISIBLE);
        mBinding.teamRule.setVisibility(View.VISIBLE);
        mBinding.teamTeacherContainer.setVisibility(View.GONE);
        mBinding.teamNotice.setVisibility(View.VISIBLE);
        mBinding.teamTitleNotice.setVisibility(View.GONE);
    }

    //有数据 （专属粉丝和其他粉丝）
    public void initOtherPage(){
        mBinding.teamBottomContainer.setVisibility(View.VISIBLE);
        mBinding.teamRule.setVisibility(View.GONE);
        mBinding.teamTeacherContainer.setVisibility(View.VISIBLE);
        mBinding.teamNotice.setVisibility(View.GONE);
        mBinding.teamTitleNotice.setVisibility(View.VISIBLE);
        initTitle(select[mBinding.viewPager.getCurrentItem() - 1],false);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) { }

    @Override
    public void onPageSelected(int position) {
        if (position == 0){
            initOnePage();
        }else {
            initOtherPage();
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) { }

    //解决AppBarLayout头布局过大与ViewPager手势冲突出现的滑动卡顿问题
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
        }
        final float curX = ev.getX();
        final float curY = ev.getY();

        xDistance += Math.abs(curX - startX);
        yDistance += Math.abs(curY - startY);

        if (xDistance >= yDistance) {
            //横向滑动
            mBinding.viewPager.setNoScroll(false);
        } else {
            //垂直滑动
            mBinding.viewPager.setNoScroll(true);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(int position) {
        mBinding.viewPager.setCurrentItem(position);
    }

    //初始化title
    public void initTitle(int select , boolean isRefresh){
        for (TextView textView : mTextViews) {
            Drawable drawable = getResources().getDrawable(R.mipmap.team_normal);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(null, null, drawable, null);
            textView.setTextColor(getResources().getColor(R.color.color_DE151515));
        }
        Drawable drawable;
        if (upOrDown[mBinding.viewPager.getCurrentItem() - 1] == 1){
            drawable = getResources().getDrawable(R.mipmap.team_up);
        }else {
            drawable = getResources().getDrawable(R.mipmap.team_down);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTextViews.get(select).setCompoundDrawables(null, null, drawable, null);
        mTextViews.get(select).setTextColor(getResources().getColor(R.color.color_E25838));
        if (isRefresh){
            if (mBinding.viewPager.getCurrentItem() == 1){
                mTwoFragment.refresh(orderByType[0]);
            }else if (mBinding.viewPager.getCurrentItem() == 2){
                mThreeFragment.refresh(orderByType[1]);
            }
        }
    }

    @Override
    public void onSuccess(TeamBean bean) {
        mBinding.setDate(bean.getData());
        mBinding.executePendingBindings();
    }

    @Override
    public void onFile(String error) {
        ToastUtil.show(error);
    }

    @Override
    public void onTeacher(TeacherBean bean) {
        mTeacherBean = bean;
    }
}
