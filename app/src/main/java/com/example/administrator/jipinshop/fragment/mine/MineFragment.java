package com.example.administrator.jipinshop.fragment.mine;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.balance.MyWalletActivity;
import com.example.administrator.jipinshop.activity.balance.team.TeamActivity;
import com.example.administrator.jipinshop.activity.follow.FollowActivity;
import com.example.administrator.jipinshop.activity.foval.FovalActivity;
import com.example.administrator.jipinshop.activity.info.editname.EditNameActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.mall.MallActivity;
import com.example.administrator.jipinshop.activity.message.MessageActivity;
import com.example.administrator.jipinshop.activity.minekt.freekt.MineFreeActivity;
import com.example.administrator.jipinshop.activity.minekt.orderkt.KTMyOrderActivity;
import com.example.administrator.jipinshop.activity.minekt.publishkt.MyPublishActivity;
import com.example.administrator.jipinshop.activity.minekt.userkt.UserActivity;
import com.example.administrator.jipinshop.activity.setting.SettingActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationNewActivity;
import com.example.administrator.jipinshop.activity.tryout.mine.MineTrialActivity;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.MyWalletBean;
import com.example.administrator.jipinshop.bean.UnMessageBean;
import com.example.administrator.jipinshop.bean.UserInfoBean;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.bean.eventbus.FollowBus;
import com.example.administrator.jipinshop.databinding.FragmentMineBinding;
import com.example.administrator.jipinshop.fragment.follow.attention.AttentionFragment;
import com.example.administrator.jipinshop.fragment.follow.fans.FansFragment;
import com.example.administrator.jipinshop.jpush.JPushReceiver;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.UAppUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;

