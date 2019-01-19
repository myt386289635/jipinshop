package com.example.administrator.jipinshop.activity.sreach;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.sreach.result.SreachResultActivity;
import com.example.administrator.jipinshop.adapter.SreachAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SreachHistoryBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.SreachBus;
import com.example.administrator.jipinshop.databinding.ActivitySreachBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SreachActivity extends BaseActivity implements TextWatcher, SreachView, View.OnClickListener, SreachAdapter.OnItem {

    public static final String sreachHistoryTag = "SreachBus";

    @Inject
    SreachPresenter mPresenter;

    private List<SreachHistoryBean.DataBean.LogListBean> mHistroyList;
    private SreachAdapter mAdapter;
    private ActivitySreachBinding mBinding;
    private List<SreachHistoryBean.DataBean.HotWordListBean> mHotText;
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sreach);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mPresenter.setView(this);
        showKeyboardDelayed(mBinding.sreachEdit);

        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialog.show();

        mHotText = new ArrayList<>();
        mBinding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHistroyList = new ArrayList<>();
        mAdapter = new SreachAdapter(mHistroyList, this);
        mAdapter.setOnItem(this);
        mBinding.searchRecyclerView.setAdapter(mAdapter);

        mBinding.sreachEdit.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                if (TextUtils.isEmpty(mBinding.sreachEdit.getText().toString().trim())) {
                    ToastUtil.show("请输入搜索内容");
                    return false;
                }
                startActivityForResult(new Intent(SreachActivity.this, SreachResultActivity.class)
                        .putExtra("content", mBinding.sreachEdit.getText().toString())
                ,200);
            }
            return false;
        });
        mBinding.sreachEdit.addTextChangedListener(this);

        mPresenter.searchLog("1",this.bindToLifecycle());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                showKeyboard(false);
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(mBinding.sreachEdit.getText().toString().trim())) {
            mBinding.sreachClose.setVisibility(View.GONE);
            mBinding.sreachCancle.setText("取消");
        } else {
            mBinding.sreachClose.setVisibility(View.VISIBLE);
            mBinding.sreachCancle.setText("搜索");
        }
    }

    /**
     * 从热门搜索、历史搜索里点击进入搜索列表
     */
    @Override
    public void jump(String from, String content) {
        mBinding.sreachEdit.setText(content);
        mBinding.sreachEdit.setSelection(mBinding.sreachEdit.getText().length());//将光标移至文字末尾
        startActivityForResult(new Intent(this, SreachResultActivity.class)
                .putExtra("content", content)
        ,200);
    }

    @Override
    public void Success(SreachHistoryBean sreachHistoryBean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mHotText.addAll(sreachHistoryBean.getData().getHotWordList());
        mPresenter.initHot(this, mBinding.searchFlexHistroy, mHotText);
        mHistroyList.addAll(sreachHistoryBean.getData().getLogList());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFaile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    /**
     * 删除搜索所有记录
     */
    @Override
    public void SuccessDeleteAll(SuccessBean successBean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mHistroyList.clear();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 删除单条记录
     */
    @Override
    public void SuccessDelete(int position,SuccessBean successBean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mHistroyList.remove(position);
        mAdapter.notifyItemRemoved(position);
        if(position != mHistroyList.size()) {
            mAdapter.notifyItemRangeChanged(position,mHistroyList.size() - position);
        }
    }

    /**
     * 更新历史记录
     */
    @Override
    public void SuccessHistory(SreachHistoryBean sreachHistoryBean) {
        mHistroyList.clear();
        mHistroyList.addAll(sreachHistoryBean.getData().getLogList());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sreach_cancle:
                if (mBinding.sreachCancle.getText().toString().equals("取消")) {
                    finish();
                } else {
                    if (TextUtils.isEmpty(mBinding.sreachEdit.getText().toString().trim())) {
                        ToastUtil.show("请输入搜索内容");
                        return;
                    }
                    startActivityForResult(new Intent(this, SreachResultActivity.class)
                            .putExtra("content", mBinding.sreachEdit.getText().toString())
                    ,200);
                }
                break;
            case R.id.sreach_close:
                mBinding.sreachEdit.setText("");
                break;
            case R.id.search_delete:
                DialogUtil.LoginDialog(this, "确认删除全部历史记录？", "确定", "取消", v -> {
                    mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                    mDialog.show();
                    mPresenter.deleteAll(this.bindToLifecycle());
                });
                break;
        }
    }

    /**
     * 历史记录的点击
     */
    @Override
    public void onItemClick(int position) {
        mBinding.sreachEdit.setText(mHistroyList.get(position).getWord());
        mBinding.sreachEdit.setSelection(mBinding.sreachEdit.getText().length());//将光标移至文字末尾
        startActivityForResult(new Intent(this, SreachResultActivity.class)
                .putExtra("content", mBinding.sreachEdit.getText().toString())
        ,200);
    }

    /**
     * 历史记录的删除
     */
    @Override
    public void onItemDelete(int position) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialog.show();
        mPresenter.searchDelete(position,mHistroyList.get(position).getId(),this.bindToLifecycle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 301:
                //点击输入框退回来的。
                showKeyboardDelayed(mBinding.sreachEdit);
                break;
            case 302:
                //点击叉子退回来的
                mBinding.sreachEdit.setText("");
                showKeyboardDelayed(mBinding.sreachEdit);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void sreachHis(SreachBus sreachBus){
        if(sreachBus != null && sreachBus.getTag().equals(sreachHistoryTag)){
            mPresenter.searchLog("2",this.bindUntilEvent(ActivityEvent.DESTROY));
        }
    }
}
