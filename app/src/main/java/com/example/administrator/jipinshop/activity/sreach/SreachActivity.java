package com.example.administrator.jipinshop.activity.sreach;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.sreach.result.SreachResultActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SreachTagBean;
import com.example.administrator.jipinshop.databinding.ActivitySreachBinding;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SreachActivity extends BaseActivity implements TextWatcher, SreachView, View.OnClickListener {

    @Inject
    SreachPresenter mPresenter;

    private List<ImageView> FlexHistroy = new ArrayList<>();
    //    private List<String> mList;
//    private SreachAdapter mAdapter;
    private ActivitySreachBinding mBinding;

    private List<String> hotText;
    private List<SreachTagBean> histroyText;

    //用于存储历史搜索里的View
    private List<View> histroyFlex;

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

        hotText = new ArrayList<>();
        hotText.add("电动牙刷");
        hotText.add("洗衣机");
        hotText.add("电热水壶");
        hotText.add("吹风机");
        hotText.add("波轮洗衣机");
        hotText.add("美容仪");
        hotText.add("厨卫");

        histroyText = new ArrayList<>();
        histroyFlex = new ArrayList<>();
        getHistory();

        mPresenter.initHistroy(this, mBinding.searchFlexHistroy, FlexHistroy, histroyText,histroyFlex);
        mPresenter.initHot(this, mBinding.searchFlexHot, hotText);
        mBinding.sreachEdit.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                if (TextUtils.isEmpty(mBinding.sreachEdit.getText().toString().trim())) {
                    Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    return false;
                }
                mPresenter.addSearchFlex(mBinding.sreachEdit.getText().toString(), SreachActivity.this,
                        mBinding.searchFlexHistroy, FlexHistroy, histroyText,histroyFlex);
                saveHistroy();
                startActivity(new Intent(SreachActivity.this, SreachResultActivity.class)
                        .putExtra("content", mBinding.sreachEdit.getText().toString())
                );
                finish();
            }
            return false;
        });
        mBinding.sreachEdit.addTextChangedListener(this);

//        mList = new ArrayList<>();
//        mAdapter = new SreachAdapter(mList, this);
//        mAdapter.setOnItem(this);
//        mBinding.listView.setAdapter(mAdapter);
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
//            mBinding.sreachHome.setVisibility(View.VISIBLE);
//            mBinding.listView.setVisibility(View.GONE);
//            mBinding.sreachNav.setVisibility(View.VISIBLE);
            mBinding.sreachClose.setVisibility(View.GONE);
            mBinding.sreachCancle.setText("取消");
        } else {
//            mBinding.sreachHome.setVisibility(View.GONE);
//            mBinding.listView.setVisibility(View.VISIBLE);
//            mBinding.sreachNav.setVisibility(View.VISIBLE);
            mBinding.sreachClose.setVisibility(View.VISIBLE);
            mBinding.sreachCancle.setText("搜索");
        }
    }

    /**
     * 从热门搜索、历史搜索里点击进入搜索列表
     */
    @Override
    public void jump(String from, String content) {
        mPresenter.addSearchFlex(content, SreachActivity.this, mBinding.searchFlexHistroy, FlexHistroy, histroyText,histroyFlex);
        saveHistroy();
        startActivity(new Intent(this, SreachResultActivity.class)
                .putExtra("content", content)
        );
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sreach_cancle:
                if (mBinding.sreachCancle.getText().toString().equals("取消")) {
                    finish();
                } else {
                    if (TextUtils.isEmpty(mBinding.sreachEdit.getText().toString().trim())) {
                        Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mPresenter.addSearchFlex(mBinding.sreachEdit.getText().toString(), SreachActivity.this,
                            mBinding.searchFlexHistroy, FlexHistroy, histroyText,histroyFlex);
                    saveHistroy();
                    startActivity(new Intent(this, SreachResultActivity.class)
                            .putExtra("content", mBinding.sreachEdit.getText().toString())
                    );
                    finish();
                }
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

    /**
     * 数组转json
     */
    public void saveHistroy() {
        String str = new Gson().toJson(histroyText);
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.historySreach, str);
    }

    /**
     * json转数组
     */
    public void getHistory() {
        String str = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.historySreach, "");
        Log.d("moxiaoting", str);
        if (!TextUtils.isEmpty(str)) {
            histroyText = new Gson().fromJson(str, new TypeToken<List<SreachTagBean>>() {
            }.getType());
        }
    }


}
