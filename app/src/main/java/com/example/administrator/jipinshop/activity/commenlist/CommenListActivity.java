package com.example.administrator.jipinshop.activity.commenlist;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.adapter.CommenListAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingCommon2Adapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.VoteBean;
import com.example.administrator.jipinshop.bean.eventbus.CommenBus;
import com.example.administrator.jipinshop.bean.eventbus.CommonEvaluationBus;
import com.example.administrator.jipinshop.bean.eventbus.EvaluationBus;
import com.example.administrator.jipinshop.bean.eventbus.FindBus;
import com.example.administrator.jipinshop.databinding.ActivityCommenlistBinding;
import com.example.administrator.jipinshop.fragment.evaluation.common.CommonEvaluationFragment;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/3
 * @Describe 评论列表
 */
public class CommenListActivity extends BaseActivity implements CommenListAdapter.OnItemReply, View.OnClickListener, CommenListAdapter.OnMoreUp, CommenListView, OnRefreshListener, OnLoadMoreListener, CommenListAdapter.OnGoodItem {

    public static final String commentResher = "ShoppingDetailActivity_commentResher";

    @Inject
    CommenListPresenter mPresenter;

    private ActivityCommenlistBinding mBinding;
    private CommenListAdapter mAdapter;
    private List<CommentBean.DataBean> mList;

    private int pos;//点击进来的位置。这里是为了商品详情中点击查看更多准备
    private List<Integer> sets;//记录每个留言的二级展示条数

    private int page = 1;
    private Boolean refresh = true;
    //是否是首次进入页面
    private boolean once = true;

    /**
     * 父评论id
     */
    private String parentId = "0";
    private String toUserId = "0";

    private Dialog mDialog;
    private int[] usableHeightPrevious = {0};

    /**
     * 记录回复楼层的层数  -1:初始值，可以用来表示没有楼层
     */
    private int parentNum = -1;

    /**
     * 判断软键盘是关闭还是打开的
     */
    private Boolean imi = false;

