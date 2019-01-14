package com.example.administrator.jipinshop.base;

import com.example.administrator.jipinshop.activity.balance.BalanceActivity;
import com.example.administrator.jipinshop.activity.balance.boundalipay.BoundAlipayActivity;
import com.example.administrator.jipinshop.activity.balance.record.RecordActivity;
import com.example.administrator.jipinshop.activity.balance.score.ScoreActivity;
import com.example.administrator.jipinshop.activity.commenlist.CommenListActivity;
import com.example.administrator.jipinshop.activity.follow.FollowActivity;
import com.example.administrator.jipinshop.activity.follow.user.UserActivity;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.activity.home.article.ArticleDetailActivity;
import com.example.administrator.jipinshop.activity.info.MyInfoActivity;
import com.example.administrator.jipinshop.activity.info.bind.BindNumberActivity;
import com.example.administrator.jipinshop.activity.info.editname.EditNameActivity;
import com.example.administrator.jipinshop.activity.info.member.MemberLevelActivity;
import com.example.administrator.jipinshop.activity.integral.IntegralActivity;
import com.example.administrator.jipinshop.activity.integral.detail.IntegralDetailActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.message.MessageActivity;
import com.example.administrator.jipinshop.activity.message.detail.MsgDetailActivity;
import com.example.administrator.jipinshop.activity.setting.SettingActivity;
import com.example.administrator.jipinshop.activity.setting.opinion.OpinionActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
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
    void inject(IntegralActivity activity);
    void inject(IntegralDetailActivity activity);
    void inject(BalanceActivity activity);
    void inject(RecordActivity activity);
    void inject(ScoreActivity activity);
    void inject(BoundAlipayActivity activity);
    void inject(SreachResultActivity activity);
    void inject(ArticleDetailActivity activity);
    void inject(SettingActivity settingActivity);
    void inject(MyInfoActivity activity);
    void inject(EditNameActivity activity);
    void inject(SignActivity activity);
    void inject(MsgDetailActivity activity);
    void inject(MemberLevelActivity activity);
}
