package com.example.administrator.jipinshop.activity.home.classification.questions.detail;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.QuestionDetailAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.QuestionsBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.databinding.ActivityQuestionDetailBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/7/3
 * @Describe 答题区详情
 */
public class QuestionDetailActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener, QuestionDetailView {

    @Inject
    QuestionDetailPresenter mPresenter;
    private ActivityQuestionDetailBinding mBinding;
    private QuestionDetailAdapter mAdapter;
    private List<QuestionsBean.DataBean.AnswerBean> mList;
    private int page = 1;
    private Boolean refersh = true;
    private int[] usableHeightPrevious = {0};
    private QuestionsBean.DataBean mBean;
    private Dialog mDialog;
    private int resultCode = 402;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_question_detail);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBean = (QuestionsBean.DataBean) getIntent().getSerializableExtra("date");
        mBinding.inClude.titleTv.setText("");
        mPresenter.setKeyListener(mBinding.detailContainer, usableHeightPrevious);
        mPresenter.initEdit(mBinding);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new QuestionDetailAdapter(mList,this);
        mAdapter.setBean(mBean);
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mBinding.swipeToLoad.setRefreshing(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                setResult(resultCode);
                finish();
                break;
            case R.id.detail_share:
                // TODO: 2019/7/3 分享
                break;
            case R.id.detail_bottomContainer:
                mBinding.detailEdit.requestFocus();
                showKeyboard(true);
                break;
            case R.id.detail_comment:
                //发表评论
                if (TextUtils.isEmpty(mBinding.detailEdit.getText().toString().trim())){
                    ToastUtil.show("请输入内容");
                    return;
                }
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                mDialog.show();
                mPresenter.addAnswer(mBinding.detailEdit.getText().toString(),mBean.getId(),this.bindToLifecycle());
                break;
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        refersh = true;
        mPresenter.answerList(page,mBean.getId(),this.bindToLifecycle());
    }

    @Override
    public void onLoadMore() {
        page++;
        refersh = false;
        mPresenter.answerList(page,mBean.getId(),this.bindToLifecycle());
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = mBinding.detailBottomContainer2;
            if (isHideInput(view, ev)) {
                showKeyboard(false);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof RelativeLayout)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyShow() {
        mBinding.detailBottomContainer2.setVisibility(View.VISIBLE);
    }

    @Override
    public void keyHint() {
        mBinding.detailBottomContainer2.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(SucBean<QuestionsBean.DataBean.AnswerBean> bean) {
        if (refersh){
            dissRefresh();
            mList.clear();
            mList.addAll(bean.getData());
            mAdapter.setBean(mBean);
            mAdapter.notifyDataSetChanged();
        }else {
            dissLoading();
            if (bean.getData().size() != 0){
                mList.addAll(bean.getData());
                mAdapter.notifyDataSetChanged();
                mBinding.swipeToLoad.setLoadMoreEnabled(false);
            }else {
                page--;
                ToastUtil.show("已经是最后一页了");
            }
        }
    }

    @Override
    public void onFile(String error) {
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
    public void onSuccessComment() {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        resultCode = 202;
        if (!mBinding.swipeToLoad.isRefreshEnabled()) {
            mBinding.swipeToLoad.setRefreshEnabled(true);
            mBinding.swipeToLoad.setRefreshing(true);
            mBinding.swipeToLoad.setRefreshEnabled(false);
        } else {
            mBinding.swipeToLoad.setRefreshing(true);
        }
        if (!mBinding.swipeToLoad.isRefreshEnabled()){
            mBinding.swipeToLoad.setLoadMoreEnabled(false);//目的是为了解决当浏览到最后一个时评论后，会触发上拉效果
        }
        mBean.setAnswerCount(mBean.getAnswerCount() + 1);
        mBinding.detailEdit.setText("");
        ToastUtil.show("发表成功");
    }

    @Override
    public void onFileComment(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void onBackPressed() {
        setResult(resultCode);
        super.onBackPressed();
    }
}
