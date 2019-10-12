package com.example.administrator.jipinshop.activity.sreach.result;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivitySreachResultBinding;
import com.example.administrator.jipinshop.fragment.sreach.article.SreachArticleFragment;
import com.example.administrator.jipinshop.fragment.sreach.find.SreachFindFragment;
import com.example.administrator.jipinshop.fragment.sreach.goods.SreachGoodsFragment;
import com.example.administrator.jipinshop.fragment.sreach.tryout.SreachTryFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/10
 * @Describe 搜索结果页
 */
public class SreachResultActivity extends BaseActivity implements View.OnClickListener {

    @Inject
    SreachResultPresenter mPresenter;
    private ActivitySreachResultBinding mBinding;
    private String content = "";

    private List<Fragment> mFragments;
    private HomeAdapter mAdapter;
    float startX = 0;
    float startY = 0;
    float xDistance = 0;
    float yDistance = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sreach_result);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        content = getIntent().getStringExtra("content");
        mBinding.sreachEdit.setText(content);
        mBinding.sreachEdit.setFocusable(false);

        mFragments = new ArrayList<>();
        mFragments.add(SreachGoodsFragment.getInstance(content));
        mFragments.add(SreachFindFragment.getInstance(content,"2"));//清单
        mFragments.add(SreachArticleFragment.getInstance(content));//问答
        mFragments.add(SreachFindFragment.getInstance(content,"4"));//评测
//        mFragments.add(SreachTryFragment.getInstance(content,"5"));//试用报告
        mAdapter = new HomeAdapter(getSupportFragmentManager());
        mAdapter.setFragments(mFragments);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mBinding.viewPager.setOffscreenPageLimit(4);

        List<String> tabTitle = new ArrayList<>();
        tabTitle.add("榜单");
        tabTitle.add("清单");
        tabTitle.add("问答");
        tabTitle.add("评测");
//        tabTitle.add("试用报告");
        mPresenter.initTabLayout(this,mBinding.tabLayout,tabTitle);
    }


    /**
     * 解决AppBarLayout头布局过大与ViewPager手势冲突出现的滑动卡顿问题
     */
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sreach_back:
                finish();
                break;
            case R.id.sreach_edit:
            case R.id.sreach_container:
                setResult(301);
                finish();
                break;
        }
    }

    public AppBarLayout getBar(){
        return mBinding.appbar;
    }
}
