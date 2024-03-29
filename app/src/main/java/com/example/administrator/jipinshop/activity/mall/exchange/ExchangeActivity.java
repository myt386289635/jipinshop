package com.example.administrator.jipinshop.activity.mall.exchange;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.address.MyAddressActivity;
import com.example.administrator.jipinshop.activity.mall.order.detail.OrderDetailActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.DefaultAddressBean;
import com.example.administrator.jipinshop.bean.MallDetailBean;
import com.example.administrator.jipinshop.bean.MyOrderBean;
import com.example.administrator.jipinshop.databinding.ActivityExchangeBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
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
    private String addressId = "";//收货地址id
    private Dialog mDialog;

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
        mBinding.inClude.titleTv.setText("确认订单");
        mMallDetailBean = (MallDetailBean.DataBean) getIntent().getSerializableExtra("date");

        if(mMallDetailBean != null){
            mBinding.exchangeGoodsName.setText(mMallDetailBean.getGoodsName());
            mBinding.exchangeCode.setText(mMallDetailBean.getExchangePoint() + "极币");
            GlideApp.loderImage(this,mMallDetailBean.getImg(),mBinding.exchangeImage,0,0);
            if (mMallDetailBean.getType() == 2 || mMallDetailBean.getType() == 3){
                if (mMallDetailBean.getType() == 2){
                    mBinding.exchangeTitle.setText("会员卡兑换");
                }else {
                    mBinding.exchangeTitle.setText("津贴兑换");
                }
                mBinding.exchangeAddressContainer.setVisibility(View.GONE);
                mBinding.exchangeNoAddress.setVisibility(View.GONE);
            }else {
                mBinding.exchangeTitle.setText("积分商城");
                mBinding.exchangeNoAddress.setVisibility(View.VISIBLE);
                mBinding.exchangeAddressContainer.setVisibility(View.GONE);
                mPresenter.defaultAddress(this.bindToLifecycle());
            }
        }
        getTotle();
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
                if(mMallDetailBean == null){
                    ToastUtil.show("数据错误，请重新进入页面");
                    return;
                }
                BigDecimal bigDecimal = new BigDecimal(mBinding.exchangeNum.getText().toString());
                int num = bigDecimal.intValue();
                if(num == 1){
                    ToastUtil.show("已经是最小兑换数量了");
                    return;
                }
                if(num > 1){
                    mBinding.exchangeNum.setText((num - 1) + "");
                }
                getTotle();
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
                getTotle();
                break;
            case R.id.exchange_exchange:
                //立即兑换
                if(mMallDetailBean == null){
                    ToastUtil.show("数据错误，请重新进入页面");
                    return;
                }
                if(mMallDetailBean.getType() != 2&&mMallDetailBean.getType() != 3&&TextUtils.isEmpty(addressId)){
                    ToastUtil.show("地址不能为空");
                    return;
                }
                BigDecimal bigDecimal2 = new BigDecimal(mBinding.exchangeTotleCode.getText().toString());
                if(SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint,0) < bigDecimal2.intValue()){
                    ToastUtil.show("极币不足");
                    return;
                }
                mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在请求...");
                mDialog.show();
                mPresenter.exchange(mMallDetailBean.getId(),addressId, "1" ,this.bindToLifecycle());
                break;
        }
    }

    /**
     * 获取默认地址
     */
    @Override
    public void onSuccess(DefaultAddressBean bean) {
        if(bean != null && bean.getData() != null){
            mBinding.exchangeNoAddress.setVisibility(View.GONE);
            mBinding.exchangeAddressContainer.setVisibility(View.VISIBLE);
            mBinding.exchangeName.setText(bean.getData().getUsername());
            mBinding.exchangeNumber.setText(bean.getData().getMobile());
            mBinding.exchangeAddress.setText(bean.getData().getArea().replace("-","") + bean.getData().getAddress());
            addressId = bean.getData().getId();
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
    public void onExchangeSuc(MyOrderBean.DataBean bean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        bean.setImg(mMallDetailBean.getImg());
        bean.setTotal("1");
        bean.setGoodsName(mMallDetailBean.getGoodsName());
        startActivity(new Intent(this, OrderDetailActivity.class)
                        .putExtra("date",bean)
        );
        ToastUtil.show("商品兑换成功");
        setResult(300);
        finish();
    }

    @Override
    public void onExchangeFile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    /**
     * 选择地址返回
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 200){
            mBinding.exchangeNoAddress.setVisibility(View.GONE);
            mBinding.exchangeAddressContainer.setVisibility(View.VISIBLE);
            mBinding.exchangeName.setText(data.getStringExtra("name"));
            mBinding.exchangeNumber.setText(data.getStringExtra("photo"));
            mBinding.exchangeAddress.setText(data.getStringExtra("address"));
            addressId = data.getStringExtra("id");
        }
    }

    /**
     * 计算总数
     */
    public void getTotle(){
        BigDecimal newbigdecimal = new BigDecimal(mBinding.exchangeNum.getText().toString());
        int totle = mMallDetailBean.getExchangePoint() * newbigdecimal.intValue();
        mBinding.exchangeTotleCode.setText( totle + "" );
    }
}
