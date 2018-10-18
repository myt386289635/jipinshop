package com.example.administrator.jipinshop.activity.follow;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.follow.user.UserActivity;
import com.example.administrator.jipinshop.adapter.FollowAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.databinding.ActivityFollowBinding;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe 我的关注
 */
public class FollowActivity extends BaseActivity implements OnRefreshListener, FollowAdapter.OnItemClick, View.OnClickListener, OnLoadMoreListener, FollowView {

    @Inject
    FollowPresenter mPresenter;

    private ActivityFollowBinding mBinding;
    private List<FollowBean.ListBean> mList;
    private FollowAdapter mAdapter;

    //页数
    private int page = 0;
    private Boolean refresh = true;//判断是刷新还是加载
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_follow);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mPresenter.setView(this);

        mBinding.inClude.titleTv.setText("我的关注");

        mBinding.swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new FollowAdapter(mList,this);
        mAdapter.setOnItemClick(this);
        mBinding.swipeTarget.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setRefreshing(true);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);

        mPresenter.solveScoll(mBinding.swipeTarget);
    }

    /**
     * 刷新开始
     */
    @Override
    public void onRefresh() {
        page = 0;
        refresh = true;
        mPresenter.concer(page,this.bindToLifecycle());
    }

    /**
     * item点击事件
     * @param pos
     */
    @Override
    public void onItem(int pos) {
        startActivity(new Intent(this, UserActivity.class));
    }

    /**
     * 取消关注
     * @param pos
     */
    @Override
    public void onItemDecAtten(int pos,TextView textView) {

        DialogUtil.LoginDialog(this, "您确定要取消对" + mList.get(pos).getFrom_nickname() +"的关注吗？", "不再关注", "取消", (View.OnClickListener) view -> {
            Dialog dialog = (new ProgressDialogView()).createLoadingDialog(FollowActivity.this, "请求中...");
            dialog.show();
            mPresenter.concerDelete(textView,pos,dialog,mList.get(pos).getConcernId(),FollowActivity.this.bindToLifecycle());
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.net_clude:
                if(mBinding.netClude.errorTitle.getText().toString().equals("网络出错")){
                    dialog = (new ProgressDialogView()).createLoadingDialog(this, "请求中...");
                    dialog.show();
                    onRefresh();
                }
                break;
        }
    }

    /**
     * 数据加载
     */
    @Override
    public void onLoadMore() {
        page++;
        refresh = false;
        mPresenter.concer(page,this.bindToLifecycle());
    }

    /**
     * 数据请求成功
     * @param followBean
     */
    @Override
    public void FollowSuccess(FollowBean followBean) {
        if(refresh){
            dissRefresh();
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
        }else {
            dissLoading();
        }
        if (followBean.getList() != null && followBean.getList().size() != 0) {
            //数据不为空时
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            if(refresh){
                mList.clear();
            }
            mList.addAll(followBean.getList());
            mAdapter.notifyDataSetChanged();
        }else {
            if(!refresh){
                //加载数据为空时
                page--;
                Toast.makeText(this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
            }else {
                //刷新数据为空时
                initError(R.mipmap.qs_collection, "暂无关注", "关注内容为空，先去逛逛吧");
            }
        }
    }

    /**
     * 数据请求失败
     * @param throwable 接口请求错误
     */
    @Override
    public void FollowFaileNet(String throwable) {
        //网络请求失败时
        if(refresh){
            //刷新失败
            dissRefresh();
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势点击试试");
        }else {
            //加载时失败
            dissLoading();
            page--;
            Toast.makeText(this, "网络出错", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 数据请求失败
     * @param error 后台返回不是200
     */
    @Override
    public void FollowFaileCode(String error) {
        //请求成功，但服务器拒绝返回数据
        if(refresh){
            //刷新时
            dissRefresh();
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势点击试试");
        }else {
            //加载时
            dissLoading();
            page--;
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取消关注回调
     * @param successBean
     */
    @Override
    public void ConcerDelSuccess(SuccessBean successBean,TextView textView, int pos) {
        if(successBean.getCode() == 200){
            textView.setBackgroundResource(R.drawable.bg_attention);
            textView.setTextColor(getResources().getColor(R.color.color_E31436));
            textView.setText("+关注");
            mBinding.swipeToLoad.setRefreshing(true);
        }else {
            Toast.makeText(this, successBean.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    public void dissRefresh(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            mBinding.swipeToLoad.setRefreshing(false);
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
}
