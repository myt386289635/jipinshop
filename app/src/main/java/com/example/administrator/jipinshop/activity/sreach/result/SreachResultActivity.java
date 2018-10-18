package com.example.administrator.jipinshop.activity.sreach.result;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.SreachResultAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivitySreachResultBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/10
 * @Describe 搜索结果页
 */
public class SreachResultActivity extends BaseActivity implements SreachResultAdapter.OnItem, SreachResultView, TextWatcher, View.OnClickListener {

    @Inject
    SreachResultPresenter mPresenter;

    private List<ImageView> FlexHistroy = new ArrayList<>();
    private SreachResultAdapter mAdapter;
    private List<String> mList;

    private Boolean tag = true;//标示是显示界面一，还是界面2
    private ActivitySreachResultBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sreach_result);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mBinding.sreachClose.setVisibility(View.VISIBLE);
        mBinding.sreachEdit.setText("asdasd");
        mBinding.sreachEdit.setSelection(mBinding.sreachEdit.getText().length());//将光标移至文字末尾
        mBinding.sreachCancle.setVisibility(View.GONE);
        mBinding.sreachBack.setVisibility(View.VISIBLE);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new SreachResultAdapter(mList, this);
        mAdapter.setOnItem(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.setView(this);
        mPresenter.initHistroy(this, mBinding.searchFlexHistroy, FlexHistroy);
        mPresenter.initHot(this, mBinding.searchFlexHot);
        mBinding.sreachEdit.addTextChangedListener(this);
    }

    /**
     * 点击图片进行跳转到商品详情页
     *
     * @param pos
     */
    @Override
    public void onItem(int pos) {
        startActivity(new Intent(this, ShoppingDetailActivity.class));
    }

    /**
     * 刷新商品
     */
    @Override
    public void onResher() {
        tag = false;
        mBinding.sreachHome.setVisibility(View.GONE);
        mBinding.recyclerView.setVisibility(View.VISIBLE);
        mBinding.sreachCancle.setVisibility(View.GONE);
        mBinding.sreachBack.setVisibility(View.VISIBLE);
        mBinding.listView.setVisibility(View.GONE);
        mBinding.sreachClose.setVisibility(View.VISIBLE);
        mBinding.sreachEdit.setText("asdasd");
        mBinding.sreachEdit.setSelection(mBinding.sreachEdit.getText().length());//将光标移至文字末尾
        tag = true;//为了避免手动修改eidtText造成没有效果的问题
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(mBinding.sreachEdit.getText().toString().trim())) {
            if(tag){
                mBinding.sreachHome.setVisibility(View.VISIBLE);
                mBinding.listView.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.GONE);
                mBinding.sreachClose.setVisibility(View.GONE);
                mBinding.sreachCancle.setVisibility(View.VISIBLE);
                mBinding.sreachBack.setVisibility(View.GONE);
            }
        } else {
            if(tag){
                mBinding.sreachHome.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.GONE);
                mBinding.listView.setVisibility(View.VISIBLE);
                mBinding.sreachClose.setVisibility(View.VISIBLE);
                mBinding.sreachCancle.setVisibility(View.VISIBLE);
                mBinding.sreachBack.setVisibility(View.GONE);
            }
        }
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
            case R.id.sreach_cancle:
                tag = false;
                mBinding.sreachHome.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mBinding.sreachCancle.setVisibility(View.GONE);
                mBinding.sreachBack.setVisibility(View.VISIBLE);
                mBinding.listView.setVisibility(View.GONE);
                mBinding.sreachClose.setVisibility(View.VISIBLE);
                mBinding.sreachEdit.setText("asdasd");
                mBinding.sreachEdit.setSelection(mBinding.sreachEdit.getText().length());//将光标移至文字末尾
                tag = true;
                break;
            case R.id.sreach_close:
                tag = true;
                mBinding.sreachEdit.setText("");
                break;
        }
    }
}
