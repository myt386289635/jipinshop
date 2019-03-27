package com.example.administrator.jipinshop.fragment.tryout;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.article.ArticleDetailActivity;
import com.example.administrator.jipinshop.activity.tryout.TryAllActivity;
import com.example.administrator.jipinshop.activity.tryout.TryReportActivity;
import com.example.administrator.jipinshop.activity.tryout.detail.TryDetailActivity;
import com.example.administrator.jipinshop.adapter.TryAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.TryBean;
import com.example.administrator.jipinshop.bean.eventbus.TryStatusBus;
import com.example.administrator.jipinshop.databinding.TryFragmentBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.view.itemDecoration.StickyItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/12/26
 * @Describe 试用模块
 */
public class TryFragment extends DBBaseFragment implements OnRefreshListener, TryAdapter.OnItemClick, TryView {

    private TryFragmentBinding mBinding;
    private List<TryBean.DataBean.TrialListBean> mTrialListBeans;
    private List<TryBean.DataBean.ReportListBean> mReportListBeans;
    private TryAdapter mAdapter;
    private Boolean[] once = {true};

    @Inject
    TryPresenter mTryPresenter;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && once[0]){
            mBinding.swipeToLoad.setRefreshing(true);
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.try_fragment,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mTryPresenter.setStatusBarHight(mBinding.statusBar,getContext());
        mTryPresenter.setView(this);
        EventBus.getDefault().register(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTrialListBeans = new ArrayList<>();
        mReportListBeans = new ArrayList<>();
        mAdapter = new TryAdapter(getContext(),mTrialListBeans,mReportListBeans);
        mAdapter.setOnItemClick(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mTryPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mTryPresenter.tryIndex(this.bindToLifecycle());
    }

    /**
     * 错误页面
     */
    public void initError(int id, String title, String content) {
        mBinding.inClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.inClude.errorImage.setBackgroundResource(id);
        mBinding.inClude.errorTitle.setText(title);
        mBinding.inClude.errorContent.setText(content);
    }

    /**
     * 停止刷新
     */
    public void stopResher() {
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

    @Override
    public void onHeadClick(String type) {
        if(type.equals("免费试用")){
            //查看全部试用
            startActivity(new Intent(getContext(), TryAllActivity.class));
        }else {
            //查看全部报告
            startActivity(new Intent(getContext(), TryReportActivity.class));
        }
    }

    @Override
    public void onItemDetailClick(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            startActivity(new Intent(getContext(),TryDetailActivity.class)
                    .putExtra("id",mTrialListBeans.get(position).getId())
                    .putExtra("pos",position)
            );
        }
    }

    @Override
    public void onItemReportClick(int position) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return;
        }else{
            BigDecimal bigDecimal = new BigDecimal(mReportListBeans.get(position).getPv());
            mReportListBeans.get(position).setPv((bigDecimal.intValue() + 1));
            mAdapter.notifyDataSetChanged();
            startActivity(new Intent(getContext(),ArticleDetailActivity.class)
                    .putExtra("id",mReportListBeans.get(position).getArticleId())
                    .putExtra("type","4")
            );
        }
    }

    @Override
    public void onSuccess(TryBean bean) {
        stopResher();
        if(bean.getData().getTrialList().size() != 0 && bean.getData().getReportList().size() != 0){
            mBinding.inClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mReportListBeans.clear();
            mTrialListBeans.clear();
            mTrialListBeans.addAll(bean.getData().getTrialList());
            mReportListBeans.addAll(bean.getData().getReportList());
            mAdapter.notifyDataSetChanged();
            mBinding.recyclerView.addItemDecoration(new StickyItemDecoration(mBinding.tryHead));
        }else {
            initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
            mBinding.recyclerView.setVisibility(View.GONE);
        }
        if(once[0]){
            once[0] = false;
        }
    }

    @Override
    public void onFile(String error) {
        stopResher();
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
        mBinding.recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 试用商品的状态改变（倒计时结束）
     */
    @Subscribe
    public void changeStutas(TryStatusBus bus){
        if(bus != null && bus.getPos() != -1){
            if (bus.getPos() < mTrialListBeans.size()){
                mTrialListBeans.get(bus.getPos()).setStatus(3);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

}
