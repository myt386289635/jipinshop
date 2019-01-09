package com.example.administrator.jipinshop.activity.sreach.result;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivitySreachResultBinding;
import com.example.administrator.jipinshop.fragment.sreach.article.SreachArticleFragment;
import com.example.administrator.jipinshop.fragment.sreach.find.SreachFindFragment;
import com.example.administrator.jipinshop.fragment.sreach.goods.SreachGoodsFragment;

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
        mFragments.add(SreachFindFragment.getInstance(content));
        mFragments.add(SreachArticleFragment.getInstance(content,"3"));//评测
        mFragments.add(SreachArticleFragment.getInstance(content,"4"));//试用报告
        mAdapter = new HomeAdapter(getSupportFragmentManager());
        mAdapter.setFragments(mFragments);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mBinding.viewPager.setOffscreenPageLimit(3);

        List<String> tabTitle = new ArrayList<>();
        tabTitle.add("商品");
        tabTitle.add("发现");
        tabTitle.add("评测");
        tabTitle.add("试用报告");
        mPresenter.initTabLayout(this,mBinding.tabLayout,tabTitle);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                showKeyboard(false);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sreach_back:
                finish();
                break;
            case R.id.sreach_close:
                setResult(302);
                mBinding.sreachEdit.setText("");
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
