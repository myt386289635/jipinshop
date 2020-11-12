package com.example.administrator.jipinshop.activity.sreach.play;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.sreach.play.result.PlaySreachResultActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SreachHistoryBean;
import com.example.administrator.jipinshop.databinding.ActivityPalySearchBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/11/11
 * @Describe 吃喝玩乐搜索
 */
public class PlaySreachActivity extends BaseActivity implements View.OnClickListener, TextWatcher, PlaySreachView {

    @Inject
    PlaySreachPresenter mPresenter;

    private ActivityPalySearchBinding mBinding;
    private Dialog mDialog;
    private List<SreachHistoryBean.DataBean.HotWordListBean> mHotText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_paly_search);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        showKeyboardDelayed(mBinding.searchEdit);
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialog.show();
        mHotText = new ArrayList<>();
        mBinding.searchEdit.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                if (TextUtils.isEmpty(mBinding.searchEdit.getText().toString().trim())) {
                    ToastUtil.show("请输入搜索内容");
                    return false;
                }
                startActivity(new Intent(PlaySreachActivity.this, PlaySreachResultActivity.class)
                                .putExtra("content", mBinding.searchEdit.getText().toString())
                );
            }
            return false;
        });
        mBinding.searchEdit.addTextChangedListener(this);

        mPresenter.searchLog(this.bindToLifecycle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_cancle:
                if (mBinding.searchCancle.getText().toString().equals("取消")){
                    finish();
                }else {
                    if (TextUtils.isEmpty(mBinding.searchEdit.getText().toString().trim())) {
                        ToastUtil.show("请输入搜索内容");
                        return;
                    }
                    startActivity(new Intent(PlaySreachActivity.this, PlaySreachResultActivity.class)
                            .putExtra("content", mBinding.searchEdit.getText().toString())
                    );
                }
                break;
            case R.id.search_close:
                mBinding.searchEdit.setText("");
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(mBinding.searchEdit.getText().toString().trim())) {
            mBinding.searchClose.setVisibility(View.GONE);
            mBinding.searchCancle.setText("取消");
        } else {
            mBinding.searchClose.setVisibility(View.VISIBLE);
            mBinding.searchCancle.setText("搜索");
        }
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
    public void jump(String content, int position) {
        mBinding.searchEdit.setText(content);
        mBinding.searchEdit.setSelection(mBinding.searchEdit.getText().length());//将光标移至文字末尾
        startActivity(new Intent(PlaySreachActivity.this, PlaySreachResultActivity.class)
                .putExtra("content", mBinding.searchEdit.getText().toString())
        );
    }

    @Override
    public void Success(SreachHistoryBean sreachHistoryBean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mHotText.addAll(sreachHistoryBean.getData().getHotWordList());
        mPresenter.initHot(this, mBinding.searchFlex, mHotText);
    }

    @Override
    public void onFaile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }
}
