package com.example.administrator.jipinshop.fragment.tryout.trymodel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.tryout.TryAllActivity;
import com.example.administrator.jipinshop.activity.tryout.TryReportActivity;
import com.example.administrator.jipinshop.activity.tryout.detail.TryDetailActivity;
import com.example.administrator.jipinshop.adapter.TryAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TryBean;
import com.example.administrator.jipinshop.bean.eventbus.TryStatusBus;
import com.example.administrator.jipinshop.databinding.TryFragmentBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.UAppUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/12/26
 * @Describe 试用首页
 */
public class TryFragment extends DBBaseFragment implements OnRefreshListener, TryAdapter.OnItemClick, TryView {

    private TryFragmentBinding mBinding;
    private List<TryBean.DataBean.TrialListBean> mTrialListBeans;
    private List<TryBean.DataBean.ReportListBean> mReportListBeans;
    private List<EvaluationTabBean.DataBean.AdListBean> mAdListBeans;
    private TryAdapter mAdapter;
    private Boolean[] once = {true};
//    private StickyItemDecoration mStickyItemDecoration;

    @Inject
    TryPresenter mTryPresenter;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once[0]) {
            mBinding.swipeToLoad.setRefreshing(true);
            mTryPresenter.tryADlist(this.bindToLifecycle());
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
        mTryPresenter.setView(this);
        EventBus.getDefault().register(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTrialListBeans = new ArrayList<>();
        mReportListBeans = new ArrayList<>();
        mAdListBeans = new ArrayList<>();
        mAdapter = new TryAdapter(getContext(),mTrialListBeans,mReportListBeans);
        mAdapter.setOnItemClick(this);
        mAdapter.setAdListBeans(mAdListBeans);
        mBinding.recyclerView.setAdapter(mAdapter);
//        mStickyItemDecoration = new StickyItemDecoration(mBinding.tryHead);
//        mBinding.recyclerView.addItemDecoration(mStickyItemDecoration);

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
            UAppUtil.oneTab_trier(getContext(),0);
        }else {
            //查看全部报告
            startActivity(new Intent(getContext(), TryReportActivity.class));
            UAppUtil.oneTab_trier(getContext(),1);
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
            ShopJumpUtil.jumpArticle(getContext(),mReportListBeans.get(position).getArticleId(),
                    mReportListBeans.get(position).getType(),mReportListBeans.get(position).getContentType());
        }
    }

    @Override
    public void onSuccess(TryBean bean) {
        stopResher();
        if(bean.getData().getTrialList().size() != 0){
            mBinding.inClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mReportListBeans.clear();
            mTrialListBeans.clear();
            mTrialListBeans.addAll(bean.getData().getTrialList());
            mReportListBeans.addAll(bean.getData().getReportList());
//            mStickyItemDecoration.clearStickyViewPosition();//清空缓存的位置，添加数据和删除数据容易崩溃
            mAdapter.notifyDataSetChanged();
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
    public void onSucAdList(SucBean<EvaluationTabBean.DataBean.AdListBean> adListBeanSucBean) {
        mAdListBeans.addAll(adListBeanSucBean.getData());
        mAdapter.notifyItemChanged(0);
    }

    @Override
    public void onFileAdList(String error) {
        ToastUtil.show(error);
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