    //判断是否点击的是“回复”按钮，记录该textview用来判断点击位置
    private List<TextView> mTextViews = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_commenlist);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mPresenter.setView(this);
        pos = getIntent().getIntExtra("position", -1);
        mBinding.titleContainer.titleTv.setText("所有评论(" + 0 + ")");
        mBinding.swipeTarget.setLayoutManager(new LinearLayoutManager(this){
            //将返回LayoutManager应该预留的额外空间(即：手机屏幕以外的预留空间，
            // 这样做不会造成到item时卡顿，而是在还未到item时卡顿体验能好点)
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        });
        mList = new ArrayList<>();
        sets = new ArrayList<>();
        mAdapter = new CommenListAdapter(mList, this);
        mAdapter.setOnItemReply(this);
        mAdapter.setSets(sets);
        mAdapter.setOnMoreUp(this);
        mAdapter.setOnGoodItem(this);

        mBinding.swipeTarget.setItemViewCacheSize(10);
        mBinding.swipeTarget.setDrawingCacheEnabled(true);//耗内存
        mBinding.swipeTarget.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);//耗内存

        mBinding.swipeTarget.setAdapter(mAdapter);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mBinding.swipeToLoad.setRefreshing(true);

        mPresenter.setKeyListener(mBinding.detailContanier, usableHeightPrevious);
        mPresenter.solveScoll(mBinding.swipeTarget,this);
    }

    /**
     * 回复一级评论
     *
     * @param pos
     * @param textView
     */
    @Override
    public void onItemReply(int pos, TextView textView) {
        if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        parentId = mList.get(pos).getCommentId();
        toUserId = "0";
        parentNum = pos;
        mBinding.keyEdit.requestFocus();
        showKeyboard(true);
        mBinding.keyEdit.setHint("回复"+mList.get(pos).getUserNickname());
        mTextViews.clear();
        mTextViews.add(textView);
    }

    /**
     * 点击二级评论更多
     *
     * @param pos 加载的条数
     *            postion 位置
     */
    @Override
    public void onItemTwoReply(int pos, ShoppingCommon2Adapter mAdapter, int postion) {
        sets.remove(postion);
        sets.add(postion, pos);
        mAdapter.setNumber(sets.get(postion));
        mAdapter.notifyDataSetChanged();
    }

    public void hintKey() {
        if (mImm.isActive() && imi) {
            // 如果开启
            mImm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (imi && ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = mBinding.detailKeyLayout;
            if (isHideInput(view, ev)) {
                hintKey();
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
            if(mTextViews != null && mTextViews.size() == 1){
                mTextViews.get(0).getLocationInWindow(l);
                int left1 = l[0], top1 = l[1], bottom1 = top1 + mTextViews.get(0).getHeight(), right1 = left1
                        + mTextViews.get(0).getWidth();
                if ((ev.getX() > left && ev.getX() < right && ev.getY() > top
                        && ev.getY() < bottom)) {
                    return false;
                } else {
                    if ((ev.getX() > left1 && ev.getX() < right1 && ev.getY() > top1
                            && ev.getY() < bottom1)){
                        return false;
                    }else {
                        return true;
                    }
                }
            }else {
                if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                        && ev.getY() < bottom ) {
                    return false;
                } else {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                hintKey();
                finish();
                break;
            case R.id.key_text:
                if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                if (TextUtils.isEmpty(mBinding.keyEdit.getText().toString())) {
                    ToastUtil.show("请输入评论");
                    return;
                }
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                mDialog.show();
                mPresenter.commentInsert(getIntent().getStringExtra("id"),toUserId, mBinding.keyEdit.getText().toString(), parentId,getIntent().getStringExtra("type"), this.bindToLifecycle());
                break;
            case R.id.key_edit:
                if(!imi){
                    //不是打开的时候点击
                    parentId = "0";
                    toUserId = "0";
                    parentNum = -1;
                }
                break;
        }
    }

    @Override
    public void onUp(int position, ShoppingCommon2Adapter mAdapter) {
        sets.remove(position);
        sets.add(position, 2);
        mAdapter.setNumber(sets.get(position));
        mAdapter.notifyDataSetChanged();
        mBinding.swipeTarget.scrollToPosition(position);
    }

    @Override
    public void keyShow() {
        imi = true;
    }

    @Override
    public void keyHint() {
        imi = false;
        mBinding.keyEdit.setHint("点击输入评论");
    }

    @Override
    public void onSucComment(CommentBean commentBean) {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            mBinding.swipeToLoad.setRefreshing(false);
        }
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore()) {
            mBinding.swipeToLoad.setLoadingMore(false);
        }
        if (commentBean.getData() != null && commentBean.getData().size() != 0) {
            if (refresh) {
                //这里的beanList 是用来记录上一次数据的，用来判断是否有新数据添加进入
                List<CommentBean.DataBean> beanList = new ArrayList<>();
                if (mList.size() != 0){
                    beanList.add(mList.get(0));
                }
                mList.clear();
                mList.addAll(commentBean.getData());
                if (once) {
                    initSets();
                } else {
                    if(beanList.size() != 0){
                        ResherSets(beanList);
                    }else {
                        initSets();
                    }
                }
                if (getIntent().getStringExtra("type").equals("2")){
                    //表示从评测进来的
                    EventBus.getDefault().post(new EvaluationBus(getIntent().getStringExtra("id"),commentBean.getTotal()));
                }else if(getIntent().getStringExtra("type").equals("3")){
                    //表示从发现进来的
                    EventBus.getDefault().post(new FindBus(commentBean.getTotal()));
                }else {
                    //表示从商品进来的。
                    EventBus.getDefault().post(new CommenBus(CommenListActivity.commentResher));
                }
            } else {
                int i = mList.size();
                mList.addAll(commentBean.getData());
                onLodeSets(i);
            }
            mAdapter.notifyDataSetChanged();
        } else {
            if (!refresh) {
                page--;
                ToastUtil.show("已经是最后一页了");
            }
        }
        mBinding.titleContainer.titleTv.setText("所有评论(" + commentBean.getTotal() + ")");
        if (once && pos != -1) {
            mBinding.swipeTarget.scrollToPosition(pos);
            once = false;
        } else if (once) {
            once = false;
        }

    }

    @Override
    public void onFileComment(String error) {
        if(!refresh){
            page--;
            if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore()) {
                mBinding.swipeToLoad.setLoadingMore(false);
            }
        }else {
            if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
                mBinding.swipeToLoad.setRefreshing(false);
            }
        }
        ToastUtil.show(error);
    }

    @Override
    public void onSucCommentInsert(SuccessBean successBean) {
        if (!parentId.equals("0")) {
            //回复评论
            CommentBean.DataBean.ChildrenBean listBean = new CommentBean.DataBean.ChildrenBean();
            listBean.setContent(mBinding.keyEdit.getText().toString());
            listBean.setUserNickname(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName));
            mList.get(parentNum).getChildren().add(listBean);
            if (mList.get(parentNum).getChildren().size() <= 2) {
                sets.remove(parentNum);
                sets.add(parentNum, mList.get(parentNum).getChildren().size());
            } else {
                if (sets.get(parentNum) != 2 && (sets.get(parentNum) % 10) != 0) {
                    int num = sets.remove(parentNum);
                    sets.add(parentNum, num + 1);
                }
            }
            mAdapter.notifyItemChanged(parentNum);
            EventBus.getDefault().post(new CommenBus(CommenListActivity.commentResher));
        } else {
            //回复楼层
            mBinding.swipeTarget.scrollToPosition(0);
            onRefresh();
        }
        mBinding.keyEdit.setText("");
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onFileCommentInsert(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    /**
     * 评论点赞成功回调
     */
    @Override
    public void onSucCommentSnapIns(int position, VoteBean successBean) {
        if(successBean.getCode() == 0){
            mList.get(position).setVote(1);
            BigDecimal bigDecimal = new BigDecimal(mList.get(position).getVoteCount());
            int num = bigDecimal.intValue();
            mList.get(position).setVoteCount((num + 1));
            mAdapter.notifyItemChanged(position);
            EventBus.getDefault().post(new CommenBus(CommenListActivity.commentResher));
        }else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }

    /**
     * 评论删除点赞成功回调
     */
    @Override
    public void onSucCommentSnapDel(int position, SuccessBean successBean) {
        if(successBean.getCode() == 0){
            mList.get(position).setVote(0);
            BigDecimal bigDecimal = new BigDecimal(mList.get(position).getVoteCount());
            int num = bigDecimal.intValue();
            mList.get(position).setVoteCount((num - 1));
            mAdapter.notifyItemChanged(position);
            EventBus.getDefault().post(new CommenBus(CommenListActivity.commentResher));
        }else {
            //602
            startActivity(new Intent(this, LoginActivity.class));
            SPUtils.getInstance(CommonDate.USER).clear();
        }
    }

    /**
     * 点赞、删除点赞  失败回调
     */
    @Override
    public void onFileSnap(String error) {
        ToastUtil.show(error);
    }

    /***
     * 刷新
     */
    @Override
    public void onRefresh() {
        refresh = true;
        page = 1;
        mPresenter.comment(getIntent().getStringExtra("type"),page + "", getIntent().getStringExtra("id"), this.bindToLifecycle());
    }

    @Override
    public void onLoadMore() {
        refresh = false;
        page++;
        mPresenter.comment(getIntent().getStringExtra("type"),page + "", getIntent().getStringExtra("id"), this.bindToLifecycle());
    }

    /**
     * 第一次进入页面时，初始化sets数组
     */
    public void initSets() {
        for (int i = 0; i < mList.size(); i++) {
            if (pos == i && pos != -1) {
                //这里判断的是二级评论的条数
                if (mList.get(i).getChildren().size() <= 10) {
                    sets.add(mList.get(i).getChildren().size());
                } else {
                    sets.add(10);
                }
            } else {
                //这里判断的是二级评论的条数
                if (mList.get(i).getChildren().size() <= 2) {
                    sets.add(mList.get(i).getChildren().size());
                } else {
                    sets.add(2);
                }
            }
        }
    }

    /**
     * 不是第一次，刷新列表时，sets数组的逻辑
     */
    private void ResherSets(List<CommentBean.DataBean> beanList) {
        List<Integer> timer = new ArrayList<>();
        if (mList.size() >= 10) {
            ResherSets2(timer,beanList,10);
        }else {
            ResherSets2(timer,beanList, mList.size());
        }
        sets.clear();
        sets.addAll(timer);
    }

    private void ResherSets2(List<Integer> timer,List<CommentBean.DataBean> beanList,int size) {
        int num = 0;//记录新加入的条数
        for (int i = 0; i < size; i++) {
            if(!beanList.get(0).getCommentId().equals(mList.get(i).getCommentId())){
                //有新评论进来了
                if (mList.get(i).getChildren().size() <= 2) {
                    timer.add(mList.get(i).getChildren().size());
                } else {
                    timer.add(2);
                }
                num++;
            }else {
                break;
            }
        }
        for (int i = 0; i < size - num; i++) {
            timer.add(sets.get(i));
        }
    }

    /**
     * 加载数据时，sets数组的逻辑
     */
    private void onLodeSets(int pos) {
        for (int i = pos; i < mList.size(); i++) {
            if (mList.get(i).getChildren().size() <= 2) {
                sets.add(mList.get(i).getChildren().size());
            } else {
                sets.add(2);
            }
        }
    }

    @Override
    public void onGood(int flag, int position) {
        if (ClickUtil.isFastDoubleClick(1000)) {
            ToastUtil.show("您点击太快了，请休息会再点");
            return;
        }else{
            if(flag == 1){
                //取消点赞
                mPresenter.snapDelete(position,mList.get(position).getCommentId(),this.bindToLifecycle());
            }else {
                //点赞
                mPresenter.snapInsert(position,mList.get(position).getCommentId(),this.bindToLifecycle());
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 登陆时刷新  评论
     */
    @Subscribe
    public void refreshPage(CommonEvaluationBus commonEvaluationBus){
        if(commonEvaluationBus != null && commonEvaluationBus.getRefersh().equals(LoginActivity.refresh)){
            if(mBinding.swipeToLoad != null && !mBinding.swipeToLoad.isRefreshing()){
                mBinding.swipeTarget.smoothScrollToPosition(0);
                mBinding.swipeToLoad.setRefreshing(true);
            }
        }
    }
}
