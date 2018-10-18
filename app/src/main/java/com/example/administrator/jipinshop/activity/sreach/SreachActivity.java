package com.example.administrator.jipinshop.activity.sreach;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.sreach.result.SreachResultActivity;
import com.example.administrator.jipinshop.adapter.SreachAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivitySreachBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SreachActivity extends BaseActivity implements TextWatcher, SreachAdapter.OnItem, SreachView, View.OnClickListener {

    @Inject
    SreachPresenter mPresenter;

    private List<ImageView> FlexHistroy = new ArrayList<>();
    private List<String> mList;
    private SreachAdapter mAdapter;
    private ActivitySreachBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sreach);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mPresenter.setView(this);
        showKeyboardDelayed(mBinding.sreachEdit);
        mPresenter.initHistroy(this, mBinding.searchFlexHistroy, FlexHistroy);
        mPresenter.initHot(this, mBinding.searchFlexHot);
        mBinding.sreachEdit.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                mPresenter.addSearchFlex(mBinding.sreachEdit.getText().toString(), SreachActivity.this, mBinding.searchFlexHistroy, FlexHistroy);
                mBinding.sreachEdit.setText("");
                startActivity(new Intent(SreachActivity.this, SreachResultActivity.class));
                finish();
            }
            return false;
        });
        mBinding.sreachEdit.addTextChangedListener(this);

        mList = new ArrayList<>();
        mAdapter = new SreachAdapter(mList, this);
        mAdapter.setOnItem(this);
        mBinding.listView.setAdapter(mAdapter);
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(mBinding.sreachEdit.getText().toString().trim())) {
            mBinding.sreachHome.setVisibility(View.VISIBLE);
            mBinding.listView.setVisibility(View.GONE);
            mBinding.sreachNav.setVisibility(View.VISIBLE);
            mBinding.sreachClose.setVisibility(View.GONE);
        } else {
            mBinding.sreachHome.setVisibility(View.GONE);
            mBinding.listView.setVisibility(View.VISIBLE);
            mBinding.sreachNav.setVisibility(View.VISIBLE);
            mBinding.sreachClose.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItem(int pos) {
        startActivity(new Intent(this, SreachResultActivity.class));
        finish();
    }

    @Override
    public void jump() {
        startActivity(new Intent(this, SreachResultActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sreach_cancle:
                finish();
                mBinding.sreachEdit.setText("");
                break;
            case R.id.sreach_home:
                if (FlexHistroy != null && FlexHistroy.size() != 0) {
                    for (int j = 0; j < FlexHistroy.size(); j++) {
                        FlexHistroy.get(j).setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.sreach_close:
                mBinding.sreachEdit.setText("");
                break;
        }
    }
}
