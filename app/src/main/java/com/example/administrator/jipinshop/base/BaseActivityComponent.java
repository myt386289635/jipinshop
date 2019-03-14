package com.example.administrator.jipinshop.base;

import com.example.administrator.jipinshop.activity.address.MyAddressActivity;
import com.example.administrator.jipinshop.activity.address.add.CreateAddressActivity;
import com.example.administrator.jipinshop.activity.balance.MyWalletActivity;
import com.example.administrator.jipinshop.activity.commenlist.CommenListActivity;
import com.example.administrator.jipinshop.activity.follow.FollowActivity;
import com.example.administrator.jipinshop.activity.follow.user.UserActivity;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.activity.home.article.ArticleDetailActivity;
import com.example.administrator.jipinshop.activity.info.MyInfoActivity;
import com.example.administrator.jipinshop.activity.info.account.AccountManageActivity;
import com.example.administrator.jipinshop.activity.info.account.change.ChangePhone2Activity;
import com.example.administrator.jipinshop.activity.info.account.change.ChangePhoneActivity;
import com.example.administrator.jipinshop.activity.info.bind.BindNumberActivity;
import com.example.administrator.jipinshop.activity.info.editname.EditNameActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.mall.MallActivity;
import com.example.administrator.jipinshop.activity.mall.detail.MallDetailActivity;
import com.example.administrator.jipinshop.activity.mall.exchange.ExchangeActivity;
import com.example.administrator.jipinshop.activity.message.MessageActivity;
import com.example.administrator.jipinshop.activity.message.detail.MsgDetailActivity;
import com.example.administrator.jipinshop.activity.order.MyOrderActivity;
import com.example.administrator.jipinshop.activity.setting.SettingActivity;
import com.example.administrator.jipinshop.activity.setting.opinion.OpinionActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.activity.sign.detail.IntegralDetailActivity;
import com.example.administrator.jipinshop.activity.sreach.SreachActivity;
import com.example.administrator.jipinshop.activity.sreach.result.SreachResultActivity;
import com.example.administrator.jipinshop.auto.ActivityScope;
import com.example.administrator.jipinshop.netwrok.ApplicationComponent;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class)
public interface BaseActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(SreachActivity sreachActivity);
    void inject(ShoppingDetailActivity shoppingDetailActivity);
    void inject(CommenListActivity commenListActivity);
    void inject(LoginActivity loginActivity);
    void inject(BindNumberActivity bindNumberActivity);
    void inject(MessageActivity messageActivity);
    void inject(OpinionActivity activity);
    void inject(FollowActivity activity);
    void inject(UserActivity activity);
    void inject(IntegralDetailActivity activity);
    void inject(SreachResultActivity activity);
    void inject(ArticleDetailActivity activity);
    void inject(SettingActivity settingActivity);
    void inject(MyInfoActivity activity);
    void inject(EditNameActivity activity);
    void inject(SignActivity activity);
    void inject(MsgDetailActivity activity);
    void inject(AccountManageActivity activity);
    void inject(ChangePhoneActivity activity);
    void inject(ChangePhone2Activity activity);
    void inject(MyAddressActivity activity);
    void inject(CreateAddressActivity activity);
    void inject(MyWalletActivity activity);
    void inject(MyOrderActivity activity);
    void inject(MallActivity activity);
    void inject(MallDetailActivity activity);
    void inject(ExchangeActivity activity);
}
