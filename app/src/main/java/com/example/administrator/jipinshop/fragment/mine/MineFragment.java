package com.example.administrator.jipinshop.fragment.mine;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.follow.FollowActivity;
import com.example.administrator.jipinshop.activity.foval.FovalActivity;
import com.example.administrator.jipinshop.activity.info.MyInfoActivity;
import com.example.administrator.jipinshop.activity.info.editname.EditNameActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.message.MessageActivity;
import com.example.administrator.jipinshop.activity.setting.SettingActivity;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.UserInfoBean;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.databinding.FragmentMineBinding;
import com.example.administrator.jipinshop.fragment.home.HomeFragment;
import com.example.administrator.jipinshop.jpush.JPushReceiver;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;

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
        if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,"").trim())) {
            //这里是判断该手机是否有账户登陆过。如果有userId不会为空，除非没有用户登录或已经退出登陆
            mPresenter.modelUser(this.bindToLifecycle());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_name:
            case R.id.mine_image:
                //我的资料
                startActivityForResult(new Intent(getContext(), MyInfoActivity.class), 100);
                break;
            case R.id.mine_sign:
            case R.id.mine_follow:
                //跳转到签到页面

                break;
            case R.id.mine_favor:
                //跳转到收藏页面
                startActivity(new Intent(getContext(), FovalActivity.class));
                break;
            case R.id.mine_message:
                //跳转到消息页面
                startActivity(new Intent(getContext(), MessageActivity.class));
                break;
            case R.id.mine_attention:
                //跳转到关注页面
                startActivity(new Intent(getContext(), FollowActivity.class));
                break;
            case R.id.mine_fans:
                //跳转到粉丝页面
                startActivity(new Intent(getContext(), FollowActivity.class));
                break;
            case R.id.mine_goodsNum:
                //点击点赞数

                break;
            case R.id.mine_setting:
                //跳转到设置页面
                startActivityForResult(new Intent(getContext(), SettingActivity.class), 100);
                break;
            case R.id.mine_login:
                //跳转到登陆页面
                startActivityForResult(new Intent(getContext(), LoginActivity.class), 100);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 201://退出登陆成功
                mBinding.mineName.setVisibility(View.GONE);
                mBinding.mineLogin.setVisibility(View.VISIBLE);
                GlideApp.loderImage(getContext(),R.drawable.logo, mBinding.mineImage, 0, 0);
                mBinding.mineGoodsNumText.setText("0");//点赞数
                mBinding.mineAttentionText.setText("0");//关注数
                mBinding.mineFansText.setText("0");//粉丝数
                mBinding.mineSignText.setText("0");//极币数
                SPUtils.getInstance(CommonDate.USER).clear();
                EventBus.getDefault().post(JPushReceiver.TAG);//刷新未读消息
                JPushInterface.stopPush(MyApplication.getInstance());//停止推送
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void eidtInfo(EditNameBus bus) {
        if (bus.getTag().equals(EditNameActivity.tag)) {
            //修改用户信息时返回刷新
            if (bus.getType().equals("1")) {
                mBinding.mineName.setText(bus.getContent());
            } else if (bus.getType().equals("4")) {
                //修改用户头像
                GlideApp.loderImage(getContext(),bus.getContent(), mBinding.mineImage, R.mipmap.rlogo, 0);
            }
        } else if (bus.getTag().equals(LoginActivity.tag)) {
            //登陆时返回更改用户信息
            mBinding.mineName.setVisibility(View.VISIBLE);
            mBinding.mineLogin.setVisibility(View.GONE);
            if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName))) {
                mBinding.mineName.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userPhone));
            } else {
                mBinding.mineName.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName));
            }
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg))) {
                GlideApp.loderImage(getContext(),SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg), mBinding.mineImage, R.mipmap.logo, 0);
            }
            mBinding.mineGoodsNumText.setText(bus.getVoteCount());//点赞数
            mBinding.mineAttentionText.setText(bus.getFollowCount());//关注数
            mBinding.mineFansText.setText(bus.getFansCount());//粉丝数
            mBinding.mineSignText.setText(SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint,0) + "");//极币数
        }else if (bus.getTag().equals(HomeFragment.MsgRefersh)){
            if(bus.getCount().equals("0")){
                mBinding.mineMsgNumber.setVisibility(View.GONE);
            }else {
                mBinding.mineMsgNumber.setVisibility(View.VISIBLE);
                mBinding.mineMsgNumber.setText(bus.getCount());
            }
        }
    }

    /**
     * 获取用户信息
     * 用来刷新用户信息
     *
     * @param userInfoBean
     */
    @Override
    public void successUserInfo(UserInfoBean userInfoBean) {

        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userBirthday, userInfoBean.getData().getBirthday());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userGender, userInfoBean.getData().getGender());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userNickImg, userInfoBean.getData().getAvatar());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userNickName, userInfoBean.getData().getNickname());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPhone, userInfoBean.getData().getMobile());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userMemberGrade, userInfoBean.getData().getRole() + "");
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint, userInfoBean.getData().getPoint());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindMobile, userInfoBean.getData().getBindMobile() + "");
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindWeibo, userInfoBean.getData().getBindWeibo() + "");
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindWeixin, userInfoBean.getData().getBindWeixin() + "");

        mBinding.mineName.setVisibility(View.VISIBLE);
        mBinding.mineLogin.setVisibility(View.GONE);
        mBinding.mineGoodsNumText.setText(userInfoBean.getData().getVoteCount());//点赞数
        mBinding.mineAttentionText.setText(userInfoBean.getData().getFollowCount());//关注数
        mBinding.mineFansText.setText(userInfoBean.getData().getFansCount());//粉丝数
        mBinding.mineSignText.setText(SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint,0) + "");//极币数
        mBinding.mineName.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName));
        if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg))) {
            GlideApp.loderImage(getContext(),SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg), mBinding.mineImage, R.mipmap.logo, 0);
        }
    }

    /**
     * 获取用户信息失败
     *
     * @param error
     */
    @Override
    public void FaileUserInfo(UserInfoBean error) {
        //获取用户信息失败 走退出登陆的逻辑
        if(error.getCode() == 602){
            //token失效
            mBinding.mineName.setVisibility(View.GONE);
            mBinding.mineLogin.setVisibility(View.VISIBLE);
            GlideApp.loderImage(getContext(),R.drawable.logo, mBinding.mineImage, 0, 0);
            SPUtils.getInstance(CommonDate.USER).clear();
            JPushInterface.stopPush(MyApplication.getInstance());//停止推送
        }else {
            mBinding.mineName.setVisibility(View.VISIBLE);
            mBinding.mineLogin.setVisibility(View.GONE);
            mBinding.mineName.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName));
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg))) {
                GlideApp.loderImage(getContext(),SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg), mBinding.mineImage, R.mipmap.logo, 0);
            }
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userMemberGrade, "1");
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint, 0);
        }
        mBinding.mineGoodsNumText.setText("0");//点赞数
        mBinding.mineAttentionText.setText("0");//关注数
        mBinding.mineFansText.setText("0");//粉丝数
        mBinding.mineSignText.setText("0");//极币数
        ToastUtil.show(error.getMsg());
    }
}
