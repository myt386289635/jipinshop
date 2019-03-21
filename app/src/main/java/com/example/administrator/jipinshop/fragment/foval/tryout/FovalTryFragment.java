package com.example.administrator.jipinshop.fragment.foval.tryout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.article.ArticleDetailActivity;
import com.example.administrator.jipinshop.adapter.SreachFindAdapter;
import com.example.administrator.jipinshop.adapter.SreachTryAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.SreachResultArticlesBean;
import com.example.administrator.jipinshop.fragment.foval.article.FovalArticleFragment;
import com.example.administrator.jipinshop.fragment.foval.article.FovalArticlePresenter;
import com.example.administrator.jipinshop.fragment.sreach.article.SreachArticleView;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 莫小婷
 * @create 2019/3/21
 * @Describe 收藏试用
 */
public class FovalTryFragment extends DBBaseFragment implements OnRefreshListener, OnLoadMoreListener, SreachFindAdapter.OnItem, SreachArticleView, SreachTryAdapter.OnItem{

    public static final String CollectResher = "ShoppingDetailActivity2FovalTryFragment";

    @Inject
    FovalArticlePresenter mPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.error_image)
    ImageView mErrorImage;
    @BindView(R.id.error_title)
    TextView mErrorTitle;
    @BindView(R.id.error_content)
    TextView mErrorContent;
    @BindView(R.id.qs_net)
    LinearLayout mQsNet;
    @BindView(R.id.swipeToLoad)
    SwipeToLoadLayout mSwipeToLoad;
    private Unbinder unbinder;

    private Boolean once = true;//记录第一次进入页面标示
    private SreachTryAdapter mAdapter;
    private List<SreachResultArticlesBean.DataBean> mList;

    private int page = 1;
    private Boolean refersh = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once) {
            mSwipeToLoad.setRefreshing(true);
        }
    }

    public static FovalTryFragment getInstance(String type) {
        FovalTryFragment fragment = new FovalTryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_fovalfind, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);
        EventBus.getDefault().register(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        mAdapter = new SreachTryAdapter(mList,getContext());
        mAdapter.setOnItem(this);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeToLoad.setOnRefreshListener(this);
        mSwipeToLoad.setOnLoadMoreListener(this);
        mPresenter.solveScoll(mRecyclerView,mSwipeToLoad);
    }

    @Override
    public void onRefresh() {
        page = 1;
        refersh = true;
        mPresenter.collect(page, getArguments().getString("type"), this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }

    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.collect(page, getArguments().getString("type"), this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }

    @Override
    public void onItem(int pos) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            BigDecimal bigDecimal = new BigDecimal(mList.get(pos).getPv());
            mList.get(pos).setPv((bigDecimal.intValue() + 1) + "");
            mAdapter.notifyDataSetChanged();
            startActivity(new Intent(getContext(),ArticleDetailActivity.class)
                    .putExtra("id",mList.get(pos).getArticleId())
                    .putExtra("type","4")
            );
        }
    }

    @Override
    public void Success(SreachResultArticlesBean articlesBean) {
        stopResher();
        stopLoading();
        if(articlesBean.getData() != null && articlesBean.getData().size() != 0){
            if(refersh){
                mQsNet.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mList.clear();
                mList.addAll(articlesBean.getData());
                mAdapter.notifyDataSetChanged();
            }else {
                mList.addAll(articlesBean.getData());
                mAdapter.notifyDataSetChanged();
                mSwipeToLoad.setLoadMoreEnabled(false);
            }
        }else {
            if(refersh){
                initError(R.mipmap.qs_collection, "暂无数据", "暂时没有任何数据");
                mRecyclerView.setVisibility(View.GONE);
            }else {
                page-- ;
                ToastUtil.show("已经是最后一页了");
            }
        }
        if(refersh){
            if (once){
                once = false;
            }
        }
    }

    @Override
    public void Faile(String error) {
        if(refersh){
            stopResher();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑试试");
            mRecyclerView.setVisibility(View.GONE);
        }else {
            stopLoading();
            page--;
        }
        ToastUtil.show(error);
    }

    public void initError(int id, String title, String content) {
        mQsNet.setVisibility(View.VISIBLE);
        mErrorImage.setImageResource(id);
        mErrorTitle.setText(title);
        mErrorContent.setText(content);
    }

    public void stopResher(){
        if (mSwipeToLoad != null && mSwipeToLoad.isRefreshing()) {
            if (!mSwipeToLoad.isRefreshEnabled()) {
                mSwipeToLoad.setRefreshEnabled(true);
                mSwipeToLoad.setRefreshing(false);
                mSwipeToLoad.setRefreshEnabled(false);
            } else {
                mSwipeToLoad.setRefreshing(false);
            }
        }
    }

    /**
     * 停止加载
     */
    public void stopLoading() {
        if (mSwipeToLoad != null && mSwipeToLoad.isLoadingMore()) {
            if (!mSwipeToLoad.isLoadMoreEnabled()) {
                mSwipeToLoad.setLoadMoreEnabled(true);
                mSwipeToLoad.setLoadingMore(false);
                mSwipeToLoad.setLoadMoreEnabled(false);
            } else {
                mSwipeToLoad.setLoadingMore(false);
            }
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void onResher(String s) {
        if (!TextUtils.isEmpty(s) && s.equals(FovalTryFragment.CollectResher)) {
            onRefresh();
        }
    }
}
