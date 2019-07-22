package com.example.administrator.jipinshop.activity.home.classification.questions.detail;

import android.app.Dialog;
import android.content.Intent;
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
import com.example.administrator.jipinshop.bean.VoteBean;
import com.example.administrator.jipinshop.databinding.ActivityQuestionDetailBinding;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/7/3
 * @Describe 答题区详情
 */
public class QuestionDetailActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener, QuestionDetailView, ShareBoardDialog.onShareListener, QuestionDetailAdapter.OnClickLayout {

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
    private boolean isCollect = false;//标志：是否收藏过此商品 false:没有

    private String shareImage = "";
    private String shareName = "";
    private String shareContent = "";
    private String shareUrl = "";
    private ShareBoardDialog mShareBoardDialog;

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

        shareName = mBean.getTitle();
        shareUrl = "https://lanhuapp.com/web/#/item/project/board?pid=a2e5a6a3-e7c0-4b07-95dd-3c261bba6bac";
        if (mBean.getAnswerCount() == 0){
            shareContent = "参与回答";
        }else {
            shareContent = "回答：" + mBean.getAnswerCount();
        }
        //是否收藏过
        if (mBean.getCollect() == 1) {
            isCollect = true;
            mBinding.itemComment.setImageResource(R.mipmap.tab_favor_sel);
            mBinding.itemComment.setColorFilter(R.color.color_E25838);
        } else {
            isCollect = false;
            mBinding.itemComment.setImageResource(R.mipmap.tab_favor_nor);
            mBinding.itemComment.setColorFilter(R.color.color_9D9D9D);
        }

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new QuestionDetailAdapter(mList,this);
        mAdapter.setBean(mBean);
        mAdapter.setOnClickLayout(this);
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
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = new ShareBoardDialog();
                    mShareBoardDialog.setOnShareListener(this);
                }
                if (!mShareBoardDialog.isAdded()) {
                    mShareBoardDialog.show(getSupportFragmentManager(), "ShareBoardDialog");
                }
                break;
            case R.id.detail_editTv:
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
            case R.id.item_comment:
                //收藏
                if (ClickUtil.isFastDoubleClick(1000)) {
                    ToastUtil.show("您点击太快了，请休息会再点");
                    return;
                } else {
                    if (isCollect) {
                        //收藏过了
                        mPresenter.collectDelete(mBean.getId(),this.bindToLifecycle());
                    } else {
                        //没有收藏
                        mPresenter.collectInsert(mBean.getId(),this.bindToLifecycle());
                    }
                }
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
    public void concerDelSuccess() {
        resultCode = 202;
        mBean.getUser().setFollow("0");
        mAdapter.notifyItemChanged(0);
        ToastUtil.show("取消关注成功");
    }

    @Override
    public void concerInsSuccess() {
        resultCode = 202;
        mBean.getUser().setFollow("1");
        mAdapter.notifyItemChanged(0);
        ToastUtil.show("关注成功");
    }

    @Override
    public void onSucCommentSnapIns(int position, VoteBean voteBean) {
        mList.get(position).setVote("1");
        mList.get(position).setVoteCount(voteBean.getData() + "");
        mAdapter.notifyItemChanged(position + 1);
        if(!voteBean.getMsg().equals("success")){
            ToastUtil.show(voteBean.getMsg());
        }
    }

    @Override
    public void onSucCommentSnapDel(int position, VoteBean voteBean) {
        mList.get(position).setVote("0");
        mList.get(position).setVoteCount(voteBean.getData() + "");
        mAdapter.notifyItemChanged(position + 1);
    }

    @Override
    public void onSucCollectInsert() {
        ToastUtil.show("收藏成功");
        isCollect = true;
        mBinding.itemComment.setImageResource(R.mipmap.tab_favor_sel);
        mBinding.itemComment.setColorFilter(R.color.color_E25838);
    }

    @Override
    public void onSucCollectDelete() {
        ToastUtil.show("取消收藏");
        isCollect = false;
        mBinding.itemComment.setImageResource(R.mipmap.tab_favor_nor);
        mBinding.itemComment.setColorFilter(R.color.color_9D9D9D);
    }

    @Override
    public void onBackPressed() {
        setResult(resultCode);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    public void share(SHARE_MEDIA share_media) {
        mPresenter.taskFinish(this.bindUntilEvent(ActivityEvent.DESTROY));
        new ShareUtils(this, share_media)
                .shareWeb(this, shareUrl, shareName, shareContent, shareImage, R.mipmap.share_logo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 点赞
     */
    @Override
    public void onClickGood(int postion) {
        if (ClickUtil.isFastDoubleClick(1000)) {
            ToastUtil.show("您点击太快了，请休息会再点");
            return;
        }else{
            mPresenter.snapInsert(postion,mList.get(postion).getId(),this.bindToLifecycle());
        }
    }

    /**
     * 取消点赞
     */
    @Override
    public void onClickUnGood(int position) {
        if (ClickUtil.isFastDoubleClick(1000)) {
            ToastUtil.show("您点击太快了，请休息会再点");
            return;
        }else{
            mPresenter.snapDelete(position,mList.get(position).getId(),this.bindToLifecycle());
        }
    }

    /**
     * 关注
     */
    @Override
    public void onClickFollow() {
        if (ClickUtil.isFastDoubleClick(1000)) {
            ToastUtil.show("您点击太快了，请休息会再点");
            return;
        }else{
            mPresenter.concernInsert(mBean.getUser().getUserId(),this.bindToLifecycle());
        }
    }

    /**
     * 取消关注
     */
    @Override
    public void onClickUnFollow() {
        if (ClickUtil.isFastDoubleClick(1000)) {
            ToastUtil.show("您点击太快了，请休息会再点");
            return;
        }else{
            mPresenter.concernDelete(mBean.getUser().getUserId(),this.bindToLifecycle());
        }
    }
}
