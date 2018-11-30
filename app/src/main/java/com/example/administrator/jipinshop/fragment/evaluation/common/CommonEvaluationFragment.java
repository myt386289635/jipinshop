package com.example.administrator.jipinshop.fragment.evaluation.common;

import android.app.Dialog;
import android.content.Intent;
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
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.adapter.CommonEvaluationAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.EvaluationListBean;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.CommonEvaluationBus;
import com.example.administrator.jipinshop.bean.eventbus.ConcerBus;
import com.example.administrator.jipinshop.bean.eventbus.EvaluationBus;
import com.example.administrator.jipinshop.databinding.FragmentEvaluationCommonBinding;
import com.example.administrator.jipinshop.fragment.evaluation.EvaluationFragment;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.google.gson.Gson;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CommonEvaluationFragment extends DBBaseFragment implements OnRefreshListener, CommonEvaluationView, OnLoadMoreListener, CommonEvaluationAdapter.OnClickItem {

    public static final String ONE = "1"; //该页为精选榜
    public static final String TWO = "2"; //该页为个护健康
    public static final String THREE = "3"; //该页为厨卫电器
    public static final String FORE = "4"; //该页为生活家居
    public static final String FIVE = "5"; //该页为家用大电

    public static final String REFERSH = "FollowActivity2CommonEvaluationFragment";
    public static final String REFERSH_PAGE = "LOGIN2CommonEvaluationFragment_REFERSHPAGE";

    @Inject
    CommonEvaluationPresenter mPresenter;

    private FragmentEvaluationCommonBinding mBinding;
    private List<EvaluationListBean.ListBean> mList;
    private CommonEvaluationAdapter mAdapter;

    private Boolean once = true;
    private String id = "0";//数据id
    private String headImg = "";//数据head图片
    private int page = 0;
    private Boolean refersh = true;
    private EvaluationListBean bean;//本地数据
    private Dialog dialog;

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
                    mAdapter.setHeadImg(headImg);
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
        mPresenter.getDate(id, page + "", this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
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
                if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.EvaluationTab,""))) {
                    EvaluationTabBean bean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.EvaluationTab), EvaluationTabBean.class);
                    headImg = bean.getList().get(0).getImg();
                }
            }
            once = false;
        }
        mAdapter = new CommonEvaluationAdapter(mList, getContext());
        mAdapter.setOnClickItem(this);
        mAdapter.setHeadImg(headImg);
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView, mBinding.swipeToLoad,getContext());
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
                    id = "";
                    headImg = bean.getList().get(0).getImg();
                } else if (getArguments().getString("type").equals(TWO)) {
                    id = bean.getList().get(1).getCategoryId();
                    headImg = bean.getList().get(1).getImg();
                } else if (getArguments().getString("type").equals(THREE)) {
                    id = bean.getList().get(2).getCategoryId();
                    headImg = bean.getList().get(2).getImg();
                } else if (getArguments().getString("type").equals(FORE)) {
                    id = bean.getList().get(3).getCategoryId();
                    headImg = bean.getList().get(3).getImg();
                } else if (getArguments().getString("type").equals(FIVE)) {
                    id = bean.getList().get(4).getCategoryId();
                    headImg = bean.getList().get(4).getImg();
                }
            } else {
                id = "0";
                headImg = "";
            }
            if (getArguments().getString("type").equals(ONE)) {
                mBinding.swipeToLoad.setRefreshing(true);
            }
        }
    }

    @Subscribe
    public void commentResher(EvaluationBus evaluationBus){
        if(evaluationBus != null){
            for (int i = 0; i < mList.size(); i++) {
                if(mList.get(i).getEvalWayId().equals(evaluationBus.getId())){
                    mList.get(i).setCommentNum(evaluationBus.getCount() + "");
                    mAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    @Subscribe
    public void concerRefresh(ConcerBus concerBus){
        if(concerBus != null && concerBus.getString().equals(CommonEvaluationFragment.REFERSH)){
            if(!once){//代表第一次已经看过该页了，所以当我的关注页面取消关注时需要刷新页面
                for (int i = 0; i < mList.size(); i++) {
                    if(mList.get(i).getUserShopmember().getUserId().equals(concerBus.getUserId())){
                        mList.get(i).setConcernNum(concerBus.getConcerNum());
                        mList.get(i).getUserShopmember().setFansCount(Integer.valueOf(concerBus.getFansNum()));
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Subscribe
    public void refershPage(CommonEvaluationBus commonEvaluationBus){
        if(commonEvaluationBus != null && commonEvaluationBus.getRefersh().equals(CommonEvaluationFragment.REFERSH_PAGE)){
            if(!once){
                if(mBinding.swipeToLoad.isRefreshEnabled()){
                    mBinding.swipeToLoad.setRefreshing(true);
                }else {
                    mBinding.swipeToLoad.setRefreshEnabled(true);
                    mBinding.recyclerView.scrollToPosition(0);
                    mBinding.swipeToLoad.setRefreshing(true);
                }
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
                mAdapter.setHeadImg(headImg);
                mAdapter.notifyDataSetChanged();
            } else {
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
                mBinding.recyclerView.setVisibility(View.GONE);
            }
        } else {
            stopLoading();
            if (evaluationListBean.getList() != null && evaluationListBean.getList().size() != 0) {
                mList.addAll(evaluationListBean.getList());
                mAdapter.notifyDataSetChanged();
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
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
            }
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        } else {
            stopLoading();
            page--;
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void concerDelSuccess(SuccessBean successBean,int pos) {
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        for (int i = 0; i < mList.size(); i++) {
            if(mList.get(pos).getUserId().equals(mList.get(i).getUserId())){
                mList.get(i).setConcernNum(0);
                mList.get(i).getUserShopmember().setFansCount(mList.get(i).getUserShopmember().getFansCount() - 1);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void concerInsSuccess(SuccessBean successBean,int pos) {
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(pos).getUserId().equals(mList.get(i).getUserId())) {
                mList.get(i).setConcernNum(1);
                mList.get(i).getUserShopmember().setFansCount(mList.get(i).getUserShopmember().getFansCount() + 1);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void concerFaile(String error) {
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
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
                if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.EvaluationTab,""))) {
                    EvaluationTabBean bean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.EvaluationTab), EvaluationTabBean.class);
                    headImg = bean.getList().get(1).getImg();
                }
                return new Gson().fromJson(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonEvaluationFragmentDATA2), EvaluationListBean.class);
            }
        } else if (getArguments().getString("type").equals(THREE)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonEvaluationFragmentDATA3, ""))) {
                if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.EvaluationTab,""))) {
                    EvaluationTabBean bean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.EvaluationTab), EvaluationTabBean.class);
                    headImg = bean.getList().get(2).getImg();
                }
                return new Gson().fromJson(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonEvaluationFragmentDATA3), EvaluationListBean.class);
            }
        } else if (getArguments().getString("type").equals(FORE)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonEvaluationFragmentDATA4, ""))) {
                if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.EvaluationTab,""))) {
                    EvaluationTabBean bean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.EvaluationTab), EvaluationTabBean.class);
                    headImg = bean.getList().get(3).getImg();
                }
                return new Gson().fromJson(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonEvaluationFragmentDATA4), EvaluationListBean.class);
            }
        } else if (getArguments().getString("type").equals(FIVE)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.NETCACHE).getString(CommonDate.CommonEvaluationFragmentDATA5, ""))) {
                if (!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.EvaluationTab,""))) {
                    EvaluationTabBean bean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.EvaluationTab), EvaluationTabBean.class);
                    headImg = bean.getList().get(4).getImg();
                }
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
        mPresenter.getDate(id, page + "", this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }

    /**
     * 添加关注
     */
    @Override
    public void onAttenInsItem(String attentionUserId, int pos) {
        if (!SPUtils.getInstance(CommonDate.USER).getBoolean(CommonDate.userLogin, false)) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        dialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "请求中...");
        dialog.show();
        mPresenter.concernInsert(attentionUserId,pos,this.bindToLifecycle());
    }

    /**
     * 删除关注
     */
    @Override
    public void onAttenDecItem(String attentionUserId, int pos) {
        if (!SPUtils.getInstance(CommonDate.USER).getBoolean(CommonDate.userLogin, false)) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        dialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "请求中...");
        dialog.show();
        mPresenter.concernDelete(attentionUserId,pos,this.bindToLifecycle());
    }
}
