package com.example.administrator.jipinshop.activity.message;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.message.saction.MessageActionActivity;
import com.example.administrator.jipinshop.activity.message.sdaily.MessageDailyActivity;
import com.example.administrator.jipinshop.activity.message.system.MessageSystemActivity;
import com.example.administrator.jipinshop.adapter.MessageAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.MessageBean;
import com.example.administrator.jipinshop.databinding.ActivityMessageSystemBinding;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener, MessageAdapter.OnItem, OnRefreshListener, MessageView {

    @Inject
    MessagePresenter mPresenter;

    private ActivityMessageSystemBinding mBinding;
    private MessageAdapter mAdapter;
    private List<MessageBean.DataBean> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_message_system);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("消息");
        mBinding.messageTitle.setVisibility(View.GONE);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new MessageAdapter(this, mList);
        mAdapter.setOnItem(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setLoadMoreEnabled(false);
        mBinding.swipeToLoad.post(() -> mBinding.swipeToLoad.setRefreshing(true));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        switch (mList.get(position).getId()){
            case "1"://每日好货
                startActivity(new Intent(this, MessageDailyActivity.class)
                        .putExtra("title",mList.get(position).getTitle())
                        .putExtra("id", mList.get(position).getId())
                );
                break;
            case "5"://佣金消息
            case "4"://系统消息
                startActivity(new Intent(this, MessageSystemActivity.class)
                        .putExtra("title",mList.get(position).getTitle())
                        .putExtra("id", mList.get(position).getId())
                );
                break;
            case "2"://活动公告
            case "3"://福利消息
                startActivity(new Intent(this, MessageActionActivity.class)
                        .putExtra("title",mList.get(position).getTitle())
                        .putExtra("id", mList.get(position).getId())
                );
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.message(this.bindToLifecycle());
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

    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    @Override
    public void onSuccess(MessageBean bean) {
        dissRefresh();
        if (bean.getData() != null && bean.getData().size() != 0){
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mList.clear();
            mList.addAll(bean.getData());
            mAdapter.notifyDataSetChanged();
        }else {
            initError(R.mipmap.qs_news, "暂无数据", "还没有任何数据哦，先休息一下吧");
            mBinding.recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFile(String error) {
        dissRefresh();
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
        mBinding.recyclerView.setVisibility(View.GONE);
        ToastUtil.show(error);
    }
}
