package com.example.administrator.jipinshop.activity.home.tabitem;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.ALLTabAdapter;
import com.example.administrator.jipinshop.adapter.SreachGoodsAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ChildrenTabBean;
import com.example.administrator.jipinshop.bean.HomeCommenBean;
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean;
import com.example.administrator.jipinshop.databinding.ActivityAlltabBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/2/20
 * @Describe 榜单二级目录查看全部页面
 */
public class ALLTabActivity extends BaseActivity implements View.OnClickListener, ALLTabAdapter.OnItemClick, OnRefreshListener, SreachGoodsAdapter.OnItem, ALLTabView {


    private ActivityAlltabBinding mBinding;
    private List<ChildrenTabBean> mChildrenBeans;
    private ALLTabAdapter mTabAdapter;
    private int sets = 0;//二级导航点击的位置   默认为第0个  这个也是最后一次点击的位置
    private Dialog mDialog;//点击二级菜单时请求数据加载框

    private SreachGoodsAdapter mAdapter;
    private List<SreachResultGoodsBean.DataBean> mList;

    @Inject
    ALLTabPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding =  DataBindingUtil.setContentView(this,R.layout.activity_alltab);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText(getIntent().getStringExtra("title"));

        mChildrenBeans = new ArrayList<>();
        List<HomeCommenBean.GoodsCategoryListBean> tabBean = (List<HomeCommenBean.GoodsCategoryListBean>) getIntent().getSerializableExtra("date");
        mChildrenBeans.add(new ChildrenTabBean("全部", true,"0"));
        if(tabBean != null){
            mBinding.categoryView.setVisibility(View.VISIBLE);
            for (int i = 0; i < tabBean.size(); i++) {
                mChildrenBeans.add(new ChildrenTabBean(tabBean.get(i).getCategoryName(), false,tabBean.get(i).getCategoryId()));
            }
        }else {
            mBinding.categoryView.setVisibility(View.GONE);
        }
        mTabAdapter = new ALLTabAdapter(mChildrenBeans,this);
        mTabAdapter.setOnItem(this);
        mBinding.categoryView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false));
        mBinding.categoryView.setAdapter(mTabAdapter);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new SreachGoodsAdapter(mList,this);
        mAdapter.setOnItem(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad,this);
        mBinding.swipeToLoad.setRefreshing(true);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }

    @Override
    public void onItem(int pos) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            BigDecimal bigDecimal = new BigDecimal(mList.get(pos).getPv());
            mList.get(pos).setPv((bigDecimal.intValue() + 1) + "");
            mAdapter.notifyDataSetChanged();
            startActivity(new Intent(this, ShoppingDetailActivity.class)
                    .putExtra("goodsId",mList.get(pos).getGoodsId())
            );
        }
    }

    @Override
    public void onRefresh() {
        if(mChildrenBeans.size() != 0){
            if(sets == 0){
                mPresenter.goodRank(getIntent().getStringExtra("category1Id"),this.bindToLifecycle());
            }else {
                mPresenter.goodsList2(mChildrenBeans.get(sets).getCategoryid(),"0",this.bindToLifecycle());
            }
        }else {
            stopResher();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            mBinding.recyclerView.setVisibility(View.GONE);
            ToastUtil.show("网络请求错误,请重新开启app");
        }
    }

    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
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
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    public void onItemClick(int pos) {
        sets = pos;
        for (int i = 0; i < mChildrenBeans.size(); i++) {
            mChildrenBeans.get(i).setTag(false);
        }
        mChildrenBeans.get(pos).setTag(true);
        mTabAdapter.notifyDataSetChanged();
        mBinding.recyclerView.scrollToPosition(0);
        mBinding.swipeToLoad.setRefreshEnabled(true);
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "请求中...");
        mDialog.show();
        onRefresh();
    }

    @Override
    public void Success(SreachResultGoodsBean resultGoodsBean) {
        if(resultGoodsBean.getCategory2Id().equals(mChildrenBeans.get(sets).getCategoryid())){
            if (resultGoodsBean.getData() != null && resultGoodsBean.getData().size() != 0) {
                mList.clear();
                mBinding.netClude.qsNet.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mList.addAll(resultGoodsBean.getData());
                mAdapter.notifyDataSetChanged();
            } else {
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
                mBinding.recyclerView.setVisibility(View.GONE);
            }
            stopResher();
        }
        stopResher();
    }

    @Override
    public void Faile(String error) {
        stopResher();
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
        mBinding.recyclerView.setVisibility(View.GONE);
        ToastUtil.show("网络出错");
    }

    @Override
    public void onSuccess(HomeCommenBean commenBean) {
        if(commenBean.getData() != null && commenBean.getData().size() != 0){
            mList.clear();
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            for (HomeCommenBean.DataBean dataBean : commenBean.getData()) {
                SreachResultGoodsBean.DataBean date = new SreachResultGoodsBean.DataBean();
                date.setImg(dataBean.getImg());
                date.setGoodsName(dataBean.getGoodsName());
                List<SreachResultGoodsBean.DataBean.GoodsTagsListBean> list = new ArrayList<>();
                for (int i = 0; i < dataBean.getGoodsTagsList().size(); i++) {
                    SreachResultGoodsBean.DataBean.GoodsTagsListBean tagsList = new SreachResultGoodsBean.DataBean.GoodsTagsListBean();
                    tagsList.setName(dataBean.getGoodsTagsList().get(i).getName());
                    list.add(tagsList);
                }
                date.setGoodsTagsList(list);
                date.setOtherPrice(dataBean.getOtherPrice());
                date.setActualPrice(dataBean.getActualPrice());
                date.setSource(dataBean.getSource());
                date.setPv(dataBean.getPv());
                date.setRecommendReason(dataBean.getRecommendReason());
                date.setGoodsId(dataBean.getGoodsId());
                mList.add(date);
            }
            mAdapter.notifyDataSetChanged();
        }else {
            initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
            mBinding.recyclerView.setVisibility(View.GONE);
        }
        stopResher();
    }
}
