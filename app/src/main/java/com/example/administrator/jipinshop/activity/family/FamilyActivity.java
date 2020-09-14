package com.example.administrator.jipinshop.activity.family;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.FamilyAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityFamilyBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/9/10
 * @Describe 家庭会员共享
 */
public class FamilyActivity extends BaseActivity implements View.OnClickListener, FamilyAdapter.OnItem {

    @Inject
    FamilyPresenter mPresenter;

    private ActivityFamilyBinding mBinding;
    private Dialog mDialog;
    private List<String> mList;
    private FamilyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_family);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("会员共享");

        mBinding.familyRv.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new FamilyAdapter(mList,this);
        mAdapter.setOnItem(this);
        mBinding.familyRv.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
        mDialog.show();
        initDate();
    }

    private void initDate() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mList.clear();
        for (int i = 0; i < 5; i++) {
            mList.add("");
        }
        mAdapter.notifyDataSetChanged();
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
    public void onItem(int position) {
        if (position == 1){
            //已加入
            DialogUtil.familyDialog(this,1 , null);
        }else if (position == mList.size()){
            //邀请加入
            ToastUtil.show("邀请加入");
        }else {
            //待加入
            DialogUtil.familyDialog(this, 2, v -> {
                ToastUtil.show("通过请求");
            });
        }
    }
}
