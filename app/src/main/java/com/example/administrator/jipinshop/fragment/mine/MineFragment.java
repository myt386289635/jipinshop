package com.example.administrator.jipinshop.fragment.mine;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.balance.BalanceActivity;
import com.example.administrator.jipinshop.activity.balance.boundalipay.BoundAlipayActivity;
import com.example.administrator.jipinshop.activity.coupon.CouponActivity;
import com.example.administrator.jipinshop.activity.follow.FollowActivity;
import com.example.administrator.jipinshop.activity.foval.FovalActivity;
import com.example.administrator.jipinshop.activity.info.MyInfoActivity;
import com.example.administrator.jipinshop.activity.info.editname.EditNameActivity;
import com.example.administrator.jipinshop.activity.integral.IntegralActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.message.MessageActivity;
import com.example.administrator.jipinshop.activity.setting.SettingActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.AccountBean;
import com.example.administrator.jipinshop.bean.UserInfoBean;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.databinding.FragmentMineBinding;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;

import javax.inject.Inject;

public class MineFragment extends DBBaseFragment implements View.OnClickListener, MineView {

    @Inject
    MinePresenter mPresenter;

    private FragmentMineBinding mBinding;

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);
        mBinding.setListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        EventBus.getDefault().register(this);

        mPresenter.setStatusBarHight(mBinding.statusBar, getContext());
        mPresenter.setView(this);
        if(!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId).trim())){
            //这里是判断该手机是否有账户登陆过。如果有userId不会为空，除非没有用户登录或已经退出登陆
            mPresenter.modelUser(getContext(),this.bindToLifecycle());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_setting:
                //跳转到设置页面
                startActivityForResult(new Intent(getContext(), SettingActivity.class),100);
                return;
            case R.id.mine_message:
                //跳转到消息页面
                startActivity(new Intent(getContext(), MessageActivity.class));
                return;
            case R.id.mine_login:
                //跳转到登陆页面
                startActivityForResult(new Intent(getContext(), LoginActivity.class), 100);
                return;
        }
        if(!SPUtils.getInstance(CommonDate.USER).getBoolean(CommonDate.userLogin,false)){
            Toast.makeText(getContext(), "请先登陆", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
            case R.id.mine_image:
                //我的资料
                startActivityForResult(new Intent(getContext(), MyInfoActivity.class),100);
                break;
            case R.id.mine_sign:
                //跳转到H5页面
                startActivity(new Intent(getContext(), SignActivity.class));
                break;
            case R.id.mine_withdraw:
                //跳转到我的余额页面
                startActivity(new Intent(getContext(), BalanceActivity.class)
                        .putExtra("totleMoney",mBinding.mineMoney.getText().toString())//总金额
                        .putExtra("processingValue",mBinding.mineProcessingValue.getText().toString())//处理中
                        .putExtra("withdrawableValue", mBinding.mineWithdrawableValue.getText().toString())//可提现
                        .putExtra("settlementValue",mBinding.mineSettlementValue.getText().toString())//待结算
                        .putExtra("withdrawedValue", mBinding.mineWithdrawedValue.getText().toString())//已提现
                );
                break;
            case R.id.mine_processing:
                //跳转到
                break;
            case R.id.mine_withdrawable:
                //跳转到
                break;
            case R.id.mine_settlement:
                //跳转到
                break;
            case R.id.mine_Withdrawed:
                //跳转到
                break;
            case R.id.mine_Order:

                break;
            case R.id.mine_follow:
                //跳转到我的关注页面
                startActivity(new Intent(getContext(), FollowActivity.class));
                break;
            case R.id.mine_coupon:
                //跳转到优惠券页面
                startActivity(new Intent(getContext(), CouponActivity.class));
                break;
            case R.id.mine_favor:
                //跳转到收藏页面
                startActivity(new Intent(getContext(), FovalActivity.class));
                break;
            case R.id.mine_integral:
                //跳转到我的积分页面
                startActivity(new Intent(getContext(), IntegralActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 200://登陆成功
                mBinding.mineName.setVisibility(View.VISIBLE);
                mBinding.mineLogin.setVisibility(View.GONE);
                if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName))){
                    mBinding.mineName.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userPhone));
                }else {
                    mBinding.mineName.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName));
                }
                if(!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg))){
                    ImageManager.displayImage(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg),mBinding.mineImage,0,R.mipmap.logo);
                }
                mBinding.mineLevel.setVisibility(View.VISIBLE);
                mBinding.mineLevel.setText("v" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userMemberGrade));
                mBinding.mineIntegral.setText("积分" + SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint));
                break;
            case 201://退出登陆成功
                mBinding.mineName.setVisibility(View.GONE);
                mBinding.mineLogin.setVisibility(View.VISIBLE);
                mBinding.mineLevel.setVisibility(View.GONE);
                mBinding.mineIntegral.setText("积分0");
                ImageManager.displayImage("drawable://" + R.drawable.logo,mBinding.mineImage,0,0);
                SPUtils.getInstance(CommonDate.USER).clear();
                SPUtils.getInstance(CommonDate.USER).put(CommonDate.userLogin,false);
                mBinding.mineMoney.setText("总佣金¥00.00");
                mBinding.mineProcessingValue.setText("0");
                mBinding.mineWithdrawableValue.setText("0");
                mBinding.mineSettlementValue.setText("0");
                mBinding.mineWithdrawedValue.setText("0");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void eidtInfo(EditNameBus bus){
        if(bus.getTag().equals(EditNameActivity.tag)){
            //修改用户信息时返回刷新
            if(bus.getType().equals("1")){
                mBinding.mineName.setText(bus.getContent());
            }else if(bus.getType().equals("4")){
                //修改用户头像
                ImageManager.displayImage(bus.getContent(),mBinding.mineImage,0,R.mipmap.rlogo);
            }
        }else if(bus.getTag().equals(LoginActivity.tag)){
            //登陆时返回刷新佣金数
            mBinding.mineMoney.setText("总佣金¥" + bus.getTotleMoney());
            mBinding.mineProcessingValue.setText(bus.getState());
            BigDecimal totleDecimal= new BigDecimal(bus.getTotleMoney());
            BigDecimal useDecimal = new BigDecimal("50");
            double value = totleDecimal.subtract(useDecimal).doubleValue();
            if(value >= 0){
                mBinding.mineWithdrawableValue.setText(bus.getTotleMoney());
            }else {
                mBinding.mineWithdrawableValue.setText("0");
            }
            mBinding.mineSettlementValue.setText(bus.getNone());
            mBinding.mineWithdrawedValue.setText(bus.getUseMoney());
        }else if(bus.getTag().equals(SignActivity.eventbusTag)){
            //签到、补签后刷新积分
            mBinding.mineIntegral.setText("积分" + SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint));
        }else if(bus.getTag().equals(BalanceActivity.alipayTag)){
            //提现后刷新佣金
            mPresenter.getMoney(getContext(),this.bindToLifecycle());
        }
    }

    /**
     * 获取佣金金额
     * @param accountBean
     */
    @Override
    public void successMoney(AccountBean accountBean) {
        if(accountBean.getCode() == 200){
            mBinding.mineMoney.setText("总佣金¥" + accountBean.getList().get(0).getTotal_account());
            mBinding.mineProcessingValue.setText(accountBean.getList().get(0).getState());
            BigDecimal totleDecimal= new BigDecimal(accountBean.getList().get(0).getTotal_account());
            BigDecimal useDecimal = new BigDecimal("50");
            double value = totleDecimal.subtract(useDecimal).doubleValue();
            if(value >= 0){
                mBinding.mineWithdrawableValue.setText(accountBean.getList().get(0).getTotal_account());
            }else {
                mBinding.mineWithdrawableValue.setText("0");
            }
            mBinding.mineSettlementValue.setText("0");
            mBinding.mineWithdrawedValue.setText(accountBean.getList().get(0).getUse_account());
        }else {
            Toast.makeText(getContext(), "佣金金额获取失败，请联系客服", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取用户信息
     * 用来刷新用户信息
     * @param userInfoBean
     */
    @Override
    public void successUserInfo(UserInfoBean userInfoBean) {
        if(userInfoBean.getCode() == 200){
            //获取用户佣金
            mPresenter.getMoney(getContext(),this.bindToLifecycle());

            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userLogin,true);
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userBirthday,userInfoBean.getList().get(0).getUserBirthday());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userGender,userInfoBean.getList().get(0).getUserGender());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userNickImg,userInfoBean.getList().get(0).getUserNickImg());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userNickName,userInfoBean.getList().get(0).getUserNickName());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPhone,userInfoBean.getList().get(0).getUserPhone());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userMemberGrade,userInfoBean.getList().get(0).getUserMemberGrade());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint,Integer.valueOf(userInfoBean.getPoints()));
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.alipAccount,userInfoBean.getList().get(0).getAlipayAccount());
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.alipName,userInfoBean.getList().get(0).getAlipayName());

            mBinding.mineName.setVisibility(View.VISIBLE);
            mBinding.mineLogin.setVisibility(View.GONE);
            mBinding.mineLevel.setVisibility(View.VISIBLE);
            if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName))){
                mBinding.mineName.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userPhone));
            }else {
                mBinding.mineName.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName));
            }
            if(!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg))){
                ImageManager.displayImage(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg),mBinding.mineImage,0,R.mipmap.logo);
            }
            mBinding.mineLevel.setText("v" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userMemberGrade));
            mBinding.mineIntegral.setText("积分" + SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint));
        }else {
            //获取用户信息失败 走退出登陆的逻辑
            mBinding.mineName.setVisibility(View.GONE);
            mBinding.mineLogin.setVisibility(View.VISIBLE);
            mBinding.mineLevel.setVisibility(View.GONE);
//            SPUtils.getInstance(CommonDate.USER).clear();这里不清空用户数据，方便用户网络好的时候免登陆
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userLogin,false);
            Toast.makeText(getContext(), userInfoBean.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取用户信息失败
     * @param error
     */
    @Override
    public void FaileUserInfo(String error) {
        mBinding.mineName.setVisibility(View.GONE);
        mBinding.mineLogin.setVisibility(View.VISIBLE);
        mBinding.mineLevel.setVisibility(View.GONE);
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userLogin,false);
        Toast.makeText(getContext(), "用户信息更新失败，请检查网络", Toast.LENGTH_SHORT).show();
    }
}
