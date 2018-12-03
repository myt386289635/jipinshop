package com.example.administrator.jipinshop.activity.sreach.result;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.SreachResultAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SreachResultBean;
import com.example.administrator.jipinshop.bean.SreachTagBean;
import com.example.administrator.jipinshop.databinding.ActivitySreachResultBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/10
 * @Describe 搜索结果页
 */
public class SreachResultActivity extends BaseActivity implements SreachResultAdapter.OnItem, SreachResultView, TextWatcher, View.OnClickListener, OnRefreshListener {

    @Inject
    SreachResultPresenter mPresenter;

    private List<ImageView> FlexHistroy = new ArrayList<>();
    private SreachResultAdapter mAdapter;
    private List<SreachResultBean.ListBean> mList;

    private Boolean tag = true;//标示是显示界面一，还是界面2
    private ActivitySreachResultBinding mBinding;

    private String content = "";
    private List<String> hotText;
    private List<SreachTagBean> histroyText;

    //用于存储历史搜索里的View
    private List<View> histroyFlex;

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

        mBinding.sreachClose.setVisibility(View.VISIBLE);
        mBinding.sreachEdit.setText(content);
        mBinding.sreachEdit.setSelection(mBinding.sreachEdit.getText().length());//将光标移至文字末尾
        mBinding.sreachCancle.setVisibility(View.GONE);
        mBinding.sreachBack.setVisibility(View.VISIBLE);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new SreachResultAdapter(mList, this);
        mAdapter.setOnItem(this);
        mBinding.recyclerView.setAdapter(mAdapter);

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

