package com.example.administrator.jipinshop.activity.member.family;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.FamilyAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.FamilyBean;
import com.example.administrator.jipinshop.databinding.ActivityFamilyBinding;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/9/10
 * @Describe 家庭会员共享
 */
public class FamilyActivity extends BaseActivity implements View.OnClickListener, FamilyAdapter.OnItem, FamilyView {

    @Inject
    FamilyPresenter mPresenter;

    private ActivityFamilyBinding mBinding;
    private Dialog mDialog;
    private List<FamilyBean.DataBean> mList;
    private FamilyAdapter mAdapter;
    private int userLevel = 0;//用户身份的
    private String count = "0";//总人数

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_family);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("会员共享");
        userLevel = getIntent().getIntExtra("userLevel",0);

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
        mPresenter.familyList(this.bindToLifecycle());
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
        if (position == mList.size()){
            //邀请加入
            if (userLevel == 0){
                ToastUtil.show("购买VIP后可邀请家庭成员");
                return;
            }
            String path = "pages/ev/ev-info/main?evListVal=";
            new ShareUtils(this, SHARE_MEDIA.WEIXIN)
                    .shareWXMin1(this, "","", "", "", path);
        }else if (mList.get(position).getStatus().equals("1")){
            //已加入
            if (userLevel != 0){
                DialogUtil.familyDialog(this,mList.get(position) , count, null);
            }
        }else if (mList.get(position).getStatus().equals("0")){
            //待加入
            if (userLevel != 0){
                DialogUtil.familyDialog(this,mList.get(position), count, v -> {
                    mDialog = (new ProgressDialogView()).createLoadingDialog(this, "");
                    mDialog.show();
                    mPresenter.familyConfirm(mList.get(position).getId(),mList.get(position).getUserId(),position,this.bindToLifecycle());
                });
            }
        }
    }

    @Override
    public void onSuccess(FamilyBean bean) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mList.clear();
        mList.addAll(bean.getData());
        mAdapter.notifyDataSetChanged();
        count = bean.getCount();
        mBinding.familyContent.setText(bean.getTitle());
    }

    @Override
    public void onFile(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void onConfirm(int position) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        BigDecimal bigDecimal =  new BigDecimal(count);
        count =  (bigDecimal.intValue() - 1) + "";
        mList.get(position).setStatus("1");
        mAdapter.notifyDataSetChanged();
        ToastUtil.show("加入成功");
    }
}
