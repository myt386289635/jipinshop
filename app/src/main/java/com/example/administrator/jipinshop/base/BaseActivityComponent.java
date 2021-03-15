package com.example.administrator.jipinshop.base;

import com.example.administrator.jipinshop.activity.KTArticleMoreActivity;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.address.MyAddressActivity;
import com.example.administrator.jipinshop.activity.address.add.CreateAddressActivity;
import com.example.administrator.jipinshop.activity.balance.MyWalletActivity;
import com.example.administrator.jipinshop.activity.balance.detail.WalletDetailActivity;
import com.example.administrator.jipinshop.activity.balance.history.WalletHistoryActivity;
import com.example.administrator.jipinshop.activity.balance.team.TeamActivity;
import com.example.administrator.jipinshop.activity.balance.withdraw.WithdrawActivity;
import com.example.administrator.jipinshop.activity.cheapgoods.record.AllowanceRecordActivity;
import com.example.administrator.jipinshop.activity.commenlist.CommenListActivity;
import com.example.administrator.jipinshop.activity.evakt.comparison.ComparActivity;
import com.example.administrator.jipinshop.activity.evakt.send.SubmitActivity;
import com.example.administrator.jipinshop.activity.evakt.send.corve.SubmitCorveActivity;
import com.example.administrator.jipinshop.activity.evakt.send.goods.AddGoodsActivity;
import com.example.administrator.jipinshop.activity.evakt.unbox.UnboxActivity;
import com.example.administrator.jipinshop.activity.follow.FollowActivity;
import com.example.administrator.jipinshop.activity.home.HomeDetailActivity;
import com.example.administrator.jipinshop.activity.home.HomeTabActivity;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.activity.home.article.ArticleDetailActivity;
import com.example.administrator.jipinshop.activity.home.classification.ClassifyActivity;
import com.example.administrator.jipinshop.activity.home.classification.article.ArticleMoreActivity;
import com.example.administrator.jipinshop.activity.home.classification.encyclopedias.EncyclopediasActivity;
import com.example.administrator.jipinshop.activity.home.classification.encyclopedias.detail.EncyclopediasDetailActivity;
import com.example.administrator.jipinshop.activity.home.classification.questions.QuestionsActivity;
import com.example.administrator.jipinshop.activity.home.classification.questions.detail.QuestionDetailActivity;
import com.example.administrator.jipinshop.activity.home.classification.questions.submit.QuestionSubmitActivity;
import com.example.administrator.jipinshop.activity.home.food.TakeOutActivity;
import com.example.administrator.jipinshop.activity.home.jd_pdd.KTJDDetailActivity;
import com.example.administrator.jipinshop.activity.home.newGift.NewGiftActivity;
import com.example.administrator.jipinshop.activity.home.newarea.NewAreaActivity;
import com.example.administrator.jipinshop.activity.home.recharge.RechargeActivity;
import com.example.administrator.jipinshop.activity.home.seckill.detail.SeckillDetailActivity;
import com.example.administrator.jipinshop.activity.home.tb.KTTBDetailActivity;
import com.example.administrator.jipinshop.activity.info.MyInfoActivity;
import com.example.administrator.jipinshop.activity.info.account.AccountManageActivity;
import com.example.administrator.jipinshop.activity.info.account.change.ChangePhone2Activity;
import com.example.administrator.jipinshop.activity.info.account.change.ChangePhoneActivity;
import com.example.administrator.jipinshop.activity.info.editname.EditNameActivity;
import com.example.administrator.jipinshop.activity.info.editsign.EditSignActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.login.bind.BindNumberActivity;
import com.example.administrator.jipinshop.activity.login.input.InputLoginActivity;
import com.example.administrator.jipinshop.activity.mall.MallActivity;
import com.example.administrator.jipinshop.activity.mall.detail.MallDetailActivity;
import com.example.administrator.jipinshop.activity.mall.exchange.ExchangeActivity;
import com.example.administrator.jipinshop.activity.mall.order.MyOrderActivity;
import com.example.administrator.jipinshop.activity.mall.order.detail.OrderDetailActivity;
import com.example.administrator.jipinshop.activity.member.buy.MemberBuyActivity;
import com.example.administrator.jipinshop.activity.member.family.FamilyActivity;
import com.example.administrator.jipinshop.activity.member.zero.ZeroActivity;
import com.example.administrator.jipinshop.activity.member.zero.detail.ZeroDetailActivity;
import com.example.administrator.jipinshop.activity.message.MessageActivity;
import com.example.administrator.jipinshop.activity.message.detail.MsgDetailActivity;
import com.example.administrator.jipinshop.activity.mine.browse.BrowseActivity;
import com.example.administrator.jipinshop.activity.mine.group.MyGroupActivity;
import com.example.administrator.jipinshop.activity.mine.welfare.OfficialWelfareActivity;
import com.example.administrator.jipinshop.activity.minekt.publishkt.detail.AuditDetailActivity;
import com.example.administrator.jipinshop.activity.minekt.recovery.OrderRecoveryActivity;
import com.example.administrator.jipinshop.activity.minekt.recovery.OrderRecoveryResultActivity;
import com.example.administrator.jipinshop.activity.minekt.userkt.UserActivity;
import com.example.administrator.jipinshop.activity.minekt.welfare.WelfareActivity;
import com.example.administrator.jipinshop.activity.money.binding.MoneyBindActivity;
import com.example.administrator.jipinshop.activity.money.record.MoneyRecordActivity;
import com.example.administrator.jipinshop.activity.money.withdraw.MoneyWithdrawActivity;
import com.example.administrator.jipinshop.activity.newpeople.NewFreeActivity;
import com.example.administrator.jipinshop.activity.newpeople.cheap.CheapBuyDetailActivity;
import com.example.administrator.jipinshop.activity.newpeople.detail.NewFreeDetailActivity;
import com.example.administrator.jipinshop.activity.report.cover.CoverReportActivity;
import com.example.administrator.jipinshop.activity.report.create.CreateReportActivity;
import com.example.administrator.jipinshop.activity.report.detail.ReportDetailActivity;
import com.example.administrator.jipinshop.activity.school.SchoolSpecialActivity;
import com.example.administrator.jipinshop.activity.school.search.SchoolSearchActivity;
import com.example.administrator.jipinshop.activity.school.search.result.SchoolResultActivity;
import com.example.administrator.jipinshop.activity.school.video.VideoActivity;
import com.example.administrator.jipinshop.activity.setting.SettingActivity;
import com.example.administrator.jipinshop.activity.setting.about.AboutActivity;
import com.example.administrator.jipinshop.activity.setting.about.cancellation.VerificationActivity;
import com.example.administrator.jipinshop.activity.setting.bind.BindWXActivity;
import com.example.administrator.jipinshop.activity.setting.opinion.OpinionActivity;
import com.example.administrator.jipinshop.activity.share.ShareActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.activity.sign.detail.IntegralDetailActivity;
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationActivity;
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationNewActivity;
import com.example.administrator.jipinshop.activity.sign.market.MarketActivity;
import com.example.administrator.jipinshop.activity.sreach.SreachActivity;
import com.example.administrator.jipinshop.activity.sreach.TBSreachActivity;
import com.example.administrator.jipinshop.activity.sreach.play.PlaySreachActivity;
import com.example.administrator.jipinshop.activity.sreach.play.result.PlaySreachResultActivity;
import com.example.administrator.jipinshop.activity.sreach.result.SreachResultActivity;
import com.example.administrator.jipinshop.activity.sreach.result.TBSreachResultActivity;
import com.example.administrator.jipinshop.activity.web.TaoBaoWebActivity;
import com.example.administrator.jipinshop.activity.web.dzp.BigWheelWebActivity;
import com.example.administrator.jipinshop.activity.web.hb.HBWebView2;
import com.example.administrator.jipinshop.activity.web.invite.InviteActionWebActivity;
import com.example.administrator.jipinshop.activity.web.tuanyou.CZBWebActivity;
import com.example.administrator.jipinshop.activity.wellcome.WellComeActivity;
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
    void inject(InvitationActivity activity);
    void inject(OrderDetailActivity activity);
    void inject(CreateReportActivity activity);
    void inject(CoverReportActivity activity);
    void inject(ReportDetailActivity activity);
    void inject(WithdrawActivity activity);
    void inject(TeamActivity activity);
    void inject(InvitationNewActivity activity);
    void inject(WebActivity activity);
    void inject(NewAreaActivity activity);
    void inject(ArticleMoreActivity activity);
    void inject(QuestionsActivity activity);
    void inject(QuestionDetailActivity activity);
    void inject(ClassifyActivity activity);
    void inject(QuestionSubmitActivity activity);
    void inject(EncyclopediasActivity activity);
    void inject(EncyclopediasDetailActivity activity);
    void inject(KTArticleMoreActivity activity);
    void inject(UnboxActivity activity);
    void inject(ComparActivity activity);
    void inject(SubmitActivity activity);
    void inject(SubmitCorveActivity activity);
    void inject(AddGoodsActivity activity);
    void inject(UserActivity activity);
    void inject(EditSignActivity activity);
    void inject(AuditDetailActivity activity);
    void inject(TaoBaoWebActivity activity);
    void inject(CheapBuyDetailActivity activity);
    void inject(TBShoppingDetailActivity activity);
    void inject(TBSreachActivity activity);
    void inject(TBSreachResultActivity activity);
    void inject(HomeDetailActivity activity);
    void inject(HomeTabActivity activity);
    void inject(AllowanceRecordActivity activity);
    void inject(WelfareActivity activity);
    void inject(MoneyWithdrawActivity activity);
    void inject(MoneyRecordActivity activity);
    void inject(MoneyBindActivity activity);
    void inject(ShareActivity activity);
    void inject(BindWXActivity activity);
    void inject(KTJDDetailActivity activity);
    void inject(InputLoginActivity activity);
    void inject(AboutActivity activity);
    void inject(VerificationActivity activity);
    void inject(NewFreeActivity activity);
    void inject(NewFreeDetailActivity activity);
    void inject(HBWebView2 activity);
    void inject(WalletDetailActivity activity);
    void inject(WalletHistoryActivity activity);
    void inject(KTTBDetailActivity activity);
    void inject(BigWheelWebActivity activity);
    void inject(MarketActivity activity);
    void inject(SchoolSpecialActivity activity);
    void inject(VideoActivity activity);
    void inject(SchoolSearchActivity activity);
    void inject(SchoolResultActivity activity);
    void inject(OrderRecoveryActivity activity);
    void inject(OrderRecoveryResultActivity activity);
    void inject(WellComeActivity activity);
    void inject(FamilyActivity activity);
    void inject(ZeroActivity activity);
    void inject(ZeroDetailActivity activity);
    void inject(InviteActionWebActivity activity);
    void inject(BrowseActivity activity);
    void inject(OfficialWelfareActivity activity);
    void inject(PlaySreachActivity activity);
    void inject(PlaySreachResultActivity activity);
    void inject(MyGroupActivity activity);
    void inject(MemberBuyActivity activity);
    void inject(CZBWebActivity activity);
    void inject(SeckillDetailActivity activity);
    void inject(RechargeActivity activity);
    void inject(NewGiftActivity activity);
}
