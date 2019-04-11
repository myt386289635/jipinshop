package com.example.administrator.jipinshop.activity.address.add;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.AddressBean;
import com.example.administrator.jipinshop.databinding.ActivityCreateaddressBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.city.CityPickerDialog;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/3/6
 * @Describe 新增地址/修改地址
 */
public class CreateAddressActivity extends BaseActivity implements View.OnClickListener, CreateAddressView {

    private ActivityCreateaddressBinding mBinding;
    private AddressBean.DataBean mBean;
    private Dialog mDialog;
    private String cityTxt;
    private CityPickerDialog cityPickerDialog;

    @Inject
    CreateAddressPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_createaddress);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText(getIntent().getStringExtra("title") + "");
        mBean = (AddressBean.DataBean) getIntent().getSerializableExtra("date");

        if(mBean != null){
            mBinding.editName.setText(mBean.getUsername());
            mBinding.editNumber.setText(mBean.getMobile());
            cityTxt = mBean.getArea();
            mBinding.editArea.setText(cityTxt.replace("-"," "));
            mBinding.editAddress.setText(mBean.getAddress());
        }

        showKeyboardDelayed(mBinding.editName);
        mBinding.editName.setSelection(mBinding.editName.getText().length());

        onEditCity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.title_add:
                //保存
                if (TextUtils.isEmpty(mBinding.editName.getText().toString().trim())){
                    ToastUtil.show("收件人不能为空");
                    return;
                }
                if (TextUtils.isEmpty(mBinding.editNumber.getText().toString().trim())){
                    ToastUtil.show("联系方式不能为空");
                    return;
                }
                if (mBinding.editNumber.getText().toString().trim().length() < 11){
                    ToastUtil.show("联系方式不正确，请检查");
                    return;
                }
                if (TextUtils.isEmpty(mBinding.editAddress.getText().toString().trim())){
                    ToastUtil.show("详细地址不能为空");
                    return;
                }
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在请求...");
                mDialog.show();
                if(mBean != null){
                    //修改地址
                    mPresenter.addressUpdate(mBean.getId(),mBinding.editName.getText().toString(),
                            mBinding.editNumber.getText().toString(),
                            mBinding.editAddress.getText().toString(),
                            cityTxt,this.bindToLifecycle());
                }else {
                    //新增地址
                    mPresenter.addressAdd(mBinding.editName.getText().toString(),
                            mBinding.editNumber.getText().toString(),
                            mBinding.editAddress.getText().toString(),
                            cityTxt,this.bindToLifecycle());
                }
                break;
            case R.id.areaContainer:
                //选择收货地区
                cityPickerDialog.show();
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                hideSoftInput();
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

    private void hideSoftInput() {
        if (mImm.isActive()) {
            // 如果开启
            mImm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
        }
    }

    @Override
    public void onSuccess() {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        setResult(201);
        finish();
        ToastUtil.show("成功");
    }

    @Override
    public void onFile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    /**
     * 选择城市
     */
    public void onEditCity() {
        cityPickerDialog = new CityPickerDialog(this);
        cityPickerDialog.setCallback(new CityPickerDialog.OnClickCallback() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onSure(String province, String city, String county, String data) {
                if (TextUtils.isEmpty(county)) {
                    cityTxt = province+"-"+city;
                } else {
                    cityTxt = province+"-"+city+"-"+county;
                }
                mBinding.editArea.setText(cityTxt.replace("-"," "));
            }
        });
    }
}
