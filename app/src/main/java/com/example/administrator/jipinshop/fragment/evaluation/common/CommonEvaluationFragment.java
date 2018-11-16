package com.example.administrator.jipinshop.fragment.evaluation.common;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.CommonEvaluationAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.EvaluationListBean;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.databinding.FragmentEvaluationCommonBinding;
import com.example.administrator.jipinshop.fragment.evaluation.EvaluationFragment;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CommonEvaluationFragment extends DBBaseFragment implements OnRefreshListener, CommonEvaluationView, OnLoadMoreListener {

    public static final String ONE = "1"; //该页为精选榜
    public static final String TWO = "2"; //该页为个护健康
    public static final String THREE = "3"; //该页为厨卫电器
    public static final String FORE = "4"; //该页为生活家居
    public static final String FIVE = "5"; //该页为家用大电

    @Inject
    CommonEvaluationPresenter mPresenter;

    private FragmentEvaluationCommonBinding mBinding;
    private List<EvaluationListBean.ListBean> mList;
    private CommonEvaluationAdapter mAdapter;

    private Boolean once = true;
    private String id = "0";//数据id
    private int page = 0;
    private Boolean refersh = true;
    private EvaluationListBean bean;//本地数据

    public static CommonEvaluationFragment getInstance(String type) {
        CommonEvaluationFragment fragment = new CommonEvaluationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && once) {
            if (!getArguments().getString("type").equals(ONE)) {
                bean = getDate(); //缓存数据
                if (bean != null){
                    mList.addAll(bean.getList());
                    mAdapter.notifyDataSetChanged();
                }
                mBinding.swipeToLoad.setRefreshing(true);
                once = false;
            }
        }
    }

    @Override
    public void onRefresh() {
        page = 0;
        refersh = true;
        mPresenter.getDate(id, page + "", this.bindToLifecycle());
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_evaluation_common, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        EventBus.getDefault().register(this);
        mPresenter.setView(this);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        //缓存数据
        if (getArguments().getString("type").equals(ONE)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonEvaluationFragmentDATA1, ""))) {
                bean = new Gson().fromJson(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonEvaluationFragmentDATA1), EvaluationListBean.class);
                mList.addAll(bean.getList());
            }
        }
        mAdapter = new CommonEvaluationAdapter(mList, getContext());
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void initDate(String s) {
        if (!TextUtils.isEmpty(s) && s.equals(EvaluationFragment.tag)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.EvaluationTab, ""))) {
                EvaluationTabBean bean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.EvaluationTab), EvaluationTabBean.class);
                if (getArguments().getString("type").equals(ONE)) {
                    id = bean.getList().get(0).getCategoryId();
                } else if (getArguments().getString("type").equals(TWO)) {
                    id = bean.getList().get(1).getCategoryId();
                } else if (getArguments().getString("type").equals(THREE)) {
                    id = bean.getList().get(2).getCategoryId();
                } else if (getArguments().getString("type").equals(FORE)) {
                    id = bean.getList().get(3).getCategoryId();
                } else if (getArguments().getString("type").equals(FIVE)) {
                    id = bean.getList().get(4).getCategoryId();
                }
            } else {
                id = "0";
            }
            if (getArguments().getString("type").equals(ONE)) {
                mBinding.swipeToLoad.setRefreshing(true);
            }
        }
    }

    /**
     * 数据成功回调
     *
     * @param evaluationListBean
     */
    @Override
    public void onSuccess(EvaluationListBean evaluationListBean) {
        if (refersh) {
            stopResher();
            setDate(evaluationListBean);//本地缓存
            if (evaluationListBean.getList() != null && evaluationListBean.getList().size() != 0) {
                mBinding.netClude.qsNet.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mList.clear();
                mList.addAll(evaluationListBean.getList());
                mAdapter.notifyDataSetChanged();
            } else {
                initError(R.mipmap.qs_404, "页面出错", "程序猿正在赶来的路上");
                mBinding.recyclerView.setVisibility(View.GONE);
            }
        } else {
            stopLoading();
            if (evaluationListBean.getList() != null && evaluationListBean.getList().size() != 0) {
                mList.addAll(evaluationListBean.getList());
                mAdapter.notifyDataSetChanged();
            } else {
                page--;
                Toast.makeText(getContext(), "已经是最后一页了", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 数据失败回调
     *
     * @param error
     */
    @Override
    public void onFile(String error) {
        if (refersh) {
            stopResher();
            if (bean == null || bean.getList() == null || bean.getList().size() == 0) {
                initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
                mBinding.recyclerView.setVisibility(View.GONE);
            } else {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        } else {
            stopLoading();
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        }
    }

    public void setDate(EvaluationListBean evaluationListBean) {
        if (getArguments().getString("type").equals(ONE)) {
            SPUtils.getInstance(CommonDate.NETCACHE).put(CommonDate.CommonEvaluationFragmentDATA1, new Gson().toJson(evaluationListBean));
        } else if (getArguments().getString("type").equals(TWO)) {
            SPUtils.getInstance(CommonDate.NETCACHE).put(CommonDate.CommonEvaluationFragmentDATA2, new Gson().toJson(evaluationListBean));
        } else if (getArguments().getString("type").equals(THREE)) {
            SPUtils.getInstance(CommonDate.NETCACHE).put(CommonDate.CommonEvaluationFragmentDATA3, new Gson().toJson(evaluationListBean));
        } else if (getArguments().getString("type").equals(FORE)) {
            SPUtils.getInstance(CommonDate.NETCACHE).put(CommonDate.CommonEvaluationFragmentDATA4, new Gson().toJson(evaluationListBean));
        } else if (getArguments().getString("type").equals(FIVE)) {
            SPUtils.getInstance(CommonDate.NETCACHE).put(CommonDate.CommonEvaluationFragmentDATA5, new Gson().toJson(evaluationListBean));
        }
    }

    public EvaluationListBean getDate() {
        if (getArguments().getString("type").equals(TWO)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonEvaluationFragmentDATA2, ""))) {
                return new Gson().fromJson(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonEvaluationFragmentDATA2), EvaluationListBean.class);
            }
        } else if (getArguments().getString("type").equals(THREE)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonEvaluationFragmentDATA3, ""))) {
                return new Gson().fromJson(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonEvaluationFragmentDATA3), EvaluationListBean.class);
            }
        } else if (getArguments().getString("type").equals(FORE)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonEvaluationFragmentDATA4, ""))) {
                return new Gson().fromJson(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonEvaluationFragmentDATA4), EvaluationListBean.class);
            }
        } else if (getArguments().getString("type").equals(FIVE)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonEvaluationFragmentDATA5, ""))) {
                return new Gson().fromJson(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonEvaluationFragmentDATA5), EvaluationListBean.class);
            }
        }
        return null;
    }

    /**
     * 错误页面
     */
    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
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

    /**
     * 停止加载
     */
    public void stopLoading() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore()) {
            if (!mBinding.swipeToLoad.isLoadingMore()) {
                mBinding.swipeToLoad.setLoadMoreEnabled(true);
                mBinding.swipeToLoad.setLoadingMore(false);
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            } else {
                mBinding.swipeToLoad.setLoadingMore(false);
            }
        }
    }

    /**
     * 加载
     */
    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.getDate(id, page + "", this.bindToLifecycle());
    }
}
