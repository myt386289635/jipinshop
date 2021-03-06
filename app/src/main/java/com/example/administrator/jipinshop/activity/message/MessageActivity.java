package com.example.administrator.jipinshop.activity.message;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.SystemMessageAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SystemMessageBean;
import com.example.administrator.jipinshop.databinding.ActivityMessageSystemBinding;
import com.example.administrator.jipinshop.jpush.JPushReceiver;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener, MessageView, SystemMessageAdapter.OnItem {

    public static final String tag = "SystemMsgDetailActivity2SystemMessageActivity";

    @Inject
    MessagePresenter mPresenter;

    private ActivityMessageSystemBinding mBinding;
    private SystemMessageAdapter mAdapter;
    private List<SystemMessageBean.DataBean> mList;
    private Dialog mDialog;
    //页数
    private int page = 1;
    //记录是刷新还是加载
    private Boolean refersh = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_message_system);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mBinding.messageTitle.setVisibility(View.VISIBLE);
        mBinding.inClude.titleTv.setText("系统消息");
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new SystemMessageAdapter(this, mList);
        mAdapter.setOnItem(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mBinding.swipeToLoad.post(() -> mBinding.swipeToLoad.setRefreshing(true));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.message_title:
                //全部已读
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                mPresenter.readMsgAll(this.bindToLifecycle());
                break;
        }
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        page = 1;
        refersh = true;
        mPresenter.messageAll(page + "" ,this.bindUntilEvent(ActivityEvent.DESTROY));
    }

    /**
     * 加载
     */
    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.messageAll(page + "",this.bindUntilEvent(ActivityEvent.DESTROY));
    }

    /**
     * 数据加载成功
     */
    @Override
    public void Success(SystemMessageBean systemMessageBean) {
        if(refersh){
            dissRefresh();
        }else {
            dissLoading();
        }
        if (systemMessageBean.getData() != null && systemMessageBean.getData().size() != 0){
            //有数据
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            if(refersh){
                mList.clear();
            }
            mList.addAll(systemMessageBean.getData());
            mAdapter.notifyDataSetChanged();
            if(!refersh){
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            }
        }else {
            //没有数据
            if(refersh){
                //刷新时
                initError(R.mipmap.qs_news, "暂无消息", "还没有任何消息哦，先休息一下吧");
                mBinding.recyclerView.setVisibility(View.GONE);
            }else {
                //加载时
                page--;
                ToastUtil.show("已经是最后一页了");
            }
        }
    }

    /**
     * 数据加载失败
     */
    @Override
    public void Faile(String error) {
        if(refersh){
            dissRefresh();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
            mBinding.recyclerView.setVisibility(View.GONE);
        }else {
            dissLoading();
            page--;
        }
        ToastUtil.show(error);
    }

    @Override
    public void onSuc() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setStatus(1);
        }
        mAdapter.notifyDataSetChanged();
        ToastUtil.show("已读成功");
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    public void dissRefresh(){
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

    public void dissLoading(){
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

    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public  void  unMessage(String s){
        if(!TextUtils.isEmpty(s) && s.equals(JPushReceiver.TAG)){
            if(mBinding != null && mBinding.swipeToLoad != null && !mBinding.swipeToLoad.isRefreshing()){
                if (!mBinding.swipeToLoad.isRefreshEnabled()) {
                    mBinding.swipeToLoad.setRefreshEnabled(true);
                    mBinding.swipeToLoad.setRefreshing(true);
                    mBinding.swipeToLoad.setRefreshEnabled(false);
                } else {
                    mBinding.swipeToLoad.setRefreshing(true);
                }
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        mList.get(position).setStatus(1);
        mAdapter.notifyDataSetChanged();
        mPresenter.readMsg(mList.get(position).getMessageUserId(),this.bindUntilEvent(ActivityEvent.DESTROY));
        if (!mList.get(position).getType().equals("0")){
            ShopJumpUtil.openBanner(this,mList.get(position).getType(),
                    mList.get(position).getTargetId(),mList.get(position).getTitle(),
                    mList.get(position).getSource(), mList.get(position).getRemark());
        }
    }

    @Override
    public void onItemLongClick(int position) {
        DialogUtil.messageDetele(this, v -> {
            mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
            mDialog.show();
            mPresenter.deleteById(position , mList.get(position).getMessageUserId(),this.bindToLifecycle());
        });
    }

    @Override
    public void onDelete(int position) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mList.remove(position);
        mAdapter.notifyItemRemoved(position);
        if(position != mList.size()) {
            mAdapter.notifyItemRangeChanged(position,mList.size() - position);
        }
        if (mList.size() == 0){
            initError(R.mipmap.qs_news, "暂无消息", "还没有任何消息哦，先休息一下吧");
            mBinding.recyclerView.setVisibility(View.GONE);
        }
    }
}
