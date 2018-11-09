package com.example.administrator.jipinshop.activity.commenlist;

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

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.CommenListAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingCommon2Adapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityCommenlistBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/3
 * @Describe 评论列表
 */
public class CommenListActivity extends BaseActivity implements CommenListAdapter.OnItemReply, View.OnClickListener, CommenListAdapter.OnMoreUp {

    @Inject
    CommenListPresenter mPresenter;

    private ActivityCommenlistBinding mBinding;
    private CommenListAdapter mAdapter;
    private List<String> mList;

    private int pos;//点击进来的位置。这里是为了商品详情中点击查看更多准备
    private List<Integer> sets;//记录每个留言的二级展示条数

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_commenlist);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        pos = getIntent().getIntExtra("position",-1);
        mBinding.titleContainer.titleTv.setText("所有评论(" + 56 + ")");
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        sets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mList.add("");
            if(pos == i && pos != -1){
                //这里判断的是二级评论的条数
//                if(mList.size() <=10){
//                    sets.add(mList.size());
//                }else {
                    sets.add(10);
//                }
            }else {
                //这里判断的是二级评论的条数
                if (mList.size() <=2){
                    sets.add(mList.size());
                }else {
                    sets.add(2);
                }
            }
        }
        mAdapter = new CommenListAdapter(mList, this);
        mAdapter.setOnItemReply(this);
        mAdapter.setSets(sets);
        mAdapter.setOnMoreUp(this);
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.recyclerView.scrollToPosition(pos);
    }

    /**
     * 回复一级评论
     *
     * @param pos
     * @param textView
     */
    @Override
    public void onItemReply(int pos, TextView textView) {
        mBinding.keyEdit.requestFocus();
        showKeyboard(true);
    }

    /**
     * 点击二级评论更多
     * @param pos 加载的条数
     *       postion 位置
     */
    @Override
    public void onItemTwoReply(int pos ,ShoppingCommon2Adapter mAdapter,int postion) {
//        mBinding.keyEdit.requestFocus();
//        showKeyboard(true);
        sets.remove(postion);
        sets.add(postion,pos);
        mAdapter.setNumber(sets.get(postion));
        mAdapter.notifyDataSetChanged();
//        Toast.makeText(this, "条数:" + sets.get(postion) + "--->位置：" + postion, Toast.LENGTH_SHORT).show();
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
                mBinding.keyEdit.setText("");
                hintKey();
                break;
        }
    }

    @Override
    public void onUp(int position,ShoppingCommon2Adapter mAdapter) {
        sets.remove(position);
        sets.add(position,2);
        mAdapter.setNumber(sets.get(position));
        mAdapter.notifyDataSetChanged();
        mBinding.recyclerView.scrollToPosition(position);
    }
}
