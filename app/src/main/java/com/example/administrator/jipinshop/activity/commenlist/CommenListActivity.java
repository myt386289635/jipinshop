package com.example.administrator.jipinshop.activity.commenlist;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.CommenListAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingCommon2Adapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.CommentInsertBean;
import com.example.administrator.jipinshop.databinding.ActivityCommenlistBinding;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/3
 * @Describe 评论列表
 */
public class CommenListActivity extends BaseActivity implements CommenListAdapter.OnItemReply, View.OnClickListener, CommenListAdapter.OnMoreUp, CommenListView, OnRefreshListener, OnLoadMoreListener {

    public static final String commentResher = "ShoppingDetailActivity_commentResher";

    @Inject
    CommenListPresenter mPresenter;

    private ActivityCommenlistBinding mBinding;
    private CommenListAdapter mAdapter;
    private List<CommentBean.ListBean> mList;

    private int pos;//点击进来的位置。这里是为了商品详情中点击查看更多准备
    private List<Integer> sets;//记录每个留言的二级展示条数

    private int page = 0;
    private Boolean refresh = true;
    //是否是首次进入页面
    private boolean once = true;

    /**
     * 父评论id
     */
    private String parentId = "0";

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_commenlist);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mPresenter.setView(this);
        pos = getIntent().getIntExtra("position", -1);
        mBinding.titleContainer.titleTv.setText("所有评论(" + 0 + ")");
        mBinding.swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        sets = new ArrayList<>();
        mAdapter = new CommenListAdapter(mList, this);
        mAdapter.setOnItemReply(this);
        mAdapter.setSets(sets);
        mAdapter.setOnMoreUp(this);
        mBinding.swipeTarget.setAdapter(mAdapter);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setOnLoadMoreListener(this);
        mBinding.swipeToLoad.setRefreshing(true);

        mPresenter.setKeyListener(mBinding.detailContanier, usableHeightPrevious);
    }

    /**
     * 回复一级评论
     *
     * @param pos
     * @param textView
     */
    @Override
    public void onItemReply(int pos, TextView textView) {
        parentId = mList.get(pos).getCommentId();
        parentNum = pos;
        mBinding.keyEdit.requestFocus();
        showKeyboard(true);
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
        if (mImm.isActive()) {
            // 如果开启
            mImm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                hintKey();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                hintKey();
                finish();
                break;
            case R.id.key_text:
                if (TextUtils.isEmpty(mBinding.keyEdit.getText().toString())) {
                    Toast.makeText(this, "请输入评论", Toast.LENGTH_SHORT).show();
                    return;
                }
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                mDialog.show();
                mPresenter.commentInsert(getIntent().getStringExtra("id"), mBinding.keyEdit.getText().toString(), parentId, this.bindToLifecycle());
                break;
            case R.id.key_edit:
//                Toast.makeText(this, "点击了" + imi, Toast.LENGTH_SHORT).show();
                if(!imi){
                    //不是打开的时候点击
                    parentId = "0";
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
    }

    @Override
    public void onSucComment(CommentBean commentBean) {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            mBinding.swipeToLoad.setRefreshing(false);
        }
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore()) {
            mBinding.swipeToLoad.setLoadingMore(false);
        }
        if (commentBean.getList() != null && commentBean.getList().size() != 0) {
            if (refresh) {
                mList.clear();
                mList.addAll(commentBean.getList());
                if (once) {
                    initSets();
                } else {
                    ResherSets();
                }
            } else {
                int i = mList.size();
                mList.addAll(commentBean.getList());
                onLodeSets(i);
            }
            mAdapter.notifyDataSetChanged();
        } else {
            if (!refresh) {
                page--;
                Toast.makeText(this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
            }
        }
        mBinding.titleContainer.titleTv.setText("所有评论(" + commentBean.getCount() + ")");
        if (once && pos != -1) {
            mBinding.swipeTarget.scrollToPosition(pos);
            once = false;
        } else if (once) {
            once = false;
        }

    }

    @Override
    public void onFileComment(String error) {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            mBinding.swipeToLoad.setRefreshing(false);
        }
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isLoadingMore()) {
            mBinding.swipeToLoad.setLoadingMore(false);
        }
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSucCommentInsert(CommentInsertBean successBean) {
        if (!parentId.equals("0")) {
            //回复评论
            CommentBean.ListBean.UserCommentListBean listBean = new CommentBean.ListBean.UserCommentListBean();
            listBean.setContent(mBinding.keyEdit.getText().toString());
            listBean.setFromNickname(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName));
            mList.get(parentNum).getUserCommentList().add(listBean);
            if (mList.get(parentNum).getUserCommentList().size() <= 2) {
                sets.remove(parentNum);
                sets.add(parentNum, mList.get(parentNum).getUserCommentList().size());
            } else {
                if (sets.get(parentNum) != 2 && (sets.get(parentNum) % 10) != 0) {
                    int num = sets.remove(parentNum);
                    sets.add(parentNum, num + 1);
                }
            }
            mAdapter.notifyItemChanged(parentNum);
        } else {
            //回复楼层
            sets.add(0, 0);
            mBinding.swipeTarget.scrollToPosition(0);
            onRefresh();
        }
        mBinding.keyEdit.setText("");
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        EventBus.getDefault().post(CommenListActivity.commentResher);
    }

    @Override
    public void onFileCommentInsert(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    /***
     * 刷新
     */
    @Override
    public void onRefresh() {
        refresh = true;
        page = 0;
        mPresenter.comment(page + "", getIntent().getStringExtra("id"), this.bindToLifecycle());
    }

    @Override
    public void onLoadMore() {
        refresh = false;
        page++;
        mPresenter.comment(page + "", getIntent().getStringExtra("id"), this.bindToLifecycle());
    }

    /**
     * 第一次进入页面时，初始化sets数组
     */
    public void initSets() {
        for (int i = 0; i < mList.size(); i++) {
            if (pos == i && pos != -1) {
                //这里判断的是二级评论的条数
                if (mList.get(i).getUserCommentList().size() <= 10) {
                    sets.add(mList.get(i).getUserCommentList().size());
                } else {
                    sets.add(10);
                }
            } else {
                //这里判断的是二级评论的条数
                if (mList.get(i).getUserCommentList().size() <= 2) {
                    sets.add(mList.get(i).getUserCommentList().size());
                } else {
                    sets.add(2);
                }
            }
        }
    }

    /**
     * 不是第一次，刷新列表时，sets数组的逻辑
     */
    private void ResherSets() {
        List<Integer> timer = new ArrayList<>();
        if (sets.size() > 10) {
            for (int i = 0; i < 10; i++) {
                timer.add(sets.get(i));
            }
            sets.clear();
            sets.addAll(timer);
        }
    }

    /**
     * 加载数据时，sets数组的逻辑
     */
    private void onLodeSets(int pos) {
        for (int i = pos; i < mList.size(); i++) {
            if (mList.get(i).getUserCommentList().size() <= 2) {
                sets.add(mList.get(i).getUserCommentList().size());
            } else {
                sets.add(2);
            }
        }
    }
}