        mPresenter.setView(this);
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
                mPresenter.addSearchFlex(mBinding.sreachEdit.getText().toString(), SreachResultActivity.this,
                        mBinding.searchFlexHistroy, FlexHistroy, histroyText,histroyFlex);
                saveHistroy();
                mBinding.sreachHome.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mBinding.sreachCancle.setVisibility(View.GONE);
                mBinding.sreachBack.setVisibility(View.VISIBLE);
                mBinding.sreachClose.setVisibility(View.VISIBLE);
                mBinding.recyclerView.scrollToPosition(0);
                mBinding.swipeToLoad.setRefreshEnabled(true);
                mBinding.swipeToLoad.setRefreshing(true);
            }
            return false;
        });
        mBinding.sreachEdit.addTextChangedListener(this);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setRefreshing(true);

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad,this);
    }

    /**
     * 点击图片进行跳转到商品详情页
     *
     * @param pos
     */
    @Override
    public void onItem(int pos) {
        if(!SPUtils.getInstance(CommonDate.USER).getBoolean(CommonDate.userLogin,false)){
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivity(new Intent(this, ShoppingDetailActivity.class)
                    .putExtra("goodsId",mList.get(pos).getGoodsId())
                    .putExtra("goodsName",mList.get(pos).getGoodsName())
                    .putExtra("priceNow",mList.get(pos).getActualPrice())
                    .putExtra("priceOld",mList.get(pos).getOtherPrice())
                    .putExtra("price",mList.get(pos).getCutPrice())
                    .putExtra("state",mList.get(pos).getSourceStatus() + "")
                    .putExtra("goodsImage",mList.get(pos).getRankGoodImg())
            );
        }
    }

    /**
     * 刷新商品
     */
    @Override
    public void onResher(String from, String content) {
        tag = false;
        mPresenter.addSearchFlex(content, this, mBinding.searchFlexHistroy, FlexHistroy, histroyText,histroyFlex);
        saveHistroy();
        mBinding.sreachHome.setVisibility(View.GONE);
        mBinding.recyclerView.setVisibility(View.VISIBLE);
        mBinding.sreachCancle.setVisibility(View.GONE);
        mBinding.sreachBack.setVisibility(View.VISIBLE);
        mBinding.sreachClose.setVisibility(View.VISIBLE);
        mBinding.sreachEdit.setText(content);
        mBinding.sreachEdit.setSelection(mBinding.sreachEdit.getText().length());//将光标移至文字末尾
        mBinding.recyclerView.scrollToPosition(0);
        mBinding.swipeToLoad.setRefreshEnabled(true);
        mBinding.swipeToLoad.setRefreshing(true);
        tag = true;//为了避免手动修改eidtText造成没有效果的问题
    }

    /**
     * 数据加载成功
     * @param resultBean
     */
    @Override
    public void Success(SreachResultBean resultBean) {
        stopResher();
        mList.clear();
        if(resultBean.getList() != null && resultBean.getList().size() != 0){
            mBinding.inClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mList.addAll(resultBean.getList());
        }else {
            mBinding.recyclerView.setVisibility(View.GONE);
            initError(R.mipmap.qs_order, "暂无数据", "没有发现商品哟，请换个关键词试试");
            Toast.makeText(this, "没有商品", Toast.LENGTH_SHORT).show();
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 数据加载失败
     * @param error
     */
    @Override
    public void Faile(String error) {
        stopResher();
        mBinding.recyclerView.setVisibility(View.GONE);
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
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
            if (tag) {
                mBinding.sreachHome.setVisibility(View.VISIBLE);
                mBinding.recyclerView.setVisibility(View.GONE);
                mBinding.sreachClose.setVisibility(View.GONE);
                mBinding.sreachCancle.setVisibility(View.VISIBLE);
                mBinding.sreachBack.setVisibility(View.GONE);
                mBinding.sreachCancle.setText("取消");
            }
        } else {
            if (tag) {
                mBinding.sreachHome.setVisibility(View.VISIBLE);
                mBinding.recyclerView.setVisibility(View.GONE);
                mBinding.sreachClose.setVisibility(View.VISIBLE);
                mBinding.sreachCancle.setVisibility(View.VISIBLE);
                mBinding.sreachBack.setVisibility(View.GONE);
                mBinding.sreachCancle.setText("搜索");
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
                if (mBinding.sreachCancle.getText().toString().equals("取消")) {
                    finish();
                } else {
                    if (TextUtils.isEmpty(mBinding.sreachEdit.getText().toString().trim())) {
                        Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mPresenter.addSearchFlex(mBinding.sreachEdit.getText().toString(), SreachResultActivity.this,
                            mBinding.searchFlexHistroy, FlexHistroy, histroyText,histroyFlex);
                    saveHistroy();
                    mBinding.sreachHome.setVisibility(View.GONE);
                    mBinding.recyclerView.setVisibility(View.VISIBLE);
                    mBinding.sreachCancle.setVisibility(View.GONE);
                    mBinding.sreachBack.setVisibility(View.VISIBLE);
                    mBinding.sreachClose.setVisibility(View.VISIBLE);
                    mBinding.recyclerView.scrollToPosition(0);
                    mBinding.swipeToLoad.setRefreshEnabled(true);
                    mBinding.swipeToLoad.setRefreshing(true);
                }
                break;
            case R.id.sreach_close:
                tag = true;
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
            histroyText = new Gson().fromJson(str, new TypeToken<List<SreachTagBean>>() {}.getType());
        }
    }

    /**
     * 刷新列表
     */
    @Override
    public void onRefresh() {
        mPresenter.searchGoods(mBinding.sreachEdit.getText().toString(),this.bindToLifecycle());
    }

    public void initError(int id, String title, String content) {
        mBinding.inClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.inClude.errorImage.setBackgroundResource(id);
        mBinding.inClude.errorTitle.setText(title);
        mBinding.inClude.errorContent.setText(content);
    }

    public void stopResher(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            if(!mBinding.swipeToLoad.isRefreshEnabled()){
                mBinding.swipeToLoad.setRefreshEnabled(true);
                mBinding.swipeToLoad.setRefreshing(false);
                mBinding.swipeToLoad.setRefreshEnabled(false);
            }else {
                mBinding.swipeToLoad.setRefreshing(false);
            }
        }
    }
}