import javax.inject.Inject;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MineFragment extends DBBaseFragment implements View.OnClickListener, MineView, Badge.OnDragStateChangedListener {

    @Inject
    MinePresenter mPresenter;

    private FragmentMineBinding mBinding;

    private Boolean flage = true;//标记是第一次走入这个页面，防止多次访问接口
    private QBadgeView mQBadgeView;
    private String officialWeChat = "";//客服电话

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

        mPresenter.setStatusBarHight(mBinding, getContext());
        mPresenter.setView(this);
        mQBadgeView = new QBadgeView(getContext());
        mPresenter.initBadgeView(mQBadgeView, mBinding.mineMessageImg, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_login:
                //跳转到登陆页面
                startActivityForResult(new Intent(getContext(), LoginActivity.class), 100);
                return;
        }
        if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        switch (view.getId()) {
            case R.id.mine_info:
            case R.id.mine_name:
            case R.id.mine_image:
                //个人主页
                startActivity(new Intent(getContext(), UserActivity.class)
                        .putExtra("userid",SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId))
                );
                break;
            case R.id.mine_signOther:
                //邀请
                startActivity(new Intent(getContext(), InvitationNewActivity.class));
                UAppUtil.mine(getContext(),4);
                break;
            case R.id.mine_sign:
                //跳转到签到页面
                startActivity(new Intent(getContext(), SignActivity.class));
                UAppUtil.mine(getContext(),0);
                break;
            case R.id.mine_follow:
                //跳转到签到页面
                startActivity(new Intent(getContext(), SignActivity.class));
                UAppUtil.mine(getContext(),9);
                break;
            case R.id.mine_favor:
                //跳转到收藏页面
                startActivity(new Intent(getContext(), FovalActivity.class));
                UAppUtil.mine(getContext(),10);
                break;
            case R.id.mine_message:
                //跳转到消息页面
                startActivity(new Intent(getContext(), MessageActivity.class));
                UAppUtil.mine(getContext(),8);
                break;
            case R.id.mine_attention:
                //跳转到关注页面
                startActivity(new Intent(getContext(), FollowActivity.class)
                        .putExtra("page",0)
                );
                UAppUtil.mine(getContext(),1);
                break;
            case R.id.mine_fans:
                //跳转到粉丝页面
                startActivity(new Intent(getContext(), FollowActivity.class)
                        .putExtra("page",1)
                );
                UAppUtil.mine(getContext(),2);
                break;
//            case R.id.mine_goodsNum:
//                //点击点赞数
//                DialogUtil.MyGoods(getContext(),mBinding.mineName.getText().toString(),mBinding.mineGoodsNumText.getText().toString());
//                UAppUtil.mine(getContext(),3);
//                break;
            case R.id.mine_setting:
                //跳转到设置页面
                startActivityForResult(new Intent(getContext(), SettingActivity.class)
                                .putExtra("officialWeChat",officialWeChat)
                        , 100);
                UAppUtil.mine(getContext(),12);
                break;
            case R.id.mine_walletBottomContainer:
            case R.id.mine_wallet:
                //我的钱包
                startActivity(new Intent(getContext(), MyWalletActivity.class));
                UAppUtil.mine(getContext(),7);
                break;
            case R.id.mine_order:
                //我的订单
                startActivity(new Intent(getContext(), KTMyOrderActivity.class));
                break;
            case R.id.mine_shoppingMall:
                //我的商城
                startActivity(new Intent(getContext(), MallActivity.class));
                UAppUtil.mine(getContext(),5);
                break;
            case R.id.mine_trial:
                //我的试用
                startActivity(new Intent(getContext(), MineTrialActivity.class));
                break;
            case R.id.mine_free:
                //我的免单
                startActivity(new Intent(getContext(), MineFreeActivity.class));
                break;
            case R.id.mine_copy:
                //复制
                ClipboardManager clip = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("jipinshop", mBinding.mineIntegral.getText().toString().replace("邀请码：",""));
                clip.setPrimaryClip(clipData);
                ToastUtil.show("复制成功");
                UAppUtil.mine(getContext(),13);
                break;
            case R.id.mine_team:
                //我的团队
                startActivity(new Intent(getContext(), TeamActivity.class));
                break;
            case R.id.mine_releaseContainer:
                //我的发布
                startActivity(new Intent(getContext(), MyPublishActivity.class));
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
                mBinding.mineInfo.setVisibility(View.GONE);
                GlideApp.loderImage(getContext(),R.drawable.logo, mBinding.mineImage, 0, 0);
                mBinding.mineBackground.setImageResource(R.mipmap.mine_imagebg_dafult);
                mBinding.mineFavorNumText.setText("0");//收藏数
                mBinding.mineAttentionText.setText("0");//关注数
                mBinding.mineFansText.setText("0");//粉丝数
                mBinding.mineSignText.setText("0");//极币数
                mBinding.mineWithdrawal.setText("¥0");
                mBinding.mineImminent.setText("¥0");
                mBinding.mineTotal.setText("¥0");
                mBinding.mineWalletText.setText("共省¥0");
                SPUtils.getInstance(CommonDate.USER).clear();
//                JPushInterface.stopPush(MyApplication.getInstance());//停止推送
                mBinding.mineCopyContainer.setVisibility(View.GONE);//复制邀请码
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
        if (bus != null && bus.getTag().equals(EditNameActivity.tag)) {
            //修改用户信息时返回刷新
            if (bus.getType().equals("1")) {
                mBinding.mineName.setText(bus.getContent());
            } else if (bus.getType().equals("4")) {
                //修改用户头像
                GlideApp.loderImage(getContext(),bus.getContent(), mBinding.mineImage, R.mipmap.rlogo, 0);
            }
        } else if (bus != null && bus.getTag().equals(LoginActivity.tag)) {
            //登陆时返回更改用户信息
            mBinding.mineName.setVisibility(View.VISIBLE);
            mBinding.mineLogin.setVisibility(View.GONE);
            mBinding.mineInfo.setVisibility(View.VISIBLE);
            mBinding.mineCopyContainer.setVisibility(View.VISIBLE);//复制邀请码
            mBinding.mineIntegral.setText("邀请码：" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.qrCode,"000000"));
            if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName))) {
                mBinding.mineName.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userPhone));
            } else {
                mBinding.mineName.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName));
            }
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg))) {
                GlideApp.loderImage(getContext(),SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg), mBinding.mineImage, R.mipmap.logo, 0);
            }
        }else if(bus != null && bus.getTag().equals(SignActivity.eventbusTag)){
            //签到页面返回过来的信息——（极币数）
            mBinding.mineSignText.setText(SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint,0) + "");//极币数
        }else if (bus != null && bus.getTag().equals(AttentionFragment.refreshAttention)){
            mBinding.mineAttentionText.setText(bus.getCount());//关注数
        }else if (bus != null && bus.getTag().equals(FansFragment.refreshFans)){
            mBinding.mineFansText.setText(bus.getCount());//粉丝数
        }
    }

    /**
     * 刷新关注(商品详情与文章详情刷新关注数量)
     */
    @Subscribe
    public void refreshFans(FollowBus bus){
       if (bus != null && bus.getTag().equals(AttentionFragment.refreshAttention) && bus.getCount() != 0){
            //刷新关注
            BigDecimal bigDecimal = new BigDecimal(mBinding.mineAttentionText.getText().toString());
            mBinding.mineAttentionText.setText( bigDecimal.intValue() + bus.getCount() + "");
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
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.qrCode, userInfoBean.getData().getInvitationCode());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.relationId, userInfoBean.getData().getRelationId());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userId,userInfoBean.getData().getUserId());

        mBinding.mineName.setVisibility(View.VISIBLE);
        mBinding.mineLogin.setVisibility(View.GONE);
        mBinding.mineInfo.setVisibility(View.VISIBLE);
        mBinding.mineCopyContainer.setVisibility(View.VISIBLE);//复制邀请码
        mBinding.mineIntegral.setText("邀请码：" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.qrCode,"000000"));
        mBinding.mineFavorNumText.setText(userInfoBean.getData().getCollectCount());//收藏数
        mBinding.mineAttentionText.setText(userInfoBean.getData().getFollowCount());//关注数
        mBinding.mineFansText.setText(userInfoBean.getData().getFansCount());//粉丝数
        mBinding.mineSignText.setText(SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint,0) + "");//极币数
        mBinding.mineName.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName));
        if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg))) {
            GlideApp.loderImage(getContext(),SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg), mBinding.mineImage, R.mipmap.logo, 0);
        }
        if (TextUtils.isEmpty(userInfoBean.getData().getBgImg())){
            mBinding.mineBackground.setImageResource(R.mipmap.mine_imagebg_dafult);
        }else {
            GlideApp.loderImage(getContext(),userInfoBean.getData().getBgImg(),mBinding.mineBackground,0,0);
        }
        officialWeChat = userInfoBean.getData().getOfficialWeChat();
    }

    @Subscribe
    public  void  unMessage(String s){
        if(!TextUtils.isEmpty(s) && s.equals(JPushReceiver.TAG)){
            mPresenter.unMessage(this.bindToLifecycle());
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
            mBinding.mineInfo.setVisibility(View.GONE);
            GlideApp.loderImage(getContext(),R.drawable.logo, mBinding.mineImage, 0, 0);
            SPUtils.getInstance(CommonDate.USER).clear();
            mBinding.mineWithdrawal.setText("¥0");
            mBinding.mineImminent.setText("¥0");
            mBinding.mineTotal.setText("¥0");
            mBinding.mineWalletText.setText("共省¥0");
//            JPushInterface.stopPush(MyApplication.getInstance());//停止推送
            mBinding.mineCopyContainer.setVisibility(View.GONE);//复制邀请码
        }else {
            mBinding.mineName.setVisibility(View.VISIBLE);
            mBinding.mineLogin.setVisibility(View.GONE);
            mBinding.mineInfo.setVisibility(View.VISIBLE);
            mBinding.mineName.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName));
            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg))) {
                GlideApp.loderImage(getContext(),SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg), mBinding.mineImage, R.mipmap.logo, 0);
            }
            mBinding.mineCopyContainer.setVisibility(View.VISIBLE);//复制邀请码
            mBinding.mineIntegral.setText("邀请码：" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.qrCode,"000000"));
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userMemberGrade, "1");
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint, 0);
        }
        mBinding.mineFavorNumText.setText("0");//收藏数
        mBinding.mineAttentionText.setText("0");//关注数
        mBinding.mineFansText.setText("0");//粉丝数
        mBinding.mineSignText.setText("0");//极币数
        ToastUtil.show(error.getMsg());
    }

    /**
     * 用于更新用户接口
     */
    @Override
    public void successUpdateInfo(UserInfoBean userInfoBean) {
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint, userInfoBean.getData().getPoint());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.relationId, userInfoBean.getData().getRelationId());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userId,userInfoBean.getData().getUserId());
        mBinding.mineFavorNumText.setText(userInfoBean.getData().getCollectCount());//收藏数
        mBinding.mineAttentionText.setText(userInfoBean.getData().getFollowCount());//关注数
        mBinding.mineFansText.setText(userInfoBean.getData().getFansCount());//粉丝数
        mBinding.mineSignText.setText(SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint,0) + "");//极币数
        if (TextUtils.isEmpty(userInfoBean.getData().getBgImg())){//更新用户资料背景
            mBinding.mineBackground.setImageResource(R.mipmap.mine_imagebg_dafult);
        }else {
            GlideApp.loderImage(getContext(),userInfoBean.getData().getBgImg(),mBinding.mineBackground,0,0);
        }
        officialWeChat = userInfoBean.getData().getOfficialWeChat();
    }

    @Override
    public void unMessageSuc(UnMessageBean unMessageBean) {
        if(unMessageBean.getWalletCount() != 0) {
            if (unMessageBean.getWalletCount() <= 99) {
                mQBadgeView.setBadgeText("" + unMessageBean.getData());
            } else {
                mQBadgeView.setBadgeText("99+");
            }
        }else {
            mQBadgeView.hide(false);
        }
    }

    @Override
    public void unMessageFaile(String error) {
        ToastUtil.show(error);
    }

    @Override
    public void onSuccess(MyWalletBean bean) {
        mBinding.mineWithdrawal.setText("¥" + bean.getData().getWithdraw());
        mBinding.mineImminent.setText("¥" + bean.getData().getPreFee());
        mBinding.mineTotal.setText("¥" + bean.getData().getFinalFee());
        mBinding.mineWalletText.setText("共省¥" + bean.getData().getTotalFee());
    }

    @Override
    public void onFile(String error) {
        ToastUtil.show(error);
    }

    @Override
    public void onResume() {
        super.onResume();
        //当页面关闭后，返回app首页就会走该方法，第一次进入这个方法的时候也会走
        if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,"").trim())) {
            if(flage){
                //这里是判断该手机是否有账户登陆过。如果有userId不会为空，除非没有用户登录或已经退出登陆
                mPresenter.modelUser(this.bindToLifecycle());
                flage = false;
            }else {
                mPresenter.updateInfo(this.bindToLifecycle());
            }
            mPresenter.myCommssionSummary(this.bindToLifecycle());//获取本人佣金
        }
        mPresenter.unMessage(this.bindToLifecycle());
    }

    @Override
    public void onDragStateChanged(int dragState, Badge badge, View targetView) {

    }
}
