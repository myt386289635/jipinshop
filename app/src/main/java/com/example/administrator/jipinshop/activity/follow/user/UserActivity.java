package com.example.administrator.jipinshop.activity.follow.user;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.UserAdapter;
import com.example.administrator.jipinshop.base.DaggerBaseActivityComponent;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.UserPageBean;
import com.example.administrator.jipinshop.bean.eventbus.ConcerBus;
import com.example.administrator.jipinshop.bean.eventbus.EvaluationBus;
import com.example.administrator.jipinshop.bean.eventbus.FollowBus;
import com.example.administrator.jipinshop.databinding.ActivityUserBinding;
import com.example.administrator.jipinshop.fragment.evaluation.common.CommonEvaluationFragment;
import com.example.administrator.jipinshop.util.NotchUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe 个人主页
 */
public class UserActivity extends RxAppCompatActivity implements UserAdapter.OnListener, OnLoadMoreListener, OnRefreshListener, UserView, View.OnClickListener {

    public static final String tag = "UserActivity2FollowActivity";

    @Inject
    UserPresenter mPresenter;

    private ActivityUserBinding mBinding;
    private ImmersionBar mImmersionBar;
    private List<UserPageBean.ListBean> mList;
    private UserAdapter mAdapter;

    private int page = 0;
    private boolean refersh = true;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_user);
        DaggerBaseActivityComponent.builder()
                .applicationComponent(MyApplication.getInstance().getComponent())
                .build().inject(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        EventBus.getDefault().register(this);
        mPresenter.setView(this);
        mBinding.setListener(this);
        if (Build.VERSION.SDK_INT >= 28) {
            //适配9.0刘海
            NotchUtil.notch(this);
        }
        initView();
    }

    private void initView() {
        mBinding.swipeTarget.setLayoutManager(new LinearLayoutManager(this));

        mList = new ArrayList<>();
        mAdapter = new UserAdapter(mList,this);
        mAdapter.setName(getIntent().getStringExtra("name"));
        mAdapter.setFans(getIntent().getIntExtra("fans",0));
        mAdapter.setHeadImage(getIntent().getStringExtra("image"));
        mAdapter.setIsfans(getIntent().getIntExtra("isFans",0));
        mAdapter.setOnListener(this);
        mBinding.swipeTarget.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setRefreshing(true);
    }

    @Override
    protected void onDestroy() {
        if (mImmersionBar != null)
            mImmersionBar.destroy(); //必须调用该方法，防止内存泄漏
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 关闭页面
     */
    @Override
    public void onFinish() {
        finish();
    }

    @Override
    public void onAttenInsItem(int pos) {
        dialog = (new ProgressDialogView()).createLoadingDialog(this, "请求中...");
        dialog.show();
        mPresenter.concernInsert(getIntent().getStringExtra("id"),this.bindToLifecycle());
    }

    @Override
    public void onAttenDecItem(int pos) {
        dialog = (new ProgressDialogView()).createLoadingDialog(this, "请求中...");
        dialog.show();
        mPresenter.concerDelete(getIntent().getStringExtra("id"),this.bindToLifecycle());
    }

    /**
     * 加载
     */
    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.getList(getIntent().getStringExtra("id"),page + "",this.bindToLifecycle());
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        page = 0;
        refersh = true;
        mPresenter.getList(getIntent().getStringExtra("id"),page + "",this.bindToLifecycle());
    }

    @Override
    public void onSuccess(UserPageBean userPageBean) {
        mBinding.netClude.qsNet.setVisibility(View.GONE);
        if(refersh){
            dissRefresh();
            mList.clear();
            mList.addAll(userPageBean.getList());
            mAdapter.notifyDataSetChanged();
        }else {
            dissLoading();
            if(userPageBean.getList() != null && userPageBean.getList().size() != 0){
                mList.addAll(userPageBean.getList());
                mAdapter.notifyDataSetChanged();
            }else {
                page--;
                Toast.makeText(this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFile(String error) {
        if(refersh){
            dissRefresh();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势点击试试");
        }else {
            dissLoading();
            page--;
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 删除关注  成功回调
     */
    @Override
    public void ConcerDelSuccess(SuccessBean successBean) {
        EventBus.getDefault().post(new ConcerBus(CommonEvaluationFragment.REFERSH,0,(mAdapter.getFans() - 1)+ "",getIntent().getStringExtra("id")));//刷新评测首页
        EventBus.getDefault().post(new FollowBus(UserActivity.tag));
        dissRefresh();
        mAdapter.setIsfans(0);
        mAdapter.setFans(mAdapter.getFans() - 1);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 添加关注、删除关注 失败回调
     */
    @Override
    public void ConcerDelFaile(String error) {
        dissRefresh();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 添加关注  成功回调
     */
    @Override
    public void concerInsSuccess(SuccessBean successBean) {
        EventBus.getDefault().post(new ConcerBus(CommonEvaluationFragment.REFERSH,1,(mAdapter.getFans() + 1)+ "",getIntent().getStringExtra("id")));//刷新评测首页
        EventBus.getDefault().post(new FollowBus(UserActivity.tag));
        dissRefresh();
        mAdapter.setIsfans(1);
        mAdapter.setFans(mAdapter.getFans() + 1);
        mAdapter.notifyDataSetChanged();
    }

    public void dissRefresh(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            mBinding.swipeToLoad.setRefreshing(false);
        }
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    public void dissLoading(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore()) {
            mBinding.swipeToLoad.setLoadingMore(false);
        }
    }


    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.net_clude:
                if(mBinding.netClude.errorTitle.getText().toString().equals("网络出错")){
                    dialog = (new ProgressDialogView()).createLoadingDialog(this, "请求中...");
                    dialog.show();
                    onRefresh();
                }
                break;
        }
    }

    @Subscribe
    public void commentResher(EvaluationBus evaluationBus){
        if(evaluationBus != null){
            for (int i = 0; i < mList.size(); i++) {
                if(mList.get(i).getArticleId().equals(evaluationBus.getId())){
                    mList.get(i).setCommentNum(evaluationBus.getCount() + "");
                    mAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }
}
