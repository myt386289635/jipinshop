package com.example.administrator.jipinshop.activity.tryout.passedMore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.PassedMoreAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.PassedMoreBean;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 莫小婷
 * @create 2019/3/26
 * @Describe 更多申请成功名单
 */
public class PassedMoreActivity extends BaseActivity implements OnRefreshListener, PassedMoreView {

    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.more_title)
    TextView mMoreTitle;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.error_image)
    ImageView mErrorImage;
    @BindView(R.id.error_title)
    TextView mErrorTitle;
    @BindView(R.id.error_content)
    TextView mErrorContent;
    @BindView(R.id.qs_net)
    LinearLayout mQsNet;
    @BindView(R.id.swipeToLoad)
    SwipeToLoadLayout mSwipeToLoad;

    @Inject
    PassedMorePresenter mPresenter;

    private List<PassedMoreBean.DataBean> mList;
    private PassedMoreAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passed_more);
        mButterKnife = ButterKnife.bind(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mTitleTv.setText("申请成功名单");

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mList = new ArrayList<>();
        mAdapter = new PassedMoreAdapter(this,mList);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mRecyclerView,mSwipeToLoad);
        mSwipeToLoad.setOnRefreshListener(this);
        mSwipeToLoad.setRefreshing(true);
    }

    @OnClick({R.id.title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mButterKnife.unbind();
    }

    @Override
    public void onRefresh() {
        mPresenter.passedUserList(getIntent().getStringExtra("id"),this.bindToLifecycle());
    }

    public void dissRefresh(){
        if (mSwipeToLoad != null && mSwipeToLoad.isRefreshing()) {
            if (!mSwipeToLoad.isRefreshEnabled()) {
                mSwipeToLoad.setRefreshEnabled(true);
                mSwipeToLoad.setRefreshing(false);
                mSwipeToLoad.setRefreshEnabled(false);
            } else {
                mSwipeToLoad.setRefreshing(false);
            }
        }
    }


    public void initError(int id, String title, String content) {
        mQsNet.setVisibility(View.VISIBLE);
        mErrorImage.setBackgroundResource(id);
        mErrorTitle.setText(title);
        mErrorContent.setText(content);
    }

    @Override
    public void onSuccess(PassedMoreBean bean) {
        dissRefresh();
        if (bean.getStatus() == 3) {
            //试用中时显示
            String passedUserList = "<font color='#151515' >请以下用户于</font><font color='#E25838' >"
                    + bean.getReportEndTime() + "</font><font color='#151515' >前完成试用报告</font><br>按时提交的优秀试用报告将获得惊喜的奖励";
            mMoreTitle.setText(Html.fromHtml(passedUserList));
        }else {
            mMoreTitle.setVisibility(View.GONE);
        }
        if (bean.getData() != null && bean.getData().size() != 0){
            //有数据
            mQsNet.setVisibility(View.GONE);
            mList.clear();
            mList.addAll(bean.getData());
            mAdapter.notifyDataSetChanged();
        }else {
            //没有数据
            initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据");
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFaile(String error) {
        dissRefresh();
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
        mRecyclerView.setVisibility(View.GONE);
        ToastUtil.show(error);
    }
}
