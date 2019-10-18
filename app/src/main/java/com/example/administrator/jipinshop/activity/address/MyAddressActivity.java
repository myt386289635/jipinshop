package com.example.administrator.jipinshop.activity.address;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.address.add.CreateAddressActivity;
import com.example.administrator.jipinshop.adapter.MyAddressAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.AddressBean;
import com.example.administrator.jipinshop.databinding.ActivityAddressBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/3/6
 * @Describe 收货地址页面
 */
public class MyAddressActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, MyAddressView, MyAddressAdapter.OnClickItem {

    private ActivityAddressBinding mBinding;

    @Inject
    MyAddressPresenter mPresenter;

    private List<AddressBean.DataBean> mList;
    private MyAddressAdapter mAdapter;
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_address);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("收货地址");

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new MyAddressAdapter(mList,this);
        mAdapter.setOnClickItem(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad);
        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.post(() -> mBinding.swipeToLoad.setRefreshing(true));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_add:
                //添加收货地址
                startActivityForResult(new Intent(this, CreateAddressActivity.class)
                        .putExtra("title","新增地址")
                ,201);
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.addresslist(this.bindUntilEvent(ActivityEvent.DESTROY));
    }

    @Override
    public void onSuccess(AddressBean addressBean) {
        stopResher();
        if(addressBean.getData() != null && addressBean.getData().size() != 0){
            mList.clear();
            mBinding.netClude.qsNet.setVisibility(View.GONE);
            mBinding.recyclerView.setVisibility(View.VISIBLE);
            mList.addAll(addressBean.getData());
            mAdapter.notifyDataSetChanged();
        }else {
            initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
            mBinding.recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFile(String error) {
        stopResher();
        initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试");
        mBinding.recyclerView.setVisibility(View.GONE);
        Log.d("RecommendFragmentPresen", error);
    }

    @Override
    public void onSuccessDelete(int position) {
        stopResher();
        mList.remove(position);
        mAdapter.notifyDataSetChanged();
        if(mList.size() == 0){
            initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ");
            mBinding.recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFileDelete(String error) {
        ToastUtil.show(error);
        stopResher();
    }


    @Override
    public void onSuccessDefault(int position) {
        stopResher();
        for (int i = 0; i < mList.size(); i++) {
            if(i == position){
                mList.get(position).setStatus("1");
            }else {
                mList.get(i).setStatus("0");
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void initError(int id, String title, String content) {
        mBinding.netClude.qsNet.setVisibility(View.VISIBLE);
        mBinding.netClude.errorImage.setBackgroundResource(id);
        mBinding.netClude.errorTitle.setText(title);
        mBinding.netClude.errorContent.setText(content);
    }

    public void stopResher(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            if(!mBinding.swipeToLoad.isRefreshEnabled()){
                mBinding.swipeToLoad.setRefreshEnabled(true);
                mBinding.swipeToLoad.setRefreshing(false);
                mBinding.swipeToLoad.setRefreshEnabled(false);
            }else {
                mBinding.swipeToLoad.setRefreshing(false);
            }
        }
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    /**
     * 编辑地址
     */
    @Override
    public void onItemEdit(int position) {
        startActivityForResult(new Intent(this, CreateAddressActivity.class)
                .putExtra("title","修改地址")
                .putExtra("date",mList.get(position))
        ,201);
    }

    /**
     * 删除地址
     */
    @Override
    public void onItemDelete(int position) {
        DialogUtil.LoginDialog(this, "确认要删除该地址吗？", "确认", "取消", v -> {
            mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在请求...");
            mDialog.show();
            mPresenter.addressDelete(position,mList.get(position).getId(),this.bindToLifecycle());
        });
    }

    /**
     * 设置为默认地址
     */
    @Override
    public void onItemDefault(int position) {
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在请求...");
        mDialog.show();
        mPresenter.addressSetDefault(position,mList.get(position).getId(),this.bindToLifecycle());
    }

    /**
     * 点击item
     */
    @Override
    public void onItemClick(int position) {
        if(!TextUtils.isEmpty(getIntent().getStringExtra("type"))){
            //标示是从确认订单页面进来的
            Intent intent = new Intent();
            intent.putExtra("name",mList.get(position).getUsername());
            intent.putExtra("photo",mList.get(position).getMobile());
            intent.putExtra("address",mList.get(position).getArea().replace("-","") + mList.get(position).getAddress());
            intent.putExtra("id",mList.get(position).getId());
            setResult(200,intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 201://新增地址或者修改地址刷新本页
                if (mBinding.swipeToLoad.isRefreshEnabled()){
                    mBinding.swipeToLoad.setRefreshing(true);
                }else {
                    mBinding.swipeToLoad.setRefreshEnabled(true);
                    mBinding.swipeToLoad.setRefreshing(true);
                    mBinding.swipeToLoad.setRefreshEnabled(false);
                }
                break;
        }
    }
}
