package com.example.administrator.jipinshop.fragment.find.common;

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
import com.example.administrator.jipinshop.adapter.CommonFindAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.FindListBean;
import com.example.administrator.jipinshop.bean.eventbus.FindTabBus;
import com.example.administrator.jipinshop.databinding.FragmentFindCommonBinding;
import com.example.administrator.jipinshop.fragment.find.FindFragment;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 精选榜、个护健康、厨卫电器、生活家居、家用大电
 */
public class CommonFindFragment extends DBBaseFragment implements OnRefreshListener, OnLoadMoreListener, CommonFindView {

    public static final String ONE = "1"; //该页为精选榜
    public static final String TWO = "2"; //该页为个护健康
    public static final String THREE = "3"; //该页为厨卫电器
    public static final String FORE = "4"; //该页为生活家居
    public static final String FIVE = "5"; //该页为家用大电

    @Inject
    CommonFindPresenter mPresenter;

    private FragmentFindCommonBinding mBinding;
    private List<FindListBean.ListBean> mList;
    private CommonFindAdapter mAdapter;

    private Boolean once = true;
    private String id = "0";//数据id
    private int page = 0;
    private Boolean refersh = true;
    private FindListBean bean;//本地数据

    public static CommonFindFragment getInstance(String type) {
        CommonFindFragment fragment = new CommonFindFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && once){
            if(!getArguments().getString("type").equals(ONE)){
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
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_find_common,container,false);
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
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonFindFragmentDATA1, ""))) {
                bean = new Gson().fromJson(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonFindFragmentDATA1), FindListBean.class);
                mList.addAll(bean.getList());
            }
            once = false;
        }
        mAdapter = new CommonFindAdapter(mList, getContext());
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
    }

    //刷新
    @Override
    public void onRefresh() {
        page = 0;
        refersh = true;
        mPresenter.getList(id,page + "",this.bindToLifecycle());
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void initDate(FindTabBus findTabBus) {
        if (findTabBus != null && findTabBus.getTag().equals(FindFragment.tag)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.FindTab, ""))) {
                EvaluationTabBean bean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.FindTab), EvaluationTabBean.class);
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
     * 加载
     */
    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.getList(id,page + "",this.bindToLifecycle());
    }

    /**
     * 数据加载成功
     */
    @Override
    public void onSuccess(FindListBean findListBean) {
        if(refersh){
            stopResher();
            setDate(findListBean);//本地缓存
            if(findListBean.getList() != null && findListBean.getList().size() != 0){
                mBinding.netClude.qsNet.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mList.clear();
                mList.addAll(findListBean.getList());
                mAdapter.notifyDataSetChanged();
            }else {
                initError(R.mipmap.qs_404, "页面出错", "程序猿正在赶来的路上");
                mBinding.recyclerView.setVisibility(View.GONE);
            }
        }else {
            stopLoading();
            if (findListBean.getList() != null && findListBean.getList().size() != 0) {
                mList.addAll(findListBean.getList());
                mAdapter.notifyDataSetChanged();
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            } else {
                page--;
                Toast.makeText(getContext(), "已经是最后一页了", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 数据加载失败
     */
    @Override
    public void onFile(String error) {
        if(refersh){
            stopResher();
            if (bean == null || bean.getList() == null || bean.getList().size() == 0) {
                initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
                mBinding.recyclerView.setVisibility(View.GONE);
            } else {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        }else {
            stopLoading();
            page--;
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        }
    }

    public void setDate(FindListBean findListBean) {
        if (getArguments().getString("type").equals(ONE)) {
            SPUtils.getInstance(CommonDate.NETCACHE).put(CommonDate.CommonFindFragmentDATA1, new Gson().toJson(findListBean));
        } else if (getArguments().getString("type").equals(TWO)) {
            SPUtils.getInstance(CommonDate.NETCACHE).put(CommonDate.CommonFindFragmentDATA2, new Gson().toJson(findListBean));
        } else if (getArguments().getString("type").equals(THREE)) {
            SPUtils.getInstance(CommonDate.NETCACHE).put(CommonDate.CommonFindFragmentDATA3, new Gson().toJson(findListBean));
        } else if (getArguments().getString("type").equals(FORE)) {
            SPUtils.getInstance(CommonDate.NETCACHE).put(CommonDate.CommonFindFragmentDATA4, new Gson().toJson(findListBean));
        } else if (getArguments().getString("type").equals(FIVE)) {
            SPUtils.getInstance(CommonDate.NETCACHE).put(CommonDate.CommonFindFragmentDATA5, new Gson().toJson(findListBean));
        }
    }

    public FindListBean getDate() {
        if (getArguments().getString("type").equals(TWO)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonFindFragmentDATA2, ""))) {
                return new Gson().fromJson(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonFindFragmentDATA2), FindListBean.class);
            }
        } else if (getArguments().getString("type").equals(THREE)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonFindFragmentDATA3, ""))) {
                return new Gson().fromJson(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonFindFragmentDATA3), FindListBean.class);
            }
        } else if (getArguments().getString("type").equals(FORE)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonFindFragmentDATA4, ""))) {
                return new Gson().fromJson(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonFindFragmentDATA4), FindListBean.class);
            }
        } else if (getArguments().getString("type").equals(FIVE)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonFindFragmentDATA5, ""))) {
                return new Gson().fromJson(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonFindFragmentDATA5), FindListBean.class);
            }
        }
        return null;
    }

}
