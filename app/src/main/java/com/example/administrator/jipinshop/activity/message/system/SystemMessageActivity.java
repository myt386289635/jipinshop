package com.example.administrator.jipinshop.activity.message.system;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.SystemMessageAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SystemMessageBean;
import com.example.administrator.jipinshop.databinding.ActivityMessageSystemBinding;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

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
public class SystemMessageActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener, SystemMessageView {

    public static final String tag = "SystemMsgDetailActivity2SystemMessageActivity";

    @Inject
    SystemMessagePresenter mPresenter;

    private ActivityMessageSystemBinding mBinding;

    private SystemMessageAdapter mAdapter;
    private List<SystemMessageBean.ListBean> mList;

    /**
     * 页数
     */
    private int page = 0;
    /**
     * 记录是刷新还是加载
     */
    private Boolean refersh = true;
    private Dialog dialog;

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
        mBinding.inClude.titleTv.setText("系统消息");
        mBinding.swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new SystemMessageAdapter(this, mList);
        mBinding.swipeTarget.setAdapter(mAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mBinding.swipeToLoad.setRefreshing(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
     * 刷新
     */
    @Override
    public void onRefresh() {
        page = 0;
        refersh = true;
        mPresenter.messageAll(page + "" ,this.bindToLifecycle());
    }

    /**
     * 加载
     */
    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.messageAll(page + "",this.bindToLifecycle());
    }

    /**
     * 数据加载成功
     */
    @Override
    public void Success(SystemMessageBean systemMessageBean) {
        if(refersh){
            dissRefresh();
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
        }else {
            dissLoading();
        }
        if (systemMessageBean.getList() != null && systemMessageBean.getList().size() != 0){
            //有数据
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            if(refersh){
                mList.clear();
            }
            mList.addAll(systemMessageBean.getList());
            mAdapter.notifyDataSetChanged();
        }else {
            //没有数据
            if(refersh){
                //刷新时
                initError(R.mipmap.qs_news, "暂无消息", "还没有任何消息哦，先休息一下吧");
            }else {
                //加载时
                page--;
                Toast.makeText(this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 数据加载失败
     */
    @Override
    public void Faile(String error) {
        if(refersh){
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
            dissRefresh();
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势点击试试");
        }else {
            dissLoading();
            page--;
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onRefersh(String s){
        if(!TextUtils.isEmpty(s) && s.equals(SystemMessageActivity.tag)){
            mBinding.swipeToLoad.setRefreshing(true);
        }
    }
}
