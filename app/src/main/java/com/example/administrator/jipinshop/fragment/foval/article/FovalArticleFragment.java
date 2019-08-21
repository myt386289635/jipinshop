package com.example.administrator.jipinshop.fragment.foval.article;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.classification.questions.detail.QuestionDetailActivity;
import com.example.administrator.jipinshop.activity.minekt.userkt.UserActivity;
import com.example.administrator.jipinshop.adapter.QuestionsAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.QuestionsBean;
import com.example.administrator.jipinshop.databinding.FragmentSreachfindBinding;
import com.example.administrator.jipinshop.fragment.sreach.article.SreachArticleView;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/6
 * @Describe 收藏问答
 */
public class FovalArticleFragment extends DBBaseFragment implements OnRefreshListener, OnLoadMoreListener, SreachArticleView, QuestionsAdapter.OnClickView {

    public static final String CollectResher = "ShoppingDetailActivity2FovalArticleFragment";

    @Inject
    FovalArticlePresenter mPresenter;

    private FragmentSreachfindBinding mBinding;
    private Boolean once = true;//记录第一次进入页面标示
    private QuestionsAdapter mAdapter;
    private List<QuestionsBean.DataBean> mList;

    private int page = 1;
    private Boolean refersh = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once) {
            mBinding.swipeToLoad.setRefreshing(true);
        }
    }

    public static FovalArticleFragment getInstance() {
        FovalArticleFragment fragment = new FovalArticleFragment();
        return fragment;
    }

    /**
     * 页面刷新
     */
    @Override
    public void onRefresh() {
        page = 1;
        refersh = true;
        mPresenter.collect(page, "5", this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sreachfind, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);
        EventBus.getDefault().register(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        mAdapter = new QuestionsAdapter(mList, getContext());
        mAdapter.setView(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);

    }

    /**
     * 页面加载
     */
    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.collect(page,"5", this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
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

    /**
     * 停止加载
     */
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
     * 收藏列表获取成功
     *
     * @param articlesBean
     */
    @Override
    public void Success(QuestionsBean articlesBean) {
        stopResher();
        stopLoading();
        if(articlesBean.getData() != null && articlesBean.getData().size() != 0){
            if(refersh){
                mBinding.inClude.qsNet.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mList.clear();
                mList.addAll(articlesBean.getData());
                mAdapter.notifyDataSetChanged();
            } else {
                mList.addAll(articlesBean.getData());
                mAdapter.notifyDataSetChanged();
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            }
        }else {
            if(refersh){
                initError(R.mipmap.qs_collection, "暂无数据", "暂时没有任何数据 ");
                mBinding.recyclerView.setVisibility(View.GONE);
            }else {
                page-- ;
                ToastUtil.show("已经是最后一页了");
            }
        }
        if (refersh) {
            if (once) {
                once = false;
            }
        }
    }

    /**
     * 收藏列表获取失败
     *
     * @param throwable
     */
    @Override
    public void Faile(String throwable) {
        if (refersh) {
            stopResher();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑试试");
            mBinding.recyclerView.setVisibility(View.GONE);
        }else {
            stopLoading();
            page--;
        }
        ToastUtil.show(throwable);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void onResher(String s) {
        if (!TextUtils.isEmpty(s) && s.equals(FovalArticleFragment.CollectResher)) {
            onRefresh();
        }
    }

    @Override
    public void onClickArticle(int pos) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        } else {
            startActivity(new Intent(getContext(), QuestionDetailActivity.class)
                    .putExtra("date",mList.get(pos)));
        }
    }

    @Override
    public void onClickUserInfo(String userId) {
        startActivity(new Intent(getContext(), UserActivity.class)
                .putExtra("userid",userId)
        );
    }
}
