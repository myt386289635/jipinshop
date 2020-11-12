package com.example.administrator.jipinshop.activity.sreach.play.result;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.PlaySreachResultAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TbkIndexBean;
import com.example.administrator.jipinshop.databinding.ActivityPalySearchResultBinding;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/11/11
 * @Describe 吃喝玩乐搜索结果
 */
public class PlaySreachResultActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, PlaySreachResultView, TextWatcher {

    @Inject
    PlaySreachResultPresenter mPresenter;
    private ActivityPalySearchResultBinding mBinding;
    private String keyword = "";
    private List<TbkIndexBean.DataBean.BoxCategoryListBean.ListBean> mList;
    private PlaySreachResultAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_paly_search_result);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        keyword = getIntent().getStringExtra("content");
        mBinding.searchEdit.setText(keyword);
        mBinding.searchEdit.setSelection(mBinding.searchEdit.getText().length());//将光标移至文字末尾
        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        mBinding.searchEdit.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                if (TextUtils.isEmpty(mBinding.searchEdit.getText().toString().trim())) {
                    ToastUtil.show("请输入搜索内容");
                    return false;
                }
                keyword = mBinding.searchEdit.getText().toString();
                refresh();
            }
            return false;
        });
        mBinding.searchEdit.addTextChangedListener(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new PlaySreachResultAdapter(mList,this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.post(() -> {
            mBinding.swipeToLoad.setRefreshing(true);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_black:
                finish();
                break;
            case R.id.search_close:
                mBinding.searchEdit.setText("");
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.boxSearch(keyword,this.bindToLifecycle());
    }

    @Override
    public void onSuccess(SucBean<TbkIndexBean.DataBean.BoxCategoryListBean.ListBean> bean) {
        dissRefresh();
        if (bean.getData() != null && bean.getData().size() != 0){
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            mList.clear();
            mList.addAll(bean.getData());
            mAdapter.notifyDataSetChanged();
        }else {
            initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据");
        }
    }

    @Override
    public void onFile(String error) {
        dissRefresh();
        initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据");
        ToastUtil.show(error);
    }

    public void dissRefresh(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            if (!mBinding.swipeToLoad.isRefreshEnabled()) {
                mBinding.swipeToLoad.setRefreshEnabled(true);
                mBinding.swipeToLoad.setRefreshing(false);
                mBinding.swipeToLoad.setRefreshEnabled(false);
            } else {
                mBinding.swipeToLoad.setRefreshing(false);
            }
        }
    }

    public void initError(int id, String title, String content) {
        mBinding.recyclerView.setVisibility(View.GONE);
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    public void refresh(){
        mBinding.recyclerView.scrollToPosition(0);
        if (mBinding.swipeToLoad != null && !mBinding.swipeToLoad.isRefreshing()) {
            if (!mBinding.swipeToLoad.isRefreshEnabled()) {
                mBinding.swipeToLoad.setRefreshEnabled(true);
                mBinding.swipeToLoad.setRefreshing(true);
                mBinding.swipeToLoad.setRefreshEnabled(false);
            } else {
                mBinding.swipeToLoad.setRefreshing(true);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        keyword = mBinding.searchEdit.getText().toString();
        mBinding.recyclerView.scrollToPosition(0);
        onRefresh();
    }
}
