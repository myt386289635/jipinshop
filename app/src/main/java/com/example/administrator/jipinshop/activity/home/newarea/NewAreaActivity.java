package com.example.administrator.jipinshop.activity.home.newarea;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.NewAreaAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.EvaluationListBean;
import com.example.administrator.jipinshop.databinding.ActivityNewAreaBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/6/28
 * @Describe 新品专区
 */
public class NewAreaActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, NewAreaAdapter.OnItem, View.OnClickListener, NewAreaView {

    @Inject
    NewAreaPresenter mPresenter;
    private ActivityNewAreaBinding mBinding;
    private NewAreaAdapter mAdapter;
    private List<EvaluationListBean.DataBean> mList;
    private int page = 1;
    private Boolean refersh = true;
    private Boolean[] once ={true};
    private String id = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding =  DataBindingUtil.setContentView(this,R.layout.activity_new_area);
        mBinding.setListener(this);
        mBinding.setImage(getIntent().getStringExtra("image"));
        mBaseActivityComponent.inject(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        id = getIntent().getStringExtra("id");
        mPresenter.setStatusBarHight(mBinding.toolBar,mBinding.titleBack,this);
        mPresenter.setTitleBlack(mBinding.appbar,mBinding.titleBack);

        mBinding.swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new NewAreaAdapter(this,mList);
        mAdapter.setOnItem(this);
        mBinding.swipeTarget.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.swipeTarget,mBinding.swipeToLoad,mBinding.appbar,once);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mBinding.swipeToLoad.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        page = 1;
        refersh = true;
        mPresenter.newList(id,page,this.bindToLifecycle());
    }

    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.newList(id,page,this.bindToLifecycle());
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

    public void stopLoading() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore()) {
            if (!mBinding.swipeToLoad.isLoadMoreEnabled()) {
                mBinding.swipeToLoad.setLoadMoreEnabled(true);
                mBinding.swipeToLoad.setLoadingMore(false);
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            } else {
                mBinding.swipeToLoad.setLoadingMore(false);
            }
        }
    }

    /**
     * 跳转到文章页面
     */
    @Override
    public void onItem(int pos) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        } else {
            BigDecimal bigDecimal = new BigDecimal(mList.get(pos).getPv());
            mList.get(pos).setPv((bigDecimal.intValue() + 1) + "");
            mAdapter.notifyDataSetChanged();
            ShopJumpUtil.jumpArticle(this,mList.get(pos).getArticleId(),
                    mList.get(pos).getType(),mList.get(pos).getContentType());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(EvaluationListBean bean) {
        if (refersh){
            stopResher();
            mList.clear();
            mList.addAll(bean.getData());
            mAdapter.notifyDataSetChanged();
        }else {
            stopLoading();
            if (bean.getData().size() != 0){
                mList.addAll(bean.getData());
                mAdapter.notifyDataSetChanged();
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            }else {
                ToastUtil.show("已经是最后一页了");
                page--;
            }
        }
        if (once[0]){
            once[0] = false;
        }
    }

    @Override
    public void onFlie(String error) {
        if(refersh){
            stopResher();
        }else {
            stopLoading();
            page--;
        }
        ToastUtil.show(error);
        if (once[0]){
            once[0] = false;
        }
    }
}
