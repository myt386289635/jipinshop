package com.example.administrator.jipinshop.activity.mall.exchange;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.address.MyAddressActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.DefaultAddressBean;
import com.example.administrator.jipinshop.bean.MallDetailBean;
import com.example.administrator.jipinshop.databinding.ActivityExchangeBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.math.BigDecimal;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/3/13
 * @Describe 确认订单页面
 */
public class ExchangeActivity extends BaseActivity implements View.OnClickListener, ExchangeView {

    @Inject
    ExchangePresenter mPresenter;
    private ActivityExchangeBinding mBinding;

    private MallDetailBean.DataBean mMallDetailBean;//数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_exchange);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mBinding.exchangeNoAddress.setVisibility(View.VISIBLE);
        mBinding.exchangeAddressContainer.setVisibility(View.GONE);
        mPresenter.defaultAddress(this.bindToLifecycle());
        mBinding.inClude.titleTv.setText("确认订单");
        mMallDetailBean = (MallDetailBean.DataBean) getIntent().getSerializableExtra("date");
        if(mMallDetailBean != null){
            mBinding.exchangeGoodsName.setText(mMallDetailBean.getGoodsName());
            mBinding.exchangeCode.setText(mMallDetailBean.getExchangePoint() + "极币");
            GlideApp.loderImage(this,mMallDetailBean.getImg(),mBinding.exchangeImage,0,0);
        }
        BigDecimal bigDecimal = new BigDecimal(mBinding.exchangeNum.getText().toString());
        int totle = mMallDetailBean.getExchangePoint() * bigDecimal.intValue();
        mBinding.exchangeTotleCode.setText( totle + "" );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.exchange_add:
            case R.id.exchange_addressContainer:
                startActivityForResult(new Intent(this, MyAddressActivity.class)
                        .putExtra("type","exchange")
                        ,200);
                break;
            case R.id.exchange_decrease:
                //  -
                BigDecimal bigDecimal = new BigDecimal(mBinding.exchangeNum.getText().toString());
                int num = bigDecimal.intValue();
                if(num == 1){
                    ToastUtil.show("已经是最小兑换数量了");
                    return;
                }
                if(num > 1){
                    mBinding.exchangeNum.setText((num - 1) + "");
                }
                break;
            case R.id.exchange_increase:
                //   +
                BigDecimal bigDecimal1 = new BigDecimal(mBinding.exchangeNum.getText().toString());
                int num1 = bigDecimal1.intValue();
                if(mMallDetailBean == null){
                    ToastUtil.show("数据错误，请重新进入页面");
                    return;
                }
                if(num1 > mMallDetailBean.getTotal()){
                    ToastUtil.show("已经是最大兑换数量了");
                    return;
                }
                mBinding.exchangeNum.setText((num1 + 1) + "");

                break;
            case R.id.exchange_exchange:
                //立即兑换
                break;
        }
    }

    @Override
    public void onSuccess(DefaultAddressBean bean) {
        if(bean != null && bean.getData() != null){
            mBinding.exchangeNoAddress.setVisibility(View.GONE);
            mBinding.exchangeAddressContainer.setVisibility(View.VISIBLE);
            mBinding.exchangeName.setText(bean.getData().getUsername());
            mBinding.exchangeNumber.setText(bean.getData().getMobile());
            mBinding.exchangeAddress.setText(bean.getData().getArea().replace("-","") + bean.getData().getAddress());
        }else {
            mBinding.exchangeNoAddress.setVisibility(View.VISIBLE);
            mBinding.exchangeAddressContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFile(String error) {
        mBinding.exchangeNoAddress.setVisibility(View.VISIBLE);
        mBinding.exchangeAddressContainer.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 200){
            mBinding.exchangeNoAddress.setVisibility(View.GONE);
            mBinding.exchangeAddressContainer.setVisibility(View.VISIBLE);
            mBinding.exchangeName.setText(data.getStringExtra("name"));
            mBinding.exchangeNumber.setText(data.getStringExtra("photo"));
            mBinding.exchangeAddress.setText(data.getStringExtra("address"));
        }
    }
}
